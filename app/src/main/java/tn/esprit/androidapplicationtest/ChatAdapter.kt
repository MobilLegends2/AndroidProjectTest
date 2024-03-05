import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import tn.esprit.androidapplicationtest.R

class ChatAdapter(private val messages: List<ChatMessage>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        val messageText: TextView = view.findViewById(R.id.textMessage)
        val timeText: TextView = view.findViewById(R.id.heure)
        val hide1:  TextView = view.findViewById(R.id.espace1)
        val hide2:  TextView = view.findViewById(R.id.espace2)
        val phote: ShapeableImageView = view.findViewById(R.id.photo)
        val cart: MaterialCardView =view.findViewById(R.id.con)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.messageitem, parent, false)
        return ViewHolder(view,parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]

        holder.messageText.text = message.message
        holder.timeText.text = message.time
        if(!message.mymessage)
        {
          holder.hide1.visibility=View.GONE
            holder.hide2.visibility=View.GONE
            holder.phote.visibility=View.GONE
            holder.cart.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.softred))

        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}
