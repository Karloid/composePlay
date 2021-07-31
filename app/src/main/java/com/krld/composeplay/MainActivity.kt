package com.krld.composeplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                Column {
                    Text(text = "Logged as ${state.currentUser.username}")
                    state.chats.forEach {
                        ChatBubble(state, it)
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(state: State, chat: Chat) {

    Surface(color = Color.LightGray) {
        Column(
            modifier = Modifier.fillMaxWidth(1f)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Spacer(modifier = Modifier.size(16.dp))
                Image(
                    painter = painterResource(id = chat.withUser.avatarResId),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Column {
                    Text(text = chat.withUser.username)
                    val lastMsg = chat.messages.last()
                    val lastMsgText = "${lastMsg.from.username}: ${lastMsg.text}"
                    Text(text = lastMsgText)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                color = Color.Gray,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .height(1.dp)
                    .fillMaxWidth(1f)
            ) {
            }
        }
    }
}