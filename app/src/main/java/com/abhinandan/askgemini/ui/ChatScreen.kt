package com.abhinandan.askgemini.ui

import android.widget.ProgressBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.abhinandan.askgemini.room.Chat
import com.abhinandan.askgemini.viewmodels.ChatViewModel
import com.abhinandan.askgemini.utils.CustomToolbar


@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel,
) {

    val lazyState = rememberLazyListState()
    
    Scaffold (
        topBar = {
            CustomToolbar(title = "Ask Gemini") {
                chatViewModel.getAllChats()
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    chatViewModel.upsertChat(Chat(5, "Ask me a question 2", true),)
                    chatViewModel.getAllChats()
                },
                shape = RoundedCornerShape(10.dp),
                containerColor = Color.Green
            ) {

            }
        }
    ){

        val chatList: List<Chat> by chatViewModel.chatList.observeAsState(initial = emptyList())

        if (chatList.isNotEmpty()){
            LazyColumn (
                Modifier.padding(it),
                state = lazyState
            ){
                items(items = chatList){chat ->

                    Card(
                        Modifier.padding(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Cyan,
                            contentColor = Color.Black
                        )
                    ){
                        Text(
                            text = chat.prompt,
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }else{
            Column(
                Modifier.padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "No Chats Found")
                LinearProgressIndicator()
                CircularProgressIndicator()
                ProgressBar(LocalContext.current)
            }
        }
    }
}