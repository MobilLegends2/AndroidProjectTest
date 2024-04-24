package tn.esprit.androidapplicationtest

<<<<<<< Updated upstream
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tn.esprit.androidapplicationtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener for the button
        binding.btnMessenger.setOnClickListener {
            // Start MessengerActivity
            startActivity(Intent(this, MessengerActivity::class.java))
        }
=======

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView


val sam = User(5, "Sam", R.drawable.sam)
val steven = User(7, "Steven", R.drawable.steven)
val olivia = User(4, "Olivia", R.drawable.olivia)
val john = User(3, "John", R.drawable.john)
val greg = User(1, "Greg", R.drawable.greg)


val users: List<User> = listOf(sam, steven, olivia, john, greg)

const val currentUser= "participant2"

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewUser: RecyclerView
    private lateinit var crossChatSdk: CrossChatSdk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerViewUser = findViewById(R.id.userIconRecyclerView)

        crossChatSdk = CrossChatSdk(this, users, currentUser)
        crossChatSdk.initRecyclerView(recyclerView)
        crossChatSdk.fetchConversationsAndUsers()
>>>>>>> Stashed changes
    }
}
