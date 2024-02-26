package com.abhinandan.askgemini.screens

import android.widget.ProgressBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhinandan.askgemini.R
import com.abhinandan.askgemini.room.Chat
import com.abhinandan.askgemini.viewmodels.HistoryViewModel
import com.abhinandan.askgemini.ui.GeminiLoad
import com.abhinandan.askgemini.ui.NoHistory
import com.abhinandan.askgemini.utils.toast
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    drawerState: DrawerState =  rememberDrawerState(initialValue = DrawerValue.Closed),
    historyViewModel: HistoryViewModel
) {

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val lazyState = rememberLazyListState()

    val query by remember {
        mutableStateOf("")
    }

   Scaffold (
       topBar = {
           TopAppBar(
               title = {

                   Column {
                       Row(
                           verticalAlignment = Alignment.CenterVertically
                       ) {
                           GeminiLoad(modifier = Modifier.size(90.dp))
                           Text(text =" History", color = Color.Green)
                       }
                   }

               },
               navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(
                            painterResource(id = R.drawable.arrow),
                            tint = Color.Green,
                            contentDescription = "Localized description"
                        )
                    }
               },
               actions = {

                   IconButton(
                       onClick = {
                           historyViewModel.deleteAllChat()
                           toast(context, "History Cleared")
                           historyViewModel.getAllChats()
                       }
                   ) {
                       Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete All Chat History",
                            modifier = Modifier
                                .size(34.dp)
                                .background(
                                    shape = CircleShape,
                                    color = Color.Yellow
                                ),
                            tint = Color.Red
                       )
                   }
               },
               colors = TopAppBarDefaults.topAppBarColors(
                   Color.Black
               )
           )
       }
   ){

       val historyList: List<Chat> by historyViewModel.historyList.observeAsState(initial = emptyList())

       Column(
           modifier = Modifier
               .padding(it)
               .fillMaxSize()
       ){

           if (historyList.isNotEmpty()) {
               LazyColumn(
                   Modifier
                       .wrapContentHeight()
                       .align(Alignment.End)
                       .padding(5.dp),
                   state = lazyState
               ) {
                   items(items = historyList) { chat ->

                       Card(
                           Modifier
                               .wrapContentHeight()
                               .padding(top = 7.dp, bottom = 7.dp, start = 10.dp, end = 10.dp)
                               .fillMaxWidth(),
                           colors = CardDefaults.cardColors(
                               containerColor = if(chat.fromUser) Color.Cyan else Color. White,
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
                       }
                   }
               }
           }else{
               Column(
                   Modifier
                       .wrapContentHeight()
                       .fillMaxWidth()
                       .padding(it),
                   horizontalAlignment = Alignment.CenterHorizontally
               ) {

                   Text(text = "No Previous Chats Found")

                   GeminiLoad(
                       rawFile = R.raw.nohist,
                       speed = 1.6f
                   )

               }
           }
       }

   }
}