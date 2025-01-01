package net.developermaster.navigationnavcontrollerjetpackcompose.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import net.developermaster.timejob.screens.FireBaseSalvar
import net.developermaster.timejob.screens.HomeLimpiezaScreen
import net.developermaster.timejob.screens.HomeMantenimientoScreen
import net.developermaster.timejob.screens.HomeScreen
import net.developermaster.timejob.screens.InformationScreen
import net.developermaster.timejob.screens.LoginGestionScreen
import net.developermaster.timejob.screens.LoginLimpiezaScreen
import net.developermaster.timejob.screens.LoginMantenimientoScreen
import net.developermaster.timejob.screens.MainScreen
import net.developermaster.timejob.screens.FireBaseListarTodosScreen
import net.developermaster.timejob.screens.Planta2Screen
import net.developermaster.timejob.screens.PlantaGestionScreen
import net.developermaster.timejob.model.ModelScreens
import net.developermaster.timejob.screens.ListScreen

@Composable
fun NavigationNavController() {

    //hook que retorna um navController
    val navController = rememberNavController()

    //controlador de navegação que recebe o navController para a rota inicial
    NavHost(navController = navController, startDestination = ModelScreens.MainScreensObject.route) {

        //rota home
        composable(ModelScreens.HomeScreensObject.route) {
            HomeScreen(navController)
        }

        //rota list
        composable(ModelScreens.ListScreenObject.route) {
            ListScreen(navController)
        }

        //rota gestion
        composable(ModelScreens.LoginGestionScreenObject.route) {
            LoginGestionScreen(navController)
        }
        composable(ModelScreens.PlantaGestionScreenObject.route) {
            PlantaGestionScreen(navController)
        }
        composable(ModelScreens.Planta1ScreenObject.route) {
            Planta2Screen(navController)
        }

        //rota limpieza
        composable(ModelScreens.LoginLimpiezaScreenObject.route) {
            LoginLimpiezaScreen(navController)
        }
        composable(ModelScreens.HomeLimpiezaScreenObject.route) {
            HomeLimpiezaScreen(navController)
        }
//        composable(ModelScreens.LimpiezaScreenObject.route) { }

        //rota mantenimiento
        composable(ModelScreens.LoginMantenimientoScreenObject.route) {
            LoginMantenimientoScreen(navController)
        }
        composable(ModelScreens.HomeMantenimientoScreenObject.route) {
            HomeMantenimientoScreen(navController)
        }

        //firebase
        composable(ModelScreens.FireBaseSalvarScreenObject.route) {
            FireBaseSalvar()
        }
        composable(ModelScreens.FireBaseListarTodosScreenObject.route) {
            FireBaseListarTodosScreen(navController)
        }

        //rota informationScreen
        composable(
            ModelScreens.InformationScreensObject.route + "/{nome}" ,
            arguments = listOf(navArgument("nome") {
            type = NavType.StringType

        })) {
            InformationScreen(navController, it.arguments?.getString("nome") ?: "Não informado")
        }

/*        //rota de informationScreen
        composable(ModelScreens.InformationScreensObject.route) {
            InformationScreen(navController, "Não informado")
        }*/

        //rota mainScreen
        composable(ModelScreens.MainScreensObject.route) {
            MainScreen(navController)
        }

        //rota home
        composable(ModelScreens.HomeScreensObject.route) {
            HomeScreen(navController)
        }

        //rota gestion
        composable(ModelScreens.LoginGestionScreenObject.route) {
            LoginGestionScreen(navController)
        }
        composable(ModelScreens.PlantaGestionScreenObject.route) {
            PlantaGestionScreen(navController)
        }
        composable(ModelScreens.Planta1ScreenObject.route) {
            Planta2Screen(navController)
        }

        //rota limpieza
        composable(ModelScreens.LoginLimpiezaScreenObject.route) {
            LoginLimpiezaScreen(navController)
        }
        composable(ModelScreens.HomeLimpiezaScreenObject.route) {
            HomeLimpiezaScreen(navController)
        }
//        composable(ModelScreens.LimpiezaScreenObject.route) { }

        //rota mantenimiento
        composable(ModelScreens.LoginMantenimientoScreenObject.route) {
            LoginMantenimientoScreen(navController)
        }
        composable(ModelScreens.HomeMantenimientoScreenObject.route) {
            HomeMantenimientoScreen(navController)
        }

        //firebase
        composable(ModelScreens.FireBaseSalvarScreenObject.route) {
            FireBaseSalvar()
        }
        composable(ModelScreens.FireBaseListarTodosScreenObject.route) {
            FireBaseListarTodosScreen(navController)
        }

        //rota informationScreen
        composable(ModelScreens.InformationScreensObject.route + "/{nome}" ,
            arguments = listOf(navArgument("nome") {
                type = NavType.StringType

            })) {
            InformationScreen(navController, it.arguments?.getString("nome") ?: "Não informado")
        }

        /*        //rota de informationScreen
                composable(ModelScreens.InformationScreensObject.route) {
                    InformationScreen(navController, "Não informado")
                }*/

        //rota mainScreen
        composable(ModelScreens.MainScreensObject.route) {
            MainScreen(navController)
        }

    }

}