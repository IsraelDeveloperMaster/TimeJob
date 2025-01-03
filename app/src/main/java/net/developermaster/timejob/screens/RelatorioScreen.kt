package net.developermaster.timejob.screens

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import java.time.Duration
import java.time.LocalTime
import java.util.Date
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlin.time.toJavaDuration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarRelatorioScreen(navcontroller: NavController) {

    TopAppBar(modifier = Modifier.padding(10.dp), title = {

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "back",
            modifier = Modifier.clickable {
                navcontroller.popBackStack()
            })

        Text(
            modifier = Modifier.padding(start = 120.dp), text = "Relatorio"
        )
    }, actions = {

/*        Icon(
            imageVector = Icons.Default.Menu, contentDescription = "Menu"
        )*/
    })
}

@Composable
fun RelatorioScreen(navcontroller: NavController) {

    Scaffold(
        Modifier
            .fillMaxSize()

            .background(color = Color.Blue), topBar = {

            TopBarRelatorioScreen(navcontroller)
        },

        bottomBar = {
/*            FloatingActionButton(
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                shape = Shapes().large,
                modifier = Modifier.padding(start = 300.dp, bottom = 50.dp),
                containerColor = Color.Blue,
                contentColor = Color.White,
                onClick = {
                    Toast.makeText(
                        navcontroller.context, "FloatingActionButton", Toast.LENGTH_SHORT
                    ).show()
                }) { }*/

        }

    ) { paddingValues ->

        BodyRelatorioScreen(paddingValues, navcontroller)
    }
}

@Composable
fun BodyRelatorioScreen(paddingValues: PaddingValues, navcontroller: NavController) {

    Relatorio()

}


@Composable
fun Relatorio() {

    val context = LocalContext.current

    val listaResultadoRetornados = mutableListOf<String>()
    var variavelGlobalSomaHora = Duration.ZERO

    var dataInicioRemember by remember { mutableStateOf("") }
    var dataFimRemember by remember { mutableStateOf("") }
    var somaHorasRemember by remember { mutableStateOf("") }
    var totalPropinaRemember by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .padding(top = 120.dp),
        verticalArrangement = Arrangement.Center
    ) {

        //row hora
        Row(
            modifier = Modifier, horizontalArrangement = Arrangement.Center
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .width(200.dp)
                    .clickable {

                        val year: Int
                        val month: Int
                        val day: Int

                        val calendar = Calendar.getInstance()
                        year = calendar.get(Calendar.YEAR)
                        month = calendar.get(Calendar.MONTH)
                        day = calendar.get(Calendar.DAY_OF_MONTH)
                        calendar.time = Date()


                        val datePickerDialog = DatePickerDialog(
                            context, { _: DatePicker, year: Int, month: Int, day: Int ->
                                dataInicioRemember = "$day/${month + 1}/$year"
                            }, year, month, day
                        )

                        datePickerDialog.show()

                    },
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


                                val datePickerDialog = DatePickerDialog(
                                    context, { _: DatePicker, year: Int, month: Int, day: Int ->
                                        dataInicioRemember = "$day/${month + 1}/$year"
                                    }, year, month, day
                                )

                                datePickerDialog.show()
                            },//clickable

                        tint = Color.Blue,// cor azul da borda
                    )
                },
                readOnly = true,

                )

            OutlinedTextField(
                modifier = Modifier
                    .width(200.dp)
                    .clickable {

                        val year: Int
                        val month: Int
                        val day: Int

                        val calendar = Calendar.getInstance()
                        year = calendar.get(Calendar.YEAR)
                        month = calendar.get(Calendar.MONTH)
                        day = calendar.get(Calendar.DAY_OF_MONTH)
                        calendar.time = Date()


                        val datePickerDialog = DatePickerDialog(
                            context, { _: DatePicker, year: Int, month: Int, day: Int ->
                                dataFimRemember = "$day/$month/$year"
                            }, year, month, day
                        )

                        datePickerDialog.show()

                    }
                    .padding(start = 8.dp),
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


                                val datePickerDialog = DatePickerDialog(
                                    context, { _: DatePicker, year: Int, month: Int, day: Int ->
                                        dataFimRemember = "$day/${month + 1}/$year"
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

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {

                val listaDeDadosRetornadas = FirebaseFirestore.getInstance().collection("TimeJob")
                    .whereGreaterThanOrEqualTo("fecha", dataInicioRemember )
                    .whereLessThanOrEqualTo("fecha", dataFimRemember )

                listaDeDadosRetornadas.addSnapshotListener { dadosRetornados, _ ->

                    val listaRetornada = dadosRetornados?.documents//todo document

                    listaRetornada?.forEach { documents ->

                        val dados = documents?.data

                        if (dados != null) {

//                            val idRetornado = documents.id
                            val fechaDadosRetornados = dados["fecha"]
                            val horaEntradaRetornados = dados["horaEntrada"]
                            val minutoEntradaRetornados = dados["minutoEntrada"]
                            val horaSalidaRetornados = dados["horaSalida"]
                            val minutoSalidaRetornados = dados["minutoSalida"]
                            val propinasDadosRetornados = dados["propinas"]

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


                            listaResultadoRetornados += ("Fecha: $fechaDadosRetornados \nHora de Entrada: $horaEntradaRetornados : $minutoEntradaRetornados \nHora de Salida: $horaSalidaRetornados : $minutoSalidaRetornados \nTotal de Horas: $resultadoCalculorHoraFormatado \nPropinas: €$propinasDadosRetornados")

                            somaHorasRemember = variavelGlobalSomaHora.toString()

//                            totalPropinaRemember += propinasDadosRetornados.toString().toInt()
                        }

//                        Log.v("firebaseSoma", "firebaseSoma : $totalPropinaRemember")

                    }
                }
            },

            ) {
            Text("Pesquisar")
        }

        Column(
            modifier = Modifier
                .padding(top = 1.dp),
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            listaResultadoRetornados.forEach { lista ->

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

        //row resultado
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center//.background(Color.LightGray),
        ) {

            OutlinedTextField(
                modifier = Modifier.width(200.dp),
                value = "€${totalPropinaRemember}",
//                value = "€ 50",
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

            OutlinedTextField(
                modifier = Modifier
                    .width(200.dp)
                    .padding(start = 8.dp),
                value = somaHorasRemember,
                onValueChange = { },
                label = { Text("Total Horas") },
                leadingIcon = {
                    Icon(

                        imageVector = Icons.Default.Notifications,//icone
                        contentDescription = null,
                        modifier = Modifier.width(50.dp),


                        tint = Color.Blue,// cor azul da borda
                    )
                },
                readOnly = true,

                )
        }
    }
}