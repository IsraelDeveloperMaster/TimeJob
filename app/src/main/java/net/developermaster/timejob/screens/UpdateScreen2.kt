package net.developermaster.timejob.screens

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import net.developermaster.timejob.R
import net.developermaster.timejob.model.ModelScreens
import net.developermaster.timejob.model.ModelTimeJob
import java.util.Date

@Composable
fun UpdateScreen2(navController: NavController, itemId: String) {

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
        val document = firestore.collection("Enero").document(itemId).get().await()

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

                                Relogio(context) { hora, minuto ->

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

                                Relogio(context) { hora, minuto ->

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
                    firestore.collection("Enero").document(modelTimeJobItem.id).update(
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

                    navController.navigate(ModelScreens.MesesScreenObject.route)

                },
            ) {
                Text("Atualizar")
            }
        }
    }
}