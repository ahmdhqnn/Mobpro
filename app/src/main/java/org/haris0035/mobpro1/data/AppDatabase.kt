package org.haris0035.mobpro1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.haris0035.mobpro1.data.dao.CategoryDao
import org.haris0035.mobpro1.data.dao.RecycleBinDao
import org.haris0035.mobpro1.data.dao.TaskDao
import org.haris0035.mobpro1.data.model.Category
import org.haris0035.mobpro1.data.model.RecycleBin
import org.haris0035.mobpro1.data.model.Task
import org.haris0035.mobpro1.util.Converters

@Database(entities = [Task::class, RecycleBin::class, Category::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun recycleBinDao(): RecycleBinDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "task_database"
                ).fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 