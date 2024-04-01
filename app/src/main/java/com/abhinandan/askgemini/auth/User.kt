package com.abhinandan.askgemini.auth

import android.provider.ContactsContract
import androidx.compose.ui.graphics.ImageBitmap
import com.abhinandan.askgemini.R

data class User(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val aiToken: String = "",
    val id: String = "",
    val profilePicture: ImageBitmap? = null,
    val isRegistered: Boolean = false,
    val isSignedIn: Boolean = false,
    val isSubscriber: Boolean = false,
)
