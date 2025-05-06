package com.elevate

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elevate.databinding.ActivityRegister2Binding
import com.elevate.utils.SharedPreferencesHelper
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegister2Binding
    private lateinit var auth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001
    private lateinit var preferences: SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        callbackManager = CallbackManager.Factory.create()
        preferences = SharedPreferencesHelper(this)

        // Navigate to LoginActivity
        binding.loginText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Facebook Login via MaterialCardView
        binding.facebookCardView.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(
                this,
                listOf("email", "public_profile")
            )
        }

        // Handle Facebook Login result
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    handleFacebookAccessToken(result.accessToken)
                }

                override fun onCancel() {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Facebook login canceled",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onError(error: FacebookException) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Facebook login failed: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        // Email/Password Registration
        binding.registerButton.setOnClickListener {
            val firstName = binding.firstName.text.toString()
            val lastName = binding.lastName.text.toString()
            val email = binding.email.editText?.text.toString()
            val password = binding.password.editText?.text.toString()

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Save user's name
                        val fullName = "$firstName $lastName".trim()
                        preferences.setUserName(fullName)
                        
                        // Update Firebase profile
                        val profileUpdates = com.google.firebase.auth.UserProfileChangeRequest.Builder()
                            .setDisplayName(fullName)
                            .build()
                        
                        auth.currentUser?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { profileTask ->
                                if (profileTask.isSuccessful) {
                                    Toast.makeText(
                                        this,
                                        "Registration successful! Please login",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(Intent(this, LoginActivity::class.java))
                                    finish()
                                }
                            }
                    } else {
                        val errorMessage = when {
                            task.exception?.message?.contains("email address is badly formatted") == true ->
                                "Invalid email format"

                            task.exception?.message?.contains("password is invalid") == true ->
                                "Password must be at least 6 characters"

                            task.exception?.message?.contains("email address is already in use") == true ->
                                "Email is already registered"

                            else -> "Signup failed: ${task.exception?.message}"
                        }
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Registration failed: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    // Facebook login result must go to callbackManager
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Google login success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, TakeoffActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Google auth failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Link Facebook login to Firebase Auth
    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Facebook login success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, TakeoffActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Facebook auth failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
