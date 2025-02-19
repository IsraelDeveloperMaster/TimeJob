package net.developermaster.timejob.model


sealed class ModelScreens(val route: String) {

    //rota detalhe
    data object DetalheScreenObject : ModelScreens("detalheScreen")

    //rota add
    data object AddScreenObject : ModelScreens("addScreen")

    //rota relatorio
    data object RelatorioScreenObject : ModelScreens("relatorioScreen")

    //rota relatorio propinas
    data object PropinaScreenObject : ModelScreens("propinaScreen")

    //rota update
    data object UpdateScreenObject : ModelScreens("updateScreen")

    //rota delete
    data object DeleteScreenObject : ModelScreens("deleteScreen")

    //rota meses
    data object MainScreenObject : ModelScreens("mainScreen")

    //rota select mes
    data object SelectMesScreenObject : ModelScreens("selectMesScreen")

    //rota listar todos
    data object ListarTodosScreenObject : ModelScreens("listarTodosScreen")




}