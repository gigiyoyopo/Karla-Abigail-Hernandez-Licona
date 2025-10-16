package com.example.merindo

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.merindo.ui.theme.MerindoTheme
import android.widget.VideoView
import android.widget.MediaController
import android.widget.Toast

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MerindoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    var showImage by remember { mutableStateOf(true) } // Estado para mostrar/ocultar imagen

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Joji vuelve, los niños te extrañan",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = {
            showImage = !showImage
            if (showImage) {
                Toast.makeText(context, "Imagen visible", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Imagen oculta", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = if (showImage) "Ocultar Imagen" else "Mostrar Imagen")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (showImage) {
            Image(
                painter = painterResource(id = R.drawable.sample_image),
                contentDescription = "Imagen de muestra",
                modifier = Modifier.size(250.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        AndroidView(
            factory = {
                VideoView(context).apply {
                    val uri = Uri.parse("android.resource://${context.packageName}/${R.raw.sample_video}")
                    setVideoURI(uri)
                    val mediaController = MediaController(context)
                    mediaController.setAnchorView(this)
                    setMediaController(mediaController)
                    start()
                }
            },
            modifier = Modifier
                .width(300.dp)
                .height(200.dp)
        )
    }
}
