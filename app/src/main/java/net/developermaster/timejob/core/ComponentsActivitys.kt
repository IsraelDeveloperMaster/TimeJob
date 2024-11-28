package net.developermaster.timejob.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

class ComponentsActivitys {

    @Composable
    fun HomeActivityAdicionar(modifier: Modifier) {

        Column( modifier = modifier.fillMaxWidth().padding()

        ) {

            ComponentsFireBase().Salvar()
        }
    }

    @Composable
    fun HomeActivityRelatorio(modifier: Modifier) {

        LazyColumn() {
            //todo lista
            items(1) { item ->

                ComponentsFireBase().ListarRelatorio()
            }
        }//LazyColumn
    }
}