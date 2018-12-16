package net.sebaorrego.evaluacion2.views.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.*
import kotlinx.android.synthetic.main.fragment_tipos.*
import net.sebaorrego.evaluacion2.services.ConexionSQL
import net.sebaorrego.evaluacion2.R
import net.sebaorrego.evaluacion2.entities.Tipo
import net.sebaorrego.evaluacion2.views.adapters.AdapterTipo


class FragmentTipos : Fragment() {

    var miContexto : Context?= null
    var adaptador: AdapterTipo? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_tipos, container, false)

        val boton: Button = view.findViewById(R.id.btnAgregar)
        var list : RecyclerView  = view.findViewById(R.id.lvlTipo)
        var txtNombre : EditText = view.findViewById(R.id.txtTipo)


        var conn = ConexionSQL(miContexto!!, null, 1)
        var arrayTipo = conn.listarTipo()
        adaptador = AdapterTipo(arrayTipo)
        list.layoutManager = LinearLayoutManager(miContexto,LinearLayout.VERTICAL,false)
        list.adapter = adaptador

        boton.setOnClickListener{
            var nombre : String

            if(TextUtils.isEmpty(txtNombre.text.toString())){
                txtNombre.error = "Ingrese nombre Tipo de pokedex"
                return@setOnClickListener
            }else{
                nombre = txtNombre.text.toString()
            }


            var conn = ConexionSQL(miContexto!!, null, 1)
            var encontrado = 0
            //Buscar si existe con el mismo nombre

            var listaC : ArrayList<Tipo> = conn.listarTipo()

            for (ti in listaC){
                if (ti.nombreTipo.toUpperCase().equals(nombre.toUpperCase())){
                    encontrado = 1
                }
            }

            if (encontrado==0){
                var tipo = Tipo(1, nombre, 1);
                conn.insertarTipo(tipo)
            }else{
                Toast.makeText(miContexto!!, "Ya Existe", Toast.LENGTH_SHORT).show()
            }

            arrayTipo = conn.listarTipo()
            adaptador = AdapterTipo(arrayTipo)
            list.layoutManager = LinearLayoutManager(miContexto,LinearLayout.VERTICAL,false)
            list.adapter = adaptador
        }



        return  view
    }

    override fun onResume() {
        var conn = ConexionSQL(miContexto!!, null, 1)
        var arrayTipo = conn.listarTipo()
        adaptador = AdapterTipo(arrayTipo)
        lvlTipo.layoutManager = LinearLayoutManager(miContexto,LinearLayout.VERTICAL,false)
        lvlTipo.adapter = adaptador
        super.onResume()
    }
}