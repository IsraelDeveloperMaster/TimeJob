package net.developermaster.timejob.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarPlanta2Screen(navcontroller: NavController) {

    TopAppBar(colors = androidx.compose.material3.TopAppBarDefaults.largeTopAppBarColors(),
        modifier = Modifier.padding(10.dp),
        title = {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back",
                modifier = Modifier.clickable {
                    navcontroller.popBackStack()
                })

            Text(
                modifier = Modifier.padding(start = 30.dp), text = "Planta 1"

            )
        },
        actions = {
//            Text(text = "Ações")
            Icon(
                imageVector = Icons.Default.Menu, contentDescription = "Menu"
            )
        })
}

@Composable
fun Planta2Screen(navcontroller: NavController) {

    Scaffold(Modifier.fillMaxSize(),

        topBar = {

            TopBarPlanta2Screen(navcontroller)
        }

    ) { paddingValues ->

        BodyPlanta2Screen(paddingValues)
    }
}

@Composable
fun BodyPlanta2Screen(paddingValues: PaddingValues) {

//    StaffRoomManagement()
}









