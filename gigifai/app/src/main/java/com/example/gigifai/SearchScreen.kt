package com.example.gigifai

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState

@Composable
fun SearchScreen(navController: NavController) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    val darkMode = LocalDarkMode.current

    val songs = listOf(
        "Pixelated Kisses",
        "Die For You",
        "Amor Amarillo",
        "Yo Voy A Amarte",
        "LAS NOCHES"
    )
    val artists = listOf("Joji", "Joji", "Gustavo Cerati", "Junior H", "Junior H")
    val images = listOf(R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5)

    val songsMap = mapOf(
        "pixelated kisses" to 0,
        "die for you" to 1,
        "amor amarillo" to 2,
        "yo voy a amarte" to 3,
        "las noches" to 4
    )

    val bgColor = if (darkMode.value) Color(0xFF101010) else Color(0xFFF2F2F2)
    val textColor = if (darkMode.value) Color.White else Color.Black
    val secondaryTextColor = if (darkMode.value) Color.Gray else Color.DarkGray
    val cardColor = if (darkMode.value) Color.Black else Color(0xFFD9D9D9)
    val selectedIconBg = if (darkMode.value) Color.Gray.copy(alpha = 0.3f) else Color.Gray.copy(alpha = 0.2f)

    var showExitDialog by remember { mutableStateOf(false) }
    var bottomMenuSelected by remember { mutableStateOf("buscar") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // --------------------- Caja de búsqueda con ícono de regresar ---------------------
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Caja búsqueda
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(cardColor, RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Buscar canción", color = secondaryTextColor) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = textColor) },
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            textColor = textColor,
                            cursorColor = textColor,
                            backgroundColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                val idx = songsMap[searchQuery.lowercase()]
                                if (idx != null) {
                                    navController.navigate("song/$idx")
                                } else {
                                    Toast.makeText(context, "Canción no encontrada", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Ícono regresar
                IconButton(onClick = { showExitDialog = true }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = textColor)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Búsquedas recientes", color = secondaryTextColor, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            // --------------------- Lista de canciones ---------------------
            Column(modifier = Modifier.weight(1f)) {
                songs.forEachIndexed { index, title ->
                    var liked by remember { mutableStateOf(false) }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(cardColor, RoundedCornerShape(12.dp))
                            .padding(8.dp)
                            .clickable { navController.navigate("song/$index") }
                    ) {
                        // Portada
                        Image(
                            painter = painterResource(id = images[index]),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(end = 12.dp)
                        )

                        // Nombre y artista
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = title,
                                fontWeight = FontWeight.Bold,
                                color = textColor,
                                fontSize = 16.sp
                            )
                            Text(
                                text = artists[index],
                                fontWeight = FontWeight.Normal,
                                color = secondaryTextColor,
                                fontSize = 14.sp
                            )
                        }

                        // Icono corazón
                        IconButton(onClick = {
                            liked = !liked
                            if (liked) Toast.makeText(context, "Agregado a tus Me Gusta", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(
                                imageVector = if (liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Me Gusta",
                                tint = if (liked) Color.Red else textColor
                            )
                        }

                        // Icono enlace
                        IconButton(onClick = {
                            Toast.makeText(context, "Enlace copiado en el portapapeles", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(Icons.Default.Link, contentDescription = "Enlace", tint = textColor)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --------------------- modo claro/oscuro ---------------------
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Modo claro/oscuro", color = secondaryTextColor, fontSize = 14.sp)
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

            Spacer(modifier = Modifier.height(16.dp))

            // --------------------- Menú inferior horizontal ---------------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Inicio (casita)
                Box(
                    modifier = Modifier
                        .background(
                            if (bottomMenuSelected == "inicio") selectedIconBg else Color.Transparent,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(8.dp)
                        .clickable {
                            showExitDialog = true
                        }
                ) {
                    Icon(Icons.Default.Home, contentDescription = "Inicio", tint = textColor)
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Buscar (lupa)
                Box(
                    modifier = Modifier
                        .background(
                            if (bottomMenuSelected == "buscar") selectedIconBg else Color.Transparent,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(8.dp)
                        .clickable {
                            bottomMenuSelected = "buscar"
                        }
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Buscar", tint = textColor)
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Canción (nota musical)
                Box(
                    modifier = Modifier
                        .background(
                            if (bottomMenuSelected == "cancion") selectedIconBg else Color.Transparent,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(8.dp)
                        .clickable {
                            bottomMenuSelected = "cancion"
                            navController.navigate("song/0")
                        }
                ) {
                    Icon(Icons.Default.MusicNote, contentDescription = "Canción", tint = textColor)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // --------------------- Diálogo de salir ---------------------
        if (showExitDialog) {
            AlertDialog(
                onDismissRequest = { showExitDialog = false },
                title = { Text("Salir de tu cuenta") },
                text = { Text("¿Deseas cerrar sesión?") },
                confirmButton = {
                    TextButton(onClick = {
                        showExitDialog = false
                        Toast.makeText(context, "Gracias por usar mi reproductor, t amo", Toast.LENGTH_SHORT).show()
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    }) { Text("Sí") }
                },
                dismissButton = {
                    TextButton(onClick = { showExitDialog = false }) { Text("No") }
                }
            )
        }
    }
}
