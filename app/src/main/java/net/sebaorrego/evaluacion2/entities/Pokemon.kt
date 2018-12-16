package net.sebaorrego.evaluacion2.entities

open class Pokemon {

    var idPokemon: Int? = 0
    var nombrePokemon: String? = null
    var numero: String? = null
    var fechaInscripcion: String? = null
    var estado: Int? = 1

    constructor()

    constructor(idPokemon: Int?, nombrePokemon: String?, numero: String?, fechaInscripcion: String?, estado: Int?) {
        this.idPokemon = idPokemon
        this.nombrePokemon = nombrePokemon
        this.numero = numero
        this.fechaInscripcion = fechaInscripcion
        this.estado = estado
    }

    override fun toString(): String {
        return nombrePokemon!!
    }
}