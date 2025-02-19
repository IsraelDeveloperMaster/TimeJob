package net.developermaster.timejob.view

import android.icu.util.Calendar
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import kotlin.time.toJavaDuration

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

                    navController.navigate(ModelScreens.PropinaScreenObject.route + "/$mes")

                }) {
                    Icon(Icons.Filled.Favorite, contentDescription = null)
                }

                //pesquisa
                FloatingActionButton(onClick = {
                    navController.navigate(ModelScreens.RelatorioScreenObject.route + "/$mes")
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

                // Carregar dados do Firestore com base no mês
                val queryMes =
                    FirebaseFirestore.getInstance().collection(mes).orderBy("fecha").get().await()
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
                    val queryMes =
                        FirebaseFirestore.getInstance().collection(mes).orderBy("fecha").get()
                            .await()
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
            .clickable {
                navController.navigate(ModelScreens.DetalheScreenObject.route + "/${modelTimeJob.id}/${mes}")
            }
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
fun UpdateScreen(navController: NavController, itemId: String, itemMes: String) {

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

        MaterialThemeScreen {

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
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
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
                    modifier = Modifier
                        .width(200.dp)
                        .clickable {},
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
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
                    modifier = Modifier
                        .width(200.dp)
                        .clickable {},
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
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
                OutlinedTextField(modifier = Modifier
                    .width(200.dp)
                    .clickable {},
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
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
                            val mes =
                                partesData[1].toInt() // O mês está na segunda posição (índice 1)
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

                            Toast.makeText(context, "Atualizado com sucesso", Toast.LENGTH_SHORT)
                                .show()

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

@Composable
fun PropinaScreen(navController: NavHostController, itemMes: String) {

    Duration.ZERO

    val context = LocalContext.current
    val listaResultadoRetornados = mutableListOf<String>()
    var dataInicioRemember by remember { mutableStateOf("") }
    var dataFimRemember by remember { mutableStateOf("") }
    var totalPropinaRemember by remember { mutableIntStateOf(0) }

    MaterialThemeScreen {

        Column(
            modifier = Modifier, verticalArrangement = Arrangement.Center
        ) {

            //fecha inicial e final
            Row(
                modifier = Modifier, horizontalArrangement = Arrangement.Center
            ) {

                //hora inicial
                OutlinedTextField(
                    modifier = Modifier.width(200.dp),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                    value = dataInicioRemember,
                    onValueChange = { dataInicioRemember = it },
                    label = { Text("Fecha Inicial") },
                    trailingIcon = {
                        Icon(

                            imageVector = Icons.Default.DateRange,//icone
                            contentDescription = null,
                            modifier = Modifier
                                .width(50.dp)

                                .clickable {

                                    val year: Int
                                    val month: Int
                                    val day: Int
                                    val calendar = Calendar.getInstance()
                                    year = calendar.get(Calendar.YEAR)
                                    month = calendar.get(Calendar.MONTH)
                                    day = calendar.get(Calendar.DAY_OF_MONTH)
                                    calendar.time = Date()

                                    val datePickerDialog = android.app.DatePickerDialog(
                                        context, { _: DatePicker, year: Int, month: Int, day: Int ->
                                            dataInicioRemember = "$day/${month + 1}/$year"

                                            val fechaFormatada = String.format(
                                                "%02d/%02d/%02d", day, month + 1, year
                                            )

                                            dataInicioRemember = fechaFormatada

                                        }, year, month, day
                                    )

                                    datePickerDialog.show()

                                    totalPropinaRemember = 0

                                },//clickable

                            tint = Color.Blue,// cor azul da borda
                        )
                    },
                    readOnly = true,

                    )

                //hora final
                OutlinedTextField(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(start = 8.dp),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                    value = dataFimRemember,
                    onValueChange = { dataFimRemember = it },
                    label = { Text("Fecha Final") },
                    trailingIcon = {
                        Icon(

                            imageVector = Icons.Default.DateRange,//icone
                            contentDescription = null,
                            modifier = Modifier
                                .width(50.dp)
                                .clickable {

                                    val year: Int
                                    val month: Int
                                    val day: Int
                                    val calendar = Calendar.getInstance()
                                    year = calendar.get(Calendar.YEAR)
                                    month = calendar.get(Calendar.MONTH)
                                    day = calendar.get(Calendar.DAY_OF_MONTH)
                                    calendar.time = Date()

                                    val datePickerDialog = android.app.DatePickerDialog(
                                        context, { _: DatePicker, year: Int, month: Int, day: Int ->
                                            dataFimRemember = "$day/$month/$year"

                                            val fechaFormatada = String.format(
                                                "%02d/%02d/%02d", day, month + 1, year
                                            )

                                            dataFimRemember = fechaFormatada

                                        }, year, month, day
                                    )

                                    datePickerDialog.show()

                                },//clickable
                            tint = Color.Blue,// cor azul da borda
                        )
                    },
                    readOnly = true,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue, contentColor = Color.White
                ),

                onClick = {

                    // Limpa a lista de resultados e o total antes de buscar novos dados
                    listaResultadoRetornados.clear()
                    totalPropinaRemember = 0

                    val listaDeDadosRetornadas = FirebaseFirestore.getInstance().collection(itemMes)

                        .whereGreaterThanOrEqualTo("fecha", dataInicioRemember)
                        .whereLessThanOrEqualTo("fecha", dataFimRemember)

//                    .whereGreaterThanOrEqualTo("fecha", "04/01/2025")
//                    .whereLessThanOrEqualTo("fecha", "05/01/2025")

                    listaDeDadosRetornadas.addSnapshotListener { dadosRetornados, _ ->

                        val listaRetornada = dadosRetornados?.documents//todo document

                        listaRetornada?.forEach { documents ->

                            val dados = documents?.data

                            if (dados != null) {

                                val fechaDadosRetornados = dados["fecha"]
                                val propinasDadosRetornados = dados["propinas"]
                                val notasDadosRetornados = dados["notas"]

                                // Adiciona a string formatada à lista de resultados
                                listaResultadoRetornados.add("Fecha: $fechaDadosRetornados \nPropinas: €$propinasDadosRetornados \n\n             Notas: \n\n$notasDadosRetornados")

                                // Acumula o total de propinas
                                totalPropinaRemember += (propinasDadosRetornados.toString()
                                    .toIntOrNull() ?: 0)

                                //limpar campos
                                dataInicioRemember = ""
                                dataFimRemember = ""
                            }
                        }
                    }
                }

            ) {
                Text("Pesquisar")
            }

            //resultado
            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {

                //total propinas
                OutlinedTextField(
                    modifier = Modifier.width(200.dp),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),

                    value = if (totalPropinaRemember == 0) {
                        "" // Exibe uma string vazia se ambos os valores estiverem vazios
                    } else {
                        "$totalPropinaRemember €"
                    },

                    onValueChange = { },
                    label = { Text("Total Propinas") },
                    leadingIcon = {
                        Icon(

                            imageVector = Icons.Default.Favorite,//icone
                            contentDescription = null,
                            modifier = Modifier.width(50.dp),
                            tint = Color.Blue,// cor azul da borda
                        )
                    },
                    readOnly = true,

                    )
            }

            // Exibir resultados em uma LazyColumn
            LazyColumn(
            ) {
                items(listaResultadoRetornados) { resultado ->
                    OutlinedTextField(
                        value = resultado,
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                        onValueChange = { },
                        label = { Text("") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                    )
                }
            }

            Spacer(modifier = Modifier.height(500.dp))
        }
    }
}

@Composable
fun DetalheScreen(navController: NavController, itemId: String, itemMes: String) {

    var fechaRemember by remember { mutableStateOf("") }
    var horaEntradaRemember by remember { mutableStateOf("") }
    var minutoEntradaRemember by remember { mutableStateOf("") }
    var horaSalidaRemember by remember { mutableStateOf("") }
    var minutoSalidaRemember by remember { mutableStateOf("") }
    var propinasRemember by remember { mutableStateOf("") }
    var notasRemember by remember { mutableStateOf("") }
    val firestore = FirebaseFirestore.getInstance()
    var modelTimeJob by remember { mutableStateOf<ModelTimeJob?>(null) }
    var resultado by remember { mutableStateOf("") }

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
            propinas = document.getString("propinas") ?: "",
            notas = document.getString("notas") ?: ""
        )

        Log.d("UpdateItemDetailScreen", "Item carregado: $modelTimeJob")
    }

    modelTimeJob?.let { modelTimeJobItem ->

        fechaRemember = modelTimeJobItem.fecha
        horaEntradaRemember = modelTimeJobItem.horaEntrada
        minutoEntradaRemember = modelTimeJobItem.minutoEntrada
        horaSalidaRemember = modelTimeJobItem.horaSalida
        minutoSalidaRemember = modelTimeJobItem.minutoSalida
        propinasRemember = modelTimeJobItem.propinas
        notasRemember = modelTimeJobItem.notas

        resultado =
            "Fecha: $fechaRemember \n\nHora Entrada: $horaEntradaRemember:$minutoEntradaRemember \n\nHora Salida: $horaSalidaRemember:$minutoSalidaRemember \n\nPropina: €$propinasRemember \n\n\n             Notas: \n\n$notasRemember"

        MaterialThemeScreen {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Detalhes",
                    style = MaterialTheme.typography.headlineMedium,
                )

                Spacer(modifier = Modifier.height(16.dp))

                //Resultado
                OutlinedTextField(
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                    modifier = Modifier
                        .width(200.dp)
                        .clickable {},
                    value = resultado,
                    onValueChange = { resultado = it },
                    label = { },
                    trailingIcon = {},
                    readOnly = true,
                )

                Spacer(modifier = Modifier.height(400.dp))
            }
        }
    }
}

@Composable
fun RelatorioScreen(navController: NavHostController, itemMes: String) {

    val context = LocalContext.current
    val listaResultadoRetornados = mutableListOf<String>()
    var variavelGlobalSomaHora = Duration.ZERO
    var dataInicioRemember by remember { mutableStateOf("") }
    var dataFimRemember by remember { mutableStateOf("") }
    var somaHorasRemember by remember { mutableStateOf("") }
    var totalDiasTrabalhadosRemember by remember { mutableIntStateOf(0) }

    MaterialThemeScreen {

        Column(
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            //fecha inicial e final
            Row(
                modifier = Modifier, horizontalArrangement = Arrangement.Center
            ) {

                //fecha inicial
                OutlinedTextField(
                    modifier = Modifier.width(200.dp),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                    value = dataInicioRemember,
                    onValueChange = { dataInicioRemember = it },
                    label = { Text("Fecha Inicial") },
                    trailingIcon = {
                        Icon(

                            imageVector = Icons.Default.DateRange,//icone
                            contentDescription = null,
                            modifier = Modifier
                                .width(50.dp)

                                .clickable {

                                    val year: Int
                                    val month: Int
                                    val day: Int
                                    val calendar = Calendar.getInstance()
                                    year = calendar.get(Calendar.YEAR)
                                    month = calendar.get(Calendar.MONTH)
                                    day = calendar.get(Calendar.DAY_OF_MONTH)
                                    calendar.time = Date()
                                    val datePickerDialog = android.app.DatePickerDialog(
                                        context, { _: DatePicker, year: Int, month: Int, day: Int ->
                                            dataInicioRemember = "$day/${month + 1}/$year"

                                            val fechaFormatada = String.format(
                                                "%02d/%02d/%02d", day, month + 1, year
                                            )

                                            dataInicioRemember = fechaFormatada

                                        }, year, month, day
                                    )

                                    datePickerDialog.show()

                                },//clickable

                            tint = Color.Blue,// cor azul da borda
                        )
                    },
                    readOnly = true,

                    )

                //fecha final
                OutlinedTextField(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(start = 8.dp),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                    value = dataFimRemember,
                    onValueChange = { dataFimRemember = it },
                    label = { Text("Fecha Final") },
                    trailingIcon = {
                        Icon(

                            imageVector = Icons.Default.DateRange,//icone
                            contentDescription = null,
                            modifier = Modifier
                                .width(50.dp)
                                .clickable {

                                    val year: Int
                                    val month: Int
                                    val day: Int
                                    val calendar = Calendar.getInstance()
                                    year = calendar.get(Calendar.YEAR)
                                    month = calendar.get(Calendar.MONTH)
                                    day = calendar.get(Calendar.DAY_OF_MONTH)
                                    calendar.time = Date()
                                    val datePickerDialog = android.app.DatePickerDialog(
                                        context, { _: DatePicker, year: Int, month: Int, day: Int ->
                                            dataFimRemember = "$day/$month/$year"

                                            val fechaFormatada = String.format(
                                                "%02d/%02d/%02d", day, month + 1, year
                                            )

                                            dataFimRemember = fechaFormatada

                                        }, year, month, day
                                    )

                                    datePickerDialog.show()

                                },//clickable
                            tint = Color.Blue,// cor azul da borda
                        )
                    },
                    readOnly = true,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(16.dp), colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue, contentColor = Color.White
            ),

                onClick = {
                    // Limpar as variáveis antes de começar a nova pesquisa
                    listaResultadoRetornados.clear()
                    variavelGlobalSomaHora = Duration.ZERO
                    totalDiasTrabalhadosRemember = 0
                    somaHorasRemember = ""

                    val listaDeDadosRetornadas = FirebaseFirestore.getInstance().collection(itemMes)
                        .whereGreaterThanOrEqualTo("fecha", dataInicioRemember)
                        .whereLessThanOrEqualTo("fecha", dataFimRemember)

//                    .whereGreaterThanOrEqualTo("fecha", "04/01/2025")
//                    .whereLessThanOrEqualTo("fecha", "05/01/2025")

                    listaDeDadosRetornadas.addSnapshotListener { dadosRetornados, _ ->

                        val listaRetornada = dadosRetornados?.documents//todo document

                        listaRetornada?.forEach { documents ->

                            val dados = documents?.data

                            if (dados != null) {

                                val fechaDadosRetornados = dados["fecha"]
                                val horaEntradaRetornados = dados["horaEntrada"]
                                val minutoEntradaRetornados = dados["minutoEntrada"]
                                val horaSalidaRetornados = dados["horaSalida"]
                                val minutoSalidaRetornados = dados["minutoSalida"]

                                //calculo tempo
                                val horaEntradaTime = LocalTime.of(
                                    horaEntradaRetornados.toString().toInt(),
                                    minutoEntradaRetornados.toString().toInt()
                                )
                                val horaSalidaTime = LocalTime.of(
                                    horaSalidaRetornados.toString().toInt(),
                                    minutoSalidaRetornados.toString().toInt()
                                )
                                val duracao = Duration.between(horaEntradaTime, horaSalidaTime)
                                val horas = duracao.toHours()
                                val minutos = duracao.toMinutes() % 60
                                val resultadoCalculorHoraFormatado =
                                    String.format("%02d:%02d", horas, minutos)

                                horas.toDuration(DurationUnit.HOURS)
                                minutos.toDuration(DurationUnit.MINUTES)

                                variavelGlobalSomaHora += horas.toDuration(DurationUnit.HOURS)
                                    .toJavaDuration() + minutos.toDuration(DurationUnit.MINUTES)
                                    .toJavaDuration()

                                listaResultadoRetornados.add("Fecha: $fechaDadosRetornados \nTotal de Horas: $resultadoCalculorHoraFormatado")

                                // Atualiza somaHorasRemember
                                somaHorasRemember =
                                    "${variavelGlobalSomaHora.toHours()}h ${variavelGlobalSomaHora.toMinutes() % 60}m"
                                totalDiasTrabalhadosRemember = listaResultadoRetornados.size

                                val contadorDiasTrabalhados = listaResultadoRetornados.count()

                                totalDiasTrabalhadosRemember = contadorDiasTrabalhados

                                //limpar campos
                                dataInicioRemember = ""
                                dataFimRemember = ""

                                Log.d("Contador", "Contador: $contadorDiasTrabalhados")
                            }
                        }
                    }
                }

            ) {
                Text("Pesquisar")
            }

            //resultado
            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {

                //total dias trabalhados
                OutlinedTextField(
                    modifier = Modifier.width(200.dp),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),

                    value = if (totalDiasTrabalhadosRemember == 0) {
                        "" // Exibe uma string vazia se ambos os valores estiverem vazios
                    } else {
                        "$totalDiasTrabalhadosRemember"
                    },

                    onValueChange = { },
                    label = { Text("Dias Trabalhados") },
                    leadingIcon = {
                        Icon(

                            imageVector = Icons.Default.DateRange,//icone
                            contentDescription = null,
                            modifier = Modifier.width(50.dp),
                            tint = Color.Blue,// cor azul da borda
                        )
                    },
                    readOnly = true,

                    )

                Spacer(modifier = Modifier.height(16.dp))

                //total horas
                OutlinedTextField(
                    modifier = Modifier.width(200.dp),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                    value = somaHorasRemember,
                    onValueChange = { },
                    label = { Text("Total Horas") },
                    leadingIcon = {
                        Icon(

                            painter = painterResource(id = R.drawable.time),
                            contentDescription = null,
                            modifier = Modifier.width(50.dp),

                            tint = Color.Blue,// cor azul da borda
                        )
                    },
                    readOnly = true,
                )
            }

            // Exibir resultados em uma LazyColumn
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(listaResultadoRetornados) { resultado ->
                    OutlinedTextField(
                        value = resultado,
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                        onValueChange = { },
                        label = { Text("") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                    )
                }
            }

            Spacer(modifier = Modifier.height(300.dp))
        }
    }
}