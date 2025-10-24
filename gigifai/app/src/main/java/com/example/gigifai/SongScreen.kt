package com.example.gigifai

import android.media.MediaPlayer
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SongScreen(navController: NavController, startIndex: Int = 0) {
    val context = LocalContext.current
    val darkMode = LocalDarkMode.current

    val songs = listOf("Pixelated Kisses", "Die For You", "Amor Amarillo", "Yo Voy A Amarte", "LAS NOCHES")
    val artists = listOf("Joji", "Joji", "Gustavo Cerati", "Junior H", "Junior H")
    val images = listOf(R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5)
    val audioFiles = listOf(R.raw.aud1, R.raw.aud2, R.raw.aud3, R.raw.aud4, R.raw.aud5)

    val likedSongs = remember { mutableStateListOf(true, true, false, false, false) }

    var currentIndex by remember { mutableStateOf(startIndex) }
    var isPlaying by remember { mutableStateOf(true) }
    var sliderPosition by remember { mutableStateOf(0f) }
    var volume by remember { mutableStateOf(0.5f) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var showMenu by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) }

    val gradient = when (currentIndex) {
        0 -> Brush.verticalGradient(listOf(Color(0xFF8B0000), Color(0xFFFF0000)))
        1 -> Brush.verticalGradient(listOf(Color(0xFF00CED1), Color(0xFF4682B4)))
        2 -> Brush.verticalGradient(listOf(Color(0xFF8B4513), Color(0xFFFFA500)))
        3 -> Brush.verticalGradient(listOf(Color(0xFF00CED1), Color(0xFF4682B4)))
        else -> Brush.verticalGradient(listOf(Color(0xFF000000), Color(0xFF2F2F2F)))
    }

    // ----------------------- Slider automático -----------------------
    LaunchedEffect(currentIndex) {
        mediaPlayer?.stop()
        mediaPlayer?.release()

        mediaPlayer = MediaPlayer.create(context, audioFiles[currentIndex]).apply {
            isLooping = false
            setVolume(volume, volume)
            start()
        }
        isPlaying = true

        // Actualización del slider
        while (true) {
            if (isPlaying && mediaPlayer != null) {
                sliderPosition = mediaPlayer!!.currentPosition.toFloat()
            }
            delay(500)
        }
    }

    BackHandler {
        showExitDialog = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = if (darkMode.value) gradient else Brush.verticalGradient(listOf(Color.White, Color.White))
            )
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // ----------------------- Top bar -----------------------
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Flecha de regreso al SearchScreen
                IconButton(onClick = {
                    mediaPlayer?.pause()
                    isPlaying = false
                    navController.navigate("search") { popUpTo("song/$currentIndex") { inclusive = true } }
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Regresar",
                        tint = if (darkMode.value) Color.White else Color.Black)
                }

                IconButton(onClick = { darkMode.value = !darkMode.value }) {
                    Icon(Icons.Default.Brightness4, contentDescription = "Modo Claro/Oscuro",
                        tint = if (darkMode.value) Color.White else Color.Black)
                }

                // Menú desplegable
                Box {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu",
                            tint = if (darkMode.value) Color.White else Color.Black)
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            Toast.makeText(context, "Agregado a Playlist 1", Toast.LENGTH_SHORT).show()
                        }) { Text("Agregar a una playlist") }
                        DropdownMenuItem(onClick = {
                            Toast.makeText(context, "Código copiado al portapapeles", Toast.LENGTH_SHORT).show()
                        }) { Text("Compartir código") }
                        DropdownMenuItem(onClick = {
                            Toast.makeText(context, "Agregado a fila de reproducción", Toast.LENGTH_SHORT).show()
                        }) { Text("Agregar a fila de reproducción") }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ----------------------- Texto "Reproduciendo desde..." -----------------------
            val playingFrom = if (likedSongs[currentIndex]) "Reproduciendo desde tus " else "Reproduciendo desde "
            val boldText = if (likedSongs[currentIndex]) "Me Gusta" else "este dispositivo"
            Text(
                buildAnnotatedString {
                    append(playingFrom)
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    append(boldText)
                },
                color = if (darkMode.value) Color.White else Color.Black,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ----------------------- Portada -----------------------
            Image(
                painter = painterResource(id = images[currentIndex]),
                contentDescription = null,
                modifier = Modifier.size(300.dp).clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ----------------------- Nombre canción y artista -----------------------
            Text(songs[currentIndex], fontSize = 24.sp, fontWeight = FontWeight.Bold,
                color = if (darkMode.value) Color.White else Color.Black)
            Text(artists[currentIndex], fontSize = 16.sp, color = Color.White) // <-- blanco

            Spacer(modifier = Modifier.height(16.dp))

            // ----------------------- Slider de progreso -----------------------
            Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                Slider(
                    value = sliderPosition,
                    onValueChange = {
                        sliderPosition = it
                        mediaPlayer?.seekTo(it.toInt())
                    },
                    valueRange = 0f..(mediaPlayer?.duration?.toFloat() ?: 100f),
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = formatMillis(sliderPosition.toLong()), fontSize = 12.sp,
                        color = if (darkMode.value) Color.White else Color.Black)
                    Text(text = formatMillis(mediaPlayer?.duration?.toLong() ?: 0L), fontSize = 12.sp,
                        color = if (darkMode.value) Color.White else Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ----------------------- Controles de reproducción -----------------------
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { showExitDialog = true }) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar",
                        tint = if (darkMode.value) Color.White else Color.Black)
                }
                IconButton(onClick = {
                    currentIndex = (currentIndex - 1 + songs.size) % songs.size
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    mediaPlayer = MediaPlayer.create(context, audioFiles[currentIndex]).apply { start() }
                    isPlaying = true
                }) {
                    Icon(Icons.Default.SkipPrevious, contentDescription = "Anterior",
                        tint = if (darkMode.value) Color.White else Color.Black)
                }

                // ----------------------- Play/Pausa grande -----------------------
                IconButton(
                    onClick = {
                        mediaPlayer?.let {
                            if (it.isPlaying) {
                                it.pause()
                                isPlaying = false
                            } else {
                                it.start()
                                isPlaying = true
                            }
                        }
                    },
                    modifier = Modifier.size(80.dp)
                ) {
                    Icon(
                        if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play/Pausa",
                        tint = if (darkMode.value) Color.White else Color.Black,
                        modifier = Modifier.size(80.dp)
                    )
                }

                IconButton(onClick = {
                    currentIndex = (currentIndex + 1) % songs.size
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    mediaPlayer = MediaPlayer.create(context, audioFiles[currentIndex]).apply { start() }
                    isPlaying = true
                }) {
                    Icon(Icons.Default.SkipNext, contentDescription = "Siguiente",
                        tint = if (darkMode.value) Color.White else Color.Black)
                }
                IconButton(onClick = {
                    isPlaying = true
                    mediaPlayer?.seekTo(0)
                    mediaPlayer?.start()
                }) {
                    Icon(Icons.Default.Repeat, contentDescription = "Repetir",
                        tint = if (darkMode.value) Color.White else Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ----------------------- Slider de volumen -----------------------
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Icon(Icons.Default.VolumeDown, contentDescription = "Bajo",
                    tint = if (darkMode.value) Color.White else Color.Black)
                Slider(
                    value = volume,
                    onValueChange = {
                        volume = it
                        mediaPlayer?.setVolume(it, it)
                    },
                    modifier = Modifier.weight(1f)
                )
                Icon(Icons.Default.VolumeUp, contentDescription = "Alto",
                    tint = if (darkMode.value) Color.White else Color.Black)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ----------------------- Botones inferiores -----------------------
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = {
                    Toast.makeText(context, "Todavía no le agrego letra paps", Toast.LENGTH_SHORT).show()
                }) { Icon(Icons.Default.Mic, contentDescription = "Micrófono",
                    tint = if (darkMode.value) Color.White else Color.Black) }

                IconButton(onClick = {
                    Toast.makeText(context, "Enlace copiado al portapapeles", Toast.LENGTH_SHORT).show()
                }) { Icon(Icons.Default.Share, contentDescription = "Compartir",
                    tint = if (darkMode.value) Color.White else Color.Black) }
            }
        }

        // ----------------------- Dialogo de salida -----------------------
        if (showExitDialog) {
            AlertDialog(
                onDismissRequest = { showExitDialog = false },
                title = { Text("Cerrar canción") },
                text = { Text("¿Deseas regresar al buscador?") },
                confirmButton = {
                    TextButton(onClick = {
                        showExitDialog = false
                        Toast.makeText(context, "Gracias por usar mi reproductor, t amo", Toast.LENGTH_SHORT).show()
                        mediaPlayer?.stop()
                        navController.navigate("search") { popUpTo("song/$currentIndex") { inclusive = true } }
                    }) { Text("Sí") }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showExitDialog = false
                        mediaPlayer?.start()
                        isPlaying = true
                    }) { Text("No") }
                }
            )
        }
    }
}

fun formatMillis(ms: Long): String {
    val totalSecs = ms / 1000
    val minutes = totalSecs / 60
    val seconds = totalSecs % 60
    return "%02d:%02d".format(minutes, seconds)
}