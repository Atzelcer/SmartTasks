package com.example.smarttasks.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.example.smarttasks.model.Tarea

@Database(
    entities = [Tarea::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SmartTasksDatabase : RoomDatabase() {

    abstract fun tareaDao(): TareaDao

    companion object {
        @Volatile
        private var INSTANCE: SmartTasksDatabase? = null

        fun getDatabase(context: Context): SmartTasksDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SmartTasksDatabase::class.java,
                    "smarttasks_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
