package com.krld.composeplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.krld.composeplay.ui.theme.ComposePlayTheme

class MainActivity : ComponentActivity() {

    val state = State.generate(1000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MainUi(state)
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun MainUi(state: State = State.generate(20)) {
        ComposePlayTheme {
            Surface(color = MaterialTheme.colors.background) {
                Greeting("Android 123")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}