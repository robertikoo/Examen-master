package net.sebaorrego.evaluacion2.context

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_agregar_lista.*
import net.sebaorrego.evaluacion2.R
import net.sebaorrego.evaluacion2.entities.Habilidad
import net.sebaorrego.evaluacion2.entities.Lista
import net.sebaorrego.evaluacion2.entities.Pokemon
import net.sebaorrego.evaluacion2.entities.Tipo
import net.sebaorrego.evaluacion2.services.ConexionSQL
import net.sebaorrego.evaluacion2.services.Valores


class AgregarLista : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_lista)
        this.requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        listarSpinner()

/*/
        var valores = Valores()
        var poke = valores.poke

        var name: String = ""
        var nombreP: String = name
        var ability: String = ""
        var habilidad: String = ability
        var cant: Int = 0
        var stat: String = ""
        var estado: String = stat

        var url: String = "https://pokeapi.co/api/v2/ability/150/"
*/

        val Iva = 0.19
        var valores = Valores()
        var dolar = valores.dolar

        var valorDolar:Double = 0.0
        var valorConIva :Double = 0.0
        var precio: Double =0.0
        var nombreP: String =""
        var cant: Int = 0


        btnCalcular.setOnClickListener {
            precio = 0.0
            dolar = valores.dolar

            if(TextUtils.isEmpty(txtValorP.text.toString())){
                txtValorP.error = "Ingrese Valor"
                return@setOnClickListener
            }else{
                precio = txtValorP.text.toString().toDouble()
            }

            valorDolar = (precio/dolar)
            valorConIva = (precio*Iva + precio)
            lblPrecioConIva.text = "$$valorConIva"
            lblValorDolar.text = "$valorDolar USD"
        }

        btnAgregarLista.setOnClickListener {
            var pokemon = sp_pokemon.selectedItem as Pokemon
            var tipo = sp_tipo.selectedItem as Tipo
            var habilidad = sp_habilidad.selectedItem as Habilidad

            precio = 0.0
            dolar=valores.dolar
            if(TextUtils.isEmpty(txtValorP.text.toString())){
                txtValorP.error = "Ingrese Valor"
                return@setOnClickListener
            }else{
                precio = txtValorP.text.toString().toDouble()
            }
            // todo dato modificado

            valorDolar = (precio/dolar)
            valorConIva = (precio*Iva + precio)

            if(TextUtils.isEmpty(txtNombreLista.text.toString())){
                txtNombreLista.error = "Ingrese nombre lista"
                return@setOnClickListener
            }else{
                nombreP = txtNombreLista.text.toString()
            }
            if(TextUtils.isEmpty(txtCantidad.text.toString())){
                txtCantidad.error = "Ingrese cantidad de la lista"
                return@setOnClickListener
            }else{
                cant = txtCantidad.text.toString().toInt()
            }

            var lista = Lista(
                1,
                nombreP,
                cant,
                precio,
                valorConIva,
                valorDolar,
                pokemon.idPokemon,
                tipo.idTipo,
                habilidad.idHabilidad,
                1
            )

            var conn = ConexionSQL(this, null, 1)

            conn.insertarLista(lista)

            finish()
            // Toast.makeText(this,"${proveedor.nombreProveedor} +  ${categoria.nombreCategoria}", Toast.LENGTH_SHORT).show()
        }


    }

    fun listarSpinner(){
        var pokemones :ArrayList<Pokemon>  = ArrayList()
        var tipos : ArrayList<Tipo> = ArrayList()
        var habilidades: ArrayList<Habilidad> = ArrayList()
        val db = ConexionSQL(this, null, 1)
        habilidades = db.listarHabilidad()
        tipos = db.listarTipo()
        pokemones = db.listarPokemon()


        var habilidadesActivas: ArrayList<Habilidad> = ArrayList()
        var tiposActivos : ArrayList<Tipo> = ArrayList()
        var pokemonesActivos :ArrayList<Pokemon>  = ArrayList()

        for (ha in habilidades){
            if (ha.estado==1){
                habilidadesActivas.add(ha)
            }
        }
        for (ti in tipos){
            if (ti.estado==1){
                tiposActivos.add(ti)
            }
        }

        for(lis in pokemones){
            if (lis.estado ==1){
                pokemonesActivos.add(lis)
            }
        }

        if (pokemonesActivos.size>0 && tiposActivos.size>0){
            sp_pokemon.adapter =  ArrayAdapter<Pokemon>(this, android.R.layout.simple_expandable_list_item_1, pokemonesActivos)
            sp_tipo.adapter = ArrayAdapter<Tipo>(this, android.R.layout.simple_expandable_list_item_1,tiposActivos)
            sp_habilidad.adapter = ArrayAdapter<Habilidad>(this, android.R.layout.simple_expandable_list_item_1,habilidadesActivas)
        }else{
            Toast.makeText(this,"Faltan Datos", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}

