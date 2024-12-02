package net.developermaster.timejob.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import net.developermaster.timejob.core.ComponentsActivitys
import net.developermaster.timejob.view.ui.theme.TimeJobTheme

class ActivityRelatorio : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeJobTheme {

                ComponentsActivitys().HomeActivityRelatorio()
            }
        }
    }
}
