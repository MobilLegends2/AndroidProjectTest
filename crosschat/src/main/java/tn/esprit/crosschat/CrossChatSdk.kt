package tn.esprit.crosschat

import android.content.Context
import android.util.Log
import android.widget.Toast
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

data class User(
    val id: Int,
    val name: String,
    val imageResourceId: Int // Resource ID for the user's image
)

data class Sender(
    val name: String
)

interface ConversationService {
    @GET("conversation/{currentUser}")
    fun getConversations(@retrofit2.http.Path("currentUser") currentUser: String): Call<List<Conversation>>
}

class CrossChatSdk(
    private val context: Context,
    private val users: List<User>,
    private val currentUser: String,
    private val recyclerViewUser: RecyclerView
) {
    private lateinit var recyclerView: RecyclerView
    private val conversationsAdapter = ContactAdapter(context, emptyList())

    fun initRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        this.recyclerView.layoutManager = LinearLayoutManager(context)
        this.recyclerView.adapter = conversationsAdapter
    }

    fun fetchConversationsAndUsers() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:9090/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ConversationService::class.java)

        service.getConversations(currentUser).enqueue(object : Callback<List<Conversation>> {
            override fun onResponse(call: Call<List<Conversation>>, response: Response<List<Conversation>>) {
                if (response.isSuccessful) {
                    val conversations = response.body()
                    conversations?.let {
                        Log.d("CrossChatSdk", "Number of conversations received: ${conversations.size}")
                        displayConversations(it)
                    }
                    Log.d("CrossChatSdk", "Response: $response")
                } else {
                    Log.e("CrossChatSdk", "Failed to fetch conversations: ${response.code()}")
                    Toast.makeText(context, "Failed to fetch conversations", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Conversation>>, t: Throwable) {
                Log.e("CrossChatSdk", "Network Error: ${t.message}")
                Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })

        displayUsers(users)
    }

    private fun displayConversations(conversations: List<Conversation>) {
        conversationsAdapter.updateData(conversations)
    }

    private fun displayUsers(users: List<User>) {
        val adapterUser = UserAdapter(context, users)
        recyclerViewUser.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewUser.adapter = adapterUser
    }
}
