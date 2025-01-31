package net.developermaster.timejob.screens

import android.R.attr.enabled
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import net.developermaster.timejob.model.ModelScreens

@Composable
fun LoginScreen(navcontroller: NavController) {

    MaterialTheme {

       if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()

    var enabled by remember { mutableStateOf(true) }


    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.tertiary),

        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "Login",
            color = Color.Blue,
            fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier
                .width(250.dp)
                .background(MaterialTheme.colorScheme.tertiary),

            value = "nombre",
            shape = Shapes().small,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.width(50.dp),//todo largura
                    tint = Color.Black
                )
            },
            onValueChange = {
                enabled = it.isNotEmpty()
            },

            )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier
                .width(250.dp)
                .background(MaterialTheme.colorScheme.tertiary),
            value = "contraseña",
            shape = Shapes().small,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    modifier = Modifier.width(50.dp),//todo largura
                    tint = Color.Black
                )
            },

            onValueChange = {
                    enabled = it.isNotEmpty()
                }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {

                    navcontroller.navigate(ModelScreens.ListarTodosScreenObject.route)

                }) {

                    Text(text = "Login")
                }
            }
    }

}