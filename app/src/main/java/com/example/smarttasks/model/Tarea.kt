package com.example.smarttasks.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tareas")
data class Tarea(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val descripcion: String = "",
    val completada: Boolean = false,
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaCompletada: Long? = null,
    val prioridad: PrioridadTarea = PrioridadTarea.MEDIA
)

enum class PrioridadTarea {
    ALTA, MEDIA, BAJA
}
