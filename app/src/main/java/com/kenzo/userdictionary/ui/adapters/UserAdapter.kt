package com.kenzo.userdictionary.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kenzo.userdictionary.R
import com.kenzo.userdictionary.ui.model.User

class UserAdapter(private var users:List<User>,private val onClick: (User) -> Unit): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(view: View): RecyclerView.ViewHolder(view){
        var name=view.findViewById<TextView>(R.id.tv_Name)
        var email=view.findViewById<TextView>(R.id.tv_email)
        var mobileNumber=view.findViewById<TextView>(R.id.tv_mobileNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val viewHolder= LayoutInflater.from(parent.context).inflate(R.layout.item_users,parent,false)
        return UserViewHolder(viewHolder)
    }

    override fun getItemCount(): Int =users.size
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.name.text=users[position].name
        holder.email.text=users[position].email
        holder.mobileNumber.text=users[position].phone
        holder.itemView.setOnClickListener {
            onClick(users[position])
        }
    }

}