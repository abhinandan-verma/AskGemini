package com.abhinandan.askgemini.screens

import android.widget.ToggleButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abhinandan.askgemini.R
import com.abhinandan.askgemini.ui.GeminiLoad
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    drawerState: DrawerState =  rememberDrawerState(initialValue = DrawerValue.Closed),
    scope: CoroutineScope
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        GeminiLoad(modifier = Modifier.size(90.dp))
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(text = "Settings", color = Color.Magenta)
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch(Dispatchers.IO) {
                                drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            painterResource(id = R.drawable.arrow),
                            tint = Color.Magenta,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            Text(text = "Settings Screen")

        }
    }

}