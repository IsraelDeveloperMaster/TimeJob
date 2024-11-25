package net.developermaster.timejob.core

import android.util.Log
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
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import net.developermaster.timejob.model.Model

class ComponentsFireBase {

    val listaResultadoRetornados = mutableListOf<String>()

    var fechaResultado = ""

    @Composable
    fun Salvar() {

        var fecha by remember { mutableStateOf("") }
        var horasTrabalhadas by remember { mutableStateOf("") }
        var propinas by remember { mutableStateOf("") }

        val model = Model(

            fecha = fecha, horasTrabalhadas = horasTrabalhadas, propinas = propinas

        )

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center

        ) {

            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = horasTrabalhadas,
                onValueChange = { horasTrabalhadas = it },
                label = { Text("Horas trabajadas") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = propinas,
                onValueChange = { propinas = it },
                label = { Text("Propinas") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(

                onClick = {

                    FirebaseFirestore.getInstance().collection("TimeJob").document().set(model)
                        .addOnSuccessListener { sucesso ->

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

        val model = Model(

            fecha = fechaRemember,
            horasTrabalhadas = horasTrabalhadasRemember,
            propinas = propinasRemember

        )

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center

        ) {

            OutlinedTextField(
                value = fechaRemember,
                onValueChange = { fechaRemember = it },
                label = { Text("Fecha") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = horasTrabalhadasRemember,
                onValueChange = { horasTrabalhadasRemember = it },
                label = { Text("Horas trabajadas") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = propinasRemember,
                onValueChange = { propinasRemember = it },
                label = { Text("Propinas") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

          Button(
              shape = (),

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

                                listaResultadoRetornados += (" id: $idRetornado \n Fecha: $fechaDadosRetornados \n Propinas: $propinasDadosRetornados \n Horas Trabajadas: $horasTrabajadasadosRetornados \n \n ")

                                Log.d("firebase", " id: $idRetornado \n Fecha: $fechaDadosRetornados \n Propinas: $propinasDadosRetornados \n Horas Trabajadas: $horasTrabajadasadosRetornados \n \n ")


                                fechaResultado = fechaDadosRetornados.toString()

                            }
                        }
                    }
                },

                modifier = Modifier.fillMaxWidth()


            ) {

                Text("Listar")

                Text(listaResultadoRetornados.toString())
            }


        }
    }

    @Composable
    fun Listar() {

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center

        ) {

            listaResultadoRetornados.forEach { lista ->

                Text(lista)
            }
        }
    }
}