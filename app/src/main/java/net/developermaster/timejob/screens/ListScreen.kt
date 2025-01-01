package net.developermaster.timejob.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import net.developermaster.timejob.R
import net.developermaster.timejob.model.ModelFireBase
import net.developermaster.timejob.model.ModelScreens
import net.developermaster.timejob.model.ModelTimeJob
import net.developermaster.timejob.model.Movie
import net.developermaster.timejob.model.listaResultadoRetornados
import net.developermaster.timejob.model.movies
import net.developermaster.timejob.model.timejobs
import java.time.Duration
import java.time.LocalTime


@Composable
fun ListScreen(navcontroller: NavController) {

    Scaffold(Modifier.fillMaxSize(),

        topBar = {

            TopBarListScreen(navcontroller)
        }

    ) { paddingValues ->

        BodyListScreen(paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarListScreen(navcontroller: NavController) {

//    Spacer(modifier = Modifier.height(200.dp))

    TopAppBar(colors = TopAppBarDefaults.largeTopAppBarColors(),
        modifier = Modifier.padding(10.dp),
        title = {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back",
                modifier = Modifier.clickable {
                    navcontroller.popBackStack()
                })

            Text(
                modifier = Modifier.padding(start = 30.dp), text = "List Screen"

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
fun BodyListScreen(paddingValues: PaddingValues) {

    var texto by remember { mutableStateOf("") }

    LazyVerticalGrid(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
        columns = GridCells.Adaptive(120.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

//        items(movies, key = { it.id }) {  MovieItem(movie = it)

        items(timejobs, key = { it.fecha }) { ModelFireBaseTimeJob(modelTimeJob = it)
        }

    }
}

@Composable
fun MovieItem(movie: Movie) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.fillMaxSize().aspectRatio(2 / 3f)
                .clip(androidx.compose.material3.Shapes().small)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
        Text(
            text = movie.title,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1// para quebrar a linha uma vez que o texto ultrapassa o limite
        )
    }
}

@Composable
fun ModelFireBaseTimeJob(modelTimeJob: ModelTimeJob) {

    var idRemember by remember { mutableStateOf("") }
    var apartamentoRemember by remember { mutableStateOf("") }
    var fechaRemember by remember { mutableStateOf("") }
    var propinasRemember by remember { mutableStateOf("") }
    val enabled by remember { mutableStateOf(true) }

    Column(

    ) {

        Box(
            modifier = Modifier.fillMaxSize().aspectRatio(2 / 3f)
                .clip(androidx.compose.material3.Shapes().small)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
        Text(
            text = modelTimeJob.minutoEntrada,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1// para quebrar a linha uma vez que o texto ultrapassa o limite
        )


        propinasRemember

        val listaDeDadosRetornadas = FirebaseFirestore.getInstance().collection("planta1")
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
                    var propinasDadosRetornados = dados["propinas"]

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

                    listaResultadoRetornados += ("Fecha: $fechaDadosRetornados \nHora de Entrada: $horaEntradaRetornados : $minutoEntradaRetornados \nHora de Salida: $horaSalidaRetornados : $minutoSalidaRetornados \nTotal de Horas: $resultadoCalculorHoraFormatado \nPropinas: €$propinasDadosRetornados")

                    propinasRemember = " "
                }

                Log.d("firebaseListar", "firebaseListar: $listaResultadoRetornados")

            }
        }

        listaResultadoRetornados.forEach { lista ->

            val context = LocalContext.current

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .height(100.dp)
                    .width(100.dp)
                    .clip(Shapes().small)
                    .background(Color.Green)
                    .clickable {

                        Toast.makeText(context, "Box", Toast.LENGTH_SHORT).show()

//                        navcontroller.navigate(ModelScreens.InformationScreensObject.route + "/$idRemember")


//                    enabled = !enabled

                    }

            ) {

                Column(modifier = Modifier.padding(start = 8.dp))  {

                    Text(
                        text = lista,
                        modifier = Modifier
                            .padding(start = 22.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge
                    )

                    val ico1 = Icon(
                        painter = painterResource(id = R.drawable.timejob),
                        contentDescription = "",
                        tint = Color.Red
                    )

                    val ico2 = Icon(
                        painter = painterResource(id = R.drawable.timejob),
                        contentDescription = "",
                        tint = Color.Red
                    )

                    val ico3 = Icon(
                        painter = painterResource(id = R.drawable.timejob),
                        contentDescription = "",
                        tint = Color.Red
                    )
                }

                Column(modifier = Modifier.padding(start = 50.dp, top = 28.dp)) {

                    val ico4 = Icon(
                        painter = painterResource(id = R.drawable.clientesalindo),
                        contentDescription = "",
                        tint = Color.Red
                    )

                    val ico5 = Icon(
                        painter = painterResource(id = R.drawable.clientesalindo),
                        contentDescription = "",
                        tint = Color.Red
                    )

                    val ico6 = Icon(
                        painter = painterResource(id = R.drawable.clientesalindo),
                        contentDescription = "",
                        tint = Color.Red
                    )

                    if (enabled) {

                        Icon(
                            painter = painterResource(id = R.drawable.timejob),
                            contentDescription = "",
                            tint = Color.Red
                        )

                    } else {

                        Icon(
                            painter = painterResource(id = R.drawable.timejob),
                            contentDescription = "",
                            tint = Color.Red
                        )

                    }
                }

            }
        }
    }
}




