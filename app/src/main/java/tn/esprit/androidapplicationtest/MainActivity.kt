package tn.esprit.androidapplicationtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import tn.esprit.androidapplicationtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val messages = mutableListOf<ChatContact>(
            ChatContact("adem", "ok we ganna see later ", true),
            ChatContact("arafet", "ti jeweb 3al messaget ", false),
            // Add more messages as needed
        )
        // Set click listener for the button
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        val adapter = ContactAdapter(messages)
        binding.recyclerView.adapter = adapter
    }
}
