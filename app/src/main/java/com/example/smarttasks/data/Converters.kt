package com.example.smarttasks.data

import androidx.room.TypeConverter
import com.example.smarttasks.model.PrioridadTarea

class Converters {

    @TypeConverter
    fun fromPrioridad(prioridad: PrioridadTarea): String {
        return prioridad.name
    }

    @TypeConverter
    fun toPrioridad(prioridad: String): PrioridadTarea {
        return PrioridadTarea.valueOf(prioridad)
    }
}
