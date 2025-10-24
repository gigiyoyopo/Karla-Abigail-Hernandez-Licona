package com.example.sonidos

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sonidos.ui.theme.SonidosTheme

class MainActivity : ComponentActivity() {

    private var mediaPlayer: MediaPlayer? = null // instancia global

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SonidosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GridDeImagenes()
                }
            }
        }
    }

    @Composable
    fun GridDeImagenes() {
        val context = LocalContext.current

        val imagenes = listOf(
            R.drawable.donaldduck,
            R.drawable.scoobydoo, R.drawable.scoobydoo, R.drawable.scoobydoo,
            R.drawable.scoobydoo, R.drawable.scoobydoo, R.drawable.scoobydoo,
            R.drawable.scoobydoo, R.drawable.scoobydoo, R.drawable.scoobydoo,
            R.drawable.scoobydoo, R.drawable.scoobydoo
        )

        val sonidos = List(12) { R.raw.scoobydoorunning }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(imagenes.size) { index ->
                Image(
                    painter = painterResource(imagenes[index]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .height(120.dp)
                        .fillMaxSize()
                        .clickable {
                            // Si hay un audio reproduciéndose, se detiene
                            mediaPlayer?.let {
                                if (it.isPlaying) it.stop()
                                it.release()
                            }

                            // Se crea un nuevo MediaPlayer
                            mediaPlayer = MediaPlayer.create(context, sonidos[index])
                            mediaPlayer?.start()
                        }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Liberar MediaPlayer si la activity se destruye
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
