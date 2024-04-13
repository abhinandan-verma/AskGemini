package com.abhinandan.askgemini.auth.login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun Login() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {


        var progress by remember {
            mutableStateOf(false)
        }
        var progressVal by remember {
            mutableDoubleStateOf(0.0)
        }



        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            val context = LocalContext.current

            var username by remember {
                mutableStateOf("")
            }

            var phoneNumber by remember {
                mutableStateOf("")
            }

            var otp by remember {
                mutableStateOf(TextFieldValue(""))
            }
            var isOtpVisible by remember {
                mutableStateOf(false)
            }
            var email by remember {
                mutableStateOf("")
            }
            var password by remember {
                mutableStateOf("")
            }
            var otpProgress by remember {
                mutableStateOf(false)
            }
            var passwordVisible by rememberSaveable { mutableStateOf(false) }

            LaunchedEffect(key1 = true){
                while (true){
                    delay(1400)
                    progressVal = (progressVal + 0.1f) % 1.0f
                }
            }

            Card {
                Column(
                    modifier = Modifier
                        .background(
                            Color.Black
                        )
                        .padding(16.dp)
                        .fillMaxWidth(1f)
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Login With Phone Number",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    OutlinedTextField(
                        value = username,
                        onValueChange = {
                            if (it.length <= 14) {
                                username = it
                            }
                        },
                        label = { Text("Username") },
                        colors = OutlinedTextFieldDefaults.colors(Color.White),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Phone",
                                tint = Color.White
                            )
                        }
                    )
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = {
                            if (it.length <= 14) {
                                phoneNumber = it
                            }
                        },
                        label = { Text("Phone Number") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        colors = OutlinedTextFieldDefaults.colors(Color.White),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Call,
                                contentDescription = "Phone",
                                tint = Color.White
                            )
                        }
                    )
                    if (isOtpVisible) {

                        otpProgress = true

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedTextField(
                                value = otp,
                                onValueChange = { otp = it },
                                label = { Text(text = "Enter OTP") },
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .padding(top = 8.dp),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(Color.White),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                            )
                        }
                    }
                    if (!isOtpVisible) {
                        Button(
                            onClick = {
                                onLoginClicked(context, phoneNumber, username) {
                                    Toast.makeText(context, "Sent Otp Visible", Toast.LENGTH_SHORT)
                                        .show()
                                    Log.d("Msg", "Sent OTP Visible")
                                    isOtpVisible = true
                                }
                            }, colors = ButtonDefaults.textButtonColors(
                                Color.Green
                            ),
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(top = 8.dp)
                        ) {
                            Text(text = "Send OTP", color = Color.Black)
                        }
                    } else {
                        Button(
                            onClick = {
                                verifyPhoneNumberWithCode(
                                    context,
                                    storedVerificationId,
                                    otp.text,
                                    username,
                                    phoneNumber
                                )
                                otpProgress = false
                                progress = true
                            },
                            colors = ButtonDefaults.textButtonColors(Color.Cyan),
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(8.dp)
                        ) {
                            Text(text = "Verify OTP", color = Color.Black)
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            Card {
                Column(
                    modifier = Modifier
                        .background(
                            Color.Black
                        )
                        .padding(16.dp)
                        .fillMaxWidth(1f)
                        .wrapContentHeight()
                    ,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Login with Email and Password",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(text = "Enter Email") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = OutlinedTextFieldDefaults.colors(Color.White),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = "Email",
                                tint = Color.White
                            )
                        }
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(text = "Enter Password") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = OutlinedTextFieldDefaults.colors(Color.White),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "Password",
                                tint = Color.White
                            )
                        },
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.VisibilityOff
                            else Icons.Filled.Visibility

                            // Please provide localized description for accessibility services
                            val description = if (passwordVisible) "Hide password" else "Show password"

                            IconButton(onClick = {passwordVisible = !passwordVisible}){
                                Icon(imageVector  = image, description)
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    )
                    Button(
                        onClick = {
                            progress = true
                            Log.d("msg", "Sign in Initiated")
                            signInWithEmail(email, password, context)
                        },
                        colors = ButtonDefaults.textButtonColors(
                            Color.Cyan
                        ),
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(top = 8.dp)
                    ) {
                        if (progress){
                            CircularProgressIndicator(
                                color = Color.Black,
                            )
                        }else{
                            Text(
                                text = "Login ",
                                color = Color.Black
                            )
                        }


                    }

                }
            }

        }
    }
}