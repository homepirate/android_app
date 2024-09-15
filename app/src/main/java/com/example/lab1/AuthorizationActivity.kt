package com.example.lab1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class AuthorizationActivity : BaseActivity() {

    private val validEmail = "test@test.com"
    private val validPassword = "12345"
    private val SIGN_UP_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        val emailInput: EditText = findViewById(R.id.email_input)
        val passwordInput: EditText = findViewById(R.id.password_input)
        val loginButton: Button = findViewById(R.id.login_button)
        val registerButton: Button = findViewById(R.id.register_button)

        loginButton.setOnClickListener {
            val userEmail = emailInput.text.toString().trim()
            val userPassword = passwordInput.text.toString().trim()

            if (userEmail == validEmail && userPassword == validPassword) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivityForResult(intent, SIGN_UP_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_UP_REQUEST && resultCode == RESULT_OK) {
            val newUser = data?.getSerializableExtra("newUser") as? User
            newUser?.let {
                val emailInput: EditText = findViewById(R.id.email_input)
                val passwordInput: EditText = findViewById(R.id.password_input)

                emailInput.setText(it.email)
                passwordInput.setText(it.password)

                Toast.makeText(
                    this, "New User: ${it.username}, Email: ${it.email}", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
