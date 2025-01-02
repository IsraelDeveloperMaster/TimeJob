package net.developermaster.timejob.model

val listaResultadoRetornados = mutableListOf<String>()

data class ModelFireBase(

//    val id: String,
    val fecha: String,
    val planta: String,
    val apartamento: String,
    val situacao: String,// limpo ou sujo
    val chica: String, //que logo se vai carregar em sua home
    val sabanas: String,
    val comentario: String,
    val inventarioLimpieza: String,

    //manutencao
    val mantenimento: String,
    val foto: String,
    val comentarioMantenimento: String,



    )

val modelFireBase = (1..10).map {
    ModelFireBase(
        fecha = listaResultadoRetornados.toString(),
        planta = "$it",
        apartamento = "$it",
        situacao = "$it",
        chica = "$it",
        sabanas = "$it",
        comentario = "$it",
        inventarioLimpieza = "$it",
        mantenimento = "$it",
        foto = "$it",
        comentarioMantenimento = "$it",
    )
}



