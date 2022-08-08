package com.cobacoba.chapp

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.oneuser.view.*

class UserAdapter(
    var context: Context,
    var userList: MutableList<UserContainer?>
):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.oneuser, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        var user = userList.get(position)
        holder.itemView.nameOfUsers.text = user?.name
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatGui::class.java)
            intent.putExtra("Name", user!!.name)
            intent.putExtra("otherId", user!!.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}