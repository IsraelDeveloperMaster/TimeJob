package net.developermaster.timejob.model


data class ModelTimeJob(
    val id: String = "",
    var fecha: String,
    var horaEntrada: String,
    var minutoEntrada: String,
    var horaSalida: String,
    var minutoSalida: String,
    val propinas: String,
    val notas: String = "",

)
