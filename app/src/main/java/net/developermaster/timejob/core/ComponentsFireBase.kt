package net.developermaster.timejob.core

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.developermaster.timejob.model.ModelTimeJob

class ComponentsFireBase {

    val listaResultadoRetornados = mutableListOf<String>()


    @Composable
    fun Salvar() {

        var fecha by remember { mutableStateOf("") }
        var horasTrabalhadas by remember { mutableStateOf("") }
        var propinas by remember { mutableStateOf("") }

        val modelTimeJob = ModelTimeJob(

            fecha = fecha, horasTrabalhadas = horasTrabalhadas, propinas = propinas

        )

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center

        ) {

            OutlinedTextField(value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = horasTrabalhadas,
                onValueChange = { horasTrabalhadas = it },
                label = { Text("Horas trabajadas") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = propinas,
                onValueChange = { propinas = it },
                label = { Text("Propinas") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(

                onClick = {

                    FirebaseFirestore.getInstance().collection("TimeJob").document()
                        .set(modelTimeJob).addOnSuccessListener { sucesso ->

//                            mensagemToast("Salvo com sucesso")

                            Log.d("firebase", "Salvo com sucesso")

                        }.addOnFailureListener { erro ->

//                            mensagemToast("Salvo com sucesso ${erro.message}")//

                            Log.d("firebase", "Erro : ${erro.message}")
                        }
                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Salvar")
            }
        }
    }

    @Composable
    fun ListarTodos() {

        var fechaRemember by remember { mutableStateOf("") }
        var horasTrabalhadasRemember by remember { mutableStateOf("") }
        var propinasRemember by remember { mutableStateOf("") }

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center

        ) {

            OutlinedTextField(value = fechaRemember,
                onValueChange = { fechaRemember = it },
                label = { Text("Fecha") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = horasTrabalhadasRemember,
                onValueChange = { horasTrabalhadasRemember = it },
                label = { Text("Horas trabajadas") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = propinasRemember,
                onValueChange = { propinasRemember = it },
                label = { Text("Propinas") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(

                onClick = {

                    val listaDeDadosRetornadas =
                        FirebaseFirestore.getInstance().collection("TimeJob")

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

                                listaResultadoRetornados += (" Fecha: $fechaDadosRetornados \n Horas Trabajadas: $horasTrabajadasadosRetornados \n Propinas: $propinasDadosRetornados ")

                                Log.d(
                                    "firebase",
                                    " id: $idRetornado \n Fecha: $fechaDadosRetornados \n Horas Trabajadas: $horasTrabajadasadosRetornados \n Propinas: $propinasDadosRetornados \n \n "
                                )


                                fechaRemember = fechaDadosRetornados.toString()
                                horasTrabalhadasRemember = horasTrabajadasadosRetornados.toString()
                                propinasRemember = propinasDadosRetornados.toString()

                            }
                        }
                    }
                },

                modifier = Modifier.fillMaxWidth()


            ) {

                Text("Listar")
            }

            Spacer(modifier = Modifier.height(16.dp))

//                        itemsRemember.add(ModelTimeJob(fecha = item.toString()))

            listaResultadoRetornados.forEach { lista ->

                val context = LocalContext.current

                OutlinedTextField(

                    value = lista,
                    onValueChange = { },
                    label = { Text("") },
                    modifier = Modifier.fillMaxWidth(),

                    trailingIcon = {
                        Icon(

                            imageVector = Icons.Default.Create,//icone
                            contentDescription = null,
                            modifier = Modifier
                                .width(50.dp)

                                .clickable {
                                    Toast
                                        .makeText(
                                            context, "Clicou no icone", Toast.LENGTH_SHORT
                                        )
                                        .show()
                                },//clickable

                            tint = Color.Blue,// cor azul da borda
                        )
                    },

                    )

//                Text("$lista") //Texto que será exibido na tela

            }

//            Text("Resultado: $fechaResultado")
        }
    }

    @Composable
    fun ListaResultadoRetornadosTela() {


        val listaDeDadosRetornadas = FirebaseFirestore.getInstance().collection("TimeJob")

        listaDeDadosRetornadas.addSnapshotListener { dadosRetornados, error ->

            val listaRetornada = dadosRetornados?.documents//todo document

            listaRetornada?.forEach { documents ->

            }


            val listaDeDadosRetornadas = FirebaseFirestore.getInstance().collection("TimeJob")

            listaDeDadosRetornadas.addSnapshotListener { dadosRetornados, error ->

                val listaRetornada = dadosRetornados?.documents//todo document

                listaRetornada?.forEach { documents ->

                    val listaDeDadosRetornadas =
                        FirebaseFirestore.getInstance().collection("TimeJob")

                    listaDeDadosRetornadas.addSnapshotListener { dadosRetornados, error ->

                        val listaRetornada = dadosRetornados?.documents//todo document

                        listaRetornada?.forEach { documents ->

                            val dados = documents?.data

                            if (dados != null) {

                                val idRetornado = documents.id
                                val fechaDadosRetornados = dados["fecha"]
                                val horasTrabajadasadosRetornados = dados["horasTrabalhadas"]
                                val propinasDadosRetornados = dados["propinas"]

                                listaResultadoRetornados += (" Fecha: $fechaDadosRetornados \n Horas Trabajadas: $horasTrabajadasadosRetornados \n Propinas: $propinasDadosRetornados ")

                                Log.d(
                                    "firebase",
                                    " id: $idRetornado \n Fecha: $fechaDadosRetornados \n Horas Trabajadas: $horasTrabajadasadosRetornados \n Propinas: $propinasDadosRetornados \n \n "
                                )


                            }

                        }


                    }


                }

            }


        }





        listaResultadoRetornados.forEach { lista ->

            Text("Resultador $lista") //Texto que será exibido na tela
        }

    }

    @Composable
    fun Listar2() {

        Spacer(modifier = Modifier.height(16.dp))

//                        itemsRemember.add(ModelTimeJob(fecha = item.toString()))

        listaResultadoRetornados.forEach { lista ->

            val context = LocalContext.current

            OutlinedTextField(

                value = lista,
                onValueChange = { },
                label = { Text("") },
                modifier = Modifier.fillMaxWidth(),

                trailingIcon = {
                    Icon(

                        imageVector = Icons.Default.Create,//icone
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)

                            .clickable {
                                Toast
                                    .makeText(
                                        context, "Clicou no icone", Toast.LENGTH_SHORT
                                    )
                                    .show()
                            },//clickable

                        tint = Color.Blue,// cor azul da borda
                    )
                },

                )

//                Text("$lista") //Texto que será exibido na tela

        }

        Text("Resultado: $listaResultadoRetornados")

    }


}




