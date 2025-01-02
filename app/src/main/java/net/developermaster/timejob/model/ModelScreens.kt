package net.developermaster.timejob.model


sealed class ModelScreens(val route: String) {

    //rota home
    data object HomeScreensObject : ModelScreens("homeScreen")

    //rota de Gestion
    data object LoginGestionScreenObject : ModelScreens("LoginGestionScreen")
    data object PlantaGestionScreenObject : ModelScreens("PlantaGestionScreen")
    data object Planta1ScreenObject : ModelScreens("Planta1Screen")
    data object Planta2ScreenObject : ModelScreens("Planta2Screen")
    data object Planta0ScreenObject : ModelScreens("Planta0Screen")
    data object ListScreenObject : ModelScreens("ListScreen")
    data object PesquisaScreenObject : ModelScreens("PesquisaScreen")
    data object RelatorioScreenObject : ModelScreens("GestionScreen")
    data object ScaffoldRelatorioScreenObject : ModelScreens("ScaffoldRelatorioScreen")
    data object AddScreenObject : ModelScreens("AddScreen")


    //rota de Limpieza
    data object LoginLimpiezaScreenObject : ModelScreens("LoginLimpiezaScreen")
    data object HomeLimpiezaScreenObject : ModelScreens("HomeLimpiezaScreen")

    //rota de mantenimiento
    data object LoginMantenimientoScreenObject : ModelScreens("LoginMantenimientoScreen")
    data object HomeMantenimientoScreenObject : ModelScreens("HomeMantenimientoScreen")

    //rota firebase
    data object FireBaseSalvarScreenObject : ModelScreens("FireBaseSalvarScreen")
//    data object FireBaseHomeScreenObject : ModelScreens("FireBaseHomeScreen")
    data object FireBaseListarTodosScreenObject : ModelScreens("FireBaseListarTodosScreen")
















    data object MainScreensObject : ModelScreens("mainScreen")
    data object InformationScreensObject : ModelScreens("informationScreen")




}