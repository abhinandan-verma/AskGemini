package com.abhinandan.askgemini.navigation

sealed class Screen(val route: String)  {

    data object ChatScreen : Screen("chat")

    data object HistoryScreen : Screen("history")

    data object ProfileScreen: Screen("profile")


}