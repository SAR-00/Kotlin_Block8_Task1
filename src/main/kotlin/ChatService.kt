object ChatService {

    var chats = mutableListOf<Chat>()
    private var messageId = 0


    fun deleteChat(chatId: Int): Int {

        chats.firstOrNull { it.chatId == chatId }
            .let { chats.remove(it ?: throw NotFoundException("Чат с id $chatId не найден")) }

        return 1
    }


    fun get_Chats(): List<Chat> = chats


    fun getUnreadChatsCount(): Int =

        chats.filter { it.messages.isNotEmpty() }
            .filter { !it.messages.last().read }
            .size


    fun createMessage(
        userId: Int,
        chatId: Int,
        text: String
    ): Int {

        val message = Message(userId, ++messageId, text, false)

        chats.find { it.chatId == chatId }
            ?.messages?.add(message)
            ?: chats.add(Chat(chatId, mutableListOf(message)))

        return 1
    }


    fun editMessage(
        chatId: Int,
        messageId: Int,
        text: String
    ): Int {

        chats.firstOrNull { it.chatId == chatId }
            .let { it?.messages ?: throw NotFoundException("Чат c id $chatId не найден") }
            .firstOrNull { it.messageId == messageId }
            .let { it?.copy(text = text) ?: throw NotFoundException("Сообщение с id $messageId не найдено") }

        return 1
    }


    fun deleteMessage(
        chatId: Int,
        messageId: Int
    ): Int {

        val message = chats.firstOrNull { it.chatId == chatId }
            .let { it?.messages ?: throw NotFoundException("Чат с id $chatId не найден") }
            .firstOrNull { it.messageId == messageId }
            .let { it ?: throw NotFoundException("Сообщение с id $messageId не найдено") }

        chats.first { it.chatId == chatId }.messages.remove(message)

        return 1
    }


    fun getLastMessagesFromAllChats(): List<String> =
        chats.map { it.messages.lastOrNull()?.text ?: "Нет сообщений" }


    fun getMessagesByChatId(
        chatId: Int,
        messageCount: Int
    ): List<String> =

        chats.firstOrNull { it.chatId == chatId }
            .let { it?.messages ?: throw NotFoundException("Чат с id $chatId не найден") }
            .asSequence()
            .take(messageCount)
            .ifEmpty { throw NotFoundException("Сообщения не найдены") }
            .map { it.text }
            .toList()


    fun clear() {
        chats = mutableListOf()
        messageId = 0
    }
}