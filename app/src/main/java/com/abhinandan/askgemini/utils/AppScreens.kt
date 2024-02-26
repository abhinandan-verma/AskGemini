package com.abhinandan.askgemini.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppScreens (
    val route: String,
    val title: String,
    val icon: ImageVector
){

    data object ChatScreen: AppScreens(
        route = "chat",
        title = "Chat",
        icon = Icons.Default.Home
    )

    data object HistoryScreen: AppScreens(
        route = "history",
        title = "History",
        icon = Icons.Default.Menu
    )

    data object ProfileScreen: AppScreens(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Menu
    )

    fun routeWithArgs(vararg args: String ): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}