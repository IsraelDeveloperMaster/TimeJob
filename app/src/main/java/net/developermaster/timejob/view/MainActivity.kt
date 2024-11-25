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
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.map
import net.developermaster.timejob.core.ComponentsFireBase
import net.developermaster.timejob.core.ComponentsMainActivity
import net.developermaster.timejob.model.Model
import net.developermaster.timejob.view.ui.theme.TimeJobTheme

data class Item(val name: String = "", val description: String = "")

class MainActivity : ComponentActivity() {

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

            ListItems()//chamando a funcao ListItems
        }
    }

    @Composable
    fun Home(modifier: Modifier) {

        Column(
            modifier = modifier //todo modificador
                .fillMaxWidth()//todo largura

        ) {

            Row {
                ComponentsMainActivity().ImagemPerfil()
            }//Row imagem perfil

            Row {
//                TextoPerfil()
            }//Row nome perfil

//            Espaco()

            LazyColumn {


                //todo lista
                items(2) {

                    ComponentsFireBase().ListarTodos()

//                    ComponentsFireBase().Listar()

//                    ListItems()

                }

         /*       items(items) { item ->
                }*/

            }//LazyColumn Caixa de texto
        }
    }

    @Composable
    fun ListItems() {
        var items by remember { mutableStateOf(emptyList<Item>()) }

        // Fetch data from Firestore
        LaunchedEffect(true) {
            val db = FirebaseFirestore.getInstance()
            db.collection("TimeJob")
                .orderBy("propinas", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener { documents ->
                    items = documents.map { it.toObject(Item::class.java) }

                    Log.d("firebase", "$items")
                }
                .addOnFailureListener { exception ->
                    // Handle error
                }
        }

   /*     LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(items) { item ->
                ItemRow(item)
            }
        }*/
    }

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

    @Composable
    fun Listar2() {
         val db = FirebaseFirestore.getInstance()
        val items = db.collection("items").snapshots().map { snapshot ->
            snapshot.documents.map { document ->
                Item(
                    document.getString("name") ?: "",document.getString("description") ?: ""
                )
            }
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