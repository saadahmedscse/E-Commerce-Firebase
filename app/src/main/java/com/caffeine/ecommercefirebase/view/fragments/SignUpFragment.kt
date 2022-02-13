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
import com.caffeine.ecommercefirebase.databinding.FragmentSignUpBinding
import com.caffeine.ecommercefirebase.helper.AlertDialog
import com.caffeine.ecommercefirebase.util.Task
import com.caffeine.ecommercefirebase.view.activities.DashboardActivity
import com.caffeine.ecommercefirebase.viewmodel.AuthViewModel

class SignUpFragment : Fragment() {

    private lateinit var binding : FragmentSignUpBinding
    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var email : String
    private lateinit var password : String
    private lateinit var confirmPassword : String
    private val EMAIL_PATTERN : String = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        authViewModel.userMutableLiveData.observe(viewLifecycleOwner) {
            when(it){
                is Task.Loading -> {
                    binding.authBtn.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    //loading
                }

                is Task.Success -> {
                    binding.authBtn.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE

                    context?.startActivity(Intent(context, DashboardActivity::class.java))
                    activity?.finish()
                    //success
                }

                is Task.Failed -> {
                    binding.authBtn.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    AlertDialog.getInstance(requireContext()).showAlertDialog(it.message!!, "Close")
                }
            }
        }

        binding.lsBtn.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.sign_up_to_login)
        }

        binding.authBtn.setOnClickListener{
            initialize()
            if (validate()){
                authViewModel.createNewUser(email, password)
            }
        }

        return binding.root
    }

    private fun initialize(){
        email = binding.email.text.toString()
        password = binding.password.text.toString()
        confirmPassword = binding.confirmPassword.text.toString()
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

        else if (password.length < 6){
            AlertDialog.getInstance(requireContext()).showAlertDialog("Hey, your password shout be at least 6 characters long", "Close")
            v = false
        }

        else if (confirmPassword.isEmpty()){
            AlertDialog.getInstance(requireContext()).showAlertDialog("Hey, it looks you haven't confirmed your password", "Close")
            v = false
        }

        else if (password != confirmPassword){
            AlertDialog.getInstance(requireContext()).showAlertDialog("Hey, it looks your password didn't match with each other", "Close")
            v = false
        }
        return v;
    }
}