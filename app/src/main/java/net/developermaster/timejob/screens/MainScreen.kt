package net.developermaster.timejob.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import net.developermaster.timejob.model.ModelScreens

@Composable
fun MainScreen(navcontroller: NavController) {

    Scaffold(
        Modifier
            .fillMaxSize()

            .background(color = Color.Blue),
        topBar = {
//            TopBarMainScreen(navcontroller)
            LoginGestionScreen(navcontroller)
        }

    ) { paddingValues ->

        Body(
            paddingValues, navcontroller
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarMainScreen(navcontroller: NavController) {

    val context = LocalContext.current

    Spacer(modifier = Modifier.height(100.dp))

    TopAppBar(
        modifier = Modifier
            .padding(10.dp),

        title = {

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back",
                modifier = Modifier.clickable {
                    navcontroller.popBackStack()
                }
            )

            Text(
                modifier = Modifier.padding(start = 30.dp),
                text = "Main Screen"
            )
        },

        actions = {
//            Text(text = "Ações")
            Icon(
                modifier = Modifier.clickable {

                    Toast.makeText(context, "Menu", Toast.LENGTH_SHORT).show()
                },
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu"
            )
        }
    )
}

@Composable
fun Body(paddingValues: PaddingValues, navcontroller: NavController) {

    var textoRemember by remember { mutableStateOf("") }

/*    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(120.dp))

        Text(
            text = "Main Screen",
            color = Color.Red,
            fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif,
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(modifier = Modifier.height(50.dp),
            shape = Shapes().medium,
            value = textoRemember,
            onValueChange = { textoDigitado ->

                textoRemember = textoDigitado
            })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {

        }) {

            Text(text = "Recuperar Texto")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {

            navcontroller.navigate(ModelScreens.InformationScreensObject.route + "/$textoRemember")

        }) {

            Text(text = "Information Screen")
        }
    }*/
}

