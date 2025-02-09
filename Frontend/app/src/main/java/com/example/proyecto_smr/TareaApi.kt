package com.example.proyecto_smr

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface TareaApi {

    // para obtener todas las tareas
    @GET("/api/tareas")
    // suspend porque realiza una solicitud asíncrona
    suspend fun listarTareas(): List<Tarea>

    // para crear una nueva tarea
    @POST("/api/tareas")
    // toma un objeto "Tarea" como cuerpo de la solicitud y devuelve una tarea creada
    suspend fun crearTarea(@Body tarea: Tarea): Tarea

    // para actualizar una tarea existente
    @PUT("/api/tareas/{id}")
    // El "id "de la tarea es parte de la URL (path parameter), y la tarea a actualizar se pasa en el cuerpo de la solicitud
    suspend fun actualizarTarea(@Path("id") id: Long?, @Body tarea: Tarea): Tarea

    // para eliminar una tarea
    @DELETE("/api/tareas/{id}")
    // El "id" de la tarea se pasa como un parámetro en la URL
    suspend fun eliminarTarea(@Path("id") id: Long): Response<Void>
}