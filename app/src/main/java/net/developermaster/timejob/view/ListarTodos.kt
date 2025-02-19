package net.developermaster.timejob.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.navigation.NavController
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import net.developermaster.timejob.R
import net.developermaster.timejob.model.ModelTimeJob
import net.developermaster.timejob.model.ModelScreens
import java.util.Date

@Composable
fun TopBarListarTodosScreen(paddingValues: PaddingValues, navcontroller: NavController) {
}

@Composable
internal fun ListarTodos(navController: NavController) {

    MaterialTheme {

        if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()

        Scaffold(Modifier.fillMaxSize(),

            bottomBar = {

                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    actions = {

                        //Relatorio Propinas
                        IconButton(onClick = {

                            navController.navigate(ModelScreens.PropinaScreenObject.route)

                        }) {
                            Icon(Icons.Filled.Favorite, contentDescription = "Search")
                        }

                    },

                    floatingActionButton = {

                        //Add
                        FloatingActionButton(
                            modifier = Modifier.padding(start = 155.dp),
                            onClick = {

                                navController.navigate(ModelScreens.AddScreenObject.route)

                            }) {
                            Icon(Icons.Filled.AddCircle, contentDescription = "Add")
                        }

                        //Relatorio
                        FloatingActionButton(onClick = {

                            navController.navigate(ModelScreens.RelatorioScreenObject.route)

                        }) {
                            Icon(Icons.Filled.Search, contentDescription = "Search")
                        }
                    },
                )
            },

            topBar = {

                TopBarListarTodosScreen(
                    paddingValues = PaddingValues(), navcontroller = navController
                )

            }) { paddingValues ->

            TopBarListarTodosScreen(paddingValues, navController)

            val firestore = FirebaseFirestore.getInstance()
            var modelTimeJobs by remember { mutableStateOf<List<ModelTimeJob>>(emptyList()) }

            // Carregar os itens do Firestore
            LaunchedEffect(Unit) {
                val querySnapshot =
                    firestore.collection("TimeJob").get().await().query.orderBy("fecha").get()
                        .await()

                modelTimeJobs = querySnapshot.documents.map { document ->

                    ModelTimeJob(
                        id = document.id,
                        fecha = document.getString("fecha") ?: "",
                        horaEntrada = document.getString("horaEntrada") ?: "",
                        minutoEntrada = document.getString("minutoEntrada") ?: "",
                        horaSalida = document.getString("horaSalida") ?: "",
                        minutoSalida = document.getString("minutoSalida") ?: "",
                        propinas = document.getString("propinas") ?: "",
                        notas = document.getString("nota") ?: ""
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp, top = 80.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(modelTimeJobs) { item ->

                    ItemBox(modelTimeJob = item, navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun ItemBox(navController: NavController, modelTimeJob: ModelTimeJob) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(16.dp)
    ) {

        Column {

            OutlinedTextField(

                value = "Fecha: " + modelTimeJob.fecha + "\n" + "Hora de Entrada: " + modelTimeJob.horaEntrada + ":" + modelTimeJob.minutoEntrada + "\n" + "Hora de Saida: " + modelTimeJob.horaSalida + ":" + modelTimeJob.minutoSalida,
                textStyle = TextStyle(color = Color.Black),
                onValueChange = { },
                label = { },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {

                    //icon editar
                    Icon(
                        imageVector = Icons.Default.Create,//icone
                        contentDescription = null,
                        modifier = Modifier
                            .padding(bottom = 40.dp)
                            .width(50.dp)
                            .clickable {

                                navController.navigate("updateItem/${modelTimeJob.id}")

                            },//clickable

                        tint = Color.Blue,// cor azul da borda
                    )

                    //icon Deletar
                    Icon(
                        imageVector = Icons.Default.Delete,//icone
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .width(50.dp)
                            .clickable {

                                navController.navigate("deleteItem/${modelTimeJob.id}")

                            },//clickable

                        tint = Color.Blue,// cor azul da borda
                    )
                },
                readOnly = true,

                )
        }
    }
}

@Composable
fun UpdateScreen(navController: NavController, itemId: String) {

    var fechaRemember by remember { mutableStateOf("") }
    var horaEntradaRemember by remember { mutableStateOf("") }
    var minutoEntradaRemember by remember { mutableStateOf("") }
    var horaSalidaRemember by remember { mutableStateOf("") }
    var minutoSalidaRemember by remember { mutableStateOf("") }
    var propinasRemember by remember { mutableStateOf("") }

    val firestore = FirebaseFirestore.getInstance()
    var modelTimeJob by remember { mutableStateOf<ModelTimeJob?>(null) }

    // Carregar o item específico do Firestore
    LaunchedEffect(itemId) {
        val document = firestore.collection("TimeJob").document(itemId).get().await()

        modelTimeJob = ModelTimeJob(
            id = document.id,
            fecha = document.getString("fecha") ?: "",
            horaEntrada = document.getString("horaEntrada") ?: "",
            minutoEntrada = document.getString("minutoEntrada") ?: "",
            horaSalida = document.getString("horaSalida") ?: "",
            minutoSalida = document.getString("minutoSalida") ?: "",
            propinas = document.getString("propinas") ?: "",
            notas = document.getString("nota") ?: ""
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


                                val datePickerDialog = DatePickerDialog(
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
                    // Atualizar o item no Firestore
                    firestore.collection("TimeJob").document(modelTimeJobItem.id).update(
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
                        Log.e("UpdateItemDetailScreen", "Erro ao atualizar item", e)
                    }

                    Toast.makeText(context, "Atualizado com sucesso", Toast.LENGTH_SHORT).show()

                    navController.navigate(ModelScreens.ListarTodosScreenObject.route)

                },
            ) {
                Text("Atualizar")
            }
        }
    }
}

@Composable
fun DeleteItemDialog(navController: NavController, itemId: String) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false },
            title = { Text(text = "Deletar Item") },
            text = { Text("Tem certeza que deseja deletar este item?") },
            confirmButton = {
                Button(onClick = {
                    FirebaseFirestore.getInstance().collection("TimeJob").document(itemId).delete()
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

fun relogioListarTodos(context: Context, onTimeSet: (Int, Int) -> Unit) {
    val calendar = Calendar.getInstance()
    val mHour = calendar[Calendar.HOUR_OF_DAY]
    val mMinute = calendar[Calendar.MINUTE]
    calendar.time = Date()

    val timePickerDialog = TimePickerDialog(
        context, { _, hourOfDay, minute ->
            onTimeSet(hourOfDay, minute)
        }, mHour, mMinute, true
    )

    timePickerDialog.show()
}