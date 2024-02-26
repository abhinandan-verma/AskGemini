package com.abhinandan.askgemini.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.abhinandan.askgemini.utils.SpeechRecognizerContract
import com.abhinandan.askgemini.utils.linearBackground
import com.abhinandan.askgemini.utils.linearBackground2
import com.abhinandan.askgemini.utils.toast
import com.abhinandan.askgemini.viewmodels.ChatViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    chatViewModel: ChatViewModel,
    generateUiState: GenerateUiState = GenerateUiState.Initial,
    onGenerateClicked: (String) -> Unit = {},
    drawerState: DrawerState,
    startTime: Long
) {
    val time = remember {
        startTime
    }

    val lazyState = rememberLazyListState()
    val context = LocalContext.current

    var prompt by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    var selectedImages by remember { mutableStateOf(emptyList<Uri>()) }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(
           maxItems = 5 // max number of images to be selected
        )
    ) { uris ->
        selectedImages = uris
    }

    val permissionState = rememberPermissionState(
        permission = android.Manifest.permission.RECORD_AUDIO
    )
    SideEffect {
        permissionState.launchPermissionRequest()
    }

    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = SpeechRecognizerContract()
    ) {
        it?.get(0)?.let { it1 -> chatViewModel.changeTextValue(it1) }
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GeminiLoad(Modifier.size(90.dp))
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Chat",
                            color = Color.Cyan
                        )
                    }

                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(
                            painterResource(id = R.drawable.arrow),
                            contentDescription = "Menu",
                            tint = Color.Cyan
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(Color.Black)
            )
        },
        floatingActionButtonPosition = FabPosition.End,

        floatingActionButton = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalAlignment = Alignment.End
            ) {
                FloatingActionButton(
                    onClick = {
                       val request = PickVisualMediaRequest()
                        pickImageLauncher.launch(request
                       )
                    },
                    shape = CircleShape,
                    containerColor = Color.Cyan,
                    modifier = Modifier
                        .size(53.dp)
                        .align(Alignment.End)
                ) {
                    Icon(
                        painterResource(id = R.drawable.camera),
                        contentDescription = "Add",
                        tint = Color.Black,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                FloatingActionButton(
                    onClick = {
                              // launch the microphone and record the audio, convert it to text and send it to the server
                        if (permissionState.status.isGranted) {
                            speechRecognizerLauncher.launch(Unit)
                        }
                        else permissionState.launchPermissionRequest()
                    },
                    shape = CircleShape,
                    containerColor = Color.Magenta,
                    modifier = Modifier
                        .size(53.dp)
                        .align(Alignment.End)
                ) {
                    Icon(
                        painterResource(
                            id = R.drawable.micon
                        ),
                        contentDescription = "Delete",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        },

        bottomBar = {
            OutlinedTextField(
                modifier = Modifier
                    .padding(end = 5.dp, start = 5.dp)
                    .fillMaxWidth(),
                value = prompt,
                onValueChange = {
                    prompt = it
                },
                label = {
                    Text(text = "Prompt")
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (prompt.isNotBlank()) {
                                onGenerateClicked(prompt)
                                val chat = Chat(
                                    id = 0,
                                    prompt = prompt,
                                    fromUser = true,
                                    timeStamp = time
                                )
                                chatViewModel.upsertChat(chat)
                                chatViewModel.getAllChatsFromCurrent(time = time)
                                toast(context, "Prompt Requested")
                                prompt = ""
                            } else {
                                toast(context, "Please enter a prompt")
                            }

                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.gembtn),
                            contentDescription = "Gemini Icon",
                            tint = if (prompt == "") Color.Gray else Color.Green,
                            modifier = Modifier.size(34.dp)
                        )
                    }
                },
            )

        }
    ) {
        val chatList: List<Chat> by chatViewModel.chatList.observeAsState(initial = emptyList())
        Column(
            Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        )
        {

            Column(

            ) {
                if (chatList.isNotEmpty()){
                    LazyColumn(
                        Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(5.dp),
                        state = lazyState
                    ) {
                        items(items = chatList) { chat ->

                            if (chat.timeStamp >= (time)) {

                                Card(
                                    Modifier
                                        .wrapContentHeight()
                                        .fillMaxWidth()
                                        .align(
                                            Alignment.CenterHorizontally
                                            // if (chat.fromUser) Alignment.End else Alignment.Start
                                        )
                                        .padding(
                                            start = if (chat.fromUser) 30.dp else 10.dp,
                                            end = if (chat.fromUser) 10.dp else 30.dp,
                                            top = 10.dp,
                                            bottom = 10.dp
                                        ),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Transparent,
                                        contentColor = Color.Black
                                    )
                                ) {
                                    Text(
                                        text = chat.prompt,
                                        color = Color.Black,
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.padding(10.dp)
                                    )

                                    Text(
                                        text = chat.timeStamp.toString(),
                                        color = Color.Black,
                                        fontSize = 10.sp,
                                        modifier = Modifier.padding(start = 10.dp, bottom = 4.dp)
                                    )

                                    Text(
                                        text = time.toString(),
                                        color = Color.Black,
                                        fontSize = 10.sp,
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Column(
                        Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(it),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Start a chat with...", color = Color.Cyan)
                        GeminiLoad(
                            modifier = Modifier.size(63.dp),
                        )
                    }

                }
            }
            // the generateUiState is used to display the loading, error and success states
            when (generateUiState) {
                GenerateUiState.Initial -> {
                    if (chatList.isNotEmpty()) {
                        LazyColumn(
                            Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .padding(5.dp),
                            state = lazyState
                        ) {
                            items(items = chatList) { chat ->

                                if (chat.timeStamp >= (time)) {

                                    Card(
                                        Modifier
                                            .wrapContentHeight()
                                            .fillMaxWidth()
                                            .align(if (chat.fromUser) Alignment.End else Alignment.Start)
                                            .padding(
                                                start = if (chat.fromUser) 30.dp else 10.dp,
                                                end = if (chat.fromUser) 10.dp else 30.dp,
                                                top = 10.dp,
                                            )
                                            .background(
                                                brush = if(chat.fromUser) linearBackground else linearBackground2,
                                                shape = RoundedCornerShape(10.dp)
                                            ),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.Transparent,
                                            contentColor = Color.White
                                        )
                                    ) {
                                        Text(
                                            text = chat.prompt,
                                            color = Color.Black,
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.padding(10.dp)
                                        )

                                        Text(
                                            text = chat.timeStamp.toString(),
                                            color = Color.Black,
                                            fontSize = 10.sp,
                                            modifier = Modifier.padding(start = 10.dp, bottom = 4.dp)
                                        )

                                        Text(
                                            text = time.toString(),
                                            color = Color.Black,
                                            fontSize = 10.sp,
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        Column(
                            Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .padding(it),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Start a chat with...", color = Color.Cyan)
                            GeminiLoad(
                                modifier = Modifier.size(63.dp),
                            )
                        }
                    }

                }

                GenerateUiState.Loading -> {
                    Box(
                        contentAlignment = Alignment.BottomCenter,
                        modifier = Modifier
                            .padding(all = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Column {
                            LinearProgressIndicator(
                                color = Color.Cyan,
                                trackColor = Color.Magenta
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                GeminiLoad(
                                    modifier = Modifier.size(63.dp),
                                )
                                Text(
                                    text = " is Loading...",
                                    color = Color.Cyan
                                )
                                CircularProgressIndicator(
                                    modifier = Modifier.wrapContentSize()
                                )
                            }

                            LinearProgressIndicator(
                                color = Color.Yellow,
                                trackColor = Color.Green
                            )

                            Card (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp)
                                    .background(
                                        brush = linearBackground,
                                        shape = RoundedCornerShape(10.dp)
                                    ),
                                colors = CardDefaults.cardColors(
                                    contentColor = Color.White,
                                    containerColor = Color.Transparent
                                )
                            ){
                                Text(
                                    text = prompt,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Text(
                                    text = "Generated time: $time",
                                    color = Color.Black,
                                )
                            }

                        }
                    }
                }

                is GenerateUiState.Inserting -> {
                }

                is GenerateUiState.Success -> {
                    chatViewModel.getAllChatsFromCurrent(time = time)
                }

                is GenerateUiState.Error -> {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(all = 8.dp)
                        ) {

                            Icon(
                                imageVector = Icons.Filled.Warning,
                                contentDescription = "Warning",
                                tint = Color.Yellow,
                                modifier = Modifier.size(44.dp)
                            )
                            Text(
                                text = generateUiState.errorMessage,
                                color = Color.White,
                                modifier = Modifier.padding(all = 8.dp)
                            )
                        }
                    }

                }
            }
        }
    }
}