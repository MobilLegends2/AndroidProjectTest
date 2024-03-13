import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.androidapplicationtest.Message2
import tn.esprit.androidapplicationtest.R

class ChatAdapter(private val messages: List<Message2>, private val currentUserId: String) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private val VIEW_TYPE_SENT = 1
    private val VIEW_TYPE_RECEIVED = 2

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Views for the left side layout (received messages)
        val messageTextLeft: TextView = view.findViewById(R.id.textMessage_left)
        val timeTextLeft: TextView = view.findViewById(R.id.heure_left)
        val photo: ImageView = view.findViewById(R.id.photo_left)
        // Views for the right side layout (sent messages)
        val messageTextRight: TextView = view.findViewById(R.id.textMessage_right)
        val timeTextRight: TextView = view.findViewById(R.id.heure_right)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.messageitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]

        if (message.sender == currentUserId) {
            // Message sent by the current user
            holder.photo.visibility = View.INVISIBLE
            holder.messageTextRight.visibility = View.VISIBLE
            holder.timeTextRight.visibility = View.VISIBLE
            holder.messageTextRight.text = message.content
            holder.timeTextRight.text = message.timestamp

            // Hide views for the left side layout
            holder.messageTextLeft.visibility = View.GONE
            holder.timeTextLeft.visibility = View.GONE
        } else {
            // Message received from other users
            holder.messageTextLeft.visibility = View.VISIBLE
            holder.timeTextLeft.visibility = View.VISIBLE
            holder.messageTextLeft.text = message.content
            holder.timeTextLeft.text = message.timestamp

            // Hide views for the right side layout
            holder.messageTextRight.visibility = View.GONE
            holder.timeTextRight.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.sender == currentUserId) {
            VIEW_TYPE_SENT
        } else {
            VIEW_TYPE_RECEIVED
        }
    }
}
