package net.sebaorrego.evaluacion2.context

import android.app.DatePickerDialog
import android.content.pm.ActivityInfo
import java.util.Calendar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import kotlinx.android.synthetic.main.activity_agregar_pokemon.*
import net.sebaorrego.evaluacion2.R
import net.sebaorrego.evaluacion2.entities.Pokemon
import net.sebaorrego.evaluacion2.services.ConexionSQL

class AgregarPokemon : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_pokemon)
        this.requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        var nombrePokemon : String

        var num : String

        var fecha : String

        val c = Calendar.getInstance()
        val anio = c.get(Calendar.YEAR)
        val mes = c.get(Calendar.MONTH)
        val dia = c.get(Calendar.DAY_OF_MONTH)
        var m= mes+1
        lblFecha.text = "$dia/$m/$anio"
        fecha =  "$dia/$m/$anio"

        btnFecha.setOnClickListener{
            val dp = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{ view, year, monthOfYear, dayOfMonth ->
                    var m=monthOfYear+1
                    lblFecha.text = "$dayOfMonth/$m/$year"
                    fecha = "$dayOfMonth/$m/$year"
                    c.set(anio,mes,dia)
                }, anio,mes,dia)
            dp.show()
        }

        btnAgregarPoke.setOnClickListener {

            if(TextUtils.isEmpty(txtNombrePoke.text.toString())){
                txtNombrePoke.error = "Ingrese Pokemon"
                return@setOnClickListener
            }else{
                nombrePokemon = txtNombrePoke.text.toString()
            }


            if(TextUtils.isEmpty(txtNumero.text.toString())){
                txtNumero.error = "Ingrese numero"
                return@setOnClickListener
            }else{
                num = txtNumero.text.toString()
            }



            var pokemon = Pokemon(1, nombrePokemon, num, fecha, 1)

            var conn = ConexionSQL(this, null, 1)

            conn.insertarPokemon(pokemon)

            finish()
        }
    }
}
