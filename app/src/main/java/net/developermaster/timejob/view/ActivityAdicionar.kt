package net.developermaster.timejob.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.developermaster.timejob.core.ComponentsActivityAdicionar
import net.developermaster.timejob.view.ui.theme.TimeJobTheme

class ActivityAdicionar : ComponentActivity() {

//    lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeJobTheme {

                HomeAdicionar(
                    Modifier.fillMaxSize()
                    /*.background(Color.Blue)*/
                )//todo chamando a funcao Home com o padding interno
            }
        }
    }

    @Composable
    fun HomeAdicionar(modifier: Modifier) {

        Column(
            modifier = modifier //todo modificador
                .fillMaxWidth()//todo largura

        ) {

            Column {

                Spacer(modifier = Modifier.height(30.dp))

                ComponentsActivityAdicionar().TextoCabecalhoActivityAdicionar()

                Salvar()

                /*Data()

                Espaco()

                Ganancia()

                Espaco()

                HorasTrabalhadas()

                Espaco()

                EfectivoRecorrido()

                Espaco()

                EfectivoIngresado()

                Espaco()

                Propina()

                ButtonOkActivityAdicionar()*/

            }//Column Caixa de texto
        }
    }

    @Composable
    fun Espaco() {
        Spacer(modifier = Modifier.height(16.dp))
    }

    @Composable
    fun Salvar() {

//        firestore = FirebaseFirestore.getInstance()

        var text by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Enter some text") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(

                onClick = {

                    val data = hashMapOf(
                        "text" to text
                    )
                    /*firestore.collection("texts").add(data)
                        .addOnSuccessListener { documentReference ->
                            println("DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            println("Error adding document: $e")
                        }*/
                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Save to Firestore")

            }
        }
    }

    @Preview(showBackground = true)

    @Composable
    fun GreetingPreview() {
        TimeJobTheme {
            HomeAdicionar(Modifier.fillMaxSize())
        }
    }
}