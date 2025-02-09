package org.example.proyecto_sarahmizrahivol2.entity

import jakarta.persistence.Entity
import jakarta.persistence.*

//TABLAS
@Entity //representa una tabla
@Table(name = "tareas")   //nombre exacto de la tabla
data class Tarea(
    @Id //clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-incremento
    val id: Long = 0,

    @Column(nullable = false, length = 50) //restricciones
    val titulo: String,

    @Column(nullable = false, length = 100) //restricciones
    val descripcion: String,

   @Column(nullable = false)  //unique evita duplicados
    val completado: Boolean = false,
) {
    //Contructor vacio
    constructor() : this(0, "", "", false)
}