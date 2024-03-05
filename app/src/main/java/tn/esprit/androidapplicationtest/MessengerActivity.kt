package tn.esprit.androidapplicationtest

import ChatAdapter
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
import retrofit2.http.Path
import tn.esprit.androidapplicationtest.databinding.ActivityMessengerBinding
data class Conversation2(
    val _id: String,
    val messages: List<Message2>,
    val attachments: List<Any>, // Modify this as needed
    val __v: Int
)

data class Message2(
    val _id: String,
    val sender: String,
    val conversation: String,
    val content: String,
    val timestamp: String,
    val __v: Int
)

interface MessageService {
    @GET("conversations/{conversationId}/messages")
    fun getMessages(@Path("conversationId") conversationId: String): Call<Conversation2>
}
class MessengerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessengerBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var conversationId: String // Store the conversation ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessengerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.recyclerView

        // Retrieve the conversation ID from intent or wherever you get it
        conversationId = intent.getStringExtra("conversationId") ?: ""
        if (conversationId.isBlank()) {
            // Handle the case where conversationId is not available
            return
        }

        // Initialize Retrofit and make network call
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:9090/") // Update with your server URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MessageService::class.java)
        service.getMessages(conversationId).enqueue(object : Callback<Conversation2> {
            override fun onResponse(
                call: Call<Conversation2>,
                response: Response<Conversation2>
            ) {
                if (response.isSuccessful) {
                    val conversation = response.body()
                    conversation?.let {
                        Log.d("MessengerActivity", "Number of messages received: ${conversation.messages.size}")
                        displayMessages(conversation.messages)
                    }
                } else {
                    Log.e("MessengerActivity", "Failed to fetch messages: ${response.code()}")
                    Toast.makeText(this@MessengerActivity, "Failed to fetch messages", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Conversation2>, t: Throwable) {
                Log.e("MessengerActivity", "Network Error: ${t.message}")
                Toast.makeText(this@MessengerActivity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun displayMessages(messages: List<Message2>) {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = ChatAdapter(messages)
        recyclerView.adapter = adapter
    }
}
