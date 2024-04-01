package com.abhinandan.askgemini.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abhinandan.askgemini.R
import com.abhinandan.askgemini.room.Chat
import com.abhinandan.askgemini.ui.GeminiLoad
import com.abhinandan.askgemini.ui.theme.BluePrimary
import com.abhinandan.askgemini.utils.linearBackground
import com.abhinandan.askgemini.utils.linearBackground2
import com.abhinandan.askgemini.viewmodels.ChatViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {

    val messages by viewModel.messageList.collectAsState(initial = emptyList())
    val messageText = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    GeminiLoad(modifier = Modifier.size(70.dp))
                },
                navigationIcon = {
                    IconButton(onClick = {
                       scope.launch {
                            drawerState.open()
                       }
                    }) {
                        Icon(
                          painter = painterResource(id = R.drawable.arrow),
                            contentDescription = "Menu",
                            tint = BluePrimary
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
                .fillMaxSize()
        ) {
            Surface(
                modifier = Modifier.weight(1f),
            ) {
                // Display chat messages

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = rememberLazyListState()
                ) {
                    items(messages) { chat ->
                        ChatItem(chat = chat)
                    }
                }
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                OutlinedTextField(
                    value = messageText.value,
                    onValueChange = {
                        messageText.value = it
                    },
                    label = { Text("Prompt") },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Type a message") }
                )
                IconButton(
                    onClick = {

                        messageText.value = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send"
                    )
                }
            }
        }
    }

}

@Composable
fun ChatItem(chat: Chat) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(
                brush = if (chat.fromUser) {
                    linearBackground2
                } else {
                    linearBackground
                },
                shape = RoundedCornerShape(6)
            ),
        colors = CardDefaults.cardColors(Color.Transparent)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = chat.prompt, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = chat.timeStamp.toString(), style = MaterialTheme.typography.bodyMedium)
        }
    }
}
