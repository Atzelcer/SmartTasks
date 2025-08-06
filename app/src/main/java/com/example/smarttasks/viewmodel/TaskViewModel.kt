package com.example.smarttasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarttasks.data.SmartTasksDatabase
import com.example.smarttasks.data.TareaRepository
import com.example.smarttasks.model.Tarea
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TareaRepository

    // Estados de la UI
    private val _tareas = MutableStateFlow<List<Tarea>>(emptyList())
    val tareas: StateFlow<List<Tarea>> = _tareas.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _filtroActual = MutableStateFlow(FiltroTareas.TODAS)
    val filtroActual: StateFlow<FiltroTareas> = _filtroActual.asStateFlow()

    init {
        val tareaDao = SmartTasksDatabase.getDatabase(application).tareaDao()
        repository = TareaRepository(tareaDao)

        // Observar cambios en las tareas según el filtro
        viewModelScope.launch {
            _filtroActual.collectLatest { filtro ->
                val flowToCollect = when (filtro) {
                    FiltroTareas.TODAS -> repository.getAllTareas()
                    FiltroTareas.PENDIENTES -> repository.getTareasPendientes()
                    FiltroTareas.COMPLETADAS -> repository.getTareasCompletadas()
                }

                flowToCollect.collect { listaTareas ->
                    _tareas.value = listaTareas
                }
            }
        }
    }

    fun agregarTarea(tarea: Tarea) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.insertTarea(tarea)
            _isLoading.value = false
        }
    }

    fun marcarComoCompletada(tarea: Tarea) {
        viewModelScope.launch {
            repository.marcarComoCompletada(tarea)
        }
    }

    fun eliminarTarea(tarea: Tarea) {
        viewModelScope.launch {
            repository.deleteTarea(tarea)
        }
    }

    fun cambiarFiltro(filtro: FiltroTareas) {
        _filtroActual.value = filtro
    }

    fun limpiarCompletadas() {
        viewModelScope.launch {
            repository.deleteAllCompletadas()
        }
    }
}

enum class FiltroTareas {
    TODAS, PENDIENTES, COMPLETADAS
}