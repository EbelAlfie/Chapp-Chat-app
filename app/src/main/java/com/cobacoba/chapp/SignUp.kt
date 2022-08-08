package com.cobacoba.chapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var dataRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        loginNav.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        signUpBtn.setOnClickListener {
            val email = emailUser.text.toString()
            val user = userNameSignUp.text.toString()
            val pass = passwordSignUp.text.toString()
            dataRef = FirebaseDatabase.getInstance().reference

            if(!(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
                emailUser.setError("Invalid Email format!")
                emailUser.requestFocus()
                return@setOnClickListener
            }
            if(pass.length < 8){
                passwordSignUp.setError("Password length must be 8 or more!")
                passwordSignUp.requestFocus()
                return@setOnClickListener
            }

            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                if(it.isSuccessful){
                    val userID = mAuth.currentUser!!.uid
                    dataRef.child("Users").child(userID).setValue(UserContainer(user,email,userID))
                        .addOnCompleteListener {
                            if(it.isSuccessful){
                                val intent = Intent(this,HomeActivity::class.java)
                                startActivity(intent)
                            }else{
                                it.exception
                                Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                }else{
                    Toast.makeText(this, "Sign up failed!", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}