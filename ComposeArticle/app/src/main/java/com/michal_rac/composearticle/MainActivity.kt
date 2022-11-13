package com.michal_rac.composearticle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.michal_rac.composearticle.ui.theme.ComposeArticleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeArticleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Article(
                        title = getString(R.string.article_title),
                        introTxt = getString(R.string.article_intro),
                        bodyTxt = getString(R.string.article_body),
                        imageId = R.drawable.bg_compose_background,
                    )
                }
            }
        }
    }
}

@Composable
fun Article(title: String, introTxt: String, bodyTxt: String, imageId: Int)
{
    Column (Modifier.verticalScroll(rememberScrollState())) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Text(title, fontSize = 24.sp, modifier = Modifier.padding(all = 16.dp))
        Text(introTxt, textAlign = TextAlign.Justify, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
        Text(bodyTxt, textAlign = TextAlign.Justify,  modifier = Modifier.padding(all = 16.dp))
    }
}

@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ){}

    val title = stringResource(id = R.string.article_title)
    val intro = stringResource(id = R.string.article_intro)
    val body = stringResource(id = R.string.article_body)
    ComposeArticleTheme {
        Article(title, intro, body, R.drawable.bg_compose_background)

    }
}