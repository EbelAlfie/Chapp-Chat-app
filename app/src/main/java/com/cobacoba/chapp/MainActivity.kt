package com.cobacoba.chapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        loginBtn.setOnClickListener {
            val user = userName.text.toString()
            val pass = password.text.toString()
            if(!Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
                userName.setError("Invalid email format!")
                userName.requestFocus()
                return@setOnClickListener
            }
            if(pass.length < 8){
                password.setError("Password length must be 8 or more!")
                password.requestFocus()
                return@setOnClickListener
            }
            mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener {
                if(it.isSuccessful){
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        signUpNav.setOnClickListener{
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }
}