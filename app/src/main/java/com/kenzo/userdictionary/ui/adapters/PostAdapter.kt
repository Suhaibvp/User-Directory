package com.kenzo.userdictionary.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kenzo.userdictionary.R
import com.kenzo.userdictionary.ui.model.Post


class PostAdapter(private var posts:List<Post>): RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    class PostViewHolder(view: View): RecyclerView.ViewHolder(view){
    var title=view.findViewById<TextView>(R.id.tv_title)
        var body=view.findViewById<TextView>(R.id.tv_Body)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val viewHolder= LayoutInflater.from(parent.context).inflate(R.layout.item_post,parent,false)
        return PostViewHolder(viewHolder)
    }

    override fun getItemCount(): Int =posts.size
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.title.text=posts[position].title
        holder.body.text=posts[position].body
    }

}