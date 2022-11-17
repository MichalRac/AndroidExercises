package com.michal_rac.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.michal_rac.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LemonFlow()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LemonFlow()
{
    var step by remember {
        mutableStateOf(0)
    }
    var squeezeStep by remember {
        mutableStateOf(0)
    }
    var squeezesRequired by remember {
        mutableStateOf((3..6).random())
    }

    when(step){
        0 -> LemonState(stringResource(R.string.step_0), R.drawable.lemon_tree, stringResource(R.string.step_description_0)) {
            step++
        }
        1 -> LemonState(stringResource(R.string.step_1), R.drawable.lemon_squeeze, stringResource(R.string.step_description_1)){
            squeezeStep++
            if(squeezeStep >= squeezesRequired) {
                squeezeStep = 0
                squeezesRequired = (3..6).random()
                step++
            }
        }
        2 -> LemonState(stringResource(R.string.step_2), R.drawable.lemon_drink, stringResource(R.string.step_description_2)){
            step++
        }
        3 -> LemonState(stringResource(R.string.step_3), R.drawable.lemon_restart, stringResource(R.string.step_description_3)){
            step = 0
        }
        else -> step = 0
    }
}

@Composable
fun LemonState(text: String, imgId: Int, contentDescription: String, onClick: () -> Unit)
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = text,
            Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally),
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painterResource(id = imgId),
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .border(2.dp, Color(105,205,216), AbsoluteRoundedCornerShape(4))
                .clickable { onClick?.invoke() }
        )
    }
}