package net.developermaster.timejob.core

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import net.developermaster.timejob.model.ModelTimeJob
import java.time.Duration
import java.time.LocalTime

class ComponentsFireBase {

    val listaResultadoRetornados = mutableListOf<String>()

    @Composable
    fun Salvar() {

        //context local
        val context = LocalContext.current

        var fechaRemember by remember { mutableStateOf("") }

        var horaEntradaRemember by remember { mutableStateOf("") }
        var minutoEntradaRemember by remember { mutableStateOf("0") }

        var horaSalidaRemember by remember { mutableStateOf("") }
        var minutoSalidaRemember by remember { mutableStateOf("0") }

        var propinasRemember by remember { mutableStateOf("") }

        val modelTimeJob = ModelTimeJob(
            fecha = fechaRemember,
            horaEntrada = horaEntradaRemember,
            minutoEntrada = minutoEntradaRemember,
            horaSalida = horaSalidaRemember,
            minutoSalida = minutoSalidaRemember,
            propinas = propinasRemember,
        )

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center

        ) {

            //fecha
            OutlinedTextField(
                modifier = Modifier.width(290.dp).padding(start = 89.dp),
                value = fechaRemember,
                onValueChange = { fechaRemember = it },
                label = { Text("Fecha") },
            )

            Spacer(modifier = Modifier.height(16.dp))

            //row hora entrada
            Row(

                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center

            ) {

                OutlinedTextField(
                    modifier = Modifier.width(100.dp),
                    value = horaEntradaRemember,
                    onValueChange = { horaEntradaRemember = it },
                    label = { Text("Hora") },

                    )
                OutlinedTextField(
                    modifier = Modifier
                        .width(100.dp)
                        .padding(start = 8.dp),
                    value = minutoEntradaRemember,
                    onValueChange = { minutoEntradaRemember = it },
                    label = { Text("Minuto") },
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            //row hora saida
            Row(

                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center

            ) {

                OutlinedTextField(
                    modifier = Modifier.width(100.dp),
                    value = horaSalidaRemember,
                    onValueChange = { horaSalidaRemember = it },
                    label = { Text("Hora") },


                    )
                OutlinedTextField(
                    modifier = Modifier
                        .width(100.dp)
                        .padding(start = 8.dp),
                    value = minutoSalidaRemember,
                    onValueChange = { minutoSalidaRemember = it },
                    label = { Text("Minuto") },
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.width(290.dp).padding(start = 89.dp),

                value = propinasRemember,
                onValueChange = { propinasRemember = it },
                label = { Text("Propinas") },
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(

                onClick = {

                    FirebaseFirestore.getInstance().collection("TimeJob").document()
                        .set(modelTimeJob).addOnSuccessListener { sucesso ->

                            Log.d("firebase", "Salvo com sucesso")

                        }.addOnFailureListener { erro ->

                            Log.d("firebase", "Erro : ${erro.message}")
                        }

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
                readOnly = true
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
                        val minutoEntradaRetornados = dados["minutoEntrada"]
                        val horaSalidaRetornados = dados["horaSalida"]
                        val minutoSalidaRetornados = dados["minutoSalida"]
                        val propinasDadosRetornados = dados["propinas"]

                        //calculo tempo
                        val horaEntradaTime = LocalTime.of(horaEntradaRetornados.toString().toInt(), minutoEntradaRetornados.toString().toInt())
                        val horaSalidaTime = LocalTime.of(horaSalidaRetornados.toString().toInt(),minutoSalidaRetornados.toString().toInt())
                        val duracao = Duration.between(horaEntradaTime, horaSalidaTime)
                        val horas = duracao.toHours()
                        val minutos = duracao.toMinutes() % 60
                        val resultadoCalculorHoraFormatado = String.format("%02d:%02d", horas, minutos)

                        //resultado tempo
                        Log.i("tempo", "Calculo = $resultadoCalculorHoraFormatado")

                        listaResultadoRetornados += ("Fecha: $fechaDadosRetornados \nHora de Entrada: $horaEntradaRetornados \nHora de Salida: $horaSalidaRetornados \nTotal de Horas: $resultadoCalculorHoraFormatado \nPropinas: $propinasDadosRetornados")

                        Log.d(
                            "firebase",
                            " id: $idRetornado \n Fecha: $fechaDadosRetornados \n Horas Trabajadas: $horaSalidaRetornados \n Propinas: $propinasDadosRetornados \n \n "
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
                    textStyle = TextStyle(color = Color.Red),
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
                                    Toast.makeText(
                                        context, "Clicou no icone", Toast.LENGTH_SHORT
                                    ).show()
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
    fun ListarRelatorio() {

        var propinasRemember by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center

        ) {

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                enabled = false,
                value = propinasRemember,
                onValueChange = { propinasRemember = it },
                label = { Text(" ") },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            val listaDeDadosRetornadas = FirebaseFirestore.getInstance().collection("TimeJob")
                .whereGreaterThanOrEqualTo("fecha" , "1")
                .whereLessThanOrEqualTo("fecha" , "4")

            listaDeDadosRetornadas.addSnapshotListener { dadosRetornados, error ->

                val listaRetornada = dadosRetornados?.documents//todo document

                listaRetornada?.forEach { documents ->

                    val dados = documents?.data

                    if (dados != null) {

                        val idRetornado = documents.id
                        val fechaDadosRetornados = dados["fecha"]
                        val horaEntradaRetornados = dados["horaEntrada"]
                        val minutoEntradaRetornados = dados["minutoEntrada"]
                        val horaSalidaRetornados = dados["horaSalida"]
                        val minutoSalidaRetornados = dados["minutoSalida"]
                        val propinasDadosRetornados = dados["propinas"]

                        //calculo tempo
                        val horaEntradaTime = LocalTime.of(horaEntradaRetornados.toString().toInt(), minutoEntradaRetornados.toString().toInt())
                        val horaSalidaTime = LocalTime.of(horaSalidaRetornados.toString().toInt(),minutoSalidaRetornados.toString().toInt())
                        val duracao = Duration.between(horaEntradaTime, horaSalidaTime)
                        val horas = duracao.toHours()
                        val minutos = duracao.toMinutes() % 60
                        val resultadoCalculorHoraFormatado = String.format("%02d:%02d", horas, minutos)

                        //resultado tempo
                        Log.i("tempo", "Calculo = $resultadoCalculorHoraFormatado")

                        listaResultadoRetornados += ("Fecha: $fechaDadosRetornados \nHora de Entrada: $horaEntradaRetornados \nHora de Salida: $horaSalidaRetornados \nTotal de Horas: $resultadoCalculorHoraFormatado \nPropinas: $propinasDadosRetornados")

                        Log.d(
                            "firebase",
                            " id: $idRetornado \n Fecha: $fechaDadosRetornados \n Horas Trabajadas: $horaSalidaRetornados \n Propinas: $propinasDadosRetornados \n \n "
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
                    textStyle = TextStyle(color = Color.Red),
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
                                    Toast.makeText(
                                        context, "Clicou no icone", Toast.LENGTH_SHORT
                                    ).show()
                                },//clickable

                            tint = Color.Blue,// cor azul da borda
                        )
                    },
                    readOnly = true,
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

