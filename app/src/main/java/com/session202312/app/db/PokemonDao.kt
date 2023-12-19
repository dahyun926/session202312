package com.session202312.app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.session202312.app.model.PokemonInfo
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemons")
    fun getAllPokemon(): Flowable<MutableList<PokemonInfo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(pokemon: PokemonInfo): Single<Long>
}