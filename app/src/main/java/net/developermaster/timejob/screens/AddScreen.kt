package net.developermaster.timejob.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import net.developermaster.timejob.R
import net.developermaster.timejob.model.ModelTimeJob
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAddScreen(navcontroller: NavController) {

    TopAppBar(modifier = Modifier.padding(10.dp), title = {

        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "back",
            modifier = Modifier.clickable {

                navcontroller.popBackStack()
            })

        Text(
            modifier = Modifier.padding(start = 120.dp), text = "Salvar"
        )
    }, actions = {

/*        Icon(
            imageVector = Icons.Default.Menu, contentDescription = "Menu"
        )*/
    })
}

@Composable
fun AddScreen(navcontroller: NavController) {

    Scaffold(Modifier
        .fillMaxSize()

        .background(color = Color.Blue), topBar = {

        TopBarAddScreen(navcontroller)
    },

        bottomBar = {}

    ) { paddingValues ->

        BodyAddScreen(paddingValues, navcontroller)
    }
}

@Composable
fun BodyAddScreen(paddingValues: PaddingValues, navcontroller: NavController) {

    AddItem(navcontroller)
}

@Composable
fun AddItem(navcontroller: NavController) {

    //context local
    val context = LocalContext.current
    var fechaRemember by remember { mutableStateOf("") }
    var horaEntradaRemember by remember { mutableStateOf("") }
    var minutoEntradaRemember by remember { mutableStateOf("") }
    var horaSalidaRemember by remember { mutableStateOf("") }
    var minutoSalidaRemember by remember { mutableStateOf("") }
    var propinasRemember by remember { mutableStateOf("") }

    val modelTimeJob = ModelTimeJob(
        fecha = fechaRemember,
        horaEntrada = horaEntradaRemember,
        minutoEntrada = minutoEntradaRemember,
        horaSalida = horaSalidaRemember,
        minutoSalida = minutoSalidaRemember,
        propinas = propinasRemember,
    )

    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(150.dp))

        Text(text = "Fecha de Hoy")

        //fecha
        OutlinedTextField(
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

                                }, ano, mes, dia
                            )

                            datePickerDialog.show()

                        },//clickable

                    tint = Color.Blue,// cor azul da borda
                )
            },
            readOnly = true,

            )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Hora de Entrada")

        //hora entrada
        OutlinedTextField(
            modifier = Modifier
                .width(200.dp)
                .clickable {},
            value = "$horaEntradaRemember : $minutoEntradaRemember",
            onValueChange = { horaEntradaRemember = it },
            label = { Text("Hora") },
            trailingIcon = {
                Icon(

                    painter = painterResource(id = R.drawable.time),
                    contentDescription = null,
                    modifier = Modifier
                        .width(50.dp)

                        .clickable {

                            Relogio(context) { hour, minute ->
                                horaEntradaRemember = "$hour"
                                minutoEntradaRemember = "$minute"

                                val horaFormatado = String.format("%02d", hour)
                                horaEntradaRemember = horaFormatado

                                val minutoFormatado = String.format("%02d", minute)
                                minutoEntradaRemember = minutoFormatado
                            }

                        },//clickable

                    tint = Color.Blue,// cor azul da borda
                )
            },
            readOnly = true,

            )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Hora de Salida")

        //hora saida
        OutlinedTextField(
            modifier = Modifier.width(200.dp),
            value = "$horaSalidaRemember:$minutoSalidaRemember",
            onValueChange = { horaSalidaRemember = it },
            label = { Text("Hora") },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.time),
                    contentDescription = null,
                    modifier = Modifier
                        .width(50.dp)

                        .clickable {

                            Relogio(context) { hour, minute ->
                                horaSalidaRemember = "$hour"
                                minutoSalidaRemember = "$minute"

                                val horaFormatado = String.format("%02d", hour)
                                horaSalidaRemember = horaFormatado

                                val minutoFormatado = String.format("%02d", minute)
                                minutoSalidaRemember = minutoFormatado
                            }

                            Log.d(
                                "minutoEntradaRemember",
                                "minutoEntradaRemember : $minutoEntradaRemember"
                            )

                        },//clickable

                    tint = Color.Blue,// cor azul da borda
                )
            },
            readOnly = true,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Propinas de Hoy")

        //propinas
        OutlinedTextField(
            modifier = Modifier.width(200.dp),
            value = "$propinasRemember",
            onValueChange = { propinasRemember = it },
            label = { Text("Propinas") },
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                // Verifica se todos os campos estão preenchidos
                if (propinasRemember.isEmpty() || fechaRemember.isEmpty() || horaEntradaRemember.isEmpty() || minutoEntradaRemember.isEmpty() || horaSalidaRemember.isEmpty() || minutoSalidaRemember.isEmpty()) {
                    Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                } else {
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

                        // Salva no Firestore na coleção correspondente ao mês
                        FirebaseFirestore.getInstance().collection(nomeMes).document()
                            .set(modelTimeJob).addOnSuccessListener {
                                Log.d("firebase", "Salvo com sucesso na coleção $nomeMes")
                            }.addOnFailureListener { erro ->
                                Log.d("firebase", "Erro : ${erro.message}")
                            }

                        Toast.makeText(context, "Salvo com sucesso", Toast.LENGTH_SHORT).show()
                        navcontroller.popBackStack()
                    } else {
                        Toast.makeText(context, "Data inválida", Toast.LENGTH_SHORT).show()
                    }
                }
            },
        ) {
            Text("Salvar")
        }
    }
}

fun Relogio(context: Context, onTimeSet: (Int, Int) -> Unit) {
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

