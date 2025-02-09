package com.example.proyecto_smr


// Rutas para el navHost
sealed class Rutas(val ruta: String) {
    object Progress : Rutas("progress")
    object TareasLista : Rutas("tareasLista")
    object CrearTareas : Rutas("crearTareas")
    object TareaDetalle : Rutas("tareaDetalle/{tareaId}")
}
