package com.cobacoba.chapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.chat_baloon_nder.view.*
import kotlinx.android.synthetic.main.chat_baloon_other.view.*

class ChatAdapter(
    var chatList: MutableList<Chat?>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class ChatSenderItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nderMsg = itemView.findViewById<TextView>(R.id.dariNder)
    }
    class ChatOtherItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val otherMsg = itemView.findViewById<TextView>(R.id.dariOrgLain)
    }

    override fun getItemViewType(position: Int): Int {
        val nder = chatList.get(position)
        if(FirebaseAuth.getInstance().currentUser!!.uid.equals(nder?.senderID)){
            return 1 ; //nder
        }else{
            return 0 ; //not nder
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){//return blu
            return ChatSenderItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.chat_baloon_nder, parent, false)
            )
        }
        return ChatOtherItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.chat_baloon_other, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val nder = chatList.get(position)
        if(holder.javaClass == ChatSenderItemViewHolder::class.java){
            val chatBaloon = holder as ChatSenderItemViewHolder
            chatBaloon.nderMsg.text = nder?.message
        }
        if(holder.javaClass == ChatOtherItemViewHolder::class.java){
            val chatBaloon = holder as ChatOtherItemViewHolder
            chatBaloon.otherMsg.text = nder?.message
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}