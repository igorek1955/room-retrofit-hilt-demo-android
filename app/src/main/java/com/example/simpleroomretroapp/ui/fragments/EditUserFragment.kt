package com.example.simpleroomretroapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.simpleroomretroapp.R
import com.example.simpleroomretroapp.databinding.FragmentEditUserBinding
import com.example.simpleroomretroapp.models.UserData
import com.example.simpleroomretroapp.view_models.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditUserFragment : Fragment() {

    val mainViewModel: MainViewModel by viewModels()
    lateinit var currentUser: UserData
    var binding: FragmentEditUserBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditUserBinding.inflate(layoutInflater, container, false)
        currentUser = arguments?.getParcelable("user")!!
        setupButtons()
        setupView()
        return binding!!.root
    }

    private fun setupView() {
        binding!!.etEmail.setText(currentUser.email)
        binding!!.etFirst.setText(currentUser.firstName)
        binding!!.etLast.setText(currentUser.lastName)
    }

    private fun setupButtons() {
        binding!!.buttonCancel.setOnClickListener {
            findNavController().navigate(R.id.action_editUserFragment_to_userListFragment)
        }
        binding!!.buttonSave.setOnClickListener {
            when {
                binding!!.etEmail.text.isNullOrEmpty() -> notify("email cannot be empty")
                binding!!.etFirst.text.isNullOrEmpty() -> notify("first name must be specified")
                binding!!.etLast.text.isNullOrEmpty() -> notify("last name must be specified")
                else -> {
                    saveUser()
                    val action = EditUserFragmentDirections.actionEditUserFragmentToUserListFragment(null, true)
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun notify(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun saveUser() {
        val editedUser = UserData(
            currentUser.avatar,
            binding!!.etEmail.text.toString(),
            binding!!.etFirst.text.toString(),
            currentUser.id,
            binding!!.etLast.text.toString()
        )
       if (editedUser != currentUser) {
           mainViewModel.updateUser(editedUser)
       }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}