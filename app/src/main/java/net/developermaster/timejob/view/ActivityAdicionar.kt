package net.developermaster.timejob.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import net.developermaster.timejob.core.ComponentsFireBase
import net.developermaster.timejob.view.ui.theme.TimeJobTheme

class ActivityAdicionar : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeJobTheme {
                ComponentsFireBase().Salvar()
            }
        }
    }
}
