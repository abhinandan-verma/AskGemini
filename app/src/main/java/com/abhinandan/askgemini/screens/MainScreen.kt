package com.abhinandan.askgemini.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhinandan.askgemini.GenerateUiState
import com.abhinandan.askgemini.R
import com.abhinandan.askgemini.ui.GeminiLoad
import com.abhinandan.askgemini.viewmodels.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class,
    ExperimentalFoundationApi::class
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

                }
            )
        },
        bottomBar = {
            OutlinedTextField(
                value = prompt,
                onValueChange = { prompt = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.Transparent, RoundedCornerShape(8.dp)),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onGenerateClicked(prompt)
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
            reverseLayout = true,
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.Bottom
        ) {

            items(items = mainList.value){chat ->

                Card(
                    colors = CardDefaults.cardColors(Color.Gray)
                ){
                    Text(
                        text = chat.prompt,
                        fontSize = 20.sp
                    )
                    Text(
                        text = chat.timeStamp.toString(),
                        fontSize = 10.sp
                    )
                    Text(
                        text = time.toString(),
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}
