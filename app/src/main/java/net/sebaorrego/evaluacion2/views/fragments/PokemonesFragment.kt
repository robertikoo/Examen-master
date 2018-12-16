package net.sebaorrego.evaluacion2.views.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import net.sebaorrego.evaluacion2.R
import net.sebaorrego.evaluacion2.context.retrofit.GetApi
import net.sebaorrego.evaluacion2.context.retrofit.interfaces.PokemonInterfaces
import net.sebaorrego.evaluacion2.entities.PokemonRespuestaApi
import net.sebaorrego.evaluacion2.views.adapters.PokemonListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonesFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var pokemonImp: PokemonInterfaces
    lateinit var pokemonListAdapter: PokemonListAdapter

    private var offset: Int = 0
    private var aptoParaCargar: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_pokemones2, container, false)

        bindUI(view)

        return view
    }


    private fun bindUI(view: View) {

        recyclerView = view.findViewById(R.id.recyclerView)
        pokemonListAdapter = PokemonListAdapter(context!!)
        recyclerView.adapter = pokemonListAdapter
        recyclerView.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(context!!, 3)
        recyclerView.layoutManager = layoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    if (aptoParaCargar) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {

                            aptoParaCargar = false
                            offset += 20
                            obtenerDatos(offset)
                        }
                    }
                }
            }
        })

        aptoParaCargar = true
        offset = 0
        obtenerDatos(offset)

    }

    private fun obtenerDatos(offset: Int) {

        pokemonImp = GetApi.api.create(PokemonInterfaces::class.java)
        val pokemonRespuestaCall = pokemonImp.obtenerListaPokemon(20, offset)

        pokemonRespuestaCall.enqueue(object : Callback<PokemonRespuestaApi>{
            override fun onFailure(call: Call<PokemonRespuestaApi>, t: Throwable) {
                aptoParaCargar = true
                Log.e("TAG", " onFailure: " + t.message)
            }

            override fun onResponse(call: Call<PokemonRespuestaApi>, response: Response<PokemonRespuestaApi>) {
                if (response.isSuccessful){
                    val pokemonRespuesta = response.body()
                    val listaPokemon = pokemonRespuesta!!.results

                    pokemonListAdapter.adicionarListaPokemon(listaPokemon!!)
                }else{
                    Log.e("TAG",response.errorBody().toString())
                }
            }

        })


    }


}
