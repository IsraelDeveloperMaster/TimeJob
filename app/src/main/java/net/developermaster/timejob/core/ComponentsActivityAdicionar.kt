package net.developermaster.timejob.core

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class ComponentsActivityAdicionar {

    @Composable
    fun TextoCabecalhoActivityAdicionar() {

        Text(
            modifier = Modifier.padding(start = 100.dp),

            text = "Adicione sus Actividads",//todo texto
            color = Color.Black,//todo cor preta
        )
    }

    @Composable
    fun Data() {

        val context = LocalContext.current

        var texto by remember { mutableStateOf("") }

        OutlinedTextField( //todo caixa de texto com borda

            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(all = 8.dp),

            value = texto,//todo valor do texto

            onValueChange = { textoDigitado ->

                texto = textoDigitado//todo valor do texto digitado na variavel texto

                Toast.makeText(context, texto, Toast.LENGTH_SHORT).show()
            },

            label = {

                Text(text = "Fecha") //todo texto do label
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
    private fun HorasTrabalhadas() {

        val context = LocalContext.current

        var texto by remember { mutableStateOf("") }

        OutlinedTextField( //todo caixa de texto com borda

            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(all = 8.dp),

            value = texto,//todo valor do texto

            onValueChange = { textoDigitado ->

                texto = textoDigitado//todo valor do texto digitado na variavel texto

                Toast.makeText(context, texto, Toast.LENGTH_SHORT).show()
            },

            label = {

                Text(text = "Horas trabajadas") //todo texto do label
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
    fun ButtonAdicionar() {

        val context = LocalContext.current

        Button(modifier = Modifier.padding(start = 150.dp), onClick = {

            Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show()

        }) {

            Text(text = "Ok")

            Spacer(modifier = Modifier.width(10.dp))

            Icon(

                imageVector = Icons.Default.Favorite, contentDescription = null, tint = Color.Red
            )
        }


    }


}