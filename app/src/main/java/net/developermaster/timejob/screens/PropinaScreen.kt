package net.developermaster.timejob.screens

import android.app.DatePickerDialog
import android.icu.util.Calendar
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import java.time.Duration
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarPropinaScreen(navcontroller: NavController) {

    TopAppBar(modifier = Modifier.padding(10.dp), title = {

        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
fun PropinaScreen(navcontroller: NavController) {

    Scaffold(Modifier
        .fillMaxSize()

        .background(color = Color.Blue), topBar = {

        TopBarPropinaScreen(navcontroller)
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

        BodyPropinaScreen(paddingValues, navcontroller)
    }
}

@Composable
fun BodyPropinaScreen(paddingValues: PaddingValues, navcontroller: NavController) {

    RelatorioPropinas()

}

@Composable
fun RelatorioPropinas() {

    Duration.ZERO

    val context = LocalContext.current
    val listaResultadoRetornados = mutableListOf<String>()
    var dataInicioRemember by remember { mutableStateOf("") }
    var dataFimRemember by remember { mutableStateOf("") }
    var totalPropinaRemember by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.padding(top = 120.dp), verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier, horizontalArrangement = Arrangement.Center
        ) {

            //hora inicial
            OutlinedTextField(
                modifier = Modifier.width(200.dp),
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

                                        val fechaFormatada =
                                            String.format("%02d/%02d/%02d", day, month + 1, year)

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
                                        dataFimRemember = "$day/$month/$year"

                                        val fechaFormatada =
                                            String.format("%02d/%02d/%02d", day, month + 1, year)

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

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), onClick = {

            val listaDeDadosRetornadas = FirebaseFirestore.getInstance().collection("Enero")
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
                        val propinasDadosRetornados = dados["propinas"]

                        listaResultadoRetornados += ("Fecha: $fechaDadosRetornados \nHora de Entrada: $horaEntradaRetornados : $minutoEntradaRetornados \nHora de Salida: $horaSalidaRetornados : $minutoSalidaRetornados \nTotal de Horas: $ \nPropinas: €$propinasDadosRetornados")

                        totalPropinaRemember += propinasDadosRetornados.toString().toInt()

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

        Column(
            modifier = Modifier.padding(top = 1.dp),
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
        Column(
            modifier = Modifier, verticalArrangement = Arrangement.Bottom
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            //total propinas
            OutlinedTextField(
                modifier = Modifier.width(200.dp),
                value = "€$totalPropinaRemember",
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
    }
}