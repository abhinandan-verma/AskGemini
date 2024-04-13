package com.abhinandan.askgemini.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abhinandan.askgemini.navigation.Screen

sealed class NavRoute(val path: String) {

    data object Login: NavRoute("login")
    data object Sign: NavRoute("sign")
    data object SignUp: NavRoute("signup"){
        const val id = "id"
    }
    data object ForgotPassword: NavRoute("forgot-password")
    data object ResetPassword: NavRoute("reset-password")
    data object VerifyEmail: NavRoute("verify-email")


    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach {
                append("/$it")
            }
        }
    }

    // build and setup route format (in navigation graph)
    fun withArgsFormat(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach {
                append("{$it}")
            }
        }
    }
}


fun NavGraphBuilder.addDestination(screen: NavRoute) {
    composable(screen.path) {
        when(screen){
           NavRoute.Sign -> {
               // add sign screen
           }
            NavRoute.Login -> {

            }
            NavRoute.SignUp -> {

            }
            NavRoute.VerifyEmail ->{

            }
            NavRoute.ForgotPassword -> {

            }
            NavRoute.ResetPassword -> {

            }
        }
    }
}

