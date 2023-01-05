package com.michal_rac.audioplayerrecorder

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.media.audiofx.Visualizer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.michal_rac.audioplayerrecorder.ui.theme.AudioPlayerRecorderTheme
import java.io.File
import java.lang.Math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AudioPlayerRecorderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AudioPlayerRecorder()
                }
            }
        }
    }
}

var mediaRecorder = MediaRecorder()
var mediaPlayer: MediaPlayer? = null;
var file = File.createTempFile("tmp", "3gpp")

@Composable
fun AudioPlayerRecorder() {

    val localContext = LocalContext.current

    var visualizationData by remember { mutableStateOf( IntArray(32) ) }
    var isPlayingAudio by remember { mutableStateOf( false) }
    var isRecording by remember { mutableStateOf( false) }
    var isRecorded by remember { mutableStateOf( false) }

    Box(modifier = Modifier.fillMaxSize())
    {
        Column(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()) {

            if(mediaPlayer != null)
            {
                Visualizer(mediaPlayer!!.audioSessionId).apply {
                    enabled = false // All configuration have to be done in a disabled state
                    captureSize = Visualizer.getCaptureSizeRange()[0] // Minimum sampling
                    setDataCaptureListener(
                        object : Visualizer.OnDataCaptureListener {
                            override fun onFftDataCapture(visualizer: Visualizer, fft: ByteArray, samplingRate: Int) {
                            }
                            override fun onWaveFormDataCapture(visualizer: Visualizer, waveform: ByteArray, samplingRate: Int) {

                                val resolution = 32
                                val processed = IntArray(resolution)
                                val captureSize = Visualizer.getCaptureSizeRange()[0] // Same value than in the Visualizer setup
                                val groupSize = captureSize / resolution
                                for (i in 0 until resolution) {
                                    var groupSum = 0;
                                    for (j in 0 until groupSize) {
                                        groupSum += abs(waveform[i * groupSize + j].toInt())
                                    }
                                    processed[i] = groupSum / groupSize
                                }
                                visualizationData = processed
                            }
                        },
                        Visualizer.getMaxCaptureRate(), true, true)
                    enabled = true
                }
            }

            BarEqualizer(
                Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .height(100.dp),
                visualizationData
            )

            Text(text = "Michał Rać 165605 - Audio Player/Recorder!", modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally))

            Button(onClick = {
                mediaPlayer?.pause()
                mediaPlayer = MediaPlayer.create(localContext, R.raw.nggyu_wav)
                mediaPlayer?.start()
                isPlayingAudio = true;
            }, modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally), !isPlayingAudio && !isRecording) {
                Text("Play Wav!")
            }

            Button(onClick = {
                mediaPlayer?.pause()
                mediaPlayer = MediaPlayer.create(localContext, R.raw.nggyu_flac)
                mediaPlayer?.start()
                isPlayingAudio = true;
            }, modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally), !isPlayingAudio && !isRecording) {
                Text("Play Flac!")
            }

            Button(onClick = {
                mediaPlayer?.pause()
                mediaPlayer = MediaPlayer.create(localContext, R.raw.nggyu_mp3)
                mediaPlayer?.start()
                isPlayingAudio = true;
            }, modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally), !isPlayingAudio && !isRecording) {
                Text("Play mp3!")
            }

            Button(onClick = {
                mediaPlayer?.pause()
                mediaPlayer = MediaPlayer.create(localContext, R.raw.nggyu_mp4)
                mediaPlayer?.start()
                isPlayingAudio = true;
            }, modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally), !isPlayingAudio && !isRecording) {
                Text("Play mp4!")
            }

            Button(onClick = {
                mediaPlayer?.pause()
                mediaPlayer = MediaPlayer.create(localContext, R.raw.nggyu_ogg)
                mediaPlayer?.start()
                isPlayingAudio = true;
            }, modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally), !isPlayingAudio && !isRecording) {
                Text("Play ogg!")
            }

            Button(onClick = {
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
                isPlayingAudio = false;
            }, modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally), isPlayingAudio && !isRecording) {
                Text("Stop playing!")
            }


            Button(onClick = {
                mediaRecorder.setAudioChannels(1)
                mediaRecorder.setAudioSamplingRate(8000);
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setOutputFile(file.getAbsolutePath());
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                mediaRecorder.prepare();
                mediaRecorder.start()
                isRecording = true;
            }, modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally), !isPlayingAudio && !isRecording && !isRecorded) {
                Text(text = "Record!")
            }

            Button(onClick = {
                mediaRecorder?.stop()
                mediaRecorder?.release()
                isRecorded = true
                isRecording = false
                             }, modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally), isRecording) {
                Text(text = "Stop recording!")
            }

            Button(onClick = {
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null

                mediaPlayer = MediaPlayer()
                mediaPlayer?.setDataSource(file.absolutePath)
                mediaPlayer?.prepare()
                mediaPlayer?.setVolume(1f, 1f)
                mediaPlayer?.start()
                isPlayingAudio = true;
            }, modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally), !isPlayingAudio && !isRecording && isRecorded) {
                Text(text = "Play recording!")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AudioPlayerRecorderTheme {
        AudioPlayerRecorder()
    }
}

@Composable
fun BarEqualizer(
    modifier: Modifier,
    visualizationData: IntArray,
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    Row(modifier.onSizeChanged { size = it }) {
        val widthDp = size.getWidthDp()
        val heightDp = size.getHeightDp()
        val padding = 1.dp

        var colorId = (1 .. 3).random()
        var color = Color.Red
        if(colorId == 1) color = Color.Red
        if(colorId == 2) color = Color.Green
        if(colorId == 3) color = Color.Red

        visualizationData.forEachIndexed { index, data ->
            val height by animateDpAsState(targetValue = heightDp * data / 128f)
            Box(
                Modifier
                    .width(5.dp)
                    .height(height)
                    .padding(start = if (index == 0) 0.dp else padding)
                    .background(color)
                    .align(Alignment.Bottom)
            )
        }
    }
}

@Composable
fun IntSize.getWidthDp(): Dp = LocalDensity.current.run { width.toDp() }

@Composable
fun IntSize.getHeightDp(): Dp = LocalDensity.current.run { height.toDp() }