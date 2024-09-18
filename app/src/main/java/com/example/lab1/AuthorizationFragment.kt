package com.example.lab1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

import androidx.fragment.app.setFragmentResultListener

class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {
    private val validEmail = "test@test.com"
    private val validPassword = "12345"

    private val userList = mutableListOf<User>() // This should be passed or retained somehow
    private var registeredUsers: List<User> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailInput: EditText = view.findViewById(R.id.email_input)
        val passwordInput: EditText = view.findViewById(R.id.password_input)
        val loginButton: Button = view.findViewById(R.id.login_button)
        val registerButton: Button = view.findViewById(R.id.register_button)

        setFragmentResultListener("requestKey") { key, bundle ->
            val user = bundle.getSerializable("user") as? User // Cast to User

            user?.let {
                registeredUsers = registeredUsers + it
                emailInput.setText(it.email)
                passwordInput.setText(it.password)
                Toast.makeText(context, "new User ${it.username} ${it.email} registered", Toast.LENGTH_SHORT).show()
            }
        }

        loginButton.setOnClickListener {
            val userEmail = emailInput.text.toString().trim()
            val userPassword = passwordInput.text.toString().trim()
            val user = registeredUsers.find { it.email == userEmail && it.password == userPassword }

            if ((userEmail == validEmail && userPassword == validPassword) || user != null) {
                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                (activity as MainActivity).showFragment(HomeFragment::class.java)
            } else {
                Toast.makeText(context, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            (activity as MainActivity).showFragment(RegistrationFragment::class.java)
        }
    }
}
