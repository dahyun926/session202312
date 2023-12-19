package com.session202312.app.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.session202312.app.db.PokemonDB
import com.session202312.app.model.PokemonInfo
import com.session202312.app.network.RetrofitService
import com.session202312.app.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class PokemonViewModel @Inject constructor(private val repository: PokemonRepository) :
    ViewModel() {
    val pokemon: MutableLiveData<PokemonInfo> = MutableLiveData()

    init {
        getRandomPokemon()
    }

    @SuppressLint("CheckResult")
    fun getRandomPokemon() {
        val id = Random.nextInt(1, 1011)
        repository.getRandomPokemon(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ pokemon ->
                insertPokemon(pokemon)
                this.pokemon.value = pokemon
            }) {
                Log.d("Error!", "" + it.message)
            }
    }

    private fun insertPokemon(pokemon: PokemonInfo) {
        repository.insertPokemon(pokemon)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}