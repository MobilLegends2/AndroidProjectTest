package tn.esprit.crosschat

data class ChatMessage(
    val message: String,
    val time: String,
    val isRead: Boolean,
    val mymessage:Boolean
)
