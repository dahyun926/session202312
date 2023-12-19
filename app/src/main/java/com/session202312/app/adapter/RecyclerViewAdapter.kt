package com.session202312.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.session202312.app.databinding.ItemPokemonBinding
import com.session202312.app.model.PokemonInfo

class RecyclerViewAdapter(private val mContext: Context, mList: MutableList<PokemonInfo>?) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private var mList: MutableList<PokemonInfo>?
    private var binding: ItemPokemonBinding? = null

    init {
        this.mList = mList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(mContext)
        binding = ItemPokemonBinding.inflate(inflater, parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding.number.text = "No. ${mList!![position].num}"
        holder.itemBinding.name.text = mList!![position].koName
        ViewAdapter.glide(holder.itemBinding.image, mList!![position].getImageUrl())
    }

    override fun getItemCount(): Int {
        return if (mList == null) 0 else mList!!.size
    }

    inner class ViewHolder(itemBinding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(itemBinding.getRoot()) {
        internal val itemBinding: ItemPokemonBinding

        init {
            this.itemBinding = itemBinding
        }
    }

    fun updateList(updatedList: MutableList<PokemonInfo>?) {
        mList = updatedList
        notifyDataSetChanged()
    }

    fun getResultAt(position: Int): PokemonInfo {
        return mList!![position]
    }
}
