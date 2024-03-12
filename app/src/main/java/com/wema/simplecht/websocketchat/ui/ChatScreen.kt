package com.wema.simplecht.websocketchat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wema.simplecht.ui.theme.SimpleChtTheme
import com.wema.simplecht.websocketchat.data.ChatDirection
import com.wema.simplecht.websocketchat.data.ChatModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UserChat(
    webSocketViewModel: WebSocketViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by webSocketViewModel.uiState.collectAsState()
    var message by remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = modifier
        .fillMaxSize()
        .clickable {
            keyboardController?.hide()
            focusRequester.requestFocus()
        }) {
        Text(
            text = "Simple Chat",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        )

        //chats

        Box(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(top = 45.dp)
                .background(Color.White)
        ) {
            LazyColumn {
                uiState.receivedMessages?.let { chats ->
                    var prevUserId: Int? = null
                    items(chats) { chat ->
                        if (chat != null) {
                            ChatMessage(
                                direction = if (chat.userId == uiState.userId) ChatDirection.RIGHT else ChatDirection.LEFT,
                                chat = chat
                            )
                            if (prevUserId != null && prevUserId != chat.userId) {
                                ChatAppendables(
                                    direction = if (chat.userId == uiState.userId) ChatDirection.RIGHT else ChatDirection.LEFT,
                                    chat = chat
                                )
                            }
                            prevUserId = chat.userId
                        }
                    }
                }
            }
        }



        //input field
        Box(contentAlignment = Alignment.BottomCenter, modifier = modifier.fillMaxSize()) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 12.dp)
            ) {
                TextField(
                    shape = RoundedCornerShape(8.dp),
                    value = message,
                    trailingIcon = {
                        IconButton(onClick = {
                            webSocketViewModel.sendData(message)
                            focusRequester.requestFocus()
                            message = ""
                        }) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send",
                                modifier = modifier.size(30.dp)
                            )
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(onSend = {
                        webSocketViewModel.sendData(message)
                        focusRequester.requestFocus()
                        message = ""
                    }),
                    placeholder = {
                        Text(text = "Type your message here")
                    },
                    onValueChange = { message = it },
                    modifier = modifier
                        .weight(2f)
                        .focusRequester(focusRequester),
                )

            }
        }
    }
}

@Composable
fun ChatMessage(direction: ChatDirection, chat: ChatModel, modifier: Modifier = Modifier) {
    Row {
        Column(
            horizontalAlignment = if (direction == ChatDirection.RIGHT) Alignment.End else Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 12.dp, end = 12.dp)
        ) {
            Surface(
                shape = if (direction == ChatDirection.RIGHT) {
                    RoundedCornerShape(
                        topStart = 20.dp, topEnd = 20.dp, bottomStart = 20.dp
                    )
                } else {
                    RoundedCornerShape(
                        topStart = 20.dp, topEnd = 20.dp, bottomEnd = 20.dp
                    )
                },
                color = if (direction == ChatDirection.RIGHT) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.tertiaryContainer,
                modifier = modifier.wrapContentSize()
            ) {
                Text(text = "${chat.message}", modifier = modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun ChatAppendables(direction: ChatDirection, chat: ChatModel, modifier: Modifier = Modifier){
    Column(
        horizontalAlignment = if (direction == ChatDirection.RIGHT) Alignment.End else Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 12.dp)
    ) {
        Text(
            text = "user: ${chat.userId}",
            textAlign = if (direction == ChatDirection.RIGHT) TextAlign.Right else TextAlign.Left,
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserChatPreview() {
    val chat =  ChatModel(
        userId = 19203,
        userName = "John Doe",
        message = "Weloome world"
    )
    SimpleChtTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ChatMessage(direction = ChatDirection.LEFT, chat = chat)
            ChatMessage(direction = ChatDirection.LEFT, chat = chat)
        }
    }
}