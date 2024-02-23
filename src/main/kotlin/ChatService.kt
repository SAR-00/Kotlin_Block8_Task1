object ChatService {

    var chats = mutableListOf<Chat>()
    private var messageId = 0

    private fun createChat(
        chatId: Int,
        message: Message
    ): Int {

        val chat = Chat(chatId)
        chat.messages += message
        chats += chat

        return 1
    }

    fun deleteChat(chatId: Int): Int {
        try {
            chats.remove(chats.first { it.chatId == chatId })

        } catch (exception: NoSuchElementException) {
            throw NotFoundException("Чат с id $chatId не найден")

        }

        return 1
    }

    fun get_Chats(): List<Chat> = chats

    fun getUnreadChatsCount(): Int {

           return chats.filter {
                try {
                    !it.messages.last().read

                } catch (exception: NoSuchElementException) { //Обработка пустого чата
                    false

                }
            }.size
    }

    fun createMessage(
        userId: Int,
        chatId: Int,
        text: String
    ): Int {

        val message = Message(userId, ++messageId, text, false)

        chats.find { it.chatId == chatId }
            ?.messages?.add(message) ?: return createChat(chatId, message)

        return 1
    }

    fun editMessage(
        chatId: Int,
        messageId: Int,
        text: String
    ): Int {

        try {
            chats.first { it.chatId == chatId }
                .messages.first { it.messageId == messageId }
                .text = text

        } catch (exception: NoSuchElementException) {
            throw NotFoundException("Чат или сообщение не найдены")

        }

        return 1
    }

    fun deleteMessage(
        chatId: Int,
        messageId: Int
    ): Int {

        try {
            val chat = chats.first { it.chatId == chatId }
            val message = chat.messages.first { it.messageId == messageId }

            chats.first { it.chatId == chatId }.messages.remove(message)

        } catch (exception: NoSuchElementException) {
            throw NotFoundException("Чат или сообщение не найдены")

        }

        return 1
    }

    fun getLastMessagesFromAllChats(): List<String> {

        val messages = mutableListOf<String>()

        try {
            chats.forEach { messages += it.messages.last().text }

        } catch (exception: NoSuchElementException) {
            messages += "Нет сообщений"

        }

        return messages
    }

    fun getAllMessagesByChatId(
        chatId: Int,
        messageCount: Int
    ): List<String> {

        val messages = mutableListOf<String>()
        var count = messageCount

        try {
            val chat = chats.first { it.chatId == chatId }.messages

            chat.forEach {

                if (count == 0) {
                    return@forEach
                }

                it.read = true
                messages += it.text
                count--
            }

        } catch (exception: NoSuchElementException) {
            throw NotFoundException("Чат c id $chatId не найден")

        }

        return messages
    }

    fun clear() {
        chats = mutableListOf()
        messageId = 0
    }
}