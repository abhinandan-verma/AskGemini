package com.abhinandan.askgemini.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhinandan.askgemini.R
import com.abhinandan.askgemini.ui.GeminiLoad
import com.abhinandan.askgemini.ui.theme.BluePrimary
import com.abhinandan.askgemini.ui.theme.BlueTertiary

@Preview
@Composable
fun SignUp(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // SignUp Form

        Image(
            painter = painterResource(id = R.drawable.gemini),
            contentDescription = "Gemini Logo",
            modifier = Modifier
                .size(330.dp)
        )

        GeminiLoad(
            modifier = Modifier.size(140.dp)
        )

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.White),
            modifier = Modifier.width(330.dp)
        ) {
            Row (
                
            ){
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google Logo",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Sign Up with Google",
                    color = Color.Black,
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.size(10.dp))

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(BluePrimary),
            modifier = Modifier.width(330.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.apple),
                    contentDescription = "Google Logo",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Sign Up with Apple",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.size(10.dp))

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(BluePrimary),
            modifier = Modifier.width(330.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "Google Logo",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Sign Up with Email",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.size(20.dp))

        Text(text = "Already have an account?",
            color = Color.Cyan,
            fontSize = 13.sp)

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Green),
            modifier = Modifier.width(330.dp)
        ) {
            Text(text = "Login", color = Color.Black)
        }

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = "By signing up, you agree to our Terms of Service and Privacy Policy",
            color = Color.White,
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
        )


    }
}