package net.sebaorrego.evaluacion2.views.adapters

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import net.sebaorrego.evaluacion2.entities.Lista
import net.sebaorrego.evaluacion2.R

class AdapterEstadistica(val miLista:ArrayList<Lista>?) : RecyclerView.Adapter<AdapterEstadistica.ViewHolder>() , View.OnClickListener{


    override fun onClick(p0: View?) {
        Toast.makeText(p0!!.context,"clic", Toast.LENGTH_LONG).show()
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_estadistica, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(miLista!![position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return miLista!!.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(list: Lista) {
            var vista = itemView
            var nombreLista: TextView = vista.findViewById(R.id.lblNombreListaE)
            var cant : TextView = vista.findViewById(R.id.lblCantidadE)
            var linearLayout : LinearLayout = vista.findViewById(R.id.linearEstadistica)


            nombreLista.text = list.nombre
            cant.text = list.cantidadDisponible.toString()

            if (list.cantidadDisponible<5){
                linearLayout.setBackgroundColor(ContextCompat.getColor(vista.context,
                    R.color.redPoco
                ))
            }

            if (list.cantidadDisponible>=6 && list.cantidadDisponible<20) {
                linearLayout.setBackgroundColor(ContextCompat.getColor(vista.context,
                    R.color.amarilloMedio
                ))
            }
            if (list.cantidadDisponible>=20){
                linearLayout.setBackgroundColor(ContextCompat.getColor(vista.context,
                    R.color.verdeMucho
                ))
            }
        }
    }

}