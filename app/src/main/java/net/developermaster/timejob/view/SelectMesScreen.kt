package net.developermaster.timejob.view

import android.icu.util.Calendar
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import java.time.Duration
import java.time.LocalTime
import java.util.Date
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
private fun TopBar(paddingValues: PaddingValues, navcontroller: NavController) {
}

@Composable
internal fun SelectMesScreen(navController: NavHostController, mes: String) {

    Scaffold(Modifier.fillMaxSize(), bottomBar = {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            actions = {

            },

            floatingActionButton = {
                //propinas
                FloatingActionButton(modifier = Modifier.padding(start = 155.dp), onClick = {
                    navController.navigate(ModelScreens.PropinaScreenObject.route)
                }) {
                    Icon(Icons.Filled.Favorite, contentDescription = null)
                }

                //pesquisa
                FloatingActionButton(onClick = {
                    navController.navigate(ModelScreens.RelatorioScreenObject.route)
                }) {
                    Icon(Icons.Filled.Search, contentDescription = null)
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
        var inicioMesRemember by remember { mutableStateOf("") }
        var fimMesRemember by remember { mutableStateOf("") }

        // Função para simular uma atualização de dados
        fun funcaoQueAtualizaTela() {

            coroutineScopeRemember.launch {

                isRefreshingRemember = true

                delay(2000) // Simula o carregamento de dados

                /////////////////////////////

                LaunchedEffect(mes) {
                    // Carregar dados do Firestore com base no mês
                    val queryMes = FirebaseFirestore.getInstance().collection(mes).orderBy("fecha").get().await()
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

                isRefreshingRemember = false
            }
        }

        MaterialTheme {

            SwipeRefresh(

                state = rememberSwipeRefreshState(isRefreshingRemember),
                onRefresh = { funcaoQueAtualizaTela() }, // Chama a função de atualização
                modifier = Modifier.fillMaxSize(),
                swipeEnabled = true,
                indicatorAlignment = Alignment.TopCenter,
                indicatorPadding = PaddingValues(top = 50.dp)

            ) {

                LaunchedEffect(mes) {
                    // Carregar dados do Firestore com base no mês
                    val queryMes = FirebaseFirestore.getInstance().collection(mes).orderBy("fecha").get().await()
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
    navController: NavController, modelTimeJob: ModelTimeJob, mes: String

) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),

        ) {

        //////////////// CALCULO TEMPO ////////////////

        //calculo tempo hora entrada
        val horaEntrada = LocalTime.of(
            modelTimeJob.horaEntrada.toInt(), modelTimeJob.minutoEntrada.toInt()
        )

        //calculo tempo hora saida
        val horaSalida = LocalTime.of(
            modelTimeJob.horaSalida.toInt(), modelTimeJob.minutoSalida.toInt()
        )

        val duracao = Duration.between(horaEntrada, horaSalida)
        val horas = duracao.toHours()
        val minutos = duracao.toMinutes() % 60
        val resultadoCalculorHoraFormatado = String.format("%02d:%02d", horas, minutos)

        horas.toDuration(DurationUnit.HOURS)
        minutos.toDuration(DurationUnit.MINUTES)

        //////////////// CALCULO TEMPO ////////////////

        Text(
            text = "Fecha " + modelTimeJob.fecha,
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth(),
            fontSize = 13.sp,
            fontFamily = FontFamily.Serif,
            style = MaterialTheme.typography.titleLarge
        )

        //entrada
        Text(
            text = "Entrada " + modelTimeJob.horaEntrada + ":" + modelTimeJob.minutoEntrada,
            modifier = Modifier
                .padding(top = 25.dp, start = 8.dp)
                .fillMaxWidth(),
            fontSize = 15.sp,
            fontFamily = FontFamily.Serif,
            style = MaterialTheme.typography.titleLarge
        )

        //salida
        Text(
            text = "Salida " + modelTimeJob.horaSalida + ":" + modelTimeJob.minutoSalida,
            modifier = Modifier
                .padding(top = 50.dp, start = 8.dp)
                .fillMaxWidth(),
            fontSize = 15.sp,
            fontFamily = FontFamily.Serif,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Horas $resultadoCalculorHoraFormatado",
            modifier = Modifier
                .padding(top = 80.dp, start = 8.dp)
                .fillMaxWidth(),
            fontSize = 15.sp,
            fontFamily = FontFamily.Serif,
            style = MaterialTheme.typography.titleLarge
        )

        //delete
        Icon(
            modifier = Modifier
                .padding(top = 120.dp, start = 16.dp)
                .clickable {

                    navController.navigate(ModelScreens.DeleteScreenObject.route + "/${modelTimeJob.id}/${mes}")

                }, imageVector = Icons.Default.Delete, contentDescription = null, tint = Color.Black
        )

        //atualizar
        Icon(
            modifier = Modifier
                .padding(top = 120.dp, start = 90.dp)
                .clickable {

                    navController.navigate(ModelScreens.UpdateScreenObject.route + "/${modelTimeJob.id}/${mes}")

                }, imageVector = Icons.Default.Edit, contentDescription = null, tint = Color.Black
        )
    }
}

@Composable
fun UpdateItemDetailScreen2(navController: NavController, itemId: String, itemMes: String) {

    var fechaRemember by remember { mutableStateOf("") }
    var horaEntradaRemember by remember { mutableStateOf("") }
    var minutoEntradaRemember by remember { mutableStateOf("") }
    var horaSalidaRemember by remember { mutableStateOf("") }
    var minutoSalidaRemember by remember { mutableStateOf("") }
    var propinasRemember by remember { mutableStateOf("") }

    val firestore = FirebaseFirestore.getInstance()
    var modelTimeJob by remember { mutableStateOf<ModelTimeJob?>(null) }

    // Carregar o item específico do Firestore
    LaunchedEffect(Unit) {
        val document = firestore.collection(itemMes).document(itemId).get().await()

        modelTimeJob = ModelTimeJob(
            id = document.id,
            fecha = document.getString("fecha") ?: "",
            horaEntrada = document.getString("horaEntrada") ?: "",
            minutoEntrada = document.getString("minutoEntrada") ?: "",
            horaSalida = document.getString("horaSalida") ?: "",
            minutoSalida = document.getString("minutoSalida") ?: "",
            propinas = document.getString("propinas") ?: ""
        )

        Log.d("UpdateItemDetailScreen", "Item carregado: $modelTimeJob")
    }

    val context = LocalContext.current

    modelTimeJob?.let { modelTimeJobItem ->

        fechaRemember = modelTimeJobItem.fecha
        horaEntradaRemember = modelTimeJobItem.horaEntrada
        minutoEntradaRemember = modelTimeJobItem.minutoEntrada
        horaSalidaRemember = modelTimeJobItem.horaSalida
        minutoSalidaRemember = modelTimeJobItem.minutoSalida
        propinasRemember = modelTimeJobItem.propinas

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Atualizar",
                style = MaterialTheme.typography.headlineMedium,
            )

            Spacer(modifier = Modifier.height(16.dp))

            //fecha
            OutlinedTextField(
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier.width(200.dp),
                value = fechaRemember,
                onValueChange = { fechaRemember = it },
                label = { Text("Fecha") },
                trailingIcon = {

                    Icon(

                        imageVector = Icons.Default.DateRange,//icone
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)

                            .clickable {

                                val dia: Int
                                val mes: Int
                                val ano: Int

                                val dataAtual = Calendar.getInstance()
                                dia = dataAtual.get(Calendar.DAY_OF_MONTH)
                                mes = dataAtual.get(Calendar.MONTH)
                                ano = dataAtual.get(Calendar.YEAR)
                                dataAtual.time = Date()


                                val datePickerDialog = android.app.DatePickerDialog(
                                    context, { _: DatePicker, ano: Int, mes: Int, dia: Int ->
                                        fechaRemember = "$dia/${mes + 1}/$ano"

                                        val fechaFormatada =
                                            String.format("%02d/%02d/%02d", dia, mes + 1, ano)

                                        fechaRemember = fechaFormatada

                                        modelTimeJobItem.fecha = fechaRemember

                                    }, ano, mes, dia
                                )

                                datePickerDialog.show()

                            },//clickable

                        tint = Color.Blue,// cor azul da borda
                    )
                },
                readOnly = true,

                )

            Spacer(modifier = Modifier.height(16.dp))

            //hora de entrada
            OutlinedTextField(
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier
                    .width(200.dp)
                    .clickable {},
                value = "$horaEntradaRemember : $minutoEntradaRemember",
                onValueChange = { horaEntradaRemember = it },
                label = { Text("Hora Entrada") },
                trailingIcon = {
                    Icon(

                        painter = painterResource(id = R.drawable.time),
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)

                            .clickable {

                                relogio(context) { hora, minuto ->

                                    horaEntradaRemember = "$hora"
                                    minutoEntradaRemember = "$minuto"

                                    val horaFormatado = String.format("%02d", hora)
                                    horaEntradaRemember = horaFormatado

                                    val minutoFormatado = String.format("%02d", minuto)
                                    minutoEntradaRemember = minutoFormatado

                                    modelTimeJobItem.horaEntrada = horaEntradaRemember
                                    modelTimeJobItem.minutoEntrada = minutoEntradaRemember
                                }

                            },//clickable

                        tint = Color.Blue,// cor azul da borda
                    )
                },
                readOnly = true,

                )

            Spacer(modifier = Modifier.height(16.dp))

            //hora de saida
            OutlinedTextField(
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier
                    .width(200.dp)
                    .clickable {},
                value = "$horaEntradaRemember : $minutoSalidaRemember",
                onValueChange = { horaSalidaRemember = it },
                label = { Text("Hora Salida") },
                trailingIcon = {
                    Icon(

                        painter = painterResource(id = R.drawable.time),
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)

                            .clickable {

                                relogio(context) { hora, minuto ->

                                    horaSalidaRemember = "$hora"
                                    minutoSalidaRemember = "$minuto"

                                    val horaFormatado = String.format("%02d", hora)
                                    horaSalidaRemember = horaFormatado

                                    val minutoFormatado = String.format("%02d", minuto)
                                    minutoSalidaRemember = minutoFormatado

                                    modelTimeJobItem.horaSalida = horaSalidaRemember
                                    modelTimeJobItem.minutoSalida = minutoSalidaRemember
                                }

                            },//clickable

                        tint = Color.Blue,// cor azul da borda
                    )
                },
                readOnly = true,

                )

            Spacer(modifier = Modifier.height(16.dp))

            //propinas
            OutlinedTextField(textStyle = TextStyle(color = Color.Black),
                modifier = Modifier
                    .width(200.dp)
                    .clickable {},
                value = modelTimeJobItem.propinas,
                onValueChange = { newPropinas ->
                    modelTimeJob = modelTimeJobItem.copy(propinas = newPropinas)
                },
                label = { Text("Propinas") },
                trailingIcon = {

                })

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                    // Extrai o mês da data
                    val partesData = fechaRemember.split("/")
                    if (partesData.size == 3) {
                        val mes = partesData[1].toInt() // O mês está na segunda posição (índice 1)
                        // Mapeia o número do mês para o nome da coleção
                        val nomeMes = when (mes) {
                            1 -> "Enero"
                            2 -> "Febrero"
                            3 -> "Marzo"
                            4 -> "Abril"
                            5 -> "Mayo"
                            6 -> "Junio"
                            7 -> "Julio"
                            8 -> "Agosto"
                            9 -> "Septiembre"
                            10 -> "Octubre"
                            11 -> "Noviembre"
                            12 -> "Diciembre"
                            else -> ""
                        }
                        // Atualizar o item no Firestore
                        firestore.collection(nomeMes).document(modelTimeJobItem.id).update(
                            mapOf(

                                "fecha" to modelTimeJobItem.fecha,
                                "horaEntrada" to modelTimeJobItem.horaEntrada,
                                "minutoEntrada" to modelTimeJobItem.minutoEntrada,
                                "horaSalida" to modelTimeJobItem.horaSalida,
                                "minutoSaida" to modelTimeJobItem.minutoSalida,
                                "propinas" to modelTimeJobItem.propinas,

                                )

                        ).addOnSuccessListener {
                            Log.d("UpdateItemDetailScreen", "Item atualizado com sucesso")
                        }.addOnFailureListener { e ->
                            Log.e("UpdateItemDetailScreen", "Erro ao atualizar item", e.cause)
                        }

                        Toast.makeText(context, "Atualizado com sucesso", Toast.LENGTH_SHORT).show()

                        // Navegar de volta à tela anterior
                        navController.navigate(ModelScreens.MainScreenObject.route)
                    }
                },
            ) {
                Text("Atualizar")
            }
        }
    }
}

@Composable
fun DeleteItem(navController: NavController, itemId: String, itemMes: String) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false },
            title = { Text(text = "Deletar Item") },
            text = { Text("Tem certeza que deseja deletar este item?") },
            confirmButton = {
                Button(onClick = {
                    FirebaseFirestore.getInstance().collection(itemMes).document(itemId).delete()
                        .addOnSuccessListener {
                            Toast.makeText(
                                context, "Item deletado com sucesso", Toast.LENGTH_SHORT
                            ).show()

                            navController.popBackStack()
                        }.addOnFailureListener { e ->
                            Toast.makeText(
                                context, "Erro ao deletar item: ${e.message}", Toast.LENGTH_SHORT
                            ).show()
                        }
                    showDialog = false
                }) {
                    Text("Deletar")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showDialog = false

                    Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show()

                    navController.popBackStack()

                }) {

                    Text("Cancelar")
                }
            })
    }
}