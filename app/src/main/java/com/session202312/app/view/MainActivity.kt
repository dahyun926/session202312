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
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.palette.graphics.Palette
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.session202312.app.R
import com.session202312.app.adapter.ViewAdapter
import com.session202312.app.databinding.ActivityMainBinding
import com.session202312.app.db.PokemonDB
import com.session202312.app.model.PokemonInfo
import com.session202312.app.network.RetrofitService
import com.session202312.app.repository.PokemonRepository
import com.session202312.app.viewmodel.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.random.Random

//TODO Hilt를 활용한 의존성 주입
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: PokemonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.vm = vm

        vm.pokemon.observe(this, Observer { pokemon ->
            uiUpdate(pokemon)
        })

        binding.refreshBtn.setOnClickListener {
            refresh()
        }
        binding.collection.setOnClickListener {
            startActivity(CollectionActivity.buildIntent(applicationContext))
        }
    }

    private fun uiUpdate(pokemon: PokemonInfo) {
        setBgColor(pokemon.getImageUrl())
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
                            binding.root.setBackgroundColor(color)
                            binding.number.backgroundTintList =
                                ColorStateList.valueOf(color)
                            window.statusBarColor = color
                            window.navigationBarColor = color
                        }
                    }
                }
            })
    }

    private fun refresh() {
        vm.getRandomPokemon()
    }
}