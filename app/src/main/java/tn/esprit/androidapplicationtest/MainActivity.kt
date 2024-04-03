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

data class Conversation(
    val _id: String,
    val participants: List<String>,
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
    @GET("conversation/participant1")
    fun getConversations(): Call<List<Conversation>>
}

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)

        // Initialize Retrofit and make network call
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:9090/") // Ensure the URL ends with "/"
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
                    Log.d("MainActivity", "Response: $response")
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
        adapter = ContactAdapter(this, conversations)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

}
