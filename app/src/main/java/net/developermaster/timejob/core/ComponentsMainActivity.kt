package net.developermaster.timejob.core

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.developermaster.timejob.R
import net.developermaster.timejob.view.ActivityAdicionar
import net.developermaster.timejob.view.ActivityRelatorio
import net.developermaster.timejob.view.MainActivity

class ComponentsMainActivity {

    @Composable
    fun ScaffoldFuncion() {

        Scaffold(

            topBar = {

                //                TopBar()
            },

            bottomBar = {

                BottomBar()

            },

            floatingActionButton = {

                ComponentsMainActivity().Fab()
            },

            snackbarHost = {

//                TextoExemplo()

            },

//            containerColor = Color.Black,//todo cor preta do container do scaffold

        ) { pappdingInterno ->


            Home(modifier = Modifier.padding(pappdingInterno))//.padding(pappdingInterno))//todo chamando a funcao Home com o padding interno
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

                ComponentsMainActivity().TextoTitulo() //todo texto titulo
            }

            LazyColumn {

                //todo lista
                items(1) {

                    ComponentsFireBase().ListarTodos()
                }
            }//LazyColumn
        }
    }

    @Composable
    fun ImagemPerfil() {

        Image(modifier = Modifier
            .padding(start = 16.dp, top = 16.dp)
            .clickable {
//                mensagemToast("Clicado na imagem")
            }
            .size(60.dp) //todo tamanho da imagem
            .clip(CircleShape)
            .border(2.dp, Color.Black, CircleShape)
            .background(Color.White),

            painter = painterResource(R.drawable.timejob), //todo imagem
            contentDescription = null, //todo conteudo da imagem
            contentScale = ContentScale.Crop)
    }

    @Composable
    fun TextoTitulo() {
        Text(
            modifier = Modifier
                .padding(top = 30.dp, start = 8.dp), //todo padding top) ,//todo padding top
//                .border(2.dp, Color.Yellow, CutCornerShape(20)),//todo borda amarela circular

            text = "Time Job",//todo texto
            color = Color.Blue,//todo cor vermelha
            fontSize = 25.sp,//todo tamanho da fonte
            fontFamily = FontFamily.SansSerif,//todo tipo de fonte
        )

    }

    @Composable
    fun Box() {
        Box(Modifier.background(color = Color.Gray)) {
            Box(
                Modifier
                    .padding(start = 20.dp, top = 30.dp, end = 20.dp, bottom = 30.dp)
                    .size(50.dp)
                    .background(Color.Blue)
            )
        }
    }

    @Composable
    fun BottomBar() {

        val context = LocalContext.current

        BottomAppBar(
            containerColor = Color.Gray,

            ) {

            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .padding(start = 30.dp)
                    .clickable {

                        //ação clicar
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    },//todo clique
            )
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .padding(start = 130.dp)
                    .clickable {

                        //ação clicar
                        val intent = Intent(context, ActivityRelatorio::class.java)
                        context.startActivity(intent)

                    },//todo clique
            )
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .padding(start = 130.dp)
                    .clickable {

                        //ação clicar
                        val intent = Intent(context, ActivityAdicionar::class.java)
                        context.startActivity(intent)
                    },//todo clique
            )
        }
    }

    @Composable
    fun Fab() {

        val context = LocalContext.current

        FloatingActionButton(onClick = {

            //ação clicar no botão Fab
            val intent = Intent(context, ActivityAdicionar::class.java)
            context.startActivity(intent)

            //navigation compose 2.4.0
//            NavHostController(context).navigate("ActivityAdicionar")

        }) {

            Icon(
                imageVector = Icons.Filled.Add, contentDescription = "Localized description"
            )
        }
    }

}






