package net.developermaster.timejob.screens

import android.R.attr.contentDescription
import android.R.attr.end
import android.R.attr.font
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import net.developermaster.timejob.R
import net.developermaster.timejob.model.ModelFireBase
import net.developermaster.timejob.model.ModelScreens
import net.developermaster.timejob.model.listaResultadoRetornados
import net.developermaster.timejob.model.modelFireBase
import org.checkerframework.common.subtyping.qual.Bottom
import java.time.Duration
import java.time.LocalTime
import java.util.Date
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlin.time.toJavaDuration

private var variavelGlobalSomaHora = Duration.ZERO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarPlanta1Screen(navcontroller: NavController) {

    TopAppBar(
        colors = TopAppBarDefaults.largeTopAppBarColors(),
        modifier = Modifier.padding(10.dp),
        title = {
            /*
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back",
                modifier = Modifier.clickable {
                    navcontroller.popBackStack()
                })
            */

            Text(modifier = Modifier.padding(start = 100.dp), text = "")
        },
        actions = {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search",
                modifier = Modifier.clickable {

                    navcontroller.navigate(ModelScreens.RelatorioScreenObject.route)

                }
            )

            Spacer(modifier = Modifier.width(20.dp))

            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "add",
                modifier = Modifier.clickable {

                    navcontroller.navigate(ModelScreens.AddScreenObject.route)

                }
            )
        })
}

@Composable
fun FireBaseListarTodosScreen(navcontroller: NavController) {

    Scaffold(
        Modifier.fillMaxSize(),

        topBar = {

            TopBarPlanta1Screen(navcontroller)
        }

    ) { paddingValues ->

        BodyPlanta1Screen(paddingValues, navcontroller)
    }
}

@Composable
fun BodyPlanta1Screen(paddingValues: PaddingValues, navcontroller: NavController) {

    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(100.dp),
    ) {

        items(1) {
//            ModelFireBase(modelFireBase = it, navcontroller)
            ModelFireBase(modelFireBase = modelFireBase, navcontroller)
        }
    }
}


@Composable
fun ModelFireBase(modelFireBase: List<ModelFireBase>, navcontroller: NavController) {

    var propinasRemember by remember { mutableStateOf("") }
    var dataInicioRemember by remember { mutableStateOf("") }
    var dataFimRemember by remember { mutableStateOf("") }

    val enabled by remember { mutableStateOf(true) }

    Column(

    ) {

        propinasRemember

        val listaDeDadosRetornadas = FirebaseFirestore.getInstance().collection("TimeJob")
            .orderBy("fecha", Query.Direction.ASCENDING)

        listaDeDadosRetornadas.addSnapshotListener { dadosRetornados, _ ->

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
                    val resultadoCalculorHoraFormatado = String.format("%02d:%02d", horas, minutos)

                    horas.toDuration(DurationUnit.HOURS)
                    minutos.toDuration(DurationUnit.MINUTES)

                    val totalHoras =
                        horas.toDuration(DurationUnit.HOURS) + minutos.toDuration(DurationUnit.MINUTES)

                    variavelGlobalSomaHora += horas.toDuration(DurationUnit.HOURS)
                        .toJavaDuration() + minutos.toDuration(DurationUnit.MINUTES)
                        .toJavaDuration()

                    listaResultadoRetornados += ("Fecha: $fechaDadosRetornados \nHora de Entrada: $horaEntradaRetornados : $minutoEntradaRetornados Hs \nHora de Salida: $horaSalidaRetornados : $minutoSalidaRetornados Hs \nTotal de Horas: $resultadoCalculorHoraFormatado Hs \nPropinas:€$propinasDadosRetornados")

                    propinasRemember = " "
                }

//                Log.d("firebaseListarTodos", "firebaseListarTodos: $listaResultadoRetornados")

            }
        }

        listaResultadoRetornados.forEach { lista ->

            val context = LocalContext.current

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .height(130.dp)
                    .fillMaxWidth()
                    .clip(Shapes().small)
                    .background(Color.LightGray)
                    .clickable {

//                        navcontroller.navigate(ModelScreens.InformationScreensObject.route + "/$idRemember")

//                    enabled = !enabled

                    }

            ) {

/*                //row editar
                Row(modifier = Modifier.padding(start = 300.dp, top = 40.dp)) {

                    Icon(

                        imageVector = Icons.Default.Create,//icone
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)

                            .clickable {
                                Toast
                                    .makeText(
                                        context, "icone clicado", Toast.LENGTH_SHORT
                                    )
                                    .show()
                            },//clickable

                        tint = Color.Blue,// cor azul da borda
                    )

                }

                //row deletar
                Row(modifier = Modifier.padding(start = 300.dp, top = 80.dp)) {

                    Icon(

                        imageVector = Icons.Default.Delete,//icone
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)

                            .clickable {
                                Toast
                                    .makeText(
                                        context, "icone clicado", Toast.LENGTH_SHORT
                                    )
                                    .show()
                            },//clickable

                        tint = Color.Blue,// cor azul da borda
                    )

                }

                */

                Column(modifier = Modifier.padding(start = 8.dp)) {

                    OutlinedTextField(

                        value = lista,
                        textStyle = TextStyle(color = Color.Black),
                        onValueChange = { },
                        label = { Text("") },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {

                            //icon editar
                            Icon(
                                imageVector = Icons.Default.Create,//icone
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(bottom = 50.dp)
                                    .width(50.dp)
                                    .clickable {

                                    },//clickable

                                tint = Color.Blue,// cor azul da borda
                            )

                            //icon editar
                            Icon(
                                imageVector = Icons.Default.Delete,//icone
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(top = 50.dp)
                                    .width(50.dp)
                                    .clickable {

                                    },//clickable

                                tint = Color.Blue,// cor azul da borda
                            )
                        },
                        readOnly = true,

                        )
                }
            }
        }
    }
}

