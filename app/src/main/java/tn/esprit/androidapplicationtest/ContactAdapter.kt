package tn.esprit.androidapplicationtest

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView


class ContactAdapter (private val messages: List<ChatContact>) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {


    class ViewHolder(view: View, public val context: Context) : RecyclerView.ViewHolder(view) {
        val messageText: TextView = view.findViewById(R.id.name)
        val timeText: TextView = view.findViewById(R.id.lastmessge)
        val content: LinearLayout = view.findViewById(R.id.content)
        val photo: ImageView = view.findViewById(R.id.photo)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chatitem, parent, false)
        return ViewHolder(view,parent.context)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]
        holder.messageText.text = message.nom
        holder.timeText.text = message.lastmessage
        holder.content.setOnClickListener {
            val intent = Intent(holder.context, MessengerActivity::class.java)
            holder.context.startActivity(intent)
        };

    }

    override fun getItemCount(): Int {
        return messages.size
    }
}