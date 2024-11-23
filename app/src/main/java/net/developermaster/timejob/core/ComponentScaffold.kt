package net.developermaster.timejob.core

import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import net.developermaster.timejob.view.ActivityAdicionar

class ComponentScaffold {

    @Composable
    fun BottomBar() {

        BottomAppBar(
            containerColor = Color.Gray,

            ) {

            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clickable {

                        Toast
                            .makeText(Application(), "Home Clicado", Toast.LENGTH_SHORT)
                            .show()

                    },//todo clique
            )
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier
                    .padding(start = 130.dp)
                    .clickable {

                        Toast
                            .makeText(Application(), "Favorite Clicado", Toast.LENGTH_SHORT)
                            .show()

                    },//todo clique
            )
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier
                    .padding(start = 130.dp)
                    .clickable {

                        Toast
                            .makeText(Application(), "AddCircle Clicado", Toast.LENGTH_SHORT)
                            .show()

                    },//todo clique
            )
        }
    }

    @Composable
    fun Fab() {

        val context = LocalContext.current

        FloatingActionButton(onClick = {

            Toast.makeText(context, "Clicou no botão", Toast.LENGTH_SHORT).show()

            //todo ação ao clicar no botão
            val intent = Intent(context, ActivityAdicionar::class.java)
            context.startActivity(intent)

        }) {

            Icon(
                imageVector = Icons.Filled.Add, contentDescription = "Localized description"
            )
        }
    }
}