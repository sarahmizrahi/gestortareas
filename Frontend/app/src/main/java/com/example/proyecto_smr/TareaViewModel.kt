package com.example.proyecto_smr

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class TareaViewModel : ViewModel() {

    // Lista mutable de tareas, se actualiza con un flujo de estado
    private val _tareas = MutableStateFlow<List<Tarea>>(emptyList())
    // Exposición de la lista como un flujo inmutable
    val tareas: StateFlow<List<Tarea>> get() = _tareas

    // Inicialización: carga las tareas al iniciar el ViewModel
    init {
        viewModelScope.launch {
            // Llama a la Api para obtener todas las tareas
            _tareas.value = RetrofitInstance.apiService.listarTareas()
        }
    }

    //Metodos
    // Función para crear una nueva tarea
    fun crearTarea(tarea: Tarea) {
        viewModelScope.launch {
            try {
                // Llama al API para crear la tarea
                RetrofitInstance.apiService.crearTarea(tarea)
                // Vuelve a cargar las tareas para reflejar la nueva tarea creada
                cargarTareas()
            } catch (e: Exception) {
                // Si ocurre un error, se registra en los logs
                Log.e("TareaViewModel", "Error al crear tarea: ${e.message}")
            }
        }
    }

    // Función para actualizar una tarea existente
    fun actualizarTarea(tarea: Tarea) {
        viewModelScope.launch {
            // Llama al API para actualizar la tarea
            RetrofitInstance.apiService.actualizarTarea(tarea.id, tarea)
            // Actualiza la lista de tareas desde la API
            _tareas.value = RetrofitInstance.apiService.listarTareas()
        }
    }

    // Función para obtener una tarea por su ID específico
    fun listarTareaId(tareaId: Long?): StateFlow<Tarea?> {
        val tarea = MutableStateFlow<Tarea?>(null)
        viewModelScope.launch {
            // Busca la tarea en la lista de tareas cargadas que tenga el ID proporcionado
            tarea.value = _tareas.value.find { it.id == tareaId }
        }
        return tarea
    }

    // Función para eliminar una tarea
    fun eliminarTarea(tarea: Tarea) {
        viewModelScope.launch {
            // Llama al API para eliminar la tarea usando su ID
            tarea.id?.let { RetrofitInstance.apiService.eliminarTarea(it) }
            // Vuelve a cargar todas las tareas después de eliminar una
            _tareas.value = RetrofitInstance.apiService.listarTareas()
        }
    }

    // Función para recargar todas las tareas desde el servidor
    fun cargarTareas() {
        viewModelScope.launch {
            try {
                // Obtiene todas las tareas del API y las asigna al flujo de tareas
                _tareas.value = RetrofitInstance.apiService.listarTareas()
            } catch (e: Exception) {
                // Si ocurre un error, se registra en los logs
                Log.e("TareaViewModel", "Error al cargar tareas: ${e.message}")
            }
        }
    }
}

/*
@HiltViewModel
class TareaViewModel @Inject constructor(
    private val api: TareaApi
) : ViewModel() {

    private val _tareas = MutableStateFlow<List<Tarea>>(emptyList())
    val tareas: StateFlow<List<Tarea>> get() = _tareas

    init {
        cargarTareas()
    }

    fun crearTarea(tarea: Tarea) {
        viewModelScope.launch {
            try {
                api.crearTarea(tarea)
                cargarTareas()
            } catch (e: Exception) {
                Log.e("TareaViewModel", "Error al crear tarea: ${e.message}")
            }
        }
    }

    fun actualizarTarea(tarea: Tarea) {
        viewModelScope.launch {
            try {
                api.actualizarTarea(tarea.id, tarea)
                cargarTareas()
            } catch (e: Exception) {
                Log.e("TareaViewModel", "Error al actualizar tarea: ${e.message}")
            }
        }
    }

    fun listarTareaId(tareaId: Long?): StateFlow<Tarea?> {
        val tarea = MutableStateFlow<Tarea?>(null)
        viewModelScope.launch {
            tarea.value = _tareas.value.find { it.id == tareaId }
        }
        return tarea
    }

    fun eliminarTarea(tarea: Tarea) {
        viewModelScope.launch {
            try {
                tarea.id?.let { api.eliminarTarea(it) }
                cargarTareas()
            } catch (e: Exception) {
                Log.e("TareaViewModel", "Error al eliminar tarea: ${e.message}")
            }
        }
    }

    fun cargarTareas() {
        viewModelScope.launch {
            try {
                _tareas.value = api.listarTareas()
            } catch (e: Exception) {
                Log.e("TareaViewModel", "Error al cargar tareas: ${e.message}")
            }
        }
    }
}
*/