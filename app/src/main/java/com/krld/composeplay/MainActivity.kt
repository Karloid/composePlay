package com.krld.composeplay

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.krld.composeplay.ui.theme.ComposePlayTheme

private val TAG = "MainActivity"

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    val state = State.generate(1000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainUi(state)
        }
    }

    @Preview(name = "Light Mode")
    @Preview(
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true,
        name = "Dark Mode"
    )
    @Composable
    private fun MainUi(state: State = State.generate(20)) {
        val selectedChat = remember { state.selectedChat }
        ComposePlayTheme {
            Crossfade(targetState = selectedChat) { chat ->
                if (chat.value != null) {
                    FadeAnim { ChatScreen(state, chat.value!!) }
                } else {
                    FadeAnim { ChatListScreen(state) }
                }
            }
        }
    }

    @ExperimentalAnimationApi
    @Composable
    fun FadeAnim(content: @Composable () -> Unit) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(initialAlpha = 0.3f),
            exit = fadeOut(),
            content = content,
            initiallyVisible = false
        )
    }

    @Composable
    private
    fun ChatScreen(state: State, selectedChat: Chat) {
        Column {
            ChatHeader(selectedChat)
        }
    }

    @Composable
    private fun ChatHeader(selectedChat: Chat) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(MaterialTheme.colors.primary)
        ) {
            Spacer(modifier = Modifier.size(8.dp))
            UserAvatar(selectedChat.withUser)
            Spacer(modifier = Modifier.size(4.dp))
            Text(text = "Chat with ${selectedChat.withUser.username}")

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    state.selectedChat.value = null
                },
                modifier = Modifier
                    .height(52.dp)
                    .background(Color.Transparent)
            ) {
                Text(
                    text = "BACK",
                    textAlign = TextAlign.Center
                )

            }
        }
    }

    @Composable
    private fun ChatListScreen(state: State) {
        Surface(color = MaterialTheme.colors.background) {
            Column {
                Text(text = "Logged as ${state.currentUser.username}")
                LazyColumn() {
                    items(state.chats) {
                        ChatCell(state, it)
                    }
                }
            }
        }
    }
}

@Composable
fun ChatCell(state: State, chat: Chat) {


    var isExpanded by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.clickable {
        state.selectedChat.value = chat
    }) {

        Column(
            modifier = Modifier.fillMaxWidth(1f)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Spacer(modifier = Modifier.size(16.dp))
                val withUser = chat.withUser
                UserAvatar(withUser)
                Spacer(modifier = Modifier.size(8.dp))
                Column {
                    Text(
                        text = chat.withUser.username,
                        style = MaterialTheme.typography.subtitle2
                    )
                    val lastMsg = chat.messages.last()
                    val lastMsgText = "${lastMsg.from.username}: ${lastMsg.text}"


                    val surfaceColor: Color by animateColorAsState(
                        if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
                    )

                    Surface(
                        color = surfaceColor,
                        shape = MaterialTheme.shapes.medium,
                        elevation = 1.dp,
                        modifier = Modifier
                            .animateContentSize()
                            .padding(1.dp)
                    ) {
                        var modifier = Modifier.padding(all = 4.dp)
                        if (isExpanded) {
                            modifier = modifier
                                .width(100.dp)
                        }
                        Text(
                            text = lastMsgText,
                            modifier = modifier,

                            style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun UserAvatar(withUser: User) {
    Image(
        painter = painterResource(id = withUser.avatarResId),
        contentDescription = "avatar",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
    )
}