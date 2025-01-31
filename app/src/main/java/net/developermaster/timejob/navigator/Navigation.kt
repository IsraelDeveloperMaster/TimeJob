package net.developermaster.navigationnavcontrollerjetpackcompose.navigator

import android.R.attr.type
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import net.developermaster.timejob.screens.LoginScreen
import net.developermaster.timejob.model.ModelScreens
import net.developermaster.timejob.screens.AddScreen
import net.developermaster.timejob.screens.DeleteItemDialog
import net.developermaster.timejob.screens.ListarTodos
import net.developermaster.timejob.screens.MesesScreen
import net.developermaster.timejob.screens.PropinaScreen
import net.developermaster.timejob.screens.RelatorioScreen
import net.developermaster.timejob.screens.SelectMesScreen
import net.developermaster.timejob.screens.UpdateItemDetailScreen
import net.developermaster.timejob.screens.UpdateScreen

@Composable
fun NavigationNavController() {

    //navController
    val navController = rememberNavController()

    //navController rota inicial
    NavHost(
        navController = navController,
        startDestination = ModelScreens.MesesScreenObject.route

    ) {

        //////////////////////////////

        composable(ModelScreens.ListarTodosScreenObject.route) {
            ListarTodos(navController)
        }

        composable("updateItem/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            UpdateItemDetailScreen(navController, itemId)
        }

        composable("deleteItem/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            DeleteItemDialog(navController, itemId)
        }

        //////////////////////

        //rota login
        composable(ModelScreens.LoginScreenObject.route) {
            LoginScreen( navController)
        }

        //rota add
        composable(ModelScreens.AddScreenObject.route) {
            AddScreen( navController)
        }

        //rota relatorio
        composable(ModelScreens.RelatorioScreenObject.route) {
            RelatorioScreen(navController)
        }

        //rota relatorio propinas
        composable(ModelScreens.PropinaScreenObject.route) {
            PropinaScreen(navController)
        }

        //rota meses
        composable(ModelScreens.SelectMesScreenObject.route + "/{mes}",
            arguments = listOf(navArgument("mes") {
                type = NavType.StringType
            })

        ) {
            SelectMesScreen(navController, it.arguments?.getString("mes") ?: "")
        }

        //rota select mes
        composable(ModelScreens.MesesScreenObject.route) {
            MesesScreen(navController)
        }

/*
        composable(ModelScreens.PlantaScreenObject.route + "/{planta}",
            arguments = listOf(navArgument("planta") {
                type = NavType.StringType
            })

        ) {
            PlantaScreen(navController, it.arguments?.getString("planta") ?: "")
        }
        */

        //rota update
        composable(
            ModelScreens.UpdateScreenObject.route + "/{idRetornado}/{fechaRetornada}",
            arguments = listOf(

                navArgument("idRetornado") {
                    type = NavType.StringType
                    nullable = true
                },

                navArgument("fechaRetornada") {
                    type = NavType.StringType
                    nullable = true
                },

            )

        ) {
            UpdateScreen( navController,

                idRetornado = it.arguments?.getString("idRetornado") ?: "",
                fechaRetornada = it.arguments?.getString("fechaRetornada") ?: ""
            )
        }
    }
}