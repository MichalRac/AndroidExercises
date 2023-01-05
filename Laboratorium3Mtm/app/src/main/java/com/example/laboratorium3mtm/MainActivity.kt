package com.example.laboratorium3mtm

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.laboratorium3mtm.ui.theme.Laboratorium3MtmTheme
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Laboratorium3MtmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val localContext = LocalContext.current

    var file by remember { mutableStateOf( File.createTempFile("temporary", "3gpp")) }
    var mediaPlayer by remember { mutableStateOf ( MediaPlayer()) }
    var mediaRecorder by remember { mutableStateOf( MediaRecorder()) }
    var recordingState by remember { mutableStateOf(1) }

    val centerModifer = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)

    Column(modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally))
    {
        Text("MTM Laboratorium 3", centerModifer)

        Spacer(Modifier.height(24.dp))
        Text("zad 1", centerModifer)

        Button(onClick = {
            mediaPlayer = MediaPlayer.create(localContext, R.raw.sample3s)
            mediaPlayer?.start()
        }, modifier = centerModifer) {
            Text("sfx1")
        }

        Button(onClick = {
            mediaPlayer = MediaPlayer.create(localContext, R.raw.sample3s)
            mediaPlayer?.start()
        }, modifier = centerModifer) {
            Text("sfx2")
        }

        Button(onClick = {
            mediaPlayer = MediaPlayer.create(localContext, R.raw.sample3s)
            mediaPlayer?.start()
        }, modifier = centerModifer) {
            Text("sfx3")
        }

        Spacer(Modifier.height(24.dp))
        Text("zad 2 + 3", centerModifer)

        when (recordingState) {
            1 -> {
                Button(onClick = {
                    recordingState++;
                    file = File.createTempFile("temporary", "3gpp")
                    mediaRecorder.setAudioChannels(1)
                    mediaRecorder.setAudioSamplingRate(8000);
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setOutputFile(file.getAbsolutePath());
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mediaRecorder.prepare();
                    mediaRecorder.start()
                }, modifier = centerModifer ) {
                    Text("Record")
                }
            }
            2 -> {
                Button(onClick = {
                    recordingState++
                    mediaRecorder?.stop()
                    mediaRecorder?.release()
                }, modifier = centerModifer ) {
                    Text("Stop recording")
                }
            }
            3 -> {
                Button(onClick = {
                    mediaPlayer = MediaPlayer()
                    mediaPlayer?.setDataSource(file.absolutePath)
                    mediaPlayer?.prepare()
                    mediaPlayer?.setVolume(1f, 1f)
                    mediaPlayer?.start()
                }, modifier = centerModifer) {
                    Text("Play")
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Laboratorium3MtmTheme {
        Greeting()
    }
}