package net.sebaorrego.evaluacion2.context


import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_editar_habilidad.*
import net.sebaorrego.evaluacion2.R
import net.sebaorrego.evaluacion2.entities.Habilidad
import net.sebaorrego.evaluacion2.services.ConexionSQL

class EditarHabilidad : AppCompatActivity() {

    lateinit var nombreHabilidad: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_habilidad)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        var idHabilidad = intent.getIntExtra("idHabilidad", 0)
        val db = ConexionSQL(this, null, 1)
        var habilidad: Habilidad? = db.buscarHabilidad(idHabilidad)

        nombreHabilidad = findViewById(R.id.txtHabilidad)

        nombreHabilidad.text = habilidad!!.nombreHabilidad
        lblID.text = habilidad.idHabilidad.toString()
        lblEstado.text = if (habilidad.estado == 1) {
            "Activado"
        } else {
            "Desactivado"
        }


        // QUE PASO AQUI PORQUE DECLARAS LO MISMO xd
        btnActualizarHab.setOnClickListener {
            val nuevoNombre2 = nombreHabilidad.text.toString()

            val nuevoNombre: String
            if (TextUtils.isEmpty(txtHabilidad.text.toString())) {
                txtHabilidad.error = "Ingrese nombre Habilidad"
                return@setOnClickListener
            } else {
                nuevoNombre = nuevoNombre2
            }

            var conn = ConexionSQL(this, null, 1)
            var encontrado = 0
            //Buscar si existe con el mismo nombre

            var listaC: ArrayList<Habilidad> = conn.listarHabilidad()

            for (ha in listaC) {
                if (ha.nombreHabilidad.toUpperCase().equals(nuevoNombre.toUpperCase())) {
                    encontrado = 1
                }
            }

            if (encontrado == 0) {
                habilidad.nombreHabilidad = nuevoNombre
                conn.actualizarHabilidad(habilidad)
                finish()
            } else {
                Toast.makeText(this, "Ya Existe, intente con otro", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
