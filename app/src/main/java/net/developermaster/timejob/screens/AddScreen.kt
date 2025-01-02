package net.developermaster.timejob.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import net.developermaster.timejob.model.ModelTimeJob
import java.time.Duration
import java.time.LocalTime
import java.util.Date
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlin.time.toJavaDuration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAddScreen(navcontroller: NavController) {

    TopAppBar(modifier = Modifier.padding(10.dp), title = {

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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

    Scaffold(
        Modifier
            .fillMaxSize()

            .background(color = Color.Blue), topBar = {

            TopBarAddScreen(navcontroller)
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

        BodyAddScreen(paddingValues, navcontroller)
    }
}

@Composable
fun BodyAddScreen(paddingValues: PaddingValues, navcontroller: NavController) {

    AddItem()
}


@Composable
fun AddItem() {

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

    Column(

        modifier = Modifier
            .padding(top = 60.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            modifier = Modifier.padding(start = 100.dp, top = 16.dp),//todo padding top
            color = Color.Black,//todo cor negro
            fontSize = 18.sp,//todo tamanho da fonte
            fontFamily = FontFamily.SansSerif,//todo tipo de fonte
            textAlign = TextAlign.Center,//todo alinhamento do texto
            text = ""
        )

        Spacer(modifier = Modifier.height(16.dp))

        //fecha
        OutlinedTextField(
            modifier = Modifier
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
                            fechaRemember = "$day/$month/$year"
                        }, year, month, day
                    )

                    datePickerDialog.show()

                }
                .width(300.dp)
                .padding(start = 80.dp),
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

                            /*                                var dataAtual = LocalDate.now(Clock.systemDefaultZone())
                                                            val dia = dataAtual.dayOfMonth
                                                            val mes = dataAtual.monthValue
                                                            val ano = dataAtual.year*/


                            val dataAtual = Calendar.getInstance()
                            dia = dataAtual.get(Calendar.DAY_OF_MONTH)
                            mes = dataAtual.get(Calendar.MONTH)
                            ano = dataAtual.get(Calendar.YEAR)
                            dataAtual.time = Date()


                            val datePickerDialog = DatePickerDialog(
                                context, { _: DatePicker, ano: Int, mes: Int, dia: Int ->
                                    fechaRemember = "$dia/${mes + 1}/$ano"
                                }, ano, mes, dia
                            )

                            datePickerDialog.show()

                        },//clickable

                    tint = Color.Blue,// cor azul da borda
                )
            },
            readOnly = true,

            )

        Spacer(modifier = Modifier.height(30.dp))


        //row hora entrada
        Text(
            modifier = Modifier.padding(start = 80.dp), text = "Hora de Entrada"
        )
        Spacer(modifier = Modifier.width(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .width(120.dp)
                    .clickable {},
                value = horaEntradaRemember,
                onValueChange = { horaEntradaRemember = it },
                label = { Text("Hora") },
                trailingIcon = {
                    Icon(

                        imageVector = Icons.Default.DateRange,//icone
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)

                            .clickable {

                                val calendar = Calendar.getInstance()
                                val mHour = calendar[Calendar.HOUR_OF_DAY]
                                val mMinute = calendar[Calendar.MINUTE]
                                calendar.time = Date()

                                val timePickerDialog = TimePickerDialog(
                                    context, { _, hourOfDay, minute ->
                                        horaEntradaRemember = "$hourOfDay"
                                        minutoEntradaRemember = "$minute"
                                    }, mHour, mMinute, true
                                )

                                timePickerDialog.show()

                            },//clickable

                        tint = Color.Blue,// cor azul da borda
                    )
                },
                readOnly = true,

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

        Spacer(modifier = Modifier.height(30.dp))

        //row hora saida
        Text(
            modifier = Modifier.padding(start = 80.dp), text = "Hora de Salida"
        )
        Spacer(modifier = Modifier.width(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                modifier = Modifier.width(120.dp),
                value = horaSalidaRemember,
                onValueChange = { horaSalidaRemember = it },
                label = { Text("Hora") },
                trailingIcon = {
                    Icon(

                        imageVector = Icons.Default.DateRange,//icone
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)

                            .clickable {

                                val calendar = Calendar.getInstance()
                                val mHour = calendar[Calendar.HOUR_OF_DAY]
                                val mMinute = calendar[Calendar.MINUTE]
                                calendar.time = Date()

                                val timePickerDialog = TimePickerDialog(
                                    context, { _, hourOfDay, minute ->
                                        horaSalidaRemember = "$hourOfDay"
                                        minutoSalidaRemember = "$minute"
                                    }, mHour, mMinute, true
                                )

                                timePickerDialog.show()

                            },//clickable

                        tint = Color.Blue,// cor azul da borda
                    )
                },
                readOnly = true,

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

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            modifier = Modifier.padding(start = 80.dp), text = "Hora de Entrada"
        )
        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            modifier = Modifier
                .width(220.dp)
                .padding(start = 80.dp),

            value = propinasRemember,
            onValueChange = { propinasRemember = it },
            label = { Text("Propinas") },
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            onClick = {

                FirebaseFirestore.getInstance().collection("TimeJob").document()
                    .set(modelTimeJob).addOnSuccessListener {

                        Log.d("firebase", "Salvo com sucesso")

                    }.addOnFailureListener { erro ->

                        Log.d("firebase", "Erro : ${erro.message}")
                    }

            },



        ) {

            Text("Salvar")
        }
    }
}