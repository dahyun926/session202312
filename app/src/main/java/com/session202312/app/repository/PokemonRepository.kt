package com.session202312.app.repository

import com.session202312.app.db.PokemonDao
import com.session202312.app.model.PokemonInfo
import com.session202312.app.network.ApiService
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val apiService: ApiService, private val dao: PokemonDao) {

    fun getRandomPokemon(id: Int) = apiService.fetchPokemonInfo(id)

    fun insertPokemon(pokemon: PokemonInfo) = dao.insert(pokemon)
}