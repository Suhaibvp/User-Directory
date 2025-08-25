package com.kenzo.userdictionary

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kenzo.userdictionary.ui.adapters.UserAdapter
import com.kenzo.userdictionary.ui.model.User
import com.kenzo.userdictionary.ui.retrofit.ApiClient
import com.kenzo.userdictionary.ui.retrofit.ApiService
import com.kenzo.userdictionary.ui.theme.UserDictionaryTheme
import com.kenzo.userdictionary.viewmodels.UserViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        val refresh=findViewById<SwipeRefreshLayout>(R.id.refreshListener)
        val recyclerView=findViewById<RecyclerView>(R.id.rv_users)
        val pb_bar=findViewById<ProgressBar>(R.id.pb_refresh)
        recyclerView.layoutManager= LinearLayoutManager(this)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.fetchUser()
        viewModel.users.observe (this){userList->
            recyclerView.adapter= UserAdapter(userList){ user ->
                navigateToUserDetails(user)
            }
            pb_bar.visibility=View.GONE
            refresh.visibility= View.VISIBLE
            refresh.isRefreshing = false
        }
        viewModel.errors.observe(this) {erros->
            erros?.let {
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("$it \n Would you like to Retry?")
                .setPositiveButton("Retry") { dialog, _ ->
                    viewModel.fetchUser()
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .show()
        }

        }

        refresh.setOnRefreshListener {
            viewModel.fetchUser()
        }
    }
    private fun navigateToUserDetails(user:User){
    val intent=Intent(this, UserDetails::class.java).apply {
    putExtra("userId",user.id)
    putExtra("userName",user.username)
    putExtra("FullName",user.name)
    putExtra("email",user.email)
    putExtra("phone",user.phone)
    putExtra("webLink",user.website)
}
        startActivity(intent)
    }
}

