package net.developermaster.timejob.screens

import android.widget.Button
import androidx.compose.foundation.background
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import net.developermaster.timejob.R
import net.developermaster.timejob.model.ModelScreens
import net.developermaster.timejob.model.ModelTimeJob

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(paddingValues: PaddingValues, navcontroller: NavController) {
}

@Composable
internal fun MesesScreen(navController: NavHostController) {

    Scaffold(

        Modifier.fillMaxSize(),

        bottomBar = {

            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                actions = {

                },

                floatingActionButton = {

                    //eventario
                    FloatingActionButton(
                        modifier = Modifier.padding(start = 155.dp),
                        onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.time),
                            contentDescription = "eventario"
                        )
                    }

                    //pesquisa
                    FloatingActionButton(onClick = { }) {
                        Icon(Icons.Filled.Search, contentDescription = "pesquisa")
                    }
                },
            )
        },

        topBar = {

            TopBar(
                paddingValues = PaddingValues(), navcontroller = navController
            )
        }

    ) { paddingValues ->

        TopBar(paddingValues, navController)

        val coroutineScopeRemember = rememberCoroutineScope()
        var isRefreshingRemember by remember { mutableStateOf(false) }
        var modelSwitchRememberPlantas by remember { mutableStateOf<List<ModelTimeJob>>(emptyList()) }
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

        // Função para simular uma atualização de dados
        fun FuncaoQueAtualizaTela() {

            coroutineScopeRemember.launch {

                isRefreshingRemember = true

                delay(2000) // Simula o carregamento de dados

                /////////////////////////////
                /*
                                if (planta == "planta2") {

                                    // Carregar os itens do Firestore planta 2
                                    val retornoPlanta2 =
                                        FirebaseFirestore.getInstance().collection("planta-2").get()
                                            .await().query.orderBy("id").get().await()

                                    modelSwitchRememberPlantas = retornoPlanta2.documents.map { document ->

                                        ModelSwitch(

                                            id = document.id,
                                            fecha = document.getString("fecha") ?: "",
                                            funcionaria = document.getString("funcionaria") ?: "",
                                            apartamentoLimpo = document.getBoolean("apartamentoLimpo") ?: false,
                                            apartamentoSujo = document.getBoolean("apartamentoSujo") ?: false,
                                            clienteSaindo = document.getBoolean("clienteSaindo") ?: false,
                                            clienteEntrando = document.getBoolean("clienteEntrando") ?: false,
                                            clienteFijo = document.getBoolean("clienteFijo") ?: false,
                                            trocaSabanas = document.getBoolean("trocaSabanas") ?: false,
                                            mantenimiento = document.getBoolean("mantenimiento") ?: false,
                                        )
                                    }
                                }

                                if (planta == "planta1") {

                                    // Carregar os itens do Firestore planta 1
                                    val retornoPlanta1 =
                                        FirebaseFirestore.getInstance().collection("planta-1").get()
                                            .await().query.orderBy("id").get().await()

                                    modelSwitchRememberPlantas = retornoPlanta1.documents.map { document ->

                                        ModelSwitch(

                                            id = document.id,
                                            fecha = document.getString("fecha") ?: "",
                                            funcionaria = document.getString("funcionaria") ?: "",
                                            apartamentoLimpo = document.getBoolean("apartamentoLimpo") ?: false,
                                            apartamentoSujo = document.getBoolean("apartamentoSujo") ?: false,
                                            clienteSaindo = document.getBoolean("clienteSaindo") ?: false,
                                            clienteEntrando = document.getBoolean("clienteEntrando") ?: false,
                                            clienteFijo = document.getBoolean("clienteFijo") ?: false,
                                            trocaSabanas = document.getBoolean("trocaSabanas") ?: false,
                                            mantenimiento = document.getBoolean("mantenimiento") ?: false,
                //                            camas = document.getString("camas")?.toInt() ?: 0,
                                        )
                                    }
                                }

                                if (planta == "planta0") {

                                    // Carregar os itens do Firestore planta 0
                                    val retornoPlanta0 =
                                        FirebaseFirestore.getInstance().collection("planta-0").get()
                                            .await().query.orderBy("id").get().await()

                                    modelSwitchRememberPlantas = retornoPlanta0.documents.map { document ->

                                        ModelSwitch(

                                            id = document.id,
                                            fecha = document.getString("fecha") ?: "",
                                            funcionaria = document.getString("funcionaria") ?: "",
                                            apartamentoLimpo = document.getBoolean("apartamentoLimpo") ?: false,
                                            apartamentoSujo = document.getBoolean("apartamentoSujo") ?: false,
                                            clienteSaindo = document.getBoolean("clienteSaindo") ?: false,
                                            clienteEntrando = document.getBoolean("clienteEntrando") ?: false,
                                            clienteFijo = document.getBoolean("clienteFijo") ?: false,
                                            trocaSabanas = document.getBoolean("trocaSabanas") ?: false,
                                            mantenimiento = document.getBoolean("mantenimiento") ?: false,
                //                            camas = document.getString("camas")?.toInt() ?: 0,
                                        )
                                    }
                                }

                                */

                //////////////////////////

                isRefreshingRemember = false
            }
        }

        MaterialTheme {

            SwipeRefresh(

                state = rememberSwipeRefreshState(isRefreshingRemember),
                onRefresh = { FuncaoQueAtualizaTela() }, // Chama a função de atualização
                modifier = Modifier.fillMaxSize(),
                swipeEnabled = true,
                indicatorAlignment = Alignment.TopCenter,
                indicatorPadding = PaddingValues(top = 50.dp)

            ) {

                LaunchedEffect(Unit) {

                    val inicioJaneiro = "01/01/2025" // Altere para o ano desejado
                    val fimJaneiro = "31/01/2025" // O primeiro dia de fevereiro

                    val querySnapshot = FirebaseFirestore.getInstance().collection("TimeJob")
                        .whereGreaterThanOrEqualTo("fecha", inicioJaneiro)
                        .whereLessThan("fecha", fimJaneiro)
                        .get()
                        .await()

                    modelSwitchRememberPlantas = querySnapshot.documents.map { document ->

                        ModelTimeJob(

                            id = document.id,
                            fecha = document.getString("fecha") ?: "",
                            horaEntrada = document.getString("horaEntrada") ?: "",
                            minutoEntrada = document.getString("minutoEntrada") ?: "",
                            horaSalida = document.getString("horaSalida") ?: "",
                            minutoSalida = document.getString("minutoSalida") ?: "",
                            propinas = document.getString("propinas") ?: "",
                        )
                    }
                }


                /*
                if (planta == "planta2") {

                    // Carregar os itens planta 2
                    LaunchedEffect(Unit) {

                        val querySnapshot =
                            FirebaseFirestore.getInstance().collection("planta-2").get()
                                .await().query.orderBy("id").get()
                                .await()

                        modelSwitchRememberPlantas = querySnapshot.documents.map { document ->

                            ModelSwitch(

                                id = document.id,
                                fecha = document.getString("fecha") ?: "",
                                funcionaria = document.getString("funcionaria") ?: "",
                                apartamentoLimpo = document.getBoolean("apartamentoLimpo") ?: false,
                                apartamentoSujo = document.getBoolean("apartamentoSujo") ?: false,
                                clienteSaindo = document.getBoolean("clienteSaindo") ?: false,
                                clienteEntrando = document.getBoolean("clienteEntrando") ?: false,
                                clienteFijo = document.getBoolean("clienteFijo") ?: false,
                                trocaSabanas = document.getBoolean("trocaSabanas") ?: false,
                                mantenimiento = document.getBoolean("mantenimiento") ?: false,
                                camas = document.getLong("camas") ?: 0,
                            )
                        }
                    }
                }

                if (planta == "planta1") {

                    // Carregar os itens planta 1
                    LaunchedEffect(Unit) {

                        val querySnapshot =
                            FirebaseFirestore.getInstance().collection("planta-1").get()
                                .await().query.orderBy("id").get()
                                .await()

                        modelSwitchRememberPlantas = querySnapshot.documents.map { document ->

                            ModelSwitch(

                                id = document.id,
                                fecha = document.getString("fecha") ?: "",
                                funcionaria = document.getString("funcionaria") ?: "",
                                apartamentoLimpo = document.getBoolean("apartamentoLimpo") ?: false,
                                apartamentoSujo = document.getBoolean("apartamentoSujo") ?: false,
                                clienteSaindo = document.getBoolean("clienteSaindo") ?: false,
                                clienteEntrando = document.getBoolean("clienteEntrando") ?: false,
                                clienteFijo = document.getBoolean("clienteFijo") ?: false,
                                trocaSabanas = document.getBoolean("trocaSabanas") ?: false,
                                mantenimiento = document.getBoolean("mantenimiento") ?: false,
//                                camas = document.getString("camas")?.toInt() ?: 0,
                            )
                        }
                    }
                }

                if (planta == "planta0") {

                    // Carregar os itens planta 0
                    LaunchedEffect(Unit) {

                        val querySnapshot =
                            FirebaseFirestore.getInstance().collection("planta-0").get()
                                .await().query.orderBy("id").get()
                                .await()

                        modelSwitchRememberPlantas = querySnapshot.documents.map { document ->

                            ModelSwitch(

                                id = document.id,
                                fecha = document.getString("fecha") ?: "",
                                funcionaria = document.getString("funcionaria") ?: "",
                                apartamentoLimpo = document.getBoolean("apartamentoLimpo") ?: false,
                                apartamentoSujo = document.getBoolean("apartamentoSujo") ?: false,
                                clienteSaindo = document.getBoolean("clienteSaindo") ?: false,
                                clienteEntrando = document.getBoolean("clienteEntrando") ?: false,
                                clienteFijo = document.getBoolean("clienteFijo") ?: false,
                                trocaSabanas = document.getBoolean("trocaSabanas") ?: false,
                                mantenimiento = document.getBoolean("mantenimiento") ?: false,
//                                camas = document.getString("camas")?.toInt() ?: 0,
                            )
                        }
                    }
                }
                */


                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // ➕ Fixed 3 columns
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(8.dp)
                ) {

                    items(listaMeses) { item ->

                        ItemBox(mes = item, navController = navController)
                    }
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

                if (mes == "Enero") {

                    navController.navigate(ModelScreens.SelectMesScreenObject.route + "/${"Enero"}")
                }

                if (mes == "Febrero") {

                    navController.navigate(ModelScreens.SelectMesScreenObject.route+ "/${"Febrero"}")
                }

                if (mes == "Marzo") {

                    navController.navigate(ModelScreens.SelectMesScreenObject.route+ "/${"Marzo"}")
                }

                if (mes == "Abril") {

                    navController.navigate(ModelScreens.SelectMesScreenObject.route+ "/${"Abril"}")
                }

                if (mes == "Mayo") {

                    navController.navigate(ModelScreens.SelectMesScreenObject.route+ "/${"Mayo"}")
                }
            }
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium)
//            .background(MaterialTheme.colorScheme.inversePrimary)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),

        ) {

        Text(
            text = mes,
            modifier = Modifier
                .align(Alignment.Center),
            fontSize = 20.sp,
            fontFamily = FontFamily.Serif,
            style = MaterialTheme.typography.titleLarge
        )

    }

    /*


            if (modelTimeJob.fecha.contains("/01/")) {

            Text(
                text = "Enero",
                modifier = Modifier
                    .padding(start = 15.dp)
                    .fillMaxWidth(),
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif,
                style = MaterialTheme.typography.titleLarge
            )
        }

        if (fechaAtual == "*.*//*.*") {

            Text(
                text = "Febrero",
                modifier = Modifier
                    .padding(start = 15.dp)
                    .fillMaxWidth(),
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif,
                style = MaterialTheme.typography.titleLarge
            )
        }


    if (!modelSwitch.apartamentoSujo && !modelSwitch.clienteSaindo && !modelSwitch.mantenimiento && !modelSwitch.trocaSabanas && !modelSwitch.clienteFijo && !modelSwitch.clienteEntrando && modelSwitch.camas == 0L) {

        Box(
            modifier = Modifier
                .clickable {

                    navController.navigate(ModelScreens.SelectScreenOptionsObject.route + "/${modelSwitch.id}")

                }
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(Color.Green)
                .padding(16.dp),
        ) {

            // IDs da planta 0
            val apPlanta0 = (1..99).map { it.toString() }

            if (modelSwitch.id in apPlanta0) {

                Text(
                    text = "00" + modelSwitch.id,
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .fillMaxWidth(),
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    style = MaterialTheme.typography.titleLarge
                )

            } else {

                Text(
                    text = modelSwitch.id,
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .fillMaxWidth(),
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

    } else {

        Box(
            modifier = Modifier
                .clickable {

//                    navController.navigate(ModelScreens.SelectScreenOptionsObject.route + "/${modelSwitch.id}")

                }
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.inversePrimary)
                .padding(16.dp),
        ) {

            // IDs da planta 0
            val apPlanta0 = (1..99).map { it.toString() }

            if (modelSwitch.id in apPlanta0) {

                Text(
                    text = "00" + modelSwitch.id,
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .fillMaxWidth(),
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    style = MaterialTheme.typography.titleLarge
                )

            } else {

                Text(
                    text = modelSwitch.id,
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .fillMaxWidth(),
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            *//*

            if (modelSwitch.apartamentoSujo) {

                Icon(
                    modifier = Modifier.padding(top = 40.dp),
                    painter = painterResource(id = R.drawable.sucio),
                    contentDescription = "",
                    tint = Color.Black
                )
            }

            if (modelSwitch.clienteSaindo) {

                Icon(
                    modifier = Modifier.padding(top = 40.dp, start = 30.dp),
                    painter = painterResource(id = R.drawable.clientesalindo),
                    contentDescription = "",
                    tint = Color.Black
                )
            }

            if (modelSwitch.clienteFijo) {

                Icon(
                    modifier = Modifier.padding(top = 80.dp),
                    painter = painterResource(id = R.drawable.maleta),
                    contentDescription = "",
                    tint = Color.Black
                )
            }

            if (modelSwitch.clienteEntrando) {

                Icon(
                    modifier = Modifier.padding(top = 80.dp, start = 32.dp),
                    painter = painterResource(id = R.drawable.clienteentrando),
                    contentDescription = "",
                    tint = Color.Black
                )
            }

            if (modelSwitch.trocaSabanas) {

                Icon(
                    modifier = Modifier.padding(top = 115.dp),
                    painter = painterResource(id = R.drawable.sabanas),
                    contentDescription = "",
                    tint = Color.Black
                )
            }

            if (modelSwitch.mantenimiento) {

                Icon(
                    modifier = Modifier.padding(top = 115.dp, start = 32.dp),
                    painter = painterResource(id = R.drawable.mantenimiento1),
                    contentDescription = "",
                    tint = Color.Black
                )
            }

            if (modelSwitch.camas > 0) {

                Icon(
                    modifier = Modifier.padding(top = 150.dp),
                    painter = painterResource(id = R.drawable.cama),
                    contentDescription = "",
                    tint = Color.Black
                )


                Text(
                    text = "x" + modelSwitch.camas.toString(),
                    modifier = Modifier
                        .padding(top = 210.dp, start = 25.dp)
                        .fillMaxWidth(),
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            *//*
        }
    }
    */

}