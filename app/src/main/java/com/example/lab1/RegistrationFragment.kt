package com.example.lab1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController


class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameInput: EditText = view.findViewById(R.id.username_input)
        val emailInput: EditText = view.findViewById(R.id.email_input)
        val passwordInput: EditText = view.findViewById(R.id.password_input)
        val registerButton: Button = view.findViewById(R.id.register_second_button)
        val backToLogin: Button = view.findViewById(R.id.back_to_login)


        backToLogin.setOnClickListener {
           val action = RegistrationFragmentDirections.actionRegistrationToAuthorization()
            findNavController().navigate(action)
        }

        registerButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val user = User(username, email, password)
                val action = RegistrationFragmentDirections.actionRegistrationToAuthorization(user)
                findNavController().navigate(action)
            } else {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
