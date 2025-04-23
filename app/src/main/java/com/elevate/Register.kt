package com.elevate

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class Register : AppCompatActivity() {

   // private lateinit var auth: FirebaseAuth // Moved here to be accessible throughout the class

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

       // auth = FirebaseAuth.getInstance()

        val firstName = findViewById<EditText>(R.id.first_name)
        val lastName = findViewById<EditText>(R.id.last_name)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val checkbox = findViewById<CheckBox>(R.id.checkbox_terms)
        val registerButton = findViewById<Button>(R.id.register_button)
        val loginText = findViewById<TextView>(R.id.login_text)

        loginText.setOnClickListener {
            startActivity(Intent(this, login::class.java))
            finish()
        }

        registerButton.setOnClickListener {
            val fName = firstName.text.toString()
            val lName = lastName.text.toString()
            val userEmail = email.text.toString()
            val userPassword = password.text.toString()
            val isChecked = checkbox.isChecked

            if (fName.isBlank() || lName.isBlank() || userEmail.isBlank() || userPassword.isBlank()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else if (!isChecked) {
                Toast.makeText(this, "You must accept the terms to continue", Toast.LENGTH_SHORT).show()
            } else {
               // addNewUser(userEmail, userPassword)
            }
        }
    }

//    private fun addNewUser(email: String, password: String) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(this, "User added!", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this, task.exception?.message ?: "Registration failed", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
}
