package com.example.simpleroomretroapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleroomretroapp.adapters.UserRecyclerAdapter
import com.example.simpleroomretroapp.databinding.FragmentUserListBinding
import com.example.simpleroomretroapp.models.UserData
import com.example.simpleroomretroapp.utilities.OnUserClickListener
import com.example.simpleroomretroapp.utilities.SwipeToDeleteCallback
import com.example.simpleroomretroapp.view_models.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment(), OnUserClickListener {
    var binding: FragmentUserListBinding? = null
    val mainViewModel: MainViewModel by viewModels()
    lateinit var adapter: UserRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(layoutInflater, container, false)
        setupRecyclerView()
        loadData(container)
        setupRefreshFab()
        setupClearDbFab()
        return binding!!.root
    }

    private fun loadData(container: ViewGroup?) {
        //if true -> means we just returned from edit screen and updated user info
        if (requireArguments().getBoolean("localUserUpdated")) {
            showSnackBarMessage(container!!, "User changes saved")
            loadDataFromDb()
        } else {
            requestApiData()
        }
    }

    private fun showSnackBarMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("OKAY") {}.show()
    }


    private fun requestApiData() {
        Log.d("UserListFragment", "requestApiData INFO sending api request to get user list")
        mainViewModel.getRemoteUserList()
        mainViewModel.usersNetResponse.observe(viewLifecycleOwner, { userList ->
            if (userList.isNotEmpty()) {
                Log.d(
                    "UserListFragment",
                    "requestApiData INFO liveData result: ${userList.toString()}"
                )
                adapter.setData(userList)
                initOnErrorMessageDisplay(false)
            } else {
                Log.d(
                    "UserListFragment",
                    "requestApiData INFO liveData userList is empty, attempting to load users from db"
                )
            }
        })
    }

    private fun setupRefreshFab() {
        binding!!.fab.setOnClickListener {
            Log.d("UserListFragment", "setupRefreshFab INFO refresh btn clicked")
            requestApiData()
        }
    }

    private fun setupClearDbFab() {
        binding!!.fabClearDb.setOnClickListener {
            Log.d("UserListFragment", "setupClearDbFab INFO clearDB btn clicked")
            mainViewModel.deleteAllLocalUsers()
        }
    }

    private fun loadDataFromDb() {
        Log.d("UserListFragment", "loadDataFromDb INFO loading users from DB")
        mainViewModel.getLocalUsers()
        mainViewModel.savedUsers.observe(viewLifecycleOwner, { userList ->
            if (userList.isNotEmpty()) {
                Log.d(
                    "UserListFragment",
                    "loadDataFromDb INFO liveData result: ${userList.toString()}"
                )
                adapter.setData(userList)
                initOnErrorMessageDisplay(false)
            } else {
                Log.d("UserListFragment", "loadDataFromDb INFO liveData userList is empty")
                initOnErrorMessageDisplay(true)
            }
        })
    }

    private fun initOnErrorMessageDisplay(haveErrors: Boolean) {
        if (haveErrors) {
            binding!!.rv.visibility = View.INVISIBLE
            binding!!.llError.visibility = View.VISIBLE
        } else {
            binding!!.rv.visibility = View.VISIBLE
            binding!!.llError.visibility = View.INVISIBLE
        }
    }

    private fun setupRecyclerView() {
        adapter = UserRecyclerAdapter(this)
        binding!!.rv.adapter = adapter
        binding!!.rv.layoutManager = LinearLayoutManager(requireContext())
        setupSwipeToDelete()
    }

    private fun setupSwipeToDelete() {
        //setup delete action
        val deleteHandler = object: SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                removeItem(adapter.getItemByPosition(viewHolder.adapterPosition))
            }
        }
        val deleteItemTouchHelper = ItemTouchHelper(deleteHandler)
        deleteItemTouchHelper.attachToRecyclerView(binding!!.rv)
    }

    private fun removeItem(userData: UserData) {
        mainViewModel.deleteLocalUser(userData)
        showSnackBarMessage(binding!!.root, "Successfully deleted user")
        loadDataFromDb()
    }

    override fun onUserClick(userData: UserData) {
        val action =
            UserListFragmentDirections.actionUserListFragmentToUserDetailsFragment(userData)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}