package com.wema.simplecht

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wema.simplecht.ui.theme.SimpleChtTheme
import com.wema.simplecht.websocketchat.data.utils.NotificationUtil
import com.wema.simplecht.websocketchat.ui.UserChat


class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notificationUtil: NotificationUtil =  NotificationUtil()

        notificationUtil.createNotificationChannel("chatMessages", "chatMessages", this)

        setContent {
            SimpleChtTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserChat()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimpleChtTheme {

    }
}