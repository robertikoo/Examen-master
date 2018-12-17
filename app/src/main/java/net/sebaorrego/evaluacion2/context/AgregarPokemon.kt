package net.sebaorrego.evaluacion2.context

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_agregar_pokemon.*
import net.sebaorrego.evaluacion2.R
import net.sebaorrego.evaluacion2.entities.Pokemon
import net.sebaorrego.evaluacion2.entities.PokemonApi
import net.sebaorrego.evaluacion2.helper.UtilHelper
import net.sebaorrego.evaluacion2.services.ConexionSQL
import net.sebaorrego.evaluacion2.views.fragments.SearchPokemonDialogFragment
import java.util.*

class AgregarPokemon : AppCompatActivity(), View.OnClickListener, SearchPokemonDialogFragment.ComunicatorImp {

    override fun filtroRequest(p: PokemonApi) {

        nombrePokemon = p.name!!
        num = p.getFoto().toString()

        txtNombrePoke.setText(p.name)
        txtNumero.setText(p.getFoto().toString())

        Glide.with(this)
            .load("http://pokeapi.co/media/sprites/pokemon/" + p.getFoto() + ".png")
            .centerCrop()
            .crossFade()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageViewPokemon)
    }

    @SuppressLint("WrongConstant")
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnAgregarPoke -> {
                if (!nombrePokemon.isEmpty()) {
                    if (!num.isEmpty()) {
                        val pokemon = Pokemon(1, nombrePokemon, num, UtilHelper.getFecha(), 1)
                        conn.insertarPokemon(pokemon)
                        UtilHelper.toastMensaje(this, "Pokemon Guardado")
                        finish()
                    } else {
                        txtNumero.error = "Ingrese numero"
                    }
                } else {
                    txtNombrePoke.error = "Ingrese Pokemon2"
                }
            }
            R.id.buttonSearchPokemon -> {
                val fragmentManager = supportFragmentManager
                val filtroFragment = SearchPokemonDialogFragment.newInstance("Lista de Pokemones")
                val transaction = fragmentManager!!.beginTransaction()
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                transaction.add(android.R.id.content, filtroFragment)
                    .addToBackStack(null).commit()
            }
        }
    }

    lateinit var toolbar: Toolbar
    lateinit var nombrePokemon: String
    lateinit var num: String
    lateinit var fecha: String
    lateinit var conn: ConexionSQL


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_pokemon)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        conn = ConexionSQL(this, null, 1)
        bindUI()
    }

    private fun bindUI() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull<ActionBar>(supportActionBar).title = getString(R.string.pokee)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        nombrePokemon = txtNombrePoke.text.toString()
        num = txtNumero.text.toString()

        btnAgregarPoke.setOnClickListener(this)
        buttonSearchPokemon.setOnClickListener(this)
    }
}
