package com.example.simpleroomretroapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.simpleroomretroapp.R
import com.example.simpleroomretroapp.databinding.FragmentUserDetailsBinding
import com.example.simpleroomretroapp.models.UserData

class UserDetailsFragment : Fragment() {
    lateinit var currentUser: UserData
    var binding: FragmentUserDetailsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(layoutInflater, container, false)
        currentUser = arguments?.getParcelable("user")!!
        setupButtons()
        setupView()
        return binding!!.root
    }

    private fun setupView() {
        Glide.with(requireContext())
            .load(currentUser.avatar)
            .into(binding!!.ivAvatar)
        binding!!.tvEmail.text = currentUser.email
        binding!!.tvFirstName.text = currentUser.firstName
        binding!!.tvLastName.text = currentUser.lastName
        binding!!.tvId.text = currentUser.id.toString()

    }

    private fun setupButtons() {
        binding!!.buttonReturn.setOnClickListener {
            findNavController().navigate(R.id.action_userDetailsFragment_to_userListFragment)
        }
        binding!!.buttonEdit.setOnClickListener {
            val action = UserDetailsFragmentDirections.actionUserDetailsFragmentToEditUserFragment(currentUser)
            findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}