package com.session202312.app.network

import com.session202312.app.model.PokemonInfo
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon/{id}")
    fun fetchPokemonInfo(@Path("id") id: Int?): Observable<PokemonInfo>
}