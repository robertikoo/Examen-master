package net.sebaorrego.evaluacion2.views.fragments


import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.DialogFragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import net.sebaorrego.evaluacion2.R
import net.sebaorrego.evaluacion2.context.retrofit.GetApi
import net.sebaorrego.evaluacion2.context.retrofit.interfaces.PokemonInterfaces
import net.sebaorrego.evaluacion2.entities.PokemonApi
import net.sebaorrego.evaluacion2.entities.PokemonRespuestaApi
import net.sebaorrego.evaluacion2.helper.UtilHelper
import net.sebaorrego.evaluacion2.views.adapters.SearchPokemonAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchPokemonDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(title: String): SearchPokemonDialogFragment {
            val f = SearchPokemonDialogFragment()
            val args = Bundle()
            args.putString("title", title)
            f.arguments = args
            return f
        }

    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as ComunicatorImp
    }


    lateinit var toolbar: Toolbar
    lateinit var recyclerView: RecyclerView
    lateinit var pokemonImp: PokemonInterfaces
    private var pokemonSearchAdapter: SearchPokemonAdapter? = null

    private var offset: Int = 0
    private var aptoParaCargar: Boolean = false

    lateinit var title: String
    var listener: ComunicatorImp? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments!!.getString("title")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_search_pokemon, container, false)
        bindUI(view)
        return view
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun bindUI(view: View) {
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.busqueda)
        toolbar.setNavigationIcon(R.drawable.ic_close)
        toolbar.setOnClickListener { v -> dismiss() }
        toolbar.setOnMenuItemClickListener { item ->
            val searchView = item.actionView as SearchView
            searchNombre(searchView)
            true
        }
        toolbar.title = title

        recyclerView = view.findViewById(R.id.recyclerView)
        pokemonSearchAdapter = SearchPokemonAdapter(object : SearchPokemonAdapter.OnItemClickListener {
            override fun onItemClick(pokemon: PokemonApi, position: Int) {
                listener?.filtroRequest(pokemon)
                dismiss()
            }
        })
        recyclerView.adapter = pokemonSearchAdapter
        recyclerView.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(context!!, 3)
        recyclerView.layoutManager = layoutManager
//        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                if (dy > 0) {
//                    val visibleItemCount = layoutManager.childCount
//                    val totalItemCount = layoutManager.itemCount
//                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
//
//                    if (aptoParaCargar) {
//                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
//
//                            aptoParaCargar = false
//                            offset += 20
//                            obtenerDatos(offset)
//                        }
//                    }
//                }
//            }
//        })

        aptoParaCargar = true
//        offset = 0
        obtenerDatos(20)
    }

    private fun obtenerDatos(offset: Int) {

        pokemonImp = GetApi.api.create(PokemonInterfaces::class.java)
        val pokemonRespuestaCall = pokemonImp.obtenerListaPokemon(20, offset)

        pokemonRespuestaCall.enqueue(object : Callback<PokemonRespuestaApi> {
            override fun onFailure(call: Call<PokemonRespuestaApi>, t: Throwable) {
                aptoParaCargar = true
                Log.e("TAG", " onFailure: " + t.message)
            }

            override fun onResponse(call: Call<PokemonRespuestaApi>, response: Response<PokemonRespuestaApi>) {
                if (response.isSuccessful) {
                    val pokemonRespuesta = response.body()
                    val listaPokemon = pokemonRespuesta!!.results

                    pokemonSearchAdapter?.addItems(listaPokemon!!)
                } else {
                    Log.e("TAG", response.errorBody().toString())
                }
            }
        })
    }


    private fun searchNombre(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (pokemonSearchAdapter != null) {
                    pokemonSearchAdapter?.getFilter()?.filter(newText)
                }
                return true
            }
        })

        searchView.setOnCloseListener {
            pokemonSearchAdapter?.notifyDataSetChanged()
            false
        }
    }

    interface ComunicatorImp {
        fun filtroRequest(p: PokemonApi)
    }
}