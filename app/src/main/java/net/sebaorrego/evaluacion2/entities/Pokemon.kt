package net.sebaorrego.evaluacion2.entities

import android.icu.util.Calendar

class Pokemon(var idPokemon: Int, var nombrePokemon:String? = null,
               var numero: String,
               var fechaInscripcion: String,
                var estado:Int=1){
    override fun toString(): String {
        return nombrePokemon!!
    }
}