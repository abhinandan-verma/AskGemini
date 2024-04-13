package com.abhinandan.askgemini.auth.signup

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.abhinandan.askgemini.MainActivity
import com.abhinandan.askgemini.auth.User
import com.abhinandan.askgemini.getActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


val database = Firebase.database
val reference = database.getReference("Users")

@OptIn(DelicateCoroutinesApi::class)
fun signUpUser(name: String, email: String, password: String, context: Context) {
    val auth = Firebase.auth
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(ContentValues.TAG, "createUserWithEmail:success")
                val user = auth.currentUser
                updateUI(user, context)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                Toast.makeText(
                    context,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT,
                ).show()
                updateUI(null, context)
            }
        }.addOnSuccessListener {
            Log.d("msg", "User signed up Successfully")
            Toast.makeText(context, "You Signed Up Successfully", Toast.LENGTH_SHORT).show()

            val user = User(
                username = name,
                email = email,
                password = password,
            )

            reference.child(email.removeSuffix(".com")).setValue(user).addOnSuccessListener {

                Log.d("msg", "User $name document created successfully in realtime db")

                GlobalScope.launch {

                    // delay to download the data from the server
                    delay(1000)
                    val intent = Intent(context, MainActivity::class.java).apply {
                        putExtra("PATH2", email.removeSuffix(".com"))
                    }
                    context.startActivity(intent)
                }

            }.addOnFailureListener {
                Log.d("msg", "User $name document creation failed")
            }

        }
}

fun updateUI(user: FirebaseUser?, context: Context) {
    Log.d("tag","Updating... for ${user.toString()}")
    Toast.makeText(context,"Updating for ${user.toString()}",Toast.LENGTH_SHORT).show()
    if (user != null) {
        Log.d("tag","Updating... for ${user.toString()}")
        Toast.makeText(context,"Updating for ${user.toString()}",Toast.LENGTH_SHORT).show()
    }
}