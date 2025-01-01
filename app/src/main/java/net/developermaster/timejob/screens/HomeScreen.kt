package net.developermaster.timejob.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.developermaster.timejob.R
import net.developermaster.timejob.model.ModelScreens

@Composable
fun HomeScreen(navcontroller: NavController) {

    BodyHomeScreen(navcontroller)
}

@Composable
fun BodyHomeScreen(navcontroller: NavController) {

    val context = LocalContext.current

    var enabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(100.dp))

        //box gestion
        Box(
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
                .clip(Shapes().large)
                .background(MaterialTheme.colorScheme.primaryContainer)

        ) {
            Image(modifier = Modifier
                .clickable {

                    navcontroller.navigate(ModelScreens.LoginGestionScreenObject.route)
                    Toast.makeText(context, "Gestion", Toast.LENGTH_SHORT).show()

                }
                .height(300.dp),

                painter = painterResource(id = R.drawable.timejob),
                contentDescription = null, //todo conteudo da imagem
                contentScale = ContentScale.Crop)
        }

        Spacer(modifier = Modifier.height(16.dp))

        //box limpeza
        Box(
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
                .clip(Shapes().large)
                .background(MaterialTheme.colorScheme.primaryContainer)

        ) {
            Image(modifier = Modifier
                .clickable {

                    navcontroller.navigate(ModelScreens.LoginLimpiezaScreenObject.route)
                    Toast.makeText(context, "Limpeza", Toast.LENGTH_SHORT).show()

                }
                .height(300.dp),

                painter = painterResource(id = R.drawable.timejob),
                contentDescription = null, //todo conteudo da imagem
                contentScale = ContentScale.Crop)
        }

        Spacer(modifier = Modifier.height(16.dp))

        //box manutencao
        Box(
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
                .clip(Shapes().large)
                .background(MaterialTheme.colorScheme.primaryContainer)

        ) {
            Image(modifier = Modifier
                .clickable {

                    navcontroller.navigate(ModelScreens.LoginMantenimientoScreenObject.route)
                    Toast.makeText(context, "Mantenimiento", Toast.LENGTH_SHORT).show()

                }
                .height(300.dp),

                painter = painterResource(id = R.drawable.timejob),
                contentDescription = null, //todo conteudo da imagem
                contentScale = ContentScale.Crop)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
