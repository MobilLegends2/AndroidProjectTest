// MainActivity.kt
package tn.esprit.androidapplicationtest

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import tn.esprit.androidapplicationtest.databinding.ActivityMainBinding

data class Conversation(
    val _id: String,
    val messages: List<Message>
)

data class Message(
    val _id: String,
    val sender: Sender,
    val content: String
)

data class Sender(
    val name: String
)

interface ConversationService {
    @GET("conversations/")
    fun getConversations(): Call<List<Conversation>>
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.recyclerView

        // Initialize Retrofit and make network call
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:9090") // Update with your server URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val service = retrofit.create(ConversationService::class.java)
        service.getConversations().enqueue(object : Callback<List<Conversation>> {

            override fun onResponse(
                call: Call<List<Conversation>>,
                response: Response<List<Conversation>>

            ) {
                if (response.isSuccessful) {
                    val conversations = response.body()
                    conversations?.let {
                        Log.d("MainActivity", "Number of conversations received: ${conversations.size}")
                        displayConversations(it)
                    }
                } else {
                    Log.e("MainActivity", "Failed to fetch data: ${response.code()}")
                    Toast.makeText(this@MainActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Conversation>>, t: Throwable) {
                Log.e("MainActivity", "Network Error: ${t.message}")
                Toast.makeText(this@MainActivity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayConversations(conversations: List<Conversation>) {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = ContactAdapter(this, conversations) // Pass context here
        recyclerView.adapter = adapter
    }
}
