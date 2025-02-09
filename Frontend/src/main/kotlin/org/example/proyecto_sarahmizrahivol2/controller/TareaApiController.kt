package org.example.proyecto_sarahmizrahivol2.controller

import org.example.proyecto_sarahmizrahivol2.entity.Tarea
import org.example.proyecto_sarahmizrahivol2.repository.TareaRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tareas")
class TareaApiController(private val tareaRepository: TareaRepository) {

    @GetMapping
    fun listarTareas(): List<Tarea> = tareaRepository.findAll()

    @PostMapping
    fun crearTarea(@RequestBody tarea: Tarea): ResponseEntity<Tarea> {
        val nuevaTarea = tareaRepository.save(tarea)
        return ResponseEntity.ok(nuevaTarea)
    }

    @PutMapping("/{id}")
    fun actualizarTarea(@PathVariable id: Long, @RequestBody tareaActualizada: Tarea): ResponseEntity<Tarea> {
        return tareaRepository.findById(id).map { tarea ->
            val tareaModificada = tarea.copy(
                titulo = tareaActualizada.titulo,
                descripcion = tareaActualizada.descripcion,
                completado = tareaActualizada.completado
            )
            ResponseEntity.ok(tareaRepository.save(tareaModificada))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun eliminarTarea(@PathVariable id: Long): ResponseEntity<Void> {
        return tareaRepository.findById(id).map { tarea ->
            tareaRepository.delete(tarea)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}
