package net.sebaorrego.evaluacion2.views.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import net.sebaorrego.evaluacion2.R
import net.sebaorrego.evaluacion2.entities.PokemonApi
import java.util.ArrayList

class SearchPokemonAdapter(private var listener: OnItemClickListener) :
    RecyclerView.Adapter<SearchPokemonAdapter.ViewHolder>() {

    internal var pokemons: ArrayList<PokemonApi> = ArrayList()
    internal var pokemonList: ArrayList<PokemonApi> = ArrayList()

    internal fun addItems(items: List<PokemonApi>) {
        pokemons.addAll(items)
        pokemonList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon_api, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listener.let { holder.bind(pokemonList[position], it) }
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var fotoImageView: ImageView = itemView.findViewById(R.id.fotoImageView)
        private var nombreTextView: TextView = itemView.findViewById(R.id.nombreTextView)

        @SuppressLint("SetTextI18n")
        internal fun bind(p: PokemonApi, listener: OnItemClickListener) {

            nombreTextView.text = p.name

            Glide.with(itemView.context)
                .load("http://pokeapi.co/media/sprites/pokemon/" + p.getFoto() + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(fotoImageView)

            itemView.setOnClickListener { listener.onItemClick(p, adapterPosition) }
        }
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                return Filter.FilterResults()
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
                pokemonList.clear()
                val keyword = charSequence.toString()
                if (!keyword.isEmpty()) {
                    val filteredList = ArrayList<PokemonApi>()
                    for (p: PokemonApi in pokemons) {
                        if (p.name?.toLowerCase()!!.contains(keyword)) {
                            filteredList.add(p)
                        }
                    }
                    pokemonList = filteredList

                } else {
                    pokemonList.addAll(pokemons)
                }
                notifyDataSetChanged()
            }
        }
    }


    interface OnItemClickListener {
        fun onItemClick(pokemon: PokemonApi, position: Int)
    }
}