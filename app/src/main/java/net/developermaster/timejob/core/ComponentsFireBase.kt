package net.developermaster.timejob.core

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import net.developermaster.timejob.R
import net.developermaster.timejob.model.ModelTimeJob
import net.developermaster.timejob.view.ActivityAdicionar
import java.time.Duration
import java.time.LocalTime

class ComponentsFireBase {

    val listaResultadoRetornados = mutableListOf<String>()

    @Composable
    fun Salvar() {

        //context local
        val context = LocalContext.current

        var fecha by remember { mutableStateOf("") }
        var horaEntrada by remember { mutableStateOf("") }
        var horaSalida by remember { mutableStateOf("") }
        var totalHora by remember { mutableStateOf("") }
        var propinas by remember { mutableStateOf("") }

        val modelTimeJob = ModelTimeJob(
            fecha = fecha,
            horaEntrada = horaEntrada,
            horaSalida = horaSalida,
            totalHora = totalHora,
            propinas = propinas
        )

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center

        ) {

            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = horaEntrada,
                onValueChange = { horaEntrada = it },
                label = { Text("Hora de Entrada") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = horaSalida,
                onValueChange = { horaSalida = it },
                label = { Text("Hora de Salida") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = totalHora,
                onValueChange = { totalHora = it },
                label = { Text("Total de Hora hoy") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = propinas,
                onValueChange = { propinas = it },
                label = { Text("Propinas") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(

                onClick = {

                    FirebaseFirestore.getInstance().collection("TimeJob").document()
                        .set(modelTimeJob).addOnSuccessListener { sucesso ->

//                            mensagemToast("Salvo com sucesso")

                            Log.d("firebase", "Salvo com sucesso")

                        }.addOnFailureListener { erro ->

//                            mensagemToast("Salvo com sucesso ${erro.message}")//

                            Log.d("firebase", "Erro : ${erro.message}")
                        }


                    //Variaveis de tempo
                    val horaInicio = LocalTime.of(horaEntrada.toInt(), horaSalida.toInt())
                    val horaFim = LocalTime.of(16, 0)
                    val duracao = Duration.between(horaInicio, horaFim)
                    val horas = duracao.toHours()
                    val minutos = duracao.toMinutes() % 60
                    val resultadoFormatado = String.format("%02d:%02d", horas, minutos)

                    //resultado
                    Log.i("tempo", "Calculo = $resultadoFormatado")





                    NavHostController(context).navigate("MainActivity")

//                    Toast.makeText(context, "Salvo con Suceso", Toast.LENGTH_SHORT).show()


                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Salvar")
            }
        }
    }

    @Composable
    fun ListarTodos() {

        var propinasRemember by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center

        ) {

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                enabled = false,
                value = propinasRemember,
                onValueChange = { propinasRemember = it },
                label = { Text("                                Resultados Semanales") },
                modifier = Modifier.fillMaxWidth(),

                )

            Spacer(modifier = Modifier.height(16.dp))

            val listaDeDadosRetornadas = FirebaseFirestore.getInstance().collection("TimeJob")
                .orderBy("fecha", Query.Direction.DESCENDING)

            listaDeDadosRetornadas.addSnapshotListener { dadosRetornados, error ->

                val listaRetornada = dadosRetornados?.documents//todo document

                listaRetornada?.forEach { documents ->

                    val dados = documents?.data

                    if (dados != null) {

                        val idRetornado = documents.id
                        val fechaDadosRetornados = dados["fecha"]
                        val horaEntradaRetornados = dados["horaEntrada"]
                        val horaSalidaRetornados = dados["horaSalida"]
                        val totalHoraRetornados = dados["totalHora"]
                        val propinasDadosRetornados = dados["propinas"]

                        listaResultadoRetornados += (" Fecha: $fechaDadosRetornados \n Hora de Entrada: $horaEntradaRetornados \n Hora de Salida: $horaSalidaRetornados \n Total de Horas Hoy: $totalHoraRetornados \n Propinas: $propinasDadosRetornados")

                        Log.d("firebase"," id: $idRetornado \n Fecha: $fechaDadosRetornados \n Horas Trabajadas: $horaSalidaRetornados \n Propinas: $propinasDadosRetornados \n \n "
                        )

                        propinasRemember = " "

                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

//                        itemsRemember.add(ModelTimeJob(fecha = item.toString()))

            listaResultadoRetornados.forEach { lista ->

                val context = LocalContext.current

                OutlinedTextField(

                    value = lista,
                    onValueChange = { },
                    label = { Text("") },
                    modifier = Modifier.fillMaxWidth(),

                    trailingIcon = {
                        Icon(

                            imageVector = Icons.Default.Create,//icone
                            contentDescription = null,
                            modifier = Modifier
                                .width(50.dp)

                                .clickable {
                                    Toast
                                        .makeText(
                                            context, "Clicou no icone", Toast.LENGTH_SHORT
                                        )
                                        .show()
                                },//clickable

                            tint = Color.Blue,// cor azul da borda
                        )
                    },

                    )
            }
        }
    }
}

/*
//limpar campos
fecha = ""
horaEntrada = ""
horaSalida = ""
totalHora = ""
propinas = ""

  */

