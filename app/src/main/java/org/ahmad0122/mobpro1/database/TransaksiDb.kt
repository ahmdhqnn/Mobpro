package org.ahmad0122.mobpro1.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import org.ahmad0122.mobpro1.model.Transaksi

@Database(entities = [Transaksi::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TransaksiDb: RoomDatabase() {
    abstract val dao: TransaksiDao

    companion object {
        @Volatile
        private var INSTANCE: TransaksiDb? = null

        fun getInstance(context: Context): TransaksiDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TransaksiDb::class.java,
                        "keuangan.db"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
} 