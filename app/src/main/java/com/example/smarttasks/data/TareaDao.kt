package com.example.smarttasks.data

import androidx.room.*
import com.example.smarttasks.model.Tarea
import kotlinx.coroutines.flow.Flow

@Dao
interface TareaDao {

    @Query("SELECT * FROM tareas ORDER BY fechaCreacion DESC")
    fun getAllTareas(): Flow<List<Tarea>>

    @Query("SELECT * FROM tareas WHERE completada = 0 ORDER BY fechaCreacion DESC")
    fun getTareasPendientes(): Flow<List<Tarea>>

    @Query("SELECT * FROM tareas WHERE completada = 1 ORDER BY fechaCompletada DESC")
    fun getTareasCompletadas(): Flow<List<Tarea>>

    @Query("SELECT * FROM tareas WHERE id = :id")
    suspend fun getTareaById(id: Int): Tarea?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTarea(tarea: Tarea)

    @Update
    suspend fun updateTarea(tarea: Tarea)

    @Delete
    suspend fun deleteTarea(tarea: Tarea)

    @Query("UPDATE tareas SET completada = :completada, fechaCompletada = :fecha WHERE id = :id")
    suspend fun marcarComoCompletada(id: Int, completada: Boolean, fecha: Long?)

    @Query("DELETE FROM tareas WHERE completada = 1")
    suspend fun deleteAllCompletadas()
}
