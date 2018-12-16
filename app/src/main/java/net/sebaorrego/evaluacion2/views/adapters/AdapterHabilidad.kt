package net.sebaorrego.evaluacion2.views.adapters


import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.support.v7.app.AlertDialog
import android.widget.ImageButton
import android.widget.Toast
import net.sebaorrego.evaluacion2.services.ConexionSQL
import net.sebaorrego.evaluacion2.context.EditarHabilidad
import net.sebaorrego.evaluacion2.entities.Habilidad
import net.sebaorrego.evaluacion2.R

class AdapterHabilidad(val miLista: ArrayList<Habilidad>?) : RecyclerView.Adapter<AdapterHabilidad.ViewHolder>(),
    View.OnClickListener {

    override fun onClick(p0: View?) {
        Toast.makeText(p0!!.context, "clic", Toast.LENGTH_LONG).show()
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_habilidad, parent, false)
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

        fun bindItems(hab: Habilidad) {

            val vista = itemView
            val nombreHab: TextView = vista.findViewById(R.id.lblNombreHabilidad)
            val boton: Button = vista.findViewById(R.id.btnDesactivarHabilidad)
            val boton2: ImageButton = vista.findViewById(R.id.btnActualizarHab)

            nombreHab.text = hab.nombreHabilidad
            val db = ConexionSQL(vista.context, null, 1)
            if (hab.estado == 1) {
                boton.text = "Activado"
            } else {
                boton.text = "Desactivado"
            }

            boton.setOnClickListener {
                val alerta = AlertDialog.Builder(vista.context)
                alerta.setTitle("Eliminar")
                var estado = "Activar"
                var valor = 0
                if (hab.estado == 1) {
                    estado = "Desactivar"
                    valor = 0
                } else {
                    estado = "Activar"
                    valor = 1
                }

                alerta.setMessage("¿Estás seguro que quieres $estado Habilidad?")
                alerta.setPositiveButton("Si") { dialog, which ->
                    val db = ConexionSQL(vista.context, null, 1)
                    hab.estado = valor
                    db.actualizarHabilidad(hab)
                    bindItems(hab)
                }
                alerta.setNegativeButton("No") { dialog, which ->
                    dialog.cancel()
                }

                alerta.show()
            }
            boton2.setOnClickListener {
                val intento: Intent = Intent(vista.context, EditarHabilidad::class.java)
                intento.putExtra("idHabilidad", hab.idHabilidad)
                startActivity(vista.context, intento, null)
            }
        }
    }

}