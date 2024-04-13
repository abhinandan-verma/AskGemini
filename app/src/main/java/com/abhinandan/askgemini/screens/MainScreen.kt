package com.abhinandan.askgemini.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
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
import com.abhinandan.askgemini.ui.theme.BlueSecondary
import com.abhinandan.askgemini.utils.SpeechRecognizerContract
import com.abhinandan.askgemini.utils.horizontalBackground
import com.abhinandan.askgemini.utils.linearBackground2
import com.abhinandan.askgemini.utils.toast
import com.abhinandan.askgemini.viewmodels.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import de.charlex.compose.BottomAppBarSpeedDialFloatingActionButton
import de.charlex.compose.FloatingActionButtonItem
import de.charlex.compose.SubSpeedDialFloatingActionButtons
import de.charlex.compose.rememberSpeedDialFloatingActionButtonState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class
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

    if(mainViewModel.state.text != null){
        prompt = mainViewModel.state.text
            .toString().removePrefix("[").removeSuffix("]")
        mainViewModel.state.text = null
    }

    val fabState = rememberSpeedDialFloatingActionButtonState()

    // Speech Recognition

    val permissionState = rememberPermissionState(
        permission = Manifest.permission.RECORD_AUDIO
    )
    SideEffect {
        permissionState.launchPermissionRequest()
    }

    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = SpeechRecognizerContract(),
        onResult = {
            mainViewModel.changeTextValue(it.toString())

        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                   Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center

                   ){
                        GeminiLoad(Modifier.size(83.dp))
                        Text(
                            text = " Main",
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

            BottomAppBar(
                contentColor = Color.White,
                containerColor = Color.Transparent,
                contentPadding = PaddingValues(1.dp)
            ) {
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
                        .padding(8.dp)
                        .background(Color.Transparent, RoundedCornerShape(8.dp)),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if (prompt.isNotEmpty() && prompt.isNotBlank()) {
                                    onGenerateClicked(prompt)
                                    val chat = Chat(
                                        id = 0,
                                        prompt = prompt,
                                        fromUser = true,
                                        timeStamp = System.currentTimeMillis()
                                    )
                                    mainViewModel.upsertChat(chat, time)
                                    prompt = ""
                                } else {
                                    toast(context = context, message = "Please enter a prompt")
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

                BottomAppBarSpeedDialFloatingActionButton(
                    state = fabState,
                    containerColor = Color.Cyan,
                    shape = CircleShape,
                    contentColor = Color.Black,
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.gembtn),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        },

        floatingActionButton = {
                SubSpeedDialFloatingActionButtons(
                    items = listOf(
                        FloatingActionButtonItem(
                            icon = Icons.Default.MicNone,
                            label = "Speak"
                        ) {
                            if (permissionState.status.isGranted) {
                                speechRecognizerLauncher.launch(Unit)
                            } else
                                permissionState.launchPermissionRequest()
                        },
                        FloatingActionButtonItem(
                            icon = Icons.Default.Camera,
                            label = "Scan"
                        ) {
                            if (permissionState.status.isGranted) {
                                speechRecognizerLauncher.launch(Unit)
                            } else
                                permissionState.launchPermissionRequest()
                        },
                    ),
                    state = fabState
                ) {

                }
        }

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
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            GeminiLoad(
                                rawFile = R.raw.gpt,
                                modifier = Modifier.size(70.dp),
                                speed = 2F
                            )
                            Text(
                                text = "Generating response...",
                                fontSize = 20.sp,
                                color = Color.White,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                    }
                }
                is GenerateUiState.Success -> {
                    mainViewModel.getAllChatsFromCurrent(time = time)
                }
                is GenerateUiState.Error -> {
                    item {

                        Card(
                            colors = CardDefaults.cardColors(Color.Red),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Text(
                                text = "Error: ${generateUiState.errorMessage}",
                                fontSize = 20.sp,
                                color = Color.Red,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                    }
                }
                is GenerateUiState.Initial -> {
                    item {
                       Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()

                       ){
                            GeminiLoad(
                                Modifier.size(103.dp),
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                text = "How can I help you, today?",
                                fontSize = 20.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .background(BlueSecondary, RoundedCornerShape(15.dp))
                                    .padding(8.dp)
                            )
                       }
                    }
                    mainViewModel.getAllChatsFromCurrent(time)
                }
            }
        }
    }




}
