package com.kostas.redone.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DollarInfoDbModel::class], version = 12, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        private val LOCK = Any()
        private var db: AppDatabase? = null
        private const val DB_NAME = "dollar.db"

        fun getInstance(context: Context): AppDatabase {
            db?.let {
                return it
            }
            synchronized(LOCK) {
                db?.let {
                    return it
                }
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()

                db = instance
                return  instance
            }
        }
    }

    abstract fun dollarPriceInfoDao(): DollarInfoDao
}