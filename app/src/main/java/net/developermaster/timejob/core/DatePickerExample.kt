package net.developermaster.timejob.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

    @Composable
    fun DatePickerExample() {
        var selectedDate by remember { mutableStateOf(LocalDate.now()) }
        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Data selecionada: ${selectedDate.format(dateFormatter)}",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            DatePicker(
                initialDate = selectedDate,
                onDateSelected = { date ->
                    selectedDate = date
                }
            )
        }
    }

    @Composable
    fun DatePicker(
        initialDate: LocalDate,
        onDateSelected: (LocalDate) -> Unit
    ) {
        var selectedDate by remember { mutableStateOf(initialDate) }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Selecione uma data:",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(onClick = {
                    selectedDate = selectedDate.minusDays(1)
                    onDateSelected(selectedDate)
                }) {
                    Text("Anterior")
                }

                Text(
                    text = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    style = MaterialTheme.typography.titleMedium
                )

                Button(onClick = {
                    selectedDate = selectedDate.plusDays(1)
                    onDateSelected(selectedDate)
                }) {
                    Text("Próximo")
                }
            }
        }
    }
