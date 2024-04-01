package com.abhinandan.askgemini.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhinandan.askgemini.GenerateUiState
import com.abhinandan.askgemini.R
import com.abhinandan.askgemini.room.Chat
import com.abhinandan.askgemini.ui.GeminiLoad
import com.abhinandan.askgemini.utils.horizontalBackground
import com.abhinandan.askgemini.utils.linearBackground2
import com.abhinandan.askgemini.utils.toast
import com.abhinandan.askgemini.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class
)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    generateUiState: GenerateUiState = GenerateUiState.Initial,
    onGenerateClicked: (String) -> Unit = {},
    drawerState: DrawerState,
    startTime: Long
) {

    var prompt by remember { mutableStateOf("") }
    val time = remember {
        startTime
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        GeminiLoad(Modifier.size(63.dp))
                        Text(
                            text = "Main",
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                           scope.launch (Dispatchers.IO){
                                 drawerState.open()
                           }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow),
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        bottomBar = {
            OutlinedTextField(
                value = prompt,
                onValueChange = { prompt = it },
                label = {
                        Text(
                        text = "Prompt",
                        fontSize = 16.sp
                    )
                },
                placeholder = {
                    Text(
                        text = "Enter a prompt",
                        fontSize = 16.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.Transparent, RoundedCornerShape(8.dp)),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (prompt.isNotEmpty() &&  prompt.isNotBlank()) {
                                onGenerateClicked(prompt)
                                val chat = Chat(
                                    id = 0,
                                    prompt = prompt,
                                    fromUser = true,
                                    timeStamp = System.currentTimeMillis()
                                )
                                mainViewModel.upsertChat(chat, time)
                                prompt = ""
                            }else{
                                toast(context =context, message = "Please enter a prompt")
                            }

                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color.Transparent, CircleShape),
                        enabled = prompt.isNotEmpty()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.gembtn),
                            contentDescription = "Generate",
                            tint = if (prompt.isNotEmpty()) Color.Green else Color.Gray
                        )
                    }
                }
            )
        },

    ) {padding ->

        val mainList = mainViewModel.allChats.collectAsState(initial = emptyList())

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.Bottom
        ) {

            items(items = mainList.value){chat ->

                Card(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .background(
                            brush = if (chat.fromUser) {
                                linearBackground2
                            } else {
                                horizontalBackground
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

            when(generateUiState){
                is GenerateUiState.Loading -> {
                    item {
                        LinearProgressIndicator(
                            modifier = Modifier.padding(top = 20.dp)
                        )
                    }
                }
                is GenerateUiState.Success -> {
                    mainViewModel.getAllChatsFromCurrent(time = time)
                }
                is GenerateUiState.Error -> {
                    item {
                        GeminiLoad(Modifier.size(63.dp))
                    }
                }
                is GenerateUiState.Initial -> {
                    item {
                       Text(text = "Welcome to Gemini", fontSize = 20.sp)
                    }
                    mainViewModel.getAllChatsFromCurrent(time)
                }
            }
        }
    }




}
