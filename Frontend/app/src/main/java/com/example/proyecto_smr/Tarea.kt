package com.example.proyecto_smr

data class Tarea(
    val id: Long? = null,
    val titulo: String,
    val descripcion: String,
    val completado: Boolean,
)
