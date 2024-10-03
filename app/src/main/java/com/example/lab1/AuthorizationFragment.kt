package com.example.lab1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController

import androidx.navigation.fragment.navArgs
import com.example.lab1.databinding.FragmentAuthorizationBinding
import kotlin.reflect.typeOf
class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    private val validEmail = "test@test.com"
    private val validPassword = "12345"
    private val args: AuthorizationFragmentArgs by navArgs()

    private var registeredUsers: List<User?> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAuthorizationBinding.bind(view)

        if (args.user != null) {
            val userRegEmail = args.user?.email ?: ""
            val userRegPasswd = args.user?.password ?: ""
            val userRegName = args.user?.username ?: ""
            registeredUsers = registeredUsers + args.user
            Toast.makeText(context, "new User $userRegName $userRegEmail registered", Toast.LENGTH_SHORT).show()
            binding.emailInput.setText(userRegEmail)
            binding.passwordInput.setText(userRegPasswd)
        }

        binding.loginButton.setOnClickListener {
            val userEmail = binding.emailInput.text.toString().trim()
            val userPassword = binding.passwordInput.text.toString().trim()
            val user = registeredUsers.find { it?.email == userEmail && it.password == userPassword }

            if ((userEmail == validEmail && userPassword == validPassword) || user != null) {
                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                val action = AuthorizationFragmentDirections.actionAuthorizationFragmentToHomeFragment()
                findNavController().navigate(action)
            } else {
                Toast.makeText(context, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerButton.setOnClickListener {
            val action = AuthorizationFragmentDirections.actionAuthorizationFragmentToRegistrationFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
