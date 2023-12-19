package com.session202312.app.repository

import com.session202312.app.db.PokemonDB
import com.session202312.app.model.PokemonInfo
import com.session202312.app.network.RetrofitService

class PokemonRepository {
    private val apiService = RetrofitService.apiService
    private val dao = PokemonDB.getInstance().pokemonDao()

    fun getRandomPokemon(id: Int) = apiService.fetchPokemonInfo(id)

    fun insertPokemon(pokemon: PokemonInfo) = dao.insert(pokemon)
}