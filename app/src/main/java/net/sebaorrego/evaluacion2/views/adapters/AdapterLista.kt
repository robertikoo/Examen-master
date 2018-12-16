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
import net.sebaorrego.evaluacion2.context.EditarLista
import net.sebaorrego.evaluacion2.entities.Lista
import net.sebaorrego.evaluacion2.R

class AdapterLista (val miLista:ArrayList<Lista>?) : RecyclerView.Adapter<AdapterLista.ViewHolder>() , View.OnClickListener{

    override fun onClick(p0: View?) {
        Toast.makeText(p0!!.context,"clic", Toast.LENGTH_LONG).show()
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_lista, parent, false)
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
            var nombreLista: TextView = vista.findViewById(R.id.lblNombreLista)
            var persona: TextView = vista.findViewById(R.id.lblCliente)

            val boton: Button = vista.findViewById(R.id.btnDesactivarLista)
            val boton2: ImageButton = vista.findViewById(R.id.btnActualizarLista)

            nombreLista.text = list.nombre

            val db = ConexionSQL(vista.context, null, 1)

            /*var nombrePoke =  db.buscarPokemon(list.idPokemon)!!.nombrePokemon.toString()
            persona.text = nombrePoke*/


            var nombrePoke =  db.buscarPokemon(list.idPokemon)!!.nombrePokemon.toString()
            persona.text =nombrePoke


            if(list.estado==1){
                boton.setText("Activado")
            }else{
                boton.setText("Desactivado")
            }


            boton.setOnClickListener {
                val alerta = AlertDialog.Builder(vista.context)
                alerta.setTitle("Eliminar")
                var estado = "Activar"
                var valor = 0
                if(list.estado==1){
                    estado="Desactivar"
                    valor = 0
                }else{
                    estado="Activar"
                    valor = 1
                }

                alerta.setMessage("¿Estás seguro que quieres $estado Lista?")
                alerta.setPositiveButton("Si", { dialog, which ->
                    val db = ConexionSQL(vista.context, null, 1)
                    list.estado = valor
                    db.actualizarLista(list)
                    bindItems(list)
                })
                alerta.setNegativeButton("No", { dialog, which ->
                    dialog.cancel()
                })

                alerta.show()
            }
            boton2.setOnClickListener {
                val db = ConexionSQL(vista.context, null, 1)
                var pokee = db.buscarPokemon(list.idPokemon)
                var tip = db.buscarTipo(list.idTipo)
                var hab = db.buscarHabilidad(list.idHabilidad)

                if(pokee!!.estado==1 && tip!!.estado==1){
                    var id = list.idLista
                    var intento: Intent = Intent(vista.context, EditarLista::class.java)
                    intento.putExtra("idLista",id)
                    startActivity(vista.context, intento,null)
                }else{
                    Toast.makeText(vista.context,"lista no disponible" , Toast.LENGTH_SHORT).show()
                }


            }
        }
    }
}