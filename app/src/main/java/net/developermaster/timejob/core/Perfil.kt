package net.developermaster.timejob.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.developermaster.timejob.R

class Perfil {

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

            painter = painterResource(R.drawable.ic_launcher_foreground), //todo imagem
            contentDescription = null, //todo conteudo da imagem
            contentScale = ContentScale.Crop)
    }

}
