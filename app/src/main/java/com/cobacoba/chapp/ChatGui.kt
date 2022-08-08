package com.cobacoba.chapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat_gui.*

class ChatGui : AppCompatActivity() {
    private lateinit var chatList: MutableList<Chat?>
    private lateinit var adapterChat: ChatAdapter
    private lateinit var dataRef: DatabaseReference
    private var senderChatRoom: String? = null
    private var receiverChatRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_gui)
        val intent = intent
        val otherName = intent.getStringExtra("Name")
        val otherId = intent.getStringExtra("otherId")

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        senderChatRoom = userId + otherId
        receiverChatRoom = otherId+userId

        setSupportActionBar(my_toolbar)
        supportActionBar?.show()
        supportActionBar?.title = otherName

        dataRef = FirebaseDatabase.getInstance().reference
        chatList = mutableListOf()
        adapterChat = ChatAdapter(chatList)
        chatRoom.layoutManager = LinearLayoutManager(this)
        chatRoom.adapter = adapterChat

        dataRef.child("Messages").child(senderChatRoom!!).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for(item in snapshot.children){
                    val baloonChat = item.getValue(Chat::class.java)
                    chatList.add(baloonChat)
                }
                adapterChat.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        sendBtn.setOnClickListener{
            val textMsg = messageText.text.toString()
            val msg = Chat(textMsg, userId)
            dataRef.child("Messages").child(senderChatRoom!!).push().setValue(msg).addOnCompleteListener {
                if(it.isSuccessful){
                    dataRef.child("Messages").child(receiverChatRoom!!).push().setValue(msg)
                }else{
                    Toast.makeText(this, "Failed to send message!", Toast.LENGTH_SHORT).show()
                }
            }
            messageText.text.clear()
        }
    }
}