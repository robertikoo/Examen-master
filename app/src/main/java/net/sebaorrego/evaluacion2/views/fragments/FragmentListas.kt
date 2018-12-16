package net.sebaorrego.evaluacion2.views.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_listas.*
import net.sebaorrego.evaluacion2.context.AgregarLista
import net.sebaorrego.evaluacion2.services.ConexionSQL
import net.sebaorrego.evaluacion2.R
import net.sebaorrego.evaluacion2.views.adapters.AdapterLista


class FragmentListas : Fragment() {

    var miContexto : Context?= null
    var adaptador: AdapterLista? = null
    override fun onCreateView( inflater: LayoutInflater,
                               container: ViewGroup?,
                               savedInstanceState: Bundle? ): View?
    {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_listas, container, false)

        var botonAgregar : Button = view.findViewById(R.id.btnAddLista)
        var lista : RecyclerView = view.findViewById(R.id.lv_Lista)

        var conn = ConexionSQL(miContexto!!, null, 1)
        adaptador = AdapterLista(conn.listarLista()!!)
        lista.layoutManager = LinearLayoutManager(miContexto,LinearLayout.VERTICAL,false)
        lista.adapter = adaptador

        botonAgregar.setOnClickListener {
            var intento: Intent = Intent(miContexto, AgregarLista::class.java)
            startActivity(intento)
        }

        return view
    }

    override fun onResume() {
        var conn = ConexionSQL(miContexto!!, null, 1)
        adaptador = AdapterLista(conn.listarLista()!!)
        lv_Lista.layoutManager = LinearLayoutManager(miContexto,LinearLayout.VERTICAL,false)
        lv_Lista.adapter = adaptador
        super.onResume()
    }
}
