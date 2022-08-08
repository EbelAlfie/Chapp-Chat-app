package com.cobacoba.chapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.FirebaseDatabaseKtxRegistrar
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var userList: MutableList<UserContainer?>
    private lateinit var adapter: UserAdapter
    private lateinit var dataRef: DatabaseReference
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        dataRef = FirebaseDatabase.getInstance().reference
        userId = FirebaseAuth.getInstance().currentUser!!.uid

        userList = mutableListOf()
        adapter = UserAdapter(this, userList)
        listOfUsers.layoutManager = LinearLayoutManager(this)
        listOfUsers.adapter = adapter
        dataRef.child("Users").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for(oneSnapshot in snapshot.children){
                    val user = oneSnapshot.getValue(UserContainer::class.java)
                    if(!(userId.equals(user!!.id))){
                        userList.add(user)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}