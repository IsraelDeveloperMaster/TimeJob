package net.developermaster.timejob.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import net.developermaster.timejob.core.ComponentsFireBase
import net.developermaster.timejob.core.ComponentsMainActivity
import net.developermaster.timejob.model.ModelTimeJob
import net.developermaster.timejob.view.ui.theme.TimeJobTheme

data class Item(val name: String = "", val description: String = "")

class MainActivity : ComponentActivity() {


    val listaResultadoRetornados = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeJobTheme {

                //todo esqueleto do app
                Scaffold()

//                ListItems()


            }//todo fim do tema
        }//todo fim do setContent
    }//todo fim do onCreate

    @Composable
    fun Scaffold() {

        Scaffold(

            topBar = {

                //                TopBar()
            },

            bottomBar = {

                ComponentsMainActivity().BottomBar()

            },

            floatingActionButton = {

                ComponentsMainActivity().Fab()
            },

            snackbarHost = {

//                TextoExemplo()

            },

//            containerColor = Color.Black,//todo cor preta do container do scaffold

        ) { pappdingInterno ->


            Home(Modifier.padding(pappdingInterno))//todo chamando a funcao Home com o padding interno

//            ListItems()//chamando a funcao ListItems

//            ComponentsFireBase().ListarTodos()

        }
    }

    @Composable
    fun Home(modifier: Modifier) {

        Column(
            modifier = modifier //todo modificador
                .fillMaxWidth()//todo largura

        ) {

            Row{

                ComponentsMainActivity().ImagemPerfil() //Row imagem perfil

            }

            LazyColumn {

                //todo lista
                items(1) {

//                    ComponentsFireBase().ListarTodos()

                    ComponentsFireBase().ListarTodos()

                }

            }//LazyColumn
        }
    }

/*    @Composable
    fun ListItems() {

        var items by remember { mutableStateOf(emptyList<Item>()) }

        // Fetch data from Firestore
        LaunchedEffect(true) {
            val db = FirebaseFirestore.getInstance()
            db.collection("TimeJob").orderBy("fecha", Query.Direction.ASCENDING).get()
                .addOnSuccessListener { documents ->

                    items = documents.map { it.toObject(Item::class.java) }

                    Log.d("firebase", "Sucesso Items $items")

                }.addOnFailureListener { exception ->

                    Log.d("firebase", "Erro $exception")
                    // Handle error
                }
        }

        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(items) { item ->
                ItemRow(item)
            }
        }
    }

    @Composable
    fun Listar1() {

        var items by remember { mutableStateOf(emptyList<ModelTimeJob>()) }

        // Fetch data from Firestore
        FirebaseFirestore.getInstance().collection("TimeJob")
            .orderBy("fecha", Query.Direction.ASCENDING).get()

            .addOnSuccessListener { documents ->

                items = documents.map { itensRetornadosMapeados ->

                    itensRetornadosMapeados.toObject(ModelTimeJob::class.java)
                }

                Log.d("firebase", "Sucesso Items: $items")

            }.addOnFailureListener { exception ->

                Log.d("firebase", "Erro:  $exception")
            }


        *//*        LazyColumn(
                    modifier = Modifier.padding(16.dp)
                ) {
                    items(items) { item ->
                        ItemRowModelTimeJob(item.toString())
                    }
                }*//*
    }

    @Composable
    fun Listar2() {

        val listaDeDadosRetornadas = FirebaseFirestore.getInstance().collection("TimeJob")

        listaDeDadosRetornadas.addSnapshotListener { dadosRetornados, error ->

            val listaRetornada = dadosRetornados?.documents//todo document

            listaRetornada?.forEach { documents ->

//                listaResultado.isEmpty()

                val dados = documents?.data

                if (dados != null) {

                    val idRetornado = documents.id
                    val fechaDadosRetornados = dados["fecha"]
                    val horasTrabajadasadosRetornados = dados["horasTrabalhadas"]
                    val propinasDadosRetornados = dados["propinas"]

                    listaResultadoRetornados += (" id: $idRetornado \n Fecha: $fechaDadosRetornados \n Propinas: $propinasDadosRetornados \n Horas Trabajadas: $horasTrabajadasadosRetornados \n \n ")

                    Log.d(
                        "firebase",
                        " id: $idRetornado \n Fecha: $fechaDadosRetornados \n Propinas: $propinasDadosRetornados \n Horas Trabajadas: $horasTrabajadasadosRetornados \n \n "
                    )


//                    fechaRemember = fechaDadosRetornados.toString()
//                    horasTrabalhadasRemember = horasTrabajadasadosRetornados.toString()
//                    propinasRemember = propinasDadosRetornados.toString()
                }
            }
        }


        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(listaResultadoRetornados) { item ->
                ItemRowModelTimeJob(item)
            }
        }

    }

    @Composable
    fun ItemRowModelTimeJob(dataClass: String) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = dataClass)
        }
    }*/

    @Composable
    fun ItemRow(dataClass: Item) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = dataClass.name)
            Text(text = dataClass.description)
        }
    }




    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        TimeJobTheme {
            Home(Modifier.fillMaxSize())
        }
    }
}