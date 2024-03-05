package tn.esprit.androidapplicationtest

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.androidapplicationtest.databinding.ChatitemBinding

class ContactAdapter(private val context: Context, private val conversations: List<Conversation>) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ChatitemBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(conversation: Conversation) {
            val lastMessage = conversation.messages.lastOrNull()
            binding.senderName.text = lastMessage?.sender?.name ?: "Unknown"
            binding.messageContent.text = lastMessage?.content ?: ""
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val conversation = conversations[position]
                val conversationId = conversation._id
                val intent = Intent(context, MessengerActivity::class.java).apply {
                    putExtra("conversationId", conversationId)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ChatitemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(conversations[position])
    }

    override fun getItemCount(): Int {
        return conversations.size
    }
}
