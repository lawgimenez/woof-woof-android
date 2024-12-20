package xyz.gmnz.law.woofwoof

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.gmnz.law.woofwoof.ui.theme.WoofWoofTheme
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WoofWoofTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var text by remember { mutableStateOf("") }
                    val context = LocalContext.current
                    val tts = remember {
                        mutableStateOf<TextToSpeech?>(null)
                    }
                    DisposableEffect(context) {
                        val textToSpeech = TextToSpeech(context) { status ->
                            if (status == TextToSpeech.SUCCESS) {
                                tts.value?.language = Locale.US
                            }
                        }
                        tts.value = textToSpeech
                        onDispose {
                            textToSpeech.stop()
                            textToSpeech.shutdown()
                        }
                    }
                    Column(modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedTextField(
                            value = text,
                            onValueChange = {
                                text = it
                            },
                            singleLine = false,
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 20
                        )
                        Button(onClick = {
                            if (tts.value?.isSpeaking == true) {
                                tts.value?.stop()
                                } else {
                                tts.value?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                            }
                        }, modifier = Modifier.fillMaxWidth(0.5f).padding(top = 32.dp)) {
                            Text(text = "Speak")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WoofWoofTheme {
    }
}