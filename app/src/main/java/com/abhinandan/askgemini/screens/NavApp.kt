package com.abhinandan.askgemini.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhinandan.askgemini.R
import com.abhinandan.askgemini.ui.GeminiLoad
import com.abhinandan.askgemini.viewmodels.ChatViewModel
import com.abhinandan.askgemini.viewmodels.HistoryViewModel
import com.abhinandan.askgemini.viewmodels.MainViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavApp(
    chatViewModel: ChatViewModel = viewModel(),
    mainViewModel: MainViewModel = viewModel(),
    historyViewModel: HistoryViewModel = viewModel(),
    startTime: Long = remember {
        System.currentTimeMillis()
    }
) {

    val navigationItemList = listOf(
        NavigationItem(
            title = "Home",
            route = "home",
            icon = Icons.Default.Home
        ),
        NavigationItem(
            title = "Profile",
            route = "profile",
            icon = Icons.Default.Person
        ),
        NavigationItem(
            title = "Settings",
            route = "settings",
            icon = Icons.Default.Settings
        ),
        NavigationItem(
            title = "History",
            route = "history",
            icon = Icons.Default.Info
        ),
        NavigationItem(
            title = "Chat",
            route = "chat",
            icon = Icons.Default.Email
        )
    )
        val generateUiState by mainViewModel.generativeUiState.collectAsState()

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }

        val navController = rememberNavController()

    ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
               ModalDrawerSheet(
                    drawerContentColor = Color.Cyan,
                    drawerContainerColor = Color.Black,
                   drawerShape = RoundedCornerShape(10.dp)
                ) {
                   Row {
                       Column(
                           modifier = Modifier
                               .fillMaxHeight()
                               .wrapContentWidth()
                               .padding(top = 12.dp, end = 12.dp),
                           verticalArrangement = Arrangement.spacedBy(12.dp),
                           horizontalAlignment = Alignment.CenterHorizontally
                       ) {

                           Row(
                               verticalAlignment = Alignment.CenterVertically,
                           ) {
                               Text(
                                   text = "Ask",
                                   fontSize = 32.sp
                               )
                               GeminiLoad(modifier = Modifier.size(108.dp))
                           }


                           navigationItemList.forEachIndexed { index, item ->
                               NavigationDrawerItem(
                                   label = {
                                       Row(
                                           horizontalArrangement = Arrangement.SpaceAround,
                                       ) {
                                           Icon(
                                               imageVector = item.icon, contentDescription = "icon",
                                               modifier = Modifier.align(Alignment.CenterVertically)
                                           )
                                           Text(
                                               text = " ${item.title}",
                                               color = Color.Black,
                                           )
                                       }
                                   },
                                   selected = index == selectedItemIndex,
                                   onClick = {
                                       selectedItemIndex = index
                                       scope.launch {
                                           drawerState.close()
                                       }
                                       navController.navigate(item.route)
                                   },
                                   icon = {
                                       item.icon
                                   },
                                   badge = {
                                       if (item.hasNotification) {
                                           Text(
                                               text = "1",
                                               color = Color.White,
                                               modifier = Modifier.background(
                                                   shape = CircleShape,
                                                   color = Color.Red
                                               )
                                           )
                                       }
                                   },
                                   modifier = Modifier
                                       .width(200.dp),
                                   colors = NavigationDrawerItemDefaults.colors(
                                       selectedContainerColor = Color.Cyan,
                                       unselectedContainerColor = Color.White,
                                       unselectedIconColor = Color.Black,
                                       selectedIconColor = Color.Red,
                                       unselectedTextColor = Color.Gray,
                                       selectedTextColor = Color.Black
                                   )
                               )
                           }
                       }

                       IconButton(
                           onClick = {
                               scope.launch {
                                      drawerState.close()
                               }
                           }
                       ) {
                           Icon(
                               painter = painterResource(id = R.drawable.arrow),
                               contentDescription = "Close Drawer",
                               modifier = Modifier
                                   .rotate(180f)
                           )
                       }
                   }
                }
            }
        ) {

            TopAppBar(
                title = {
                    TopAppBar(
                        title = {
                            Row {
                                Text(text ="Ask Gemini")
                                GeminiLoad(modifier = Modifier.size(90.dp))
                            }
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }


                                }
                            ) {
                                Icon(
                                    Icons.Default.Menu,
                                    contentDescription = "Localized description"
                                )
                            }
                        }
                    )
                }
            )

            NavHost(
                navController = navController,
                startDestination = "home",
                contentAlignment = Alignment.CenterStart
            ) {
                composable("home") {
                    MainScreen(
                       mainViewModel = mainViewModel,
                        generateUiState = generateUiState,
                        onGenerateClicked = {inputText ->
                            mainViewModel.generateResponse(inputText, startTime)
                        },
                        drawerState = drawerState,
                        startTime = startTime
                    )
                }

                composable("profile") {
                    ProfileScreen(
                        drawerState = drawerState
                    )
                }

                composable("settings") {
                    SettingsScreen(
                        drawerState = drawerState,
                        scope = scope
                    )
                }

                composable("history") {
                    HistoryScreen(
                        drawerState = drawerState,
                        historyViewModel = historyViewModel
                    )
                }

                composable("chat") {
                    ChatScreen(
                        viewModel = chatViewModel,
                        drawerState = drawerState
                    )
                }
            }
        }
    }




data class NavigationItem(
    val title: String,
    val route: String,
    val icon: ImageVector,
    val hasNotification: Boolean = false
)