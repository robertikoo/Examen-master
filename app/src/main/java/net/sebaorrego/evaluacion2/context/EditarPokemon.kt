package net.sebaorrego.evaluacion2.context

import android.app.DatePickerDialog
import android.content.pm.ActivityInfo
import java.util.Calendar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_editar_pokemon.*
import net.sebaorrego.evaluacion2.R
import net.sebaorrego.evaluacion2.entities.Pokemon
import net.sebaorrego.evaluacion2.services.ConexionSQL

class EditarPokemon : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_pokemon)
        this.requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        var idPokemon = intent.getIntExtra("idPokemon",0)
        val db = ConexionSQL(this, null, 1)
        var pokemon : Pokemon? = db.buscarPokemon(idPokemon)

        var nombrePokeEdit :TextView = findViewById(R.id.txtNombrePokeEdit)
        var numEdit : TextView = findViewById(R.id.txtNumeroEdit)
        var  lblFechaEdit : TextView = findViewById(R.id.lblFechaEdit)
        var activoEdit : TextView = findViewById(R.id.lblEstado)

        nombrePokeEdit.text = pokemon!!.nombrePokemon
        numEdit.text = pokemon!!.numero
        lblFechaEdit.text = pokemon!!.fechaInscripcion
        activoEdit.text = if (pokemon.estado==1){ "Activo" } else{"Desactivado"}


        var fecha= pokemon.fechaInscripcion

        val c = Calendar.getInstance()
        val anio = c.get(Calendar.YEAR)
        val mes = c.get(Calendar.MONTH)
        val dia = c.get(Calendar.DAY_OF_MONTH)

        btnFechaEdit.setOnClickListener {
            val dp = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{ view, year, monthOfYear, dayOfMonth ->
                    var m=monthOfYear+1
                    lblFechaEdit.text = "$dayOfMonth/$m/$year"
                    fecha = "$dayOfMonth/$m/$year"
                    c.set(anio,mes,dia)
                }, anio,mes,dia)
            dp.show()
        }

        btnActualizarPoke.setOnClickListener {

            var nombrePokemon : String
            var num : String



            if(TextUtils.isEmpty(nombrePokeEdit.text.toString())){
                nombrePokeEdit.error = "Ingrese Pokemon"
                return@setOnClickListener
            }else{
                nombrePokemon = nombrePokeEdit.text.toString()
            }
            if(TextUtils.isEmpty(txtNumeroEdit.text.toString())){
                txtNumeroEdit.error = "Ingrese Numero"
                return@setOnClickListener
            }else{
                num = txtNumeroEdit.text.toString()
            }



            var pokemon = Pokemon(idPokemon, nombrePokemon, num, fecha, 1)
            var conn = ConexionSQL(this, null, 1)
            conn.actualizarPokemon(pokemon)

        }

    }
}
