package com.kenzo.userdictionary

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kenzo.userdictionary.ui.adapters.PostAdapter
import com.kenzo.userdictionary.ui.adapters.UserAdapter
import com.kenzo.userdictionary.viewmodels.PostViewModel

class UserDetails: ComponentActivity() {
    private lateinit var viewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user_details)
        val userId = intent?.extras?.getLong("userId") ?: -1L
        val userName = intent?.extras?.getString("userName")
        val fullName = intent?.extras?.getString("FullName")
        val email = intent?.extras?.getString("email")
        val phoneNumber = intent?.extras?.getString("phone")
        val webLink = intent?.extras?.getString("webLink")
        if (userId == -1L || userName==null || fullName==null || email==null ||phoneNumber==null||webLink==null) {
            finish()
            return
        }
        setTextView(R.id.tv_FullName,fullName)
        setTextView(R.id.tv_userName,userName)
        setTextView(R.id.tv_email,email)
        setTextView(R.id.tv_phNumber,phoneNumber)
        setTextView(R.id.tv_webLink,webLink)


        val tvEmail=findViewById<TextView>(R.id.tv_email)
        val tvPhNumber=findViewById<TextView>(R.id.tv_phNumber)
        val tvWebLink=findViewById<TextView>(R.id.tv_webLink)

        tvEmail.setOnClickListener {
            val emIntent= Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
            }
            startActivity(emIntent)
        }
        tvPhNumber.setOnClickListener {
            val PhIntent=Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(PhIntent)
        }
        tvWebLink.setOnClickListener {
            var url = webLink
            url = "http://$url"
            val WebIntent= Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(WebIntent)
        }
        val pb_bar=findViewById<ProgressBar>(R.id.pb_refresh)
        val recyclerView=findViewById<RecyclerView>(R.id.rv_posts)
        val btn_Back=findViewById<Button>(R.id.btn_Back)
        btn_Back.setOnClickListener {
            finish()
        }
        recyclerView.layoutManager= LinearLayoutManager(this)
        viewModel= ViewModelProvider(this).get(PostViewModel::class.java)
        viewModel.fetchPosts(userId)
        viewModel.posts.observe(this) {
            posts->recyclerView.adapter=PostAdapter(posts)
            pb_bar.visibility=View.GONE
            recyclerView.visibility= View.VISIBLE


        }

        viewModel.errors.observe(this) {erros->
            erros?.let {
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("$it \n Would you like to Retry?")
                    .setPositiveButton("Retry") { dialog, _ ->
                        viewModel.fetchPosts(userId)
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setCancelable(false)
                    .show()
            }

        }
    }
    fun setTextView(view:Int,value:String){
        val item=findViewById<TextView>(view)
        item.text=value

    }
}