package net.sebaorrego.evaluacion2.views.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import net.sebaorrego.evaluacion2.R
import net.sebaorrego.evaluacion2.entities.PokemonApi
import java.util.ArrayList

class PokemonListAdapter(private var context : Context) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    internal var  dataset: ArrayList<PokemonApi> = ArrayList()
//    private val context: Context

    fun PokemonListAdapter(context: Context){
        this.context = context
        dataset = ArrayList<PokemonApi>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon_api, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = dataset[position]
        holder.nombreTextView.text = p.name

        Glide.with(context)
            .load("http://pokeapi.co/media/sprites/pokemon/" + p.getFoto() + ".png")
            .centerCrop()
            .crossFade()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.fotoImageView)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun adicionarListaPokemon(listaPokemon: ArrayList<PokemonApi>) {
        dataset.addAll(listaPokemon)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val fotoImageView: ImageView = itemView.findViewById(R.id.fotoImageView) as ImageView
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreTextView) as TextView

    }
}
