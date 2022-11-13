package com.michal_rac.composequadrant

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.michal_rac.composequadrant.ui.theme.ComposeQuadrantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeQuadrantTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ComposeQuadrant()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeQuadrantTheme {
            ComposeQuadrant()
    }
}

@Composable
fun ComposeQuadrant(){
    Row(Modifier.fillMaxHeight()) {
      Column(
          Modifier
              .weight(1f, true)
              .fillMaxHeight()) {
          Box(Modifier.fillMaxHeight(0.5f))
          {
              Quadrant(title = stringResource(R.string.quadrant_0_title), text = stringResource(R.string.quadrant_0_desc), androidx.compose.ui.graphics.Color.Green)
          }
          Quadrant(title = stringResource(R.string.quadrant_2_title), text = stringResource(R.string.quadrant_2_desc), androidx.compose.ui.graphics.Color.Cyan)
      }
      Column(
          Modifier
              .weight(1f, true)
              .fillMaxHeight()) {
          Box(Modifier.fillMaxHeight(0.5f))
          {
              Quadrant(title = stringResource(R.string.quadrant_1_title), text = stringResource(R.string.quadrant_1_desc), androidx.compose.ui.graphics.Color.Yellow)
          }
          Quadrant(title = stringResource(R.string.quadrant_3_title), text = stringResource(R.string.quadrant_3_desc), androidx.compose.ui.graphics.Color.LightGray)
      }
    }
}

@Composable
fun Quadrant(title: String, text: String, bgColor: androidx.compose.ui.graphics.Color)
{
    Column(
        Modifier
            .fillMaxHeight()
            .background(bgColor)
            .wrapContentHeight(Alignment.CenterVertically)
            .padding(all = 16.dp)
    ) {
        Text(
            text = title,
            Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally),
            color = androidx.compose.ui.graphics.Color.Black,
            fontWeight = FontWeight.Bold)
        Text(text, textAlign = TextAlign.Justify, color = androidx.compose.ui.graphics.Color.Black)
    }
}