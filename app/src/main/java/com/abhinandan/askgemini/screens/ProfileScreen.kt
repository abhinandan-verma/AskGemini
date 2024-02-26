package com.abhinandan.askgemini.screens

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhinandan.askgemini.R
import com.abhinandan.askgemini.ui.GeminiLoad
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    drawerState: DrawerState =  rememberDrawerState(initialValue = DrawerValue.Closed),
) {

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            GeminiLoad(modifier = Modifier.size(90.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "Profile", color = Color.Yellow)
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
                            painterResource(id = R.drawable.arrow),
                            tint = Color.Yellow,
                            contentDescription = "Profile Screen Menu Icon"
                        )
                    }
                }
            )
        }
    ) {

        Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(it),
        ){

            Text(text = "Profile Screen", color = Color.Yellow)

            NewUserProfile()

        }

    }
}

@Composable
fun SignedUpProfile() {

}

@Composable
fun NewUserProfile() {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.warning),
            contentDescription = "Warning",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier =Modifier.height(20.dp))

        Text(
            text = "You are not signed in yet!!" +
                    "\n\nPlease sign in to continue.",
            color = Color.Yellow,
            fontSize = 27.sp,
        )

        Spacer(modifier =Modifier.height(20.dp))

        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text ="Sign In", color = Color.Black)
        }

        Spacer(modifier =Modifier.height(20.dp))

        // [OR]
        Text(text = "OR",)

        Spacer(modifier =Modifier.height(8.dp))

        Text(
            text = "If you are new to AskGemini," +
                    "\n     Sign up to continue. 👇🏻",
        )

        Spacer(modifier =Modifier.height(20.dp))

        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Magenta)
        ) {
            Text(text ="Sign Up", color = Color.White)
        }
    }
}