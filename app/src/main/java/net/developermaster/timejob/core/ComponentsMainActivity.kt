package net.developermaster.timejob.core

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.developermaster.timejob.R
import net.developermaster.timejob.view.ActivityAdicionar

class ComponentsMainActivity {

    @Composable
    fun BottomBar() {

        BottomAppBar(
            containerColor = Color.Gray,

            ) {

            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clickable {

                        //toast

                    },//todo clique
            )
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier
                    .padding(start = 130.dp)
                    .clickable {

                        //toast

                    },//todo clique
            )
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier
                    .padding(start = 130.dp)
                    .clickable {

                        //toast

                    },//todo clique
            )
        }
    }

    @Composable
    fun Fab() {

        val context = LocalContext.current

        FloatingActionButton(onClick = {

            Toast.makeText(context, "Clicou no botão", Toast.LENGTH_SHORT).show()

            //todo ação ao clicar no botão
            val intent = Intent(context, ActivityAdicionar::class.java)
            context.startActivity(intent)

        }) {

            Icon(
                imageVector = Icons.Filled.Add, contentDescription = "Localized description"
            )
        }
    }

    @Composable
    fun CaixaDeTextoOutLineTextField() {

        var texto by remember { mutableStateOf("") }

        OutlinedTextField( //todo caixa de texto com borda

            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(all = 8.dp),

            value = texto,//todo valor do texto

            onValueChange = { textoDigitado ->

                texto = textoDigitado//todo valor do texto digitado na variavel texto


            },

            label = {

                Text(text = "Caixa de texto") //todo texto do label
            },

            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.ThumbUp,//todo icone
                    contentDescription = null, modifier = Modifier.width(50.dp),//todo largura
                    tint = Color.Blue//todo cor azul da borda
                )
            },

            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Favorite,//todo icone
                    contentDescription = null, modifier = Modifier.width(50.dp),//todo largura
                    tint = Color.Blue//todo cor azul da borda
                )
            })
    }

    @Composable
    fun ImagemPerfil() {

        Image(modifier = Modifier
            .clickable {
//                mensagemToast("Clicado na imagem")
            }
            .size(60.dp) //todo tamanho da imagem
            .clip(CircleShape)
            .border(2.dp, Color.Black, CircleShape)
            .background(Color.White),

            painter = painterResource(R.drawable.vtcgestion), //todo imagem
            contentDescription = null, //todo conteudo da imagem
            contentScale = ContentScale.Crop)
    }

}





