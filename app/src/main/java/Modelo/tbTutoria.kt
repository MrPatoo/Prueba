package Modelo

import java.sql.Blob

data class tbTutoria(
    val idTutoria: Number,
    val nombre_tutoria: String,
    val imagen_tutoria: Blob,
    val idTutorado: Number,
    val idTutor: Number

)
