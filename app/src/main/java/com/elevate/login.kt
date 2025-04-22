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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login_button)
        val signupText = findViewById<TextView>(R.id.signup_text)
        val forgotPass = findViewById<TextView>(R.id.forgot_pass)

        signupText.setOnClickListener {
            startActivity(Intent(this,Register::class.java))
            finish()
        }

        forgotPass.setOnClickListener {
            val userEmail = email.text.toString()
            Firebase.auth.sendPasswordResetEmail(email.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "email sent!", Toast.LENGTH_SHORT).show()
                    }
                }
        }


        loginButton.setOnClickListener {
            val userEmail = email.text.toString()
            val userPassword = password.text.toString()


            if ( userEmail.isBlank() || userPassword.isBlank()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }

            else {
                // هنا ممكن تبعتي البيانات لسيرفر أو تفتحي صفحة جديدة
                Toast.makeText(this, "Login Successfully 🎉", Toast.LENGTH_LONG).show()


                login(email,password)
            }
        }
    }

    private fun login(email: EditText, password: EditText?) {
        auth.signInWithEmailAndPassword(email.toString(), password.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if (auth.currentUser!!.isEmailVerified) {
                        startActivity(Intent(this, TakeoffActivity::class.java))
                        finish()
                    } else
                        Toast.makeText(this, "check your email!!!!!!!", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
    }
}
