package com.example.testshowapp.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testshowapp.common.Common
import com.example.testshowapp.models.Show

@Database(entities = [Show::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RoomDB : RoomDatabase() {
    abstract fun FavoritesDao(): FavoritesDao

    companion object {
        private val DATABASE_NAME: String = "TvShowsAppDatabase"

        @Volatile
        private var database: RoomDB? = null

        //Create database instance
         fun getDatabaseInstance(context: Context): RoomDB {
            val tempInstance = database
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                database = instance
                return instance
            }
        }
    }

}