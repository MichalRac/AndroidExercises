package com.example.mtmlab3

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mtmlab3.ui.theme.MTMLab3Theme
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MTMLab3Theme {
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

    Column()
    {
        AudioButtons(localContext = localContext, buttonText = "Sound1", R.raw.example_audio)
        AudioButtons(localContext = localContext, buttonText = "Sound2", R.raw.example_audio)
        AudioButtons(localContext = localContext, buttonText = "Sound3", R.raw.example_audio)
        RecordButtons()
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MTMLab3Theme {
        Greeting()
    }
}

@Composable
fun AudioButtons(localContext: Context, buttonText: String, soundId: Int)
{
    var playing by remember { mutableStateOf( false) }
    var mediaPlayer by remember { mutableStateOf ( MediaPlayer()) }

    Row()
    {
        Button(onClick = {
            mediaPlayer = MediaPlayer.create(localContext, soundId)
            mediaPlayer?.start()
            playing = true;
        }, modifier = Modifier.padding(10.dp), enabled = !playing) {
            Text(buttonText)
        }

        Button(onClick = {
            mediaPlayer?.pause()
            mediaPlayer?.release()
            playing = false;
        }, modifier = Modifier.padding(10.dp), enabled = playing) {
            Text("Turn off")
        }
    }
}

@Composable
fun RecordButtons()
{
    var mediaPlayer by remember { mutableStateOf ( MediaPlayer()) }
    var mediaRecorder by remember { mutableStateOf( MediaRecorder()) }
    var recording by remember { mutableStateOf( false) }
    var recorded by remember { mutableStateOf( false) }
    var playing by remember { mutableStateOf( false) }
    var file by remember { mutableStateOf( File.createTempFile("tmp", "3gpp")) }

    Row()
    {
        Button(onClick = {
            recorded = false
            playing = false
            recording = true
            file = File.createTempFile("tmp", "3gpp")
            mediaRecorder.setAudioChannels(1)
            mediaRecorder.setAudioSamplingRate(8000);
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(file.getAbsolutePath());
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start()
        }, modifier = Modifier.padding(10.dp), enabled = !recording && !recorded) {
            Text("Record")
        }

        Button(onClick = {
            recording = false
            playing = false
            recorded = true
            mediaRecorder?.stop()
            mediaRecorder?.release()
        }, modifier = Modifier.padding(10.dp), enabled = recording) {
            Text("Don't record")
        }

        Button(onClick = {
            recording = false
            playing = true
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(file.absolutePath)
            mediaPlayer?.prepare()
            mediaPlayer?.setVolume(1f, 1f)
            mediaPlayer?.start()
        }, modifier = Modifier.padding(10.dp), enabled = recorded && !playing) {
            Text("Play record")
        }
    }

}