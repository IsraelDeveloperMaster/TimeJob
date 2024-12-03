package net.developermaster.timejob.core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class ComponentsActivitys {

    @Composable
    fun HomeActivityAdicionar(modifier: Modifier) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding()

        ) {

            ComponentsFireBase().Salvar()
        }
    }

    @Composable
    fun HomeActivityRelatorio() {

        LazyColumn {
            //todo lista
            items(1) {

                ComponentsFireBase().ListarRelatorio()
            }
        }//LazyColumn
    }
}