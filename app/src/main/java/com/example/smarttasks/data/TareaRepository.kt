package com.example.smarttasks.data

import com.example.smarttasks.model.Tarea
import kotlinx.coroutines.flow.Flow

class TareaRepository(private val tareaDao: TareaDao) {

    fun getAllTareas(): Flow<List<Tarea>> = tareaDao.getAllTareas()

    fun getTareasPendientes(): Flow<List<Tarea>> = tareaDao.getTareasPendientes()

    fun getTareasCompletadas(): Flow<List<Tarea>> = tareaDao.getTareasCompletadas()

    suspend fun getTareaById(id: Int): Tarea? = tareaDao.getTareaById(id)

    suspend fun insertTarea(tarea: Tarea) = tareaDao.insertTarea(tarea)

    suspend fun updateTarea(tarea: Tarea) = tareaDao.updateTarea(tarea)

    suspend fun deleteTarea(tarea: Tarea) = tareaDao.deleteTarea(tarea)

    suspend fun marcarComoCompletada(tarea: Tarea) {
        val fecha = if (!tarea.completada) System.currentTimeMillis() else null
        tareaDao.marcarComoCompletada(tarea.id, !tarea.completada, fecha)
    }

    suspend fun deleteAllCompletadas() = tareaDao.deleteAllCompletadas()
}
