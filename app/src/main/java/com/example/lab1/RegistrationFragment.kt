package com.example.lab1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult


class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameInput: EditText = view.findViewById(R.id.username_input)
        val emailInput: EditText = view.findViewById(R.id.email_input)
        val passwordInput: EditText = view.findViewById(R.id.password_input)
        val registerButton: Button = view.findViewById(R.id.register_second_button)

        registerButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val user = User(username, email, password)
                Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()

                val resultBundle = bundleOf("user" to user)

                setFragmentResult("requestKey", resultBundle)

                (activity as MainActivity).showFragment(AuthorizationFragment::class.java)
            } else {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
