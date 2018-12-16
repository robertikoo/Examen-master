package net.sebaorrego.evaluacion2.entities

class Tipo (var idTipo:Int, var nombreTipo: String, var estado: Int=1){
    override fun toString(): String {
        return nombreTipo
    }
}