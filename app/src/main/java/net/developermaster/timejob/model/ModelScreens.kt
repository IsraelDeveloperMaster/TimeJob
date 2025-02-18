package net.developermaster.timejob.model


sealed class ModelScreens(val route: String) {

    //rota login
    data object LoginScreenObject : ModelScreens("loginScreen")

    //rota add
    data object AddScreenObject : ModelScreens("addScreen")

    //rota relatorio
    data object RelatorioScreenObject : ModelScreens("relatorioScreen")

    //rota relatorio propinas
    data object PropinaScreenObject : ModelScreens("propinaScreen")

    //rota update
    data object UpdateScreenObject : ModelScreens("updateScreen")

    //rota meses
    data object MesesScreenObject : ModelScreens("mesesScreen")

    //rota select mes
    data object SelectMesScreenObject : ModelScreens("selectMesScreen")

    //rota listar todos
    data object ListarTodosScreenObject : ModelScreens("listarTodosScreen")




























    //rota home
    data object HomeScreensObject : ModelScreens("homeScreen")

    data object PlantaGestionScreenObject : ModelScreens("PlantaGestionScreen")
    data object Planta1ScreenObject : ModelScreens("Planta1Screen")
    data object ListScreenObject : ModelScreens("ListScreen")
    data object PesquisaScreenObject : ModelScreens("PesquisaScreen")
    data object ScaffoldRelatorioScreenObject : ModelScreens("ScaffoldRelatorioScreen")


    data object LoginLimpiezaScreenObject : ModelScreens("LoginLimpiezaScreen")
    data object HomeLimpiezaScreenObject : ModelScreens("HomeLimpiezaScreen")

    data object LoginMantenimientoScreenObject : ModelScreens("LoginMantenimientoScreen")
    data object HomeMantenimientoScreenObject : ModelScreens("HomeMantenimientoScreen")
















    data object MainScreensObject : ModelScreens("mainScreen")
    data object InformationScreensObject : ModelScreens("informationScreen")




}