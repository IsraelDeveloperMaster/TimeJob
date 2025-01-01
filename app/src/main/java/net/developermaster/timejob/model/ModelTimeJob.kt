package net.developermaster.timejob.model

data class ModelTimeJob(

//    val id: Int,
    val fecha: String,
    val horaEntrada: String,
    val minutoEntrada: String,
    val horaSalida: String,
    val minutoSalida: String,
    val propinas: String?, //para que o valor seja opcional
)

val timejobs = (101..201).map {
    ModelTimeJob(
        fecha = it.toString(),
        horaEntrada = "$it",
        minutoEntrada = "https://picsum.photos/200/300?id=$it",
        horaSalida = "https://picsum.photos/200/300?id=$it",
        minutoSalida = "https://picsum.photos/200/300?id=$it",
        propinas = "https://picsum.photos/200/300?id=$it",

    )
}
