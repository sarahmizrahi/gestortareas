package org.example.proyecto_sarahmizrahivol2.controller

import org.example.proyecto_sarahmizrahivol2.entity.Tarea
import org.example.proyecto_sarahmizrahivol2.repository.TareaRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

/*@RestController
@RequestMapping("/tareas")
class TareaController(private val tareaRepository: TareaRepository) {

    @GetMapping
    fun listarTareas(): List<Tarea> = tareaRepository.findAll()

    @PostMapping
    fun crearTarea(@RequestBody tarea: Tarea): Tarea = tareaRepository.save(tarea)

    @PutMapping("/{id}")
    fun actualizarTarea(@PathVariable id: Long, @RequestBody tareaActualizada: Tarea): ResponseEntity<Tarea> {
        return tareaRepository.findById(id).map { existingTask ->
            val taskToUpdate = existingTask.copy(
                titulo = tareaActualizada.titulo,
                descripcion = tareaActualizada.titulo,
                completado = tareaActualizada.completado
            )
            ResponseEntity.ok(tareaRepository.save(taskToUpdate))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun eliminarTarea(@PathVariable id: Long): ResponseEntity<Void> {
        return tareaRepository.findById(id).map { tarea ->
            tareaRepository.delete(tarea)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}*/

@Controller
@RequestMapping("/tareas")
class TareaController(private val tareaRepository: TareaRepository) {

    @GetMapping
    fun listarTareas(model: Model): String {
        model.addAttribute("tareas", tareaRepository.findAll())
        return "listarTareas"  // 🔹 Devuelve el nombre del archivo en /templates
    }

    @GetMapping("/crear")
    fun mostrarFormularioCrear(model: Model): String {
        model.addAttribute("tarea", Tarea())
        return "crearTarea"
    }

    @PostMapping
    fun crearTarea(@ModelAttribute tarea: Tarea): String {
        tareaRepository.save(tarea)
        return "redirect:/tareas"
    }

    @GetMapping("/editar/{id}")
    fun mostrarFormularioActualizar(@PathVariable id: Long, model: Model): String {
        val tarea = tareaRepository.findById(id).orElse(null)
        model.addAttribute("tarea", tarea)
        return "actualizarTarea"
    }

    @PostMapping("/actualizar/{id}")
    fun actualizarTarea(@PathVariable id: Long, @ModelAttribute tareaActualizada: Tarea): String {
        tareaRepository.findById(id).ifPresent { tarea ->
            val tareaModificada = tarea.copy(
                titulo = tareaActualizada.titulo,
                descripcion = tareaActualizada.descripcion,
                completado = tareaActualizada.completado
            )
            tareaRepository.save(tareaModificada)
        }
        return "redirect:/tareas"
    }

    @GetMapping("/eliminar/{id}")
    fun eliminarTarea(@PathVariable id: Long): String {
        tareaRepository.deleteById(id)
        return "redirect:/tareas"
    }
}
