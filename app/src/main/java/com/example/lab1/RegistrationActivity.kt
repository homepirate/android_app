//package com.example.lab1
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//
//import androidx.appcompat.app.AppCompatActivity
//
//
//class RegistrationActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_registration)
//
//        val usernameInput: EditText = findViewById(R.id.username_input)
//        val emailInput: EditText = findViewById(R.id.email_input)
//        val passwordInput: EditText = findViewById(R.id.password_input)
//        val registerButton: Button = findViewById(R.id.register_second_button)
//
//        registerButton.setOnClickListener {
//            val username = usernameInput.text.toString().trim()
//            val email = emailInput.text.toString().trim()
//            val password = passwordInput.text.toString().trim()
//
//            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
//                val user = User(username, email, password)
//                val resultIntent = Intent()
//                resultIntent.putExtra("newUser", user)
//                setResult(RESULT_OK, resultIntent)
//                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
//                finish()
//            } else {
//                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//}