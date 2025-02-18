package net.developermaster.timejob.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import net.developermaster.timejob.model.ModelScreens
import net.developermaster.timejob.view.AddScreen
import net.developermaster.timejob.view.DeleteItem
import net.developermaster.timejob.view.ListarTodos
import net.developermaster.timejob.view.MainScreen
import net.developermaster.timejob.view.RelatorioScreen
import net.developermaster.timejob.view.PropinaScreen
import net.developermaster.timejob.view.SelectMesScreen
import net.developermaster.timejob.view.UpdateItemDetailScreen2

@Composable
fun NavigationNavController() {

    //navController
    val navController = rememberNavController()

    //navController rota inicial
    NavHost(
        navController = navController, startDestination = ModelScreens.MainScreenObject.route

    ) {

        //rota main
        composable(ModelScreens.MainScreenObject.route) {
            MainScreen(navController)
        }

        //rota listar todos
        composable(ModelScreens.ListarTodosScreenObject.route) {
            ListarTodos(navController)
        }

        //rota add
        composable(ModelScreens.AddScreenObject.route) {
            AddScreen(navController)
        }

        //rota relatorio
        composable(
            ModelScreens.RelatorioScreenObject.route + "/{itemMes}",
            arguments = listOf(navArgument("itemMes") {
                type = NavType.StringType
            })

        ) {
            RelatorioScreen(
                navController,
                // argumentos 1
                it.arguments?.getString("itemMes") ?: ""
            )
        }

        //rota propinas
        composable(
            ModelScreens.PropinaScreenObject.route + "/{itemMes}",
            arguments = listOf(navArgument("itemMes") {
                type = NavType.StringType
            })

        ) {
            PropinaScreen(
                navController,
                // argumentos 1
                it.arguments?.getString("itemMes") ?: ""
            )
        }

        //rota meses
        composable(
            ModelScreens.SelectMesScreenObject.route + "/{mes}",
            arguments = listOf(navArgument("mes") {
                type = NavType.StringType
            })

        ) {
            SelectMesScreen(
                navController, it.arguments?.getString("mes") ?: ""
            )
        }

        //rota update
        composable(ModelScreens.UpdateScreenObject.route + "/{itemId}/{itemMes}",
            arguments = listOf(navArgument("itemId") {
                type = NavType.StringType
            }, navArgument("itemMes") {
                type = NavType.StringType
            })
        ) {
            UpdateItemDetailScreen2(
                navController,
                // argumentos 1
                it.arguments?.getString("itemId") ?: "",
                // argumentos 2
                it.arguments?.getString("itemMes") ?: ""
            )
        }

        //rota delete
        composable(ModelScreens.DeleteScreenObject.route + "/{itemId}/{itemMes}",
            arguments = listOf(navArgument("itemId") {
                type = NavType.StringType
            }, navArgument("itemMes") {
                type = NavType.StringType
            })
        ) {
            DeleteItem(
                navController,
                // argumentos 1
                it.arguments?.getString("itemId") ?: "",
                // argumentos 2
                it.arguments?.getString("itemMes") ?: ""
            )
        }
    }
}