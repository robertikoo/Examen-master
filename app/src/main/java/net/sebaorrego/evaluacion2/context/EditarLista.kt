package net.sebaorrego.evaluacion2.context

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_editar_lista.*
import net.sebaorrego.evaluacion2.R
import net.sebaorrego.evaluacion2.entities.Habilidad
import net.sebaorrego.evaluacion2.entities.Lista
import net.sebaorrego.evaluacion2.entities.Pokemon
import net.sebaorrego.evaluacion2.entities.Tipo
import net.sebaorrego.evaluacion2.services.ConexionSQL

class EditarLista : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_lista)
        this.requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        listarSpinner()
        var idLista = intent.getIntExtra("idLista",0)
        val db = ConexionSQL(this, null, 1)
        var lista : Lista? = db.buscarLista(idLista)

        var pokemon : Pokemon? = db.buscarPokemon(lista!!.idPokemon)
        var tipo : Tipo? = db.buscarTipo(lista!!.idTipo)
        var habilidad: Habilidad? = db.buscarHabilidad(lista!!.idHabilidad)

        var nombreListaEdit :TextView = findViewById(R.id.txtNombreListaEdit)
        var cantEdit : TextView = findViewById(R.id.txtCantidadEdit)
        var valorEdit :TextView  = findViewById(R.id.txtValorPEdit)


        nombreListaEdit.text = lista.nombre
        cantEdit.text = lista.cantidadDisponible.toString()
        valorEdit.text = lista.precioSinIva.toString()
        lblValorDolarEdit.text = lista.PrecioDolar.toString()
        lblPrecioConIvaEdit.text = lista.precioConIva.toString()

        lblEstadoListaEdit.text = if(lista.estado==1){"Activado"}else{"Desactivado"}

        //Toast.makeText(this,"${sp_categoriaEdit.getItemAtPosition(0).toString()}" , Toast.LENGTH_SHORT).show()

        sp_tipoEdit.setSelection(numeroSpinner(sp_tipoEdit,tipo!!.nombreTipo))
        sp_habilidadEdit.setSelection(numeroSpinner(sp_habilidadEdit,habilidad!!.nombreHabilidad))
        sp_pokemonEdit.setSelection(numeroSpinner(sp_pokemonEdit, pokemon!!.nombrePokemon!!))

    }

    fun numeroSpinner(sp : Spinner, nombre : String): Int{
        var posicion = 0;

        for (i in 0 until sp.count){
            if (sp.getItemAtPosition(i).toString().equals(nombre)){
                posicion=i
            }
        }

        return  posicion
    }

    fun listarSpinner(){
        var pokemones :ArrayList<Pokemon>  = ArrayList()
        var pokemonesActivos :ArrayList<Pokemon>  = ArrayList()
        var tipos : ArrayList<Tipo> = ArrayList()
        var tiposActivos : ArrayList<Tipo> = ArrayList()
        var habilidades : ArrayList<Habilidad> = ArrayList()
        var habilidadesActivas: ArrayList<Habilidad> = ArrayList()
        val db = ConexionSQL(this, null, 1)
        tipos = db.listarTipo()
        pokemones = db.listarPokemon()
        habilidades = db.listarHabilidad()

        for(hab in habilidades){
            if (hab.estado ==1){
                habilidadesActivas.add(hab)
            }
        }


        for (ti in tipos){
            if (ti.estado==1){
                tiposActivos.add(ti)
            }
        }

        for(poke in pokemones){
            if (poke.estado ==1){
                pokemonesActivos.add(poke)
            }
        }



        if (pokemonesActivos.size>0 && tiposActivos.size>0){
            sp_pokemonEdit.adapter =  ArrayAdapter<Pokemon>(this, android.R.layout.simple_expandable_list_item_1,pokemonesActivos)
            sp_tipoEdit.adapter = ArrayAdapter<Tipo>(this, android.R.layout.simple_expandable_list_item_1,tiposActivos)
            sp_habilidadEdit.adapter = ArrayAdapter<Habilidad>(this, android.R.layout.simple_expandable_list_item_1,habilidadesActivas)
        }else{
            Toast.makeText(this,"No hay datos activos", Toast.LENGTH_SHORT).show()
            finish()
        }


    }
}
