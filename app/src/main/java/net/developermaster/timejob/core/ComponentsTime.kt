package net.developermaster.timejob.core

import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class ComponentsTime {

    @Composable
    fun ShowTimePicker() {

        val context = LocalContext.current

        val calendar = Calendar.getInstance()
        val mHour = calendar[Calendar.HOUR_OF_DAY]
        val mMinute = calendar[Calendar.MINUTE]

        val time = remember {
            mutableStateOf("")
        }

        val timePickerDialog = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                time.value = "$hourOfDay:$minute"
            }, mHour, mMinute, false
        )





        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Selected Time : ${time.value}")
            Spacer(modifier = Modifier.size(16.dp))

            Button(onClick = { timePickerDialog.show() }) {
                Text(text = "Show Time Picker")
            }
        }
    }

}