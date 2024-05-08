package tn.esprit.androidapplicationtest
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.androidapplicationtest.databinding.ChatitemBinding

class ContactAdapter(
    private val context: Context,
    private val conversations: List<Conversation>,
    private val clickListener: OnConversationClickListener
) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    interface OnConversationClickListener {
        fun onConversationClick(conversationId: String, senderName: String)
    }

    inner class ViewHolder(private val binding: ChatitemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(conversation: Conversation) {
            val lastMessage = conversation.messages.lastOrNull()
            val participants = conversation.participants
            val otherParticipantId = participants.firstOrNull { it != currentUser }
            val senderName = otherParticipantId ?: "Unknown"
            binding.senderName.text = senderName

            if (lastMessage?.content != null) {
                if (lastMessage.content.length > 20) {
                    val truncatedMessage = lastMessage.content.substring(0, 20) + "..."
                    binding.messageContent.text = truncatedMessage
                } else {
                    binding.messageContent.text = lastMessage.content
                }
            } else {
                binding.messageContent.text = ""
            }
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val conversation = conversations[position]
                val conversationId = conversation._id
                val senderName = getSenderName(conversation)
                clickListener.onConversationClick(conversationId, senderName)
            }
        }

        private fun getSenderName(conversation: Conversation): String {
            val participants = conversation.participants
            val currentUserId = currentUser // Assuming currentUser is accessible here
            return participants.firstOrNull { it != currentUserId } ?: "Unknown"
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
