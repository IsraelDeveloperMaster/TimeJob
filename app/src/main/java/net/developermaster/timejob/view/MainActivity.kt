package net.developermaster.timejob.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.developermaster.timejob.core.ComponentsFireBase
import net.developermaster.timejob.core.ComponentsMainActivity
import net.developermaster.timejob.model.ModelTimeJob
import net.developermaster.timejob.view.ui.theme.TimeJobTheme
import java.time.Duration
import java.time.LocalTime
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    val modelTimeJob = ModelTimeJob("", "", "")


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            TimeJobTheme {

                //todo esqueleto do app
                Scaffold()

/*                var startTime by remember { mutableStateOf(TextFieldValue("")) }
                var endTime by remember { mutableStateOf(TextFieldValue("")) }
                var result by remember { mutableStateOf("") }*/


                //Variaveis de tempo
                var tempo: TimeUnit
                val tempo2 = 3600
                val tempo3 = 3600


//                val horas = TimeUnit.MILLISECONDS.toHours(128000) / 60
//                val minutos = TimeUnit.MILLISECONDS.toMinutes(128000) / 60








                val horaInicio = LocalTime.of(7, 0)
                val horaFim = LocalTime.of(19, 30)
                val duracao = Duration.between(horaInicio, horaFim)
                val horas = duracao.toHours()
                val minutos = duracao.toMinutes() % 60.0
                val tempoDeDuracao = horas + (minutos / 60.0)




                Log.i("tempo", "tempo Calculo:  $tempoDeDuracao")




                //Calculos de tempo
//                val calculo1 = tempo2 + tempo3
//                val calculo2 = horas + minutos
//                val calculo3 = horaInicio.plusHours(8)


                //Log
//                Log.i("tempo", "Tempo Calculo 1 = $calculo1")
//                Log.i("tempo", "tempo Calculo 2 =  $calculo2")
//                Log.i("tempo", "tempo Calculo 3 =  $calculo2")
//                Log.i("tempo", "tempo Calculo  =  $tempo")
//                Log.i("tempo", "tempo Calculo 5 =  $tempo")








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
        }
    }

    @Composable
    fun Home(modifier: Modifier) {

        Column(
            modifier = modifier //todo modificador
                .fillMaxWidth()//todo largura

        ) {

            Row {

                ComponentsMainActivity().ImagemPerfil() //Row imagem perfil

            }

            LazyColumn {

                //todo lista
                items(1) { item ->


                    ComponentsFireBase().ListarTodos2()

                }

            }//LazyColumn
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

