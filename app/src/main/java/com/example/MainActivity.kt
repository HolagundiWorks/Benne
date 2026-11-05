package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.audio.KannadaTtsHelper
import com.example.data.UserProgressManager
import com.example.ui.MainScreen
import com.example.ui.theme.SaralaKannadaTheme

class MainActivity : ComponentActivity() {

    private lateinit var progressManager: UserProgressManager
    private lateinit var ttsHelper: KannadaTtsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        progressManager = UserProgressManager(this)
        ttsHelper = KannadaTtsHelper(this)

        setContent {
            SaralaKannadaTheme {
                MainScreen(
                    progressManager = progressManager,
                    ttsHelper = ttsHelper,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::ttsHelper.isInitialized) {
            ttsHelper.destroy()
        }
    }
}
