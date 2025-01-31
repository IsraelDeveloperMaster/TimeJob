package net.developermaster.timejob.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import net.developermaster.navigationnavcontrollerjetpackcompose.navigator.NavigationNavController

import net.developermaster.timejob.view.ui.theme.TimeJobTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            TimeJobTheme {
                //esqueleto app
                NavigationNavController()
            }
        }
    }
}

