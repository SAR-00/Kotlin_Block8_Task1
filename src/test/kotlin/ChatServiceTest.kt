import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals


class ChatServiceTest {

    @Before
    fun clearBeforeTest() {
        ChatService.clear()
    }

    @Test
    fun createChatTest() {

        //Для проверки применена функция createMessage() потому что при отсутсвии чата с указанным id
        // она использует функцию createChat()
        val result = ChatService.createMessage(1, 2, "Hello")

        assertEquals(1, result)

    }

    @Test
    fun deleteChatTest() {

        ChatService.createMessage(1, 2, "Hello")
        val  result = ChatService.deleteChat(2)

        assertEquals(1, result)
    }

    @Test(expected = NotFoundException::class)
    fun deleteChatExceptionTest() {
        ChatService.deleteChat(1)
    }

    @Test
    fun get_ChatsTest() {

        ChatService.createMessage(1, 2, "Hello")
        val result = ChatService.get_Chats()

        assertEquals(ChatService.chats, result)
    }

    @Test
    fun getUnreadChatsCountTest() {

        ChatService.createMessage(1, 2, "Hello")
        val result = ChatService.getUnreadChatsCount()

        assertEquals(1, result)
    }

    @Test
    fun createMessageTest() {

        ChatService.createMessage(1, 2, "Hello")

        //Здесь создаётся сообщение в уже существующем чате, поэтому функция createChat() не вызывается
        val result = ChatService.createMessage(2, 2, "Hi")

        assertEquals(1, result)
    }

    @Test
    fun editMessageTest() {

        ChatService.createMessage(1, 2, "Hello")
        val result = ChatService.editMessage(2, 1, "Hi, bro:)")

        assertEquals(1, result)
    }

    @Test(expected = NotFoundException::class)
    fun editMessageExceptionTest() {
        ChatService.editMessage(2, 1, "Hi, bro:)")
    }

    @Test
    fun deleteMessageTest() {

        ChatService.createMessage(1, 2, "Hello")
        val result = ChatService.deleteMessage(2, 1)

        assertEquals(1, result)
    }

    @Test(expected = NotFoundException::class)
    fun deleteMessageExceptionTest() {
        ChatService.deleteMessage(2, 1)
    }

    @Test
    fun getLastMessagesFromAllChatsTest() {

        ChatService.createMessage(1, 1, "Hello")
        ChatService.createMessage(1, 2, "Bye")

        val messages = mutableListOf("Hello", "Bye")
        val result = ChatService.getLastMessagesFromAllChats()

        assertEquals(messages, result)
    }

    @Test
    fun getAllMessagesByChatIdTest() {

        ChatService.createMessage(1, 2, "Hello")
        ChatService.createMessage(2, 2, "Hi")

        val messages = mutableListOf("Hello", "Hi")
        val result = ChatService.getAllMessagesByChatId(2, 2)

        assertEquals(messages, result)
    }

    @Test(expected = NotFoundException::class)
    fun getAllMessagesByChatIdExceptionTest() {
        ChatService.getAllMessagesByChatId(1, 1)
    }
}