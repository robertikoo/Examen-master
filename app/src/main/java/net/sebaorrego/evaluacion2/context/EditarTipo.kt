package net.sebaorrego.evaluacion2.context


import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_editar_tipo.*
import net.sebaorrego.evaluacion2.R
import net.sebaorrego.evaluacion2.entities.Tipo
import net.sebaorrego.evaluacion2.services.ConexionSQL

class EditarTipo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_tipo)
        this.requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        var idTipo = intent.getIntExtra("idTipo",0)
        val db = ConexionSQL(this, null, 1)
        var tipo : Tipo? = db.buscarTipo(idTipo)

        var nombreTipo: TextView = findViewById(R.id.txtTip)

        nombreTipo.text = tipo!!.nombreTipo
        lblID.text = tipo!!.idTipo.toString()
        lblEstado.text = if(tipo.estado==1){"Activado"} else {"Desactivado"}

        btnActualizarTip.setOnClickListener {
            var nombreTipo2: TextView = findViewById(R.id.txtTip)
            var nuevoNombre:String = ""
            if(TextUtils.isEmpty(txtTip.text.toString())){
                txtTip.error = "Ingrese nombre Tipo"
                return@setOnClickListener
            }else{
                nuevoNombre = nombreTipo2.text.toString()
            }

            var conn = ConexionSQL(this, null, 1)
            var encontrado = 0
            //Buscar si existe con el mismo nombre

            var listaC : ArrayList<Tipo> = conn.listarTipo()

            for (ti in listaC){
                if (ti.nombreTipo.toUpperCase().equals(nuevoNombre.toUpperCase())){
                    encontrado = 1
                }
            }

            if (encontrado==0){
                tipo.nombreTipo = nuevoNombre
                conn.actualizarTipo(tipo)
                finish()
            }else{
                Toast.makeText(this, "Ya Existe, intente con otro", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
