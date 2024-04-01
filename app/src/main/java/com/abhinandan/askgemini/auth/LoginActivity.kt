package com.abhinandan.askgemini.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abhinandan.askgemini.R
import com.abhinandan.askgemini.auth.ui.theme.AskGeminiTheme
import com.abhinandan.askgemini.ui.GeminiLoad

class LoginActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AskGeminiTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Row(
                                    horizontalArrangement = Arrangement.Absolute.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ){
                                    GeminiLoad(Modifier.size(120.dp))
                                }

                            }
                        )
                    }
                ) { innerPadding ->
                  Box(
                          modifier = Modifier
                              .fillMaxSize()
                              .padding(innerPadding)
                              .background(
                                  brush = Brush.linearGradient()
                              )
                   ) {

                       Image(
                           painter = painterResource(id = R.drawable.loginbackground),
                           contentDescription = "Login Background",
                           modifier = Modifier
                               .fillMaxSize(),
                           alpha = 0.8f,
                           contentScale = ContentScale.FillBounds
                       )


                   }
                }
            }
        }
    }
}
