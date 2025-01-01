package net.developermaster.timejob.screens

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import java.time.Duration
import java.util.Calendar
import java.util.Date

private var variavelGlobalSomaHora = Duration.ZERO
private var totalPropinas = 0

@Composable
fun FireBaseSalvar() {

    //context local
    val context = LocalContext.current

    var fechaRemember by remember { mutableStateOf("") }
    var plantaRemember by remember { mutableStateOf("") }
    var apartamentoRemember by remember { mutableStateOf("") }
    val situacaoRemember by remember { mutableStateOf("") }
    val chicaRemember by remember { mutableStateOf("") }
    val sabanasRemember by remember { mutableStateOf("") }
    val comentarioRemember by remember { mutableStateOf("") }
    val inventarioLimpiezaRemember by remember { mutableStateOf("") }

    //manutencao
    val mantenimentoRemember by remember { mutableStateOf("") }
    val fotoRemember by remember { mutableStateOf("") }
    val comentarioMantenimentoRemember by remember { mutableStateOf("") }

    val modelFireBase = net.developermaster.timejob.model.ModelFireBase(

        fecha = fechaRemember,
        planta = plantaRemember,
        apartamento = apartamentoRemember,
        situacao = situacaoRemember,
        chica = chicaRemember,
        sabanas = sabanasRemember,
        comentario = comentarioRemember,
        inventarioLimpieza = inventarioLimpiezaRemember,

        //manutencao
        mantenimento = mantenimentoRemember,
        foto = fotoRemember,
        comentarioMantenimento = comentarioMantenimentoRemember,

        )

    fun time() {

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
            }, ano, mes, dia
        )

        datePickerDialog.show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp), verticalArrangement = Arrangement.Center

    ) {

        Text(
            modifier = Modifier.padding(start = 100.dp, top = 16.dp),//todo padding top
            color = Color.Black,//todo cor negro
            fontSize = 18.sp,//todo tamanho da fonte
            fontFamily = FontFamily.SansSerif,//todo tipo de fonte
            textAlign = TextAlign.Center,//todo alinhamento do texto
            text = "Informacion"
        )

        Spacer(modifier = Modifier.height(16.dp))

        //fecha
        OutlinedTextField(
            modifier = Modifier
                .clickable {
                    time()
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
                            time()
                        },//clickable
                    tint = Color.Blue,// cor azul da borda
                )
            },
            readOnly = true,

            )

        Spacer(modifier = Modifier.height(8.dp))

        //planta
        OutlinedTextField(
            modifier = Modifier
                .clickable {
                    time()
                }
                .width(300.dp)
                .padding(start = 80.dp),
            value = plantaRemember,
            onValueChange = { plantaRemember = it },
            label = { Text("Planta") },
            )

        Spacer(modifier = Modifier.height(16.dp))

        //apartamento
        OutlinedTextField(
            modifier = Modifier
                .clickable {
                    time()
                }
                .width(300.dp)
                .padding(start = 80.dp),
            value = apartamentoRemember,
            onValueChange = { apartamentoRemember = it },
            label = { Text("Apartamento")},
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(

            onClick = {

                FirebaseFirestore.getInstance().collection("planta1").document().set(modelFireBase)
                    .addOnSuccessListener {

                        Log.d("firebase", "Salvo com sucesso")

                    }.addOnFailureListener { erro ->

                        Log.d("firebase", "Erro : ${erro.message}")
                    }


                Toast.makeText(context, "Salvo", Toast.LENGTH_SHORT).show()
            },

            modifier = Modifier.fillMaxWidth()

        ) {

            Text("Salvar")
        }
    }
}