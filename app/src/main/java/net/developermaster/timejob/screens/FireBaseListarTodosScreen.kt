package net.developermaster.timejob.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import net.developermaster.timejob.R
import net.developermaster.timejob.model.ModelFireBase
import net.developermaster.timejob.model.ModelScreens
import net.developermaster.timejob.model.listaResultadoRetornados
import net.developermaster.timejob.model.modelFireBase


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarPlanta1Screen(navcontroller: NavController) {

    TopAppBar(colors = TopAppBarDefaults.largeTopAppBarColors(),
        modifier = Modifier.padding(10.dp),
        title = {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back",
                modifier = Modifier.clickable {
                    navcontroller.popBackStack()
                })

            Text( modifier = Modifier.padding(start = 130.dp), text = "Planta 1" )
        },
        actions = {
//            Text(text = "Ações")
            Icon(
                imageVector = Icons.Default.Menu, contentDescription = "Menu"
            )
        })
}

@Composable
fun FireBaseListarTodosScreen(navcontroller: NavController) {

    Scaffold(Modifier.fillMaxSize(),

        topBar = {

            TopBarPlanta1Screen(navcontroller)
        }

    ) { paddingValues ->

        BodyPlanta1Screen(paddingValues, navcontroller)
    }
}

@Composable
fun BodyPlanta1Screen(paddingValues: PaddingValues, navcontroller: NavController) {

    LazyVerticalGrid(
        modifier = Modifier.padding(paddingValues),
        columns = GridCells.Adaptive(100.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalArrangement = Arrangement.spacedBy(100.dp),
    ) {

        items(modelFireBase, key = { it.apartamento }) {
            ModelFireBase(modelFireBase = it, navcontroller)
        }
    }
}

@Composable
fun ModelFireBase(modelFireBase: ModelFireBase, navcontroller: NavController) {

    var idRemember by remember { mutableStateOf("") }
    var apartamentoRemember by remember { mutableStateOf("") }
    var fechaRemember by remember { mutableStateOf("") }
    var propinasRemember by remember { mutableStateOf("") }
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
                    val fechaRetornados = dados["fecha"]
                    val plantaRetornados = dados["planta"]
                    val apartamentosRetornados = dados["apartamento"]

                    listaResultadoRetornados += ("$fechaRetornados")

                    idRemember = "$idRetornado"
                    apartamentoRemember = "$apartamentosRetornados"
                    fechaRemember = "$fechaRetornados"

                    propinasRemember = " "
                }

                Log.d("firebasePlanta1", "firebasePlanta1: $listaResultadoRetornados")

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

                        navcontroller.navigate(ModelScreens.InformationScreensObject.route + "/$idRemember")

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
                        painter = painterResource(id = R.drawable.clientesalindo),
                        contentDescription = "",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}
