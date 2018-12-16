package net.sebaorrego.evaluacion2.views.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import net.sebaorrego.evaluacion2.services.ConexionSQL
import net.sebaorrego.evaluacion2.entities.Lista
import net.sebaorrego.evaluacion2.R
import net.sebaorrego.evaluacion2.views.adapters.AdapterEstadistica


class FragmentStock : Fragment() {

    var miContexto : Context?= null

    var arrayLista: ArrayList<Lista>? = null
    var lista: RecyclerView? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var adaptador: AdapterEstadistica? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_stock, container, false)

        lista = view.findViewById(R.id.rv_estadistica)
        layoutManager = LinearLayoutManager(miContexto)

        var conn = ConexionSQL(miContexto!!, null, 1)
        arrayLista = conn.listarLista()

        adaptador = AdapterEstadistica(arrayLista!!)
        lista?.layoutManager = LinearLayoutManager(miContexto,LinearLayout.VERTICAL,false)
        lista?.adapter = adaptador

        return view
    }

    override fun onResume() {
        layoutManager = LinearLayoutManager(miContexto)
        var conn = ConexionSQL(miContexto!!, null, 1)
        arrayLista = conn.listarLista()

        adaptador = AdapterEstadistica(arrayLista!!)
        lista?.layoutManager = LinearLayoutManager(miContexto, LinearLayout.VERTICAL,false)
        lista?.adapter = adaptador
        super.onResume()
    }
}
