package com.abhinandan.askgemini.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.abhinandan.askgemini.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
fun GeminiLoad(
    modifier: Modifier = Modifier,
    rawFile:Int = R.raw.gemini,
    speed: Float = 1f
    ) {
    val progress = remember {
        Animatable(0f)
    }

    LaunchedEffect(Unit) {
        progress.animateTo(1f, animationSpec = tween(durationMillis = 1000))
    }

    Box(
        modifier = modifier
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resId = rawFile))

        val progressState = animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true,
            speed = speed,
            reverseOnRepeat = true,
            restartOnPlay = true,
        )
        LottieAnimation(
            composition = composition,
            progress = progressState.progress,
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center)
        )

    }
}


// animation for No History

@Composable
fun NoHistory(
    modifier: Modifier = Modifier
) {
    val progress = remember {
        Animatable(0f)
    }

    LaunchedEffect(Unit) {
        progress.animateTo(1f, animationSpec = tween(durationMillis = 1000))
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resId = R.raw.nohist))

        val progressState = animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true,
            speed = 1f,
            reverseOnRepeat = true,
            restartOnPlay = true,
        )
        LottieAnimation(
            composition = composition,
            progress = progressState.progress,
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center)
        )
    }
}