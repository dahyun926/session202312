package com.session202312.app.view

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.palette.graphics.Palette
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.session202312.app.R
import com.session202312.app.adapter.ViewAdapter
import com.session202312.app.db.PokemonDB
import com.session202312.app.model.PokemonInfo
import com.session202312.app.network.RetrofitService
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.random.Random

//TODO 1. 안드로이드 간단 소개
//TODO 2. Activity에서 UI와 비즈니스 로직 코드가 혼용될 때의 문제점
class MainActivity : AppCompatActivity() {
    private lateinit var root: RelativeLayout
    private lateinit var number: TextView
    private lateinit var name: TextView
    private lateinit var image: ImageView
    private lateinit var refreshBtn: ImageView
    private lateinit var collection: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        root = findViewById(R.id.root)
        number = findViewById(R.id.number)
        name = findViewById(R.id.name)
        image = findViewById(R.id.image)
        refreshBtn = findViewById(R.id.refresh_btn)
        collection = findViewById(R.id.collection)

        refreshBtn.setOnClickListener {
            refresh()
        }
        collection.setOnClickListener {
            startActivity(CollectionActivity.buildIntent(applicationContext))
        }
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
                uiUpdate(pokemon)
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

    private fun uiUpdate(pokemon: PokemonInfo) {
        setBgColor(pokemon.getImageUrl())
        number.text = "No. ${pokemon.num}"
        name.text = pokemon.koName
        ViewAdapter.glide(image, pokemon.getImageUrl())
    }

    private fun setBgColor(url: String) {
        Glide.with(applicationContext)
            .asBitmap()
            .load(url)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    Palette.from(resource).generate {
                        var color: Int? = null
                        if (it?.lightMutedSwatch?.rgb != null)
                            color = it.lightMutedSwatch?.rgb
                        if (it?.lightVibrantSwatch?.rgb != null)
                            color = it.lightVibrantSwatch?.rgb
                        if (color != null) {
                            root.setBackgroundColor(color)
                            number.backgroundTintList =
                                ColorStateList.valueOf(color)
                            window.statusBarColor = color
                            window.navigationBarColor = color
                        }
                    }
                }
            })
    }

    private fun refresh() {
        getRandomPokemon()
    }
}