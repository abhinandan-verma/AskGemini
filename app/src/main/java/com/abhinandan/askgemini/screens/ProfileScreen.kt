package com.abhinandan.askgemini.screens

import android.content.Intent
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhinandan.askgemini.R
import com.abhinandan.askgemini.auth.LoginActivity
import com.abhinandan.askgemini.getActivity
import com.abhinandan.askgemini.ui.GeminiLoad
import com.abhinandan.askgemini.ui.theme.BluePrimary
import com.abhinandan.askgemini.ui.theme.BlueSecondary
import com.google.firebase.auth.FirebaseAuth
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
            SignedUpProfile()

        }

    }
}

@Composable
fun SignedUpProfile() {

    val context = LocalContext.current
    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(5.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile",
            modifier = Modifier
                .clip(CircleShape)

        )



        Text(
            text = "Hello Abhinandan",
            color = Color.Yellow,
            fontSize = 30.sp,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextCard(
            color = BlueSecondary,
            text = "Id: 1234567890",
            size = 15
        )

        TextCard(
            color = BluePrimary,
            text = "Email: email@gmail.com",
            size = 15
        )

        TextCard(
            color = BlueSecondary,
            text = "AI Token: 1234567890",
            size = 15
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Red),
            modifier = Modifier.width(200.dp)
        ) {
            Text(text = "Edit Profile", color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Green),
            modifier = Modifier.width(300.dp)
        ) {
            Text(text = "Share the App", color = Color.Black )
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(
            onClick = {
                val auth = FirebaseAuth.getInstance()
                auth.signOut()
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
                context.getActivity()?.finish()
            },
            Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Sign Out",
                color = Color.Red,
                fontSize = 20.sp,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.ExtraBold
            )
        }

    }
}

@Composable
fun NewUserProfile() {

    val context  = LocalContext.current

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
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text ="Login", color = Color.Black)
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

@Composable
fun TextCard(
    modifier: Modifier = Modifier,
    color: Color,
    text: String,
    size:Int,

) {
    Card(
        modifier = modifier
            .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(color),
        onClick = {
//            Log.d("TextCard", "TextCard: $text")
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceAround,
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(10.dp),
                fontSize = size.sp
            )
        }
    }
}