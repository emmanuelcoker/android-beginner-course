package com.wema.simplecht.websocketchat.data

data class ChatModel(
    val userId: Int = 0,
    val userName: String = "",
    val message: String = "",
    val date: String? = null
)

enum class ChatDirection {
    LEFT,
    RIGHT
}
