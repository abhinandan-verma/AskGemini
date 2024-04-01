package com.abhinandan.askgemini.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abhinandan.askgemini.utils.linearBackground
import com.abhinandan.askgemini.utils.linearBackground2

@Composable
fun CurvedBottomBar(modifier: Modifier = Modifier) {
    BottomAppBar(
        modifier = modifier
            .fillMaxWidth()
            .background(linearBackground2)
    ) {

    }

}