package net.developermaster.timejob.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.Date

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Shapes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import com.google.firebase.firestore.FirebaseFirestore
import net.developermaster.timejob.R

@Composable
fun InformationScreen(navcontroller: NavController, texto: String) {

    //context local
    val context = LocalContext.current

    val fechaRemember by remember { mutableStateOf("") }
    val plantaRemember by remember { mutableStateOf("") }
    val apartamentoRemember by remember { mutableStateOf("") }
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

    Scaffold(Modifier.fillMaxSize(), topBar = {
        TopBarInformation(navcontroller)
    }

    ) { paddingValues ->

        BodyInformation(paddingValues, texto, navcontroller)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarInformation(navcontroller: NavController) {

    Spacer(modifier = Modifier.height(150.dp))

    TopAppBar(colors = TopAppBarDefaults.largeTopAppBarColors(),
        modifier = Modifier.padding(10.dp),
        title = {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back",
                modifier = Modifier.clickable {
                    navcontroller.popBackStack()
                })

            Text(
                modifier = Modifier.padding(start = 30.dp), text = ""
            )
        },
        actions = {
//            Text(text = "Ações")
            Icon(
                imageVector = Icons.Default.Menu, contentDescription = "Menu"
            )
        })
}

@Composable
fun BodyInformation(paddingValues: PaddingValues, texto: String, navcontroller: NavController) {

    LazyColumn() {

        item {

            Spacer(modifier = Modifier.height(130.dp))

            HorizontalDivider()

            fecha()

            SalvarEstadoCheckboxFireBase()

            Spacer(modifier = Modifier.height(8.dp))

            TextoSiNo()

            CheckBoxClienteFijo()

            HorizontalDivider()

            CheckBoxClienteSaliendo()

            HorizontalDivider()

            CheckBoxClienteEntrando()

            HorizontalDivider()

            CheckBoxApartamientoSucio()

            HorizontalDivider()

            CheckBoxApartamientoLimpio()

            HorizontalDivider()

            CheckBoxSabana()

            HorizontalDivider()

            CheckBoxMantenimiento()

            HorizontalDivider()

            Spacer(modifier = Modifier.height(8.dp))

            ButtonLogin(navcontroller)

        }
    }

}

fun salvarEstadoCheckbox(EstadoCheckBox: Boolean) {

    val checkboxState = hashMapOf("EstadoCheckBox" to EstadoCheckBox)

/*    FirebaseFirestore.getInstance().collection("EstadosCheckBox")
        .add(checkboxState)*/

    FirebaseFirestore.getInstance().collection("planta1").add( checkboxState )

        .addOnSuccessListener { documentReference ->
            println("Document ID: ${documentReference}")
        }
        .addOnFailureListener { e ->
            println("Error: $e")
        }
}

@Composable
fun SalvarEstadoCheckboxFireBase() {


    val (isChecked, setChecked) = remember { mutableStateOf(false) }

    Checkbox(
        checked = isChecked,
        onCheckedChange = { checked ->
            setChecked(checked)
            salvarEstadoCheckbox(checked)
        },
        modifier = Modifier.padding(16.dp)
    )
    Text(text = if (isChecked) "Checado" else "No Checado")
}

@Composable
fun fecha() {
    val context = LocalContext.current
    var fechaRemember by remember { mutableStateOf("") }

    //fecha
    OutlinedTextField(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 40.dp, end = 150.dp)
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

            },
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
                            }, ano, mes, dia
                        )

                        datePickerDialog.show()

                    },//clickable

                tint = Color.Blue,// cor azul da borda
            )
        },
        readOnly = true,
    )
}

@Composable
fun TextoSiNo() {

    Text(
        text = "Si       No",
        modifier = Modifier.padding(start = 315.dp, top = 10.dp),
        fontSize = 16.sp

    )

}

@Composable
fun CheckBoxClienteFijo() {

    var checadoSi by remember { mutableStateOf(false) }
    var checadoNo by remember { mutableStateOf(false) }

    var textoRememeberCheckbox by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .padding(start = 20.dp)
            .fillMaxWidth()
            .clip(Shapes().small)

    )
    {

        Row(
            modifier = Modifier
                .padding(start = 20.dp)

        )

        {


            Text(
                text = "Cliente Fijo",
                color = Color.Black,//cor do texto
                modifier = Modifier.padding(start = 0.dp, top = 10.dp),
                fontSize = 25.sp

            )

            //checkbox si
            Checkbox(
                modifier = Modifier.padding(start = 130.dp),

                checked = checadoSi,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green,
                    checkmarkColor = Color.Black,
                ),

                onCheckedChange = { estado ->

                    checadoSi = estado

                    if (estado)

                        textoRememeberCheckbox = "Hoy"
                    else

                        textoRememeberCheckbox = "No"
                }
            )

            if (checadoSi == true && checadoNo == true) {

                Toast.makeText(
                    LocalContext.current,
                    "No se puede seleccionar los dos",
                    Toast.LENGTH_SHORT
                ).show()

                checadoSi = false
                checadoNo = false

            }

            //checkbox no
            Checkbox(
                checked = checadoNo,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Red,
                    checkmarkColor = Color.Black,
                ),

                onCheckedChange = { estado ->

                    checadoNo = estado

                    if (estado)

                        textoRememeberCheckbox = "Hoy"
                    else

                        textoRememeberCheckbox = "No"
                }
            )
        }
    }


}

@Composable
fun CheckBoxClienteSaliendo() {

    var checadoSi by remember { mutableStateOf(false) }
    var checadoNo by remember { mutableStateOf(false) }

    var textoRememeberCheckbox by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .padding(start = 20.dp)
            .fillMaxWidth()
            .clip(Shapes().small)

    )
    {

        Row(
            modifier = Modifier
                .padding(start = 20.dp)

        )

        {


            Text(
                text = "Cliente Saliendo",
                color = Color.Black,//cor do texto
                modifier = Modifier.padding(start = 0.dp, top = 10.dp),
                fontSize = 25.sp

            )

            //checkbox si
            Checkbox(
                modifier = Modifier.padding(start = 73.dp),

                checked = checadoSi,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green,
                    checkmarkColor = Color.Black,
                ),

                onCheckedChange = { estado ->

                    checadoSi = estado

                    if (estado)

                        textoRememeberCheckbox = "Hoy"
                    else

                        textoRememeberCheckbox = "No"
                }
            )

            if (checadoSi == true && checadoNo == true) {

                Toast.makeText(
                    LocalContext.current,
                    "No se puede seleccionar los dos",
                    Toast.LENGTH_SHORT
                ).show()

                checadoSi = false
                checadoNo = false

            }

            //checkbox no
            Checkbox(
                checked = checadoNo,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Red,
                    checkmarkColor = Color.Black,
                ),

                onCheckedChange = { estado ->

                    checadoNo = estado

                    if (estado)

                        textoRememeberCheckbox = "Hoy"
                    else

                        textoRememeberCheckbox = "No"
                }
            )
        }
    }


}

@Composable
fun CheckBoxClienteEntrando() {

    var checadoSi by remember { mutableStateOf(false) }
    var checadoNo by remember { mutableStateOf(false) }

    var textoRememeberCheckbox by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .padding(start = 20.dp)
            .fillMaxWidth()
            .clip(Shapes().small)

    )
    {

        Row(
            modifier = Modifier
                .padding(start = 20.dp)

        )

        {


            Text(
                text = "Cliente Entrando",
                color = Color.Black,//cor do texto
                modifier = Modifier.padding(start = 0.dp, top = 10.dp),
                fontSize = 25.sp

            )

            //checkbox si
            Checkbox(
                modifier = Modifier.padding(start = 70.dp),

                checked = checadoSi,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green,
                    checkmarkColor = Color.Black,
                ),

                onCheckedChange = { estado ->

                    checadoSi = estado

                    if (estado)

                        textoRememeberCheckbox = "Hoy"
                    else

                        textoRememeberCheckbox = "No"
                }
            )

            if (checadoSi == true && checadoNo == true) {

                Toast.makeText(
                    LocalContext.current,
                    "No se puede seleccionar los dos",
                    Toast.LENGTH_SHORT
                ).show()

                checadoSi = false
                checadoNo = false

            }

            //checkbox no
            Checkbox(
                checked = checadoNo,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Red,
                    checkmarkColor = Color.Black,
                ),

                onCheckedChange = { estado ->

                    checadoNo = estado

                    if (estado)

                        textoRememeberCheckbox = "Hoy"
                    else

                        textoRememeberCheckbox = "No"
                }
            )
        }
    }


}

@Composable
fun CheckBoxApartamientoSucio() {

    var checadoSi by remember { mutableStateOf(false) }
    var checadoNo by remember { mutableStateOf(false) }

    var textoRememeberCheckbox by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .padding(start = 20.dp)
            .fillMaxWidth()
            .clip(Shapes().small)

    )
    {

        Row(
            modifier = Modifier
                .padding(start = 20.dp)

        )

        {


            Text(
                text = "Apartamiento Sucio",
                color = Color.Black,//cor do texto
                modifier = Modifier.padding(start = 0.dp, top = 10.dp),
                fontSize = 25.sp

            )

            //checkbox si
            Checkbox(
                modifier = Modifier.padding(start = 35.dp),

                checked = checadoSi,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green,
                    checkmarkColor = Color.Black,
                ),

                onCheckedChange = { estado ->

                    checadoSi = estado

                    if (estado)

                        textoRememeberCheckbox = "Hoy"
                    else

                        textoRememeberCheckbox = "No"
                }
            )

            if (checadoSi == true && checadoNo == true) {

                Toast.makeText(
                    LocalContext.current,
                    "No se puede seleccionar los dos",
                    Toast.LENGTH_SHORT
                ).show()

                checadoSi = false
                checadoNo = false

            }

            //checkbox no
            Checkbox(
                checked = checadoNo,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Red,
                    checkmarkColor = Color.Black,
                ),

                onCheckedChange = { estado ->

                    checadoNo = estado

                    if (estado)

                        textoRememeberCheckbox = "Hoy"
                    else

                        textoRememeberCheckbox = "No"
                }
            )
        }
    }


}

@Composable
fun CheckBoxApartamientoLimpio() {

    var checadoSi by remember { mutableStateOf(false) }
    var checadoNo by remember { mutableStateOf(false) }

    var textoRememeberCheckbox by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .padding(start = 20.dp)
            .fillMaxWidth()
            .clip(Shapes().small)

    )
    {

        Row(
            modifier = Modifier
                .padding(start = 20.dp)

        )

        {


            Text(
                text = "Apartamiento Limpio",
                color = Color.Black,//cor do texto
                modifier = Modifier.padding(start = 0.dp, top = 10.dp),
                fontSize = 25.sp

            )

            //checkbox si
            Checkbox(
                modifier = Modifier.padding(start = 22.dp),

                checked = checadoSi,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green,
                    checkmarkColor = Color.Black,
                ),

                onCheckedChange = { estado ->

                    checadoSi = estado

                    if (estado)

                        textoRememeberCheckbox = "Hoy"
                    else

                        textoRememeberCheckbox = "No"
                }
            )

            if (checadoSi == true && checadoNo == true) {

                Toast.makeText(
                    LocalContext.current,
                    "No se puede seleccionar los dos",
                    Toast.LENGTH_SHORT
                ).show()

                checadoSi = false
                checadoNo = false

            }

            //checkbox no
            Checkbox(
                checked = checadoNo,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Red,
                    checkmarkColor = Color.Black,
                ),

                onCheckedChange = { estado ->

                    checadoNo = estado

                    if (estado)

                        textoRememeberCheckbox = "Hoy"
                    else

                        textoRememeberCheckbox = "No"
                }
            )
        }
    }


}

@Composable
fun CheckBoxSabana() {

    var checadoSi by remember { mutableStateOf(false) }
    var checadoNo by remember { mutableStateOf(false) }

    var textoRememeberCheckbox by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .padding(start = 20.dp)
            .fillMaxWidth()
            .clip(Shapes().small)

    )
    {

        Row(
            modifier = Modifier
                .padding(start = 20.dp)

        )

        {


            Text(
                text = "Troca de Sabanas",
                color = Color.Black,//cor do texto
                modifier = Modifier.padding(start = 0.dp, top = 10.dp),
                fontSize = 25.sp

            )

            //checkbox si
            Checkbox(
                modifier = Modifier.padding(start = 60.dp),

                checked = checadoSi,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green,
                    checkmarkColor = Color.Black,
                ),

                onCheckedChange = { estado ->

                    checadoSi = estado

                    if (estado)

                        textoRememeberCheckbox = "Hoy"
                    else

                        textoRememeberCheckbox = "No"
                }
            )

            if (checadoSi == true && checadoNo == true) {

                Toast.makeText(
                    LocalContext.current,
                    "No se puede seleccionar los dos",
                    Toast.LENGTH_SHORT
                ).show()

                checadoSi = false
                checadoNo = false

            }

            //checkbox no
            Checkbox(
                checked = checadoNo,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Red,
                    checkmarkColor = Color.Black,
                ),

                onCheckedChange = { estado ->

                    checadoNo = estado

                    if (estado)

                        textoRememeberCheckbox = "Hoy"
                    else

                        textoRememeberCheckbox = "No"
                }
            )
        }
    }


}

@Composable
fun CheckBoxMantenimiento() {

    val context = LocalContext.current

    var checadoSi by remember { mutableStateOf(false) }
    var checadoNo by remember { mutableStateOf(false) }

    var textoRememeberCheckbox by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .padding(start = 20.dp)
            .fillMaxWidth()
            .clip(Shapes().small)

    )
    {

        Row(
            modifier = Modifier
                .padding(start = 20.dp)

        )

        {


            Text(
                text = "Mantenimiento",
                color = Color.Black,//cor do texto
                modifier = Modifier.padding(start = 0.dp, top = 10.dp),
                fontSize = 25.sp

            )

            Spacer(modifier = Modifier.width(1.dp))

            //camera
            Image(
                painter = painterResource(id = R.drawable.timejob),//todo drawable
                contentDescription = null,

                modifier = Modifier
                    .width(45.dp)//todo largura
                    .height(45.dp)//todo altura
                    .border(2.dp, Color.Yellow, CircleShape)//todo borda amarela circular
                    .clip(CircleShape)//todo borda circular
                    .clickable {

                        Toast.makeText(context, "Camera", Toast.LENGTH_SHORT).show()

                    },//todo clique
                contentScale = ContentScale.Crop,//todo escala da imagem para o tamanho do container
                alignment = Alignment.Center//todo alinhamento da imagem no centro)
            )

            Spacer(modifier = Modifier.width(1.dp))

            //nota
            Image(
                painter = painterResource(id = R.drawable.timejob),//todo drawable
                contentDescription = null,

                modifier = Modifier
                    .width(45.dp)//todo largura
                    .height(45.dp)//todo altura
                    .border(2.dp, Color.Yellow, CircleShape)//todo borda amarela circular
                    .clip(CircleShape)//todo borda circular
                    .clickable {

                        Toast.makeText(context, "Nota", Toast.LENGTH_SHORT).show()

                    },//todo clique
                contentScale = ContentScale.Crop,//todo escala da imagem para o tamanho do container
                alignment = Alignment.Center//todo alinhamento da imagem no centro)
            )


            //checkbox si
            Checkbox(

                checked = checadoSi,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green,
                    checkmarkColor = Color.Black,
                ),

                onCheckedChange = { estado ->

                    checadoSi = estado

                    if (estado)

                        textoRememeberCheckbox = "Hoy"
                    else

                        textoRememeberCheckbox = "No"
                }
            )

            if (checadoSi == true && checadoNo == true) {

                Toast.makeText(
                    LocalContext.current,
                    "No se puede seleccionar los dos",
                    Toast.LENGTH_SHORT
                ).show()

                checadoSi = false
                checadoNo = false

            }

            //checkbox no
            Checkbox(
                checked = checadoNo,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Red,
                    checkmarkColor = Color.Black,
                ),

                onCheckedChange = { estado ->

                    checadoNo = estado

                    if (estado)

                        textoRememeberCheckbox = "Hoy"
                    else

                        textoRememeberCheckbox = "No"
                }
            )
        }
    }


}


@Composable
fun SwitchApartamiento() {

    val context = LocalContext.current

    var verifica by remember { mutableStateOf(false) }
    var textoRememeberSwitch by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .padding(start = 20.dp)
            .fillMaxWidth()
            .clip(Shapes().small)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)


        )

        {

            Text(

                text = "Apartamento = $textoRememeberSwitch ",
                color = Color.Black,//cor do texto
                modifier = Modifier.padding(top = 10.dp),
                fontSize = 25.sp

            )

            Switch(
//            modifier = Modifier.padding(top = 10.dp),
                checked = verifica,
                onCheckedChange = { estado ->

                    verifica = estado

                    if (estado)

                        textoRememeberSwitch = "Sucio"
                    else

                        textoRememeberSwitch = "Limpio"

                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Green,
                    checkedTrackColor = Color.Red,
                    uncheckedThumbColor = Color.Red,
                    uncheckedTrackColor = Color.Green
                )
            )
        }
    }
}

@Composable
fun SwitchSalidaCliente() {

    val context = LocalContext.current

    var verifica by remember { mutableStateOf(false) }
    var textoRememeberSwitch by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .padding(start = 20.dp)
            .fillMaxWidth()
            .clip(Shapes().small)
            .background(Color.Gray)

    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        )

        {

            Text(

                text = "Salida de Cliente = $textoRememeberSwitch ",
                color = Color.White,//cor do texto
                modifier = Modifier.padding(top = 10.dp),
                fontSize = 25.sp

            )

            Switch(
//            modifier = Modifier.padding(top = 10.dp),
                checked = verifica,
                onCheckedChange = { estado ->

                    verifica = estado

                    if (estado)

                        textoRememeberSwitch = "Si"
                    else

                        textoRememeberSwitch = "No"

                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Green,
                    checkedTrackColor = Color.Red,
                    uncheckedThumbColor = Color.Red,
                    uncheckedTrackColor = Color.Green
                )
            )
        }
    }
}

@Composable
fun CheckBoxDiaSabana() {

    var checado by remember { mutableStateOf(false) }

    var textoRememeberCheckbox by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(start = 20.dp)

    )

    {

        Text(
            text = "Troca de Sabanas $textoRememeberCheckbox",
            color = Color.Blue,//cor do texto
            modifier = Modifier.padding(start = 0.dp, top = 10.dp),
            fontSize = 25.sp

        )

        Row {

            Checkbox(
                checked = checado,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green,
                    checkmarkColor = Color.Blue
                ),
                onCheckedChange = { estado ->

                    checado = estado

                    if (estado)

                        textoRememeberCheckbox = "Hoy"
                    else

                        textoRememeberCheckbox = "No"
                }
            )

            Checkbox(
                checked = checado,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green,
                    checkmarkColor = Color.Blue
                ),
                onCheckedChange = { estado ->

                    checado = estado

                    if (estado)

                        textoRememeberCheckbox = "Hoy"
                    else

                        textoRememeberCheckbox = "No"
                }
            )

            Checkbox(
                checked = checado,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green,
                    checkmarkColor = Color.Blue
                ),
                onCheckedChange = { estado ->

                    checado = estado

                    if (estado)

                        textoRememeberCheckbox = "Hoy"
                    else

                        textoRememeberCheckbox = "No"
                }
            )

            Checkbox(
                checked = checado,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green,
                    checkmarkColor = Color.Blue
                ),
                onCheckedChange = { estado ->

                    checado = estado

                    if (estado)

                        textoRememeberCheckbox = "Hoy"
                    else

                        textoRememeberCheckbox = "No"
                }
            )
        }


    }
}

@Composable
private fun RadioOptionSabana() {

    val listaSabanaSemanas = listOf(

        "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"
    )

    var itemSelecionado by remember { mutableStateOf(listaSabanaSemanas[0]) }

    listaSabanaSemanas.forEach { opcao ->

        Box(
            modifier = Modifier
                .padding(start = 20.dp)
                .fillMaxWidth()
                .clip(Shapes().small)
                .background(Color.Gray)

        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            )

            {

                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = opcao,
                    color = Color.White,//todo cor azul
                    fontSize = 30.sp,//todo tamanho da fonte
                    fontFamily = FontFamily.SansSerif,//todo tipo de fonte
                )
                RadioButton(
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Green,
                        unselectedColor = Color.Red
                    ),

                    selected = opcao == itemSelecionado,

                    onClick = {

                        itemSelecionado = opcao

                    })
            }
        }
    }
}

@Composable
fun SwitchMantenimiento() {

    val context = LocalContext.current

    var verifica by remember { mutableStateOf(false) }
    var textoRememeberSwitch by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .padding(start = 20.dp)
            .fillMaxWidth()
            .clip(Shapes().small)
            .background(Color.Gray)

    ) {

        Row(
            modifier = Modifier
                .padding(start = 20.dp)

        )

        {

            Image(
                painter = painterResource(id = R.drawable.timejob),//todo drawable
                contentDescription = null,

                modifier = Modifier
                    .padding(start = 10.dp)
                    .width(50.dp)//todo largura
                    .height(50.dp)//todo altura
                    .border(2.dp, Color.Yellow, CircleShape)//todo borda amarela circular
                    .clip(CircleShape)//todo borda circular
                    .clickable {

                        Toast.makeText(context, "Camera", Toast.LENGTH_SHORT).show()

                    },//todo clique
                contentScale = ContentScale.Crop,//todo escala da imagem para o tamanho do container
                alignment = Alignment.Center//todo alinhamento da imagem no centro)
            )
        }
    }
}

@Composable
fun ComentarioMantenimento() {

    var textoRememeberComentario by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp)
            .padding(start = 5.dp),
        value = textoRememeberComentario,
        onValueChange = { textoRememeberComentario = it },
        label = { Text("Informe de mantenimiento") },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Edit,//icone
                contentDescription = null,
                modifier = Modifier
                    .width(50.dp)
            )
        },
    )

}

@Composable
fun ButtonLogin(navcontroller: NavController) {

    Button(
        modifier = Modifier
            .padding(start = 20.dp)
            .fillMaxWidth(),
        onClick = {

            navcontroller.navigate("login")

        }) {
        Text(text = "Salvar")
    }
}


/*

@Composable
fun ApartmentoSituacao() {

    var dataClassApartamento by remember {
        mutableStateOf(listOf(
            DataClassApartamento("Apartamento", EnumApartamento.LIMPIA),
//            DataClassApartamento("102", EnumApartamento.SUCIA),

        ))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(150.dp))

        Text(
            "101",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 150.dp)
        )
        LazyColumn {
            items(dataClassApartamento) { room ->
                ApartamentosItens(room) { newStatus ->
                    dataClassApartamento = dataClassApartamento.map { if (it.ap == room.ap) it.copy(status = newStatus) else it }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApartamentosItens(dataClassApartamento: DataClassApartamento, estados: (EnumApartamento) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        fecha()

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))


        SwitchSalidaCliente()

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        CheckBoxSabana()

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                " ${dataClassApartamento.ap}",
                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.width(80.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            ApartamentoStatus(dataClassApartamento.status)

            Spacer(modifier = Modifier.width(8.dp))



            var listaExpacivelRemember by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = listaExpacivelRemember,
                onExpandedChange = { listaExpacivelRemember = !listaExpacivelRemember }
            ) {

                TextField(
                    value = dataClassApartamento.status.toString(),
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = listaExpacivelRemember) },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = listaExpacivelRemember,
                    onDismissRequest = { listaExpacivelRemember = false }
                ) {
                    EnumApartamento.values().forEach { status ->
                        DropdownMenuItem(
                            text = { Text(status.toString()) },
                            onClick = {
                                estados(status)
                                listaExpacivelRemember = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ApartamentoStatus(status: EnumApartamento) {

    val (backgroundColor, textColor) = when (status) {
        EnumApartamento.LIMPIA -> Color(0xFF4CAF50) to Color.White
        EnumApartamento.SUCIA -> Color(0xFFF44336) to Color.White
    }

    Surface(
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {

        Text(
            status.toString().replace("_", " "),
            color = textColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

enum class EnumApartamento {
    LIMPIA, SUCIA,
}

data class DataClassApartamento(val ap: String, val status: EnumApartamento)

*/







