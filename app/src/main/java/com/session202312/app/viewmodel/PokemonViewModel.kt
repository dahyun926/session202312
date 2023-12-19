package com.session202312.app.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.session202312.app.db.PokemonDB
import com.session202312.app.model.PokemonInfo
import com.session202312.app.network.RetrofitService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.random.Random

class PokemonViewModel : ViewModel() {
    val pokemon: MutableLiveData<PokemonInfo> = MutableLiveData()

    init {
        getRandomPokemon()
    }

    @SuppressLint("CheckResult")
    fun getRandomPokemon() {
        //Retrofit
        val id = Random.nextInt(1, 1011)
        RetrofitService.apiService.fetchPokemonInfo(id)
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
        //Room
        PokemonDB.getInstance()!!.pokemonDao().insert(pokemon)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

}