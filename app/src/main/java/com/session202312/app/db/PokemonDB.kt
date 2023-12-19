package com.session202312.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.session202312.app.BaseApplication
import com.session202312.app.model.PokemonInfo

@Database(entities = [PokemonInfo::class], version = 4, exportSchema = false)
abstract class PokemonDB : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        private var instance: PokemonDB? = null

        @Synchronized
        fun getInstance(): PokemonDB {
            if (instance == null)
                synchronized(PokemonDB::class) {
                    instance = Room.databaseBuilder(
                        BaseApplication.applicationContext(),
                        PokemonDB::class.java,
                        "pokemon-database"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            return instance!!
        }


    }
}