package com.abhinandan.askgemini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.abhinandan.askgemini.screens.NavApp
import com.abhinandan.askgemini.viewmodels.ChatViewModel
import com.abhinandan.askgemini.viewmodels.HistoryViewModel
import com.abhinandan.askgemini.ui.theme.AskGeminiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val chatViewModel : ChatViewModel by viewModels()
    private  val historyViewModel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {

            val startTime = remember {
                System.currentTimeMillis()
            }

            AskGeminiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    NavApp(
                        chatViewModel = chatViewModel,
                        historyViewModel = historyViewModel,
                        startTime = startTime
                    )

                }
            }
        }
    }
}


