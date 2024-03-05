package tn.esprit.androidapplicationtest

import ChatAdapter
import ChatMessage
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import tn.esprit.androidapplicationtest.databinding.ActivityMessengerBinding

class MessengerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessengerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessengerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val messages = mutableListOf<ChatMessage>(
            ChatMessage("Hello!", "10:00 AM", true,true),
            ChatMessage("Hi there!", "10:05 AM", false,false),
            // Add more messages as needed
        )

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        val adapter = ChatAdapter(messages)
        binding.recyclerView.adapter = adapter
    }
}