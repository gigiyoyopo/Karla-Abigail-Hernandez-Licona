package com.example.gigifai

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val darkMode = LocalDarkMode.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val bgColor = if (darkMode.value) Color.Black else Color.White
    val textColor = if (darkMode.value) Color.White else Color.Black
    val borderColor = if (darkMode.value) Color.White else Color.Gray

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Bienvenido a gigifai",
                color = textColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp),
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Usuario", color = textColor) },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = textColor) },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = borderColor,
                    textColor = textColor,
                    cursorColor = textColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = textColor) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = textColor) },
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = "Ver contraseña",
                            tint = textColor
                        )
                    }
                },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = borderColor,
                    textColor = textColor,
                    cursorColor = textColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            Button(
                onClick = {
                    if (username.lowercase() == "gigiyoyopo" && password == "12345") {
                        Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                        navController.navigate("search") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = textColor),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Iniciar sesión", color = bgColor, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Modo claro/oscuro", color = Color.Gray, fontSize = 14.sp)
                Switch(
                    checked = darkMode.value,
                    onCheckedChange = { darkMode.value = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.Gray
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}