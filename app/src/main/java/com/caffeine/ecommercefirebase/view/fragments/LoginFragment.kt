package com.caffeine.ecommercefirebase.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.caffeine.ecommercefirebase.R
import com.caffeine.ecommercefirebase.databinding.FragmentLoginBinding
import com.caffeine.ecommercefirebase.helper.AlertDialog
import com.caffeine.ecommercefirebase.util.DataState
import com.caffeine.ecommercefirebase.view.activities.DashboardActivity
import com.caffeine.ecommercefirebase.viewmodel.AuthViewModel

class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private val authViewModel : AuthViewModel by viewModels()
    private lateinit var email : String
    private lateinit var password : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        authViewModel.userMutableLiveData.observe(viewLifecycleOwner) {
            when(it){
                is DataState.Loading -> {
                    binding.authBtn.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    //loading
                }

                is DataState.Success -> {
                    binding.authBtn.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE

                    context?.startActivity(Intent(context, DashboardActivity::class.java))
                    activity?.finish()
                    //success
                }

                is DataState.Failed -> {
                    binding.authBtn.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    AlertDialog.getInstance(requireContext()).showAlertDialog(it.message!!, "Close")
                }
            }
        }

        binding.lsBtn.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.login_to_sign_up)
        }

        binding.authBtn.setOnClickListener{
            initialize()
            if (validate()){
                authViewModel.loginUser(email, password)
            }
        }

        return binding.root
    }

    private fun initialize(){
        email = binding.email.text.toString()
        password = binding.password.text.toString()
    }

    private fun validate() : Boolean{
        var v = true
        if (email.isEmpty()){
            AlertDialog.getInstance(requireContext()).showAlertDialog("Hey, it looks you haven't define your email", "Close")
            v = false
        }

        else if (password.isEmpty()){
            AlertDialog.getInstance(requireContext()).showAlertDialog("Hey, it looks you haven't define your password", "Close")
            v = false
        }
        return v;
    }
}