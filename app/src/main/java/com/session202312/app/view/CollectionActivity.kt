package com.session202312.app.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.session202312.app.R
import com.session202312.app.adapter.RecyclerViewAdapter
import com.session202312.app.db.PokemonDB
import com.session202312.app.model.PokemonInfo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class CollectionActivity : AppCompatActivity() {
    private lateinit var closeBtn: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapter
    private var pokemonList: MutableList<PokemonInfo>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        closeBtn = findViewById(R.id.close_btn)
        recyclerView = findViewById(R.id.recyclerview)

        closeBtn.setOnClickListener {
            finish()
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(application, 3)
        adapter = RecyclerViewAdapter(applicationContext, pokemonList)
        recyclerView.adapter = adapter

        getPokemons()
    }

    @SuppressLint("CheckResult")
    private fun getPokemons() {
        PokemonDB.getInstance()!!.pokemonDao().getAllPokemon()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapter.updateList(it)
            }
    }

    companion object {
        fun buildIntent(context: Context): Intent {
            val i = Intent(context, CollectionActivity::class.java)
            return i
        }
    }
}