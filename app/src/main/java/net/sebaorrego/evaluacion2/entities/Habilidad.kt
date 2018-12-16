package net.sebaorrego.evaluacion2.entities

class Habilidad (var idHabilidad:Int, var nombreHabilidad: String, var estado: Int=1){
    override fun toString(): String {
        return nombreHabilidad
    }
}