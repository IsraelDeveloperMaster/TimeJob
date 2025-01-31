package net.developermaster.timejob.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import java.sql.Array
import java.time.Duration
import java.time.LocalTime
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
private fun TopBar(paddingValues: PaddingValues, navcontroller: NavController) {

    /*    TopBarHomeLimpiezaScreen(
            colors = TopAppBarDefaults.largeTopAppBarColors(),
            modifier = Modifier.padding(10.dp),
            title = {
                *//*
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back",
                modifier = Modifier.clickable {
                    navcontroller.popBackStack()
                })
            *//*

            Text(modifier = Modifier.padding(start = 100.dp), text = "")
        },
        actions = {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search",
                modifier = Modifier.clickable {

                    navcontroller.navigate(ModelScreens.RelatorioScreenObject.route)

                    //limpar lista
                    listaResultadoRetornados.clear()

                })

            Spacer(modifier = Modifier.width(20.dp))

            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "add",
                modifier = Modifier.clickable {

                    navcontroller.navigate(ModelScreens.AddScreenObject.route)

                    //limpar lista
                    listaResultadoRetornados.clear()

                })
        })*/
}

@Composable
internal fun SelectMesScreen(navController: NavHostController, mes: String) {

    Scaffold(

        Modifier.fillMaxSize(),

        bottomBar = {

            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                actions = {

                },

                floatingActionButton = {

                    //eventario
                    FloatingActionButton(modifier = Modifier.padding(start = 155.dp),
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
        var modelSwitchRememberPlantas2 by remember { mutableStateOf<List<ModelTimeJob>>(emptyList()) }

        var inicioMesRemember by remember { mutableStateOf("") }
        var fimMesRemember by remember { mutableStateOf("") }

        var dias1 by remember { mutableStateOf("") }
        var dias2 = Array( 31 ) { "" }
        var dias3 = ""


        // Função para simular uma atualização de dados
        fun FuncaoQueAtualizaTela() {

            coroutineScopeRemember.launch {

                isRefreshingRemember = true

                delay(2000) // Simula o carregamento de dados

                /////////////////////////////

                if (mes == "Enero") {

                    val queryMes = FirebaseFirestore.getInstance().collection("Enero").orderBy("fecha").get().await()

                    modelSwitchRememberPlantas = queryMes.documents.map { document ->

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

                if (mes == "Febrero") {

                    val queryMes = FirebaseFirestore.getInstance().collection("Febrero").orderBy("fecha").get().await()

                    modelSwitchRememberPlantas = queryMes.documents.map { document ->

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

                if (mes == "Marzo") {

                    inicioMesRemember = "01/03/2025"
                    fimMesRemember = "31/03/2025"
                }

                if (mes == "Abril") {

                    inicioMesRemember = "01/04/2025"
                    fimMesRemember = "31/04/2025"
                }

                if (mes == "Mayo") {

                    inicioMesRemember = "01/05/2025"
                    fimMesRemember = "31/05/2025"
                }

                if (mes == "Junio") {

                    inicioMesRemember = "01/06/2025"
                    fimMesRemember = "31/06/2025"

                }


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

                    if (mes == "Enero") {

                        val queryMes = FirebaseFirestore.getInstance().collection("Enero").orderBy("fecha").get().await()

                        modelSwitchRememberPlantas = queryMes.documents.map { document ->

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

                    if (mes == "Febrero") {

                        val queryMes = FirebaseFirestore.getInstance().collection("Febrero").orderBy("fecha").get().await()

                        modelSwitchRememberPlantas = queryMes.documents.map { document ->

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

                    if (mes == "Marzo") {

                        inicioMesRemember = "01/03/2025"
                        fimMesRemember = "31/03/2025"
                    }

                    if (mes == "Abril") {

                        inicioMesRemember = "01/04/2025"
                        fimMesRemember = "31/04/2025"
                    }

                    if (mes == "Mayo") {

                        inicioMesRemember = "01/05/2025"
                        fimMesRemember = "31/05/2025"
                    }

                    if (mes == "Junio") {

                        inicioMesRemember = "01/06/2025"
                        fimMesRemember = "31/06/2025"

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
                    modifier = Modifier.padding(8.dp)
                ) {

                    items(modelSwitchRememberPlantas) { item ->

                        ItemBox(modelTimeJob = item, navController = navController, mes = mes)
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemBox(
    navController: NavController,
    modelTimeJob: ModelTimeJob,
    mes: String

) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium)
//            .background(MaterialTheme.colorScheme.inversePrimary)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),

        ) {

        //////////////// CALCULO TEMPO ////////////////

        var variavelGlobalSomaHora = Duration.ZERO

        //calculo tempo hora entrada
        val horaEntrada = LocalTime.of(
            modelTimeJob.horaEntrada.toInt(),
            modelTimeJob.minutoEntrada.toInt()
        )

        //calculo tempo hora saida
        val horaSalida = LocalTime.of(
            modelTimeJob.horaSalida.toInt(),
            modelTimeJob.minutoSalida.toInt()
        )

        val duracao = Duration.between(horaEntrada, horaSalida)
        val horas = duracao.toHours()
        val minutos = duracao.toMinutes() % 60
        val resultadoCalculorHoraFormatado = String.format("%02d:%02d", horas, minutos)

        horas.toDuration(DurationUnit.HOURS)
        minutos.toDuration(DurationUnit.MINUTES)

//        val totalHoras = horas.toDuration(DurationUnit.HOURS) + minutos.toDuration(DurationUnit.MINUTES)


        /*        variavelGlobalSomaHora += horas.toDuration(DurationUnit.HOURS)
                    .toJavaDuration() + minutos.toDuration(DurationUnit.MINUTES)
                    .toJavaDuration()*/


        //////////////// CALCULO TEMPO ////////////////


        Text(
            text = "Fecha " + modelTimeJob.fecha,
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth(),
            fontSize = 15.sp,
            fontFamily = FontFamily.Serif,
            style = MaterialTheme.typography.titleLarge
        )

        //entrada
        Text(
            text = "Entrada " + modelTimeJob.horaEntrada + ":" + modelTimeJob.minutoEntrada,
            modifier = Modifier
                .padding(top = 20.dp, start = 8.dp)
                .fillMaxWidth(),
            fontSize = 15.sp,
            fontFamily = FontFamily.Serif,
            style = MaterialTheme.typography.titleLarge
        )

        //salida
        Text(
            text = "Salida " + modelTimeJob.horaSalida + ":" + modelTimeJob.minutoSalida,
            modifier = Modifier
                .padding(top = 40.dp, start = 8.dp)
                .fillMaxWidth(),
            fontSize = 15.sp,
            fontFamily = FontFamily.Serif,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Horas " + resultadoCalculorHoraFormatado,
            modifier = Modifier
                .padding(top = 60.dp, start = 8.dp)
                .fillMaxWidth(),
            fontSize = 15.sp,
            fontFamily = FontFamily.Serif,
            style = MaterialTheme.typography.titleLarge
        )

        Icon(
            modifier = Modifier
                .padding(top = 50.dp, start = 120.dp)
                .clickable {

//                    navController.navigate(ModelScreens.EditScreenObject.route + "/${modelTimeJob.id}"),
                },
            imageVector = Icons.Default.Delete,
            contentDescription = "",
            tint = Color.Black
        )

        Icon(
            modifier = Modifier
                .padding(top = 90.dp, start = 120.dp)
                .clickable {

//                    navController.navigate(ModelScreens.EditScreenObject.route + "/${modelTimeJob.id}"),
                },
            imageVector = Icons.Default.Edit,
            contentDescription = "",
            tint = Color.Black
        )
    }
}


/*
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

