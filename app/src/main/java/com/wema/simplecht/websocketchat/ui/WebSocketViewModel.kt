package com.wema.simplecht.websocketchat.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.wema.simplecht.websocketchat.data.ChatModel
import com.wema.simplecht.websocketchat.data.utils.NotificationUtil
import com.wema.simplecht.websocketchat.data.webSocketEndpoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import kotlin.random.Random

data class UserState(
    var userId: Int = 0,
    val userName: String = "",
    val receivedMessages: MutableList<ChatModel?>? = mutableListOf(),
    var isLoading: Boolean = false
)

// In your ViewModel
class WebSocketViewModel(application: Application) : AndroidViewModel(application) {
    private val webSocketClient: WebSocketClient = MyWebSocketClient()
    private val notificationUtil: NotificationUtil = NotificationUtil()

    private val _uiState = MutableStateFlow(UserState())
    val uiState: StateFlow<UserState> = _uiState.asStateFlow()

    init {
        connectWebSocket()
        generateUserId()
    }

    private fun generateUserId() {
        _uiState.update {
            it.copy(userId = generateRandomNumber(), userName = generateRandomString())
        }
    }

    private fun generateRandomNumber(): Int {
        return Random.nextInt(10000, 100000)
    }

    private fun generateRandomString(length: Int = 10): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    private fun connectWebSocket() {
        webSocketClient.connect()
    }

    fun sendData(message: String) {
        if (webSocketClient.isOpen) {
            if (message.isNotEmpty()) {
                val chat = ChatModel(
                    userId = _uiState.value.userId,
                    userName = _uiState.value.userName,
                    message = message
                )
                webSocketClient.send(chat.toString())
                updateLoading(true)
            }

        } else {
            // Handle WebSocket not open
        }
    }

    private fun updateLoading(status: Boolean) {
        _uiState.update { it.copy(isLoading = status) }
    }

    private fun parseChatModelFromString(string: String): ChatModel? {
        val startIndex = string.indexOf('(')
        val endIndex = string.lastIndexOf(')')
        if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
            return null
        }

        val propertiesString = string.substring(startIndex + 1, endIndex)
        val propertyPairs = propertiesString.split(", ")
        val propertiesMap = mutableMapOf<String, String>()

        for (pair in propertyPairs) {
            val (key, value) = pair.split("=")
            propertiesMap[key] = value
        }

        val userId = propertiesMap["userId"]?.toIntOrNull()
        val userName = propertiesMap["userName"]
        val message = propertiesMap["message"]
        val date = propertiesMap["date"]

        return if (userId != null && userName != null && message != null) {
            ChatModel(userId, userName, message, date)
        } else {
            null
        }
    }

    inner class MyWebSocketClient: WebSocketClient(URI(webSocketEndpoint)) {
        override fun onOpen(handshakedata: ServerHandshake?) {
            // Handle open event
            Log.d("TestingSocket", "Open")
        }

        override fun onClose(code: Int, reason: String?, remote: Boolean) {
            // Handle close event
            Log.d("TestingSocket", "Closed")
        }

        override fun onMessage(message: String?) {
            if (message != null) {
                updateLoading(true)
                val chat = parseChatModelFromString(message)
                val updatedMessages = _uiState.value.receivedMessages?.toMutableList()?.apply { add(chat) }
                _uiState.update {
                    it.copy(receivedMessages = updatedMessages)
                }
                updateLoading(false)
                if (chat != null) {
                    notificationUtil.displayNotification(title = "You have a new message", content = chat.message, context = getApplication())
                }

                Log.d("Testingsocket", "newmessage $message")
            }
        }


        override fun onError(ex: Exception?) {
            // Handle error event
            if (ex != null) {
                Log.d("TestingSocket", "error: ${ex.message}")
            }
        }
    }
}
