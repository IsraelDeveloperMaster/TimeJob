package net.developermaster.timejob.view

import androidx.compose.foundation.background
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import net.developermaster.timejob.model.ModelScreens

@Composable
private fun TopBar(paddingValues: PaddingValues, navcontroller: NavController) {
}

@Composable
internal fun MainScreen(navController: NavHostController) {

    Scaffold(Modifier.fillMaxSize(), bottomBar = {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            actions = {

            },

            floatingActionButton = {
                //Add
                FloatingActionButton(onClick = {
                    navController.navigate(ModelScreens.AddScreenObject.route)
                }) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                }
                //Float temporario
                FloatingActionButton(modifier = Modifier.padding(start = 148.dp), onClick = {
                    navController.navigate(ModelScreens.ListarTodosScreenObject.route)
                }) {
                    Icon(Icons.Filled.Search, contentDescription = null)
                }
            },
        )
    }, topBar = {
        TopBar(
            paddingValues = PaddingValues(), navcontroller = navController
        )
    }

    ) { paddingValues ->

        TopBar(paddingValues, navController)

        val listaMeses by remember {
            mutableStateOf(
                listOf(
                    "Enero",
                    "Febrero",
                    "Marzo",
                    "Abril",
                    "Mayo",
                    "Junio",
                    "Julio",
                    "Agosto",
                    "Septiembre",
                    "Octubre",
                    "Noviembre",
                    "Diciembre"
                )
            )
        }

        MaterialTheme {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // ➕ Fixed 3 columns
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(8.dp)
            ) {

                items(listaMeses) { item ->

                    ItemBox(mes = item, navController = navController)
                }
            }
        }
    }
}

@Composable
private fun ItemBox(mes: String, navController: NavController) {

    Box(
        modifier = Modifier
            .clickable {

                // Navega para a tela correspondente ao mês selecionado
                navController.navigate(ModelScreens.SelectMesScreenObject.route + "/$mes")

            }
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),

        ) {

        Text(
            text = mes,
            modifier = Modifier.align(Alignment.Center),
            fontSize = 20.sp,
            fontFamily = FontFamily.Serif,
            style = MaterialTheme.typography.titleLarge
        )
    }
}