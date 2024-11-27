package net.developermaster.timejob.model

data class ModelTimeJob(

//    val id: Int,
    val fecha: String,
    val horaEntrada: String,
    val minutoEntrada: String,
    val horaSalida: String,
    val minutoSalida: String,
    val propinas: String,
//    val propinas: String?, //para que o valor seja opcional
)
