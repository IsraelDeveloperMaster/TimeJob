package net.developermaster.timejob.core

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.widget.DatePicker
import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.material3.DatePicker
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.T
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.common.primitives.UnsignedBytes.toInt
import java.time.Clock
import java.time.LocalDate
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDatePicker() {

    var fechaRemember by remember { mutableStateOf("") }

    val datePickerState = rememberDatePickerState()
    val selectedDateMillis = datePickerState.selectedDateMillis

    var formattedDate = remember(selectedDateMillis) {
        if (selectedDateMillis != null) {
            val calendar = Calendar.getInstance().apply { timeInMillis = selectedDateMillis }
            "${calendar.get(Calendar.DAY_OF_MONTH) + 1}/${calendar.get(Calendar.MONTH) + 1}/${
                calendar.get(
                    Calendar.YEAR
                )
            }"
        } else {
            "Selecione uma data"
        }
    }

    Column {

        val context = LocalContext.current

        Spacer(modifier = Modifier.height(30.dp))


//        DatePicker(datePickerState)
/*
        OnDateSetListener { datePicker, i, i2, i3 ->

            formattedDate = "$i3/${i2 + 1}/$i"
            fechaRemember = formattedDate

        }*/

        Spacer(modifier = Modifier.height(16.dp))

        //fecha
        OutlinedTextField(
            modifier = Modifier
                .clickable {

                }
                .width(290.dp)
                .padding(start = 89.dp),
            value = formattedDate,
            onValueChange = { formattedDate },
            label = { Text("Fecha") },
            trailingIcon = {
                Icon(

                    imageVector = Icons.Default.DateRange,//icone
                    contentDescription = null,
                    modifier = Modifier
                        .width(50.dp)

                        .clickable {



                            /*

                            val dia: Int
                            var mes: Int
                            val ano: Int

*//*                            var dataAtual = LocalDate.now(Clock.systemDefaultZone())
                            val dia = dataAtual.dayOfMonth
                            val mes = dataAtual.monthValue
                            val ano = dataAtual.year
                            val mes2 = mes + dataAtual.*//*


                            val dataAtual = android.icu.util.Calendar.getInstance()
                            dia = dataAtual.get(android.icu.util.Calendar.DAY_OF_MONTH)
                            mes = dataAtual.get(android.icu.util.Calendar.MONTH)
                            ano = dataAtual.get(android.icu.util.Calendar.YEAR)
                            dataAtual.time = Date()

                            val mes2 = mes + dataAtual.get(Calendar.MONTH.plus(2)) + 2 + 6 / 60


                            val datePickerDialog = DatePickerDialog(
                                context, { _: DatePicker, ano: Int, mes: Int, dia: Int ->
                                    fechaRemember = "$dia/$mes/$ano"
                                }, ano, mes, dia
                            )

                            datePickerDialog.show()

                            */

                        },//clickable

                    tint = Color.Blue,// cor azul da borda
                )
            },
            readOnly = true,
        )
    }


}
