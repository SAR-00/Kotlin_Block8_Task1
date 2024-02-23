data class Chat(
    val chatId: Int,
    val messages: MutableList<Message> = mutableListOf()
)
