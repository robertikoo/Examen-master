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
import kotlinx.android.synthetic.main.fragment_habilidades.*
import net.sebaorrego.evaluacion2.services.ConexionSQL
import net.sebaorrego.evaluacion2.entities.Habilidad
import net.sebaorrego.evaluacion2.R
import net.sebaorrego.evaluacion2.views.adapters.AdapterHabilidad


class FragmentHabilidades : Fragment() {

    var miContexto : Context?= null
    var adaptador: AdapterHabilidad? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_habilidades, container, false)

        val boton: Button = view.findViewById(R.id.btnAgregarHabilidad)
        val list : RecyclerView  = view.findViewById(R.id.lvlHabilidad)
        val txtNombre : EditText = view.findViewById(R.id.txtHab)


        val conn = ConexionSQL(miContexto!!, null, 1)
        var arrayHabilidad = conn.listarHabilidad()
        adaptador = AdapterHabilidad(arrayHabilidad)
        list.layoutManager = LinearLayoutManager(miContexto,LinearLayout.VERTICAL,false)
        list.adapter = adaptador

        boton.setOnClickListener{
            var nombre : String

            if(TextUtils.isEmpty(txtNombre.text.toString())){
                txtNombre.error = "Ingrese nombre habilidad"
                return@setOnClickListener
            }else{
                nombre = txtNombre.text.toString()
            }


            var conn = ConexionSQL(miContexto!!, null, 1)
            var encontrado = 0
            //Buscar si existe con el mismo nombre

            var listaC : ArrayList<Habilidad> = conn.listarHabilidad()

            for (ha in listaC){
                if (ha.nombreHabilidad.toUpperCase().equals(nombre.toUpperCase())){
                    encontrado = 1
                }
            }

            if (encontrado==0){
                var habilidad = Habilidad(1, nombre, 1);
                conn.insertarHabilidad(habilidad)
            }else{
                Toast.makeText(miContexto!!, "Ya Existe", Toast.LENGTH_SHORT).show()
            }

            arrayHabilidad = conn.listarHabilidad()
            adaptador = AdapterHabilidad(arrayHabilidad)
            list.layoutManager = LinearLayoutManager(miContexto,LinearLayout.VERTICAL,false)
            list.adapter = adaptador
        }



        return  view
    }

    override fun onResume() {
        var conn = ConexionSQL(miContexto!!, null, 1)
        var arrayHabilidad = conn.listarHabilidad()
        adaptador = AdapterHabilidad(arrayHabilidad)
        lvlHabilidad.layoutManager = LinearLayoutManager(miContexto,LinearLayout.VERTICAL,false)
        lvlHabilidad.adapter = adaptador
        super.onResume()
    }
}