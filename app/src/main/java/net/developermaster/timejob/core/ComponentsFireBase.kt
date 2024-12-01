package net.developermaster.timejob.core

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import net.developermaster.timejob.model.ModelTimeJob
import java.time.Duration
import java.time.LocalTime
import java.util.Date

class ComponentsFireBase {

    val listaResultadoRetornados = mutableListOf<String>()
    val totalPropinas = mutableListOf<String>()
    var totalHorasForEach = mutableListOf<String>()
    var variavelTotalHorasListar = ""
    var totalHorasSomaIndex = ""

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Salvar() {

        //context local
        val context = LocalContext.current

        val datePickerState = rememberDatePickerState()
        val selectedDateMillis = datePickerState.selectedDateMillis

        var fechaRemember by remember { mutableStateOf("") }

        var horaEntradaRemember by remember { mutableStateOf("") }
        var minutoEntradaRemember by remember { mutableStateOf("") }

        var horaSalidaRemember by remember { mutableStateOf("") }
        var minutoSalidaRemember by remember { mutableStateOf("") }

        var propinasRemember by remember { mutableStateOf("") }

        val formattedDate = remember(selectedDateMillis) {
            if (selectedDateMillis != null) {
                val calendar = Calendar.getInstance().apply { timeInMillis = selectedDateMillis }
                "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${
                    calendar.get(
                        Calendar.YEAR
                    )
                }"
            } else {

                "Selecione uma data"
            }
        }

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

            Text(
                modifier = Modifier.padding(start = 100.dp, top = 16.dp),//todo padding top
                color = Color.Black,//todo cor negro
                fontSize = 18.sp,//todo tamanho da fonte
                fontFamily = FontFamily.SansSerif,//todo tipo de fonte
                textAlign = TextAlign.Center,//todo alinhamento do texto
                text = "Salvar informacion"
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
                                        fechaRemember = "$dia/$mes/$ano"
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

            propinasRemember

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

                        //resultado tempo
                        Log.i("tempo", "Calculo = $resultadoCalculorHoraFormatado")

                        listaResultadoRetornados += ("Fecha: $fechaDadosRetornados \nHora de Entrada: $horaEntradaRetornados : $minutoEntradaRetornados \nHora de Salida: $horaSalidaRetornados : $minutoSalidaRetornados \nTotal de Horas: $resultadoCalculorHoraFormatado \nPropinas: $propinasDadosRetornados")

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

        val context = LocalContext.current

        var propinasRemember by remember { mutableStateOf("") }
        var dataInicioRemember by remember { mutableStateOf("") }
        var dataFimRemember by remember { mutableStateOf("") }


        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
//                .background(Color.LightGray)
                .fillMaxSize()
                .padding(16.dp), verticalArrangement = Arrangement.Center

        ) {

            propinasRemember

            Text(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),//todo padding top
                color = Color.Black,//todo cor negro
                fontSize = 18.sp,//todo tamanho da fonte
                fontFamily = FontFamily.SansSerif,//todo tipo de fonte
                textAlign = TextAlign.Center,//todo alinhamento do texto
                text = "Relatorio"
            )
        }


        //row hora entrada
        Row(
            modifier = Modifier,
//                .background(Color.LightGray),
            horizontalArrangement = Arrangement.Center

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
                                dataInicioRemember = "$day/$month/$year"
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
                                        dataInicioRemember = "$day/$month/$year"
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
                                        dataFimRemember = "$day/$month/$year"
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
                    .whereGreaterThanOrEqualTo("fecha", dataInicioRemember)
                    .whereLessThanOrEqualTo("fecha", dataFimRemember)

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

                            totalHorasForEach += (resultadoCalculorHoraFormatado)

                            //resultado tempo
                            Log.i("tempo", "Calculo = $resultadoCalculorHoraFormatado")

                            listaResultadoRetornados += ("Fecha: $fechaDadosRetornados \nHora de Entrada: $horaEntradaRetornados : $minutoEntradaRetornados \nHora de Salida: $horaSalidaRetornados : $minutoSalidaRetornados \nTotal de Horas: $resultadoCalculorHoraFormatado \nPropinas: $propinasDadosRetornados")

                            /*                            Log.d(
                                                            "firebase",
                                                            " id: $idRetornado \n Fecha: $fechaDadosRetornados \n Horas Trabajadas: $horaSalidaRetornados \n Propinas: $propinasDadosRetornados \n \n "
                                                        )*/

                            Log.d(
                                "firebase", "total hora relatorio: $resultadoCalculorHoraFormatado"
                            )

                            propinasRemember = " "
                        }
                    }
                }
            },

            ) {
            Text("Pesquisar")
        }

        Column(
            modifier = Modifier
//                .background(Color.LightGray)
                .fillMaxSize()
                .padding(16.dp), verticalArrangement = Arrangement.Center

        ) {

            Spacer(modifier = Modifier.height(16.dp))

///////////////////////////////////////////////////////////////////

            totalHorasForEach.forEach { totalHorasForEach ->

                variavelTotalHorasListar = totalHorasForEach

                var totalHorasForEachMapIndex1 = totalHorasForEach[1]

                totalHorasSomaIndex += totalHorasForEachMapIndex1.toString()

                Log.d("totalHoras", "totalHorasForEachMapIndex1: $totalHorasForEachMapIndex1")
                Log.d("totalHoras", "variavelTotalHoraListarDentroDoForEach: $variavelTotalHorasListar")
                Log.d("totalHoras", "totalHorasSomaIndexDentroDoForEach: $totalHorasSomaIndex")

            }

            val SomaIndex =  totalHorasSomaIndex +

            Log.d("totalHoras", "totalHorasForEach: $totalHorasForEach")
            Log.d("totalHoras", "variavelTotalHoraListarForaDoForEach: $variavelTotalHorasListar")
            Log.d("totalHoras", "totalHorasSomaIndexForaDoForEach: $totalHorasSomaIndex")




///////////////////////////////////////////////////////////////////

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
                modifier = Modifier.width(220.dp),
                value = "€ 50",
//                value = "€ 50",
                onValueChange = { " " },
                label = { Text("Total de Propinas") },
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
                    .width(220.dp)
                    .padding(start = 8.dp),
//                value = "$totalHorasListar",
                value = "12:00H",
                onValueChange = { "" },
                label = { Text("Total de Horas") },
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


    @Composable
    fun ComponentsFecha() {

        val context = LocalContext.current

        val year: Int
        val month: Int
        val day: Int

        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.time = Date()

        val date = remember {
            mutableStateOf("")
        }

        val datePickerDialog = DatePickerDialog(
            context, { _: DatePicker, year: Int, month: Int, day: Int ->
                date.value = "$day/$month/$year"
            }, year, month, day
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Selected Date : ${date.value}")
            Spacer(modifier = Modifier.size(16.dp))

            Button(onClick = { datePickerDialog.show() }) {
                Text(text = "Show Date Picker")
            }

            Text(text = "Selected Date : ${date.value}")
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

