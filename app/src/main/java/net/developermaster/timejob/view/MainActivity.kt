package net.developermaster.timejob.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import net.developermaster.timejob.core.ComponentScaffold
import net.developermaster.timejob.core.Perfil
import net.developermaster.timejob.view.ui.theme.TimeJobTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeJobTheme {

                //todo esqueleto do app
                Scaffold()

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


                ComponentScaffold().BottomBar()

            },

            floatingActionButton = {

                ComponentScaffold().Fab()
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
                Perfil().ImagemPerfil()
            }//Row imagem perfil

            Row {
//                TextoPerfil()
            }//Row nome perfil

//            Espaco()

            LazyColumn {
                //todo lista
                item {

//                    Salvar()
//                    SalvarNoFirestore()

                }
            }//LazyColumn Caixa de texto
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