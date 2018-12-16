package net.sebaorrego.evaluacion2.entities

open class Lista {

    var idLista: Int? = 0
    var nombre: String? = ""
    var cantidadDisponible: Int = 1
    var precioSinIva: Double = 0.0
    var precioConIva: Double = 0.0
    var PrecioDolar: Double = 0.0
    var idPokemon: Int? = 0
    var idTipo: Int? = 0
    var idHabilidad: Int? = 0
    var estado: Int = 1


    constructor(
        idLista: Int?,
        nombre: String?,
        cantidadDisponible: Int,
        precioSinIva: Double,
        precioConIva: Double,
        PrecioDolar: Double,
        idPokemon: Int?,
        idTipo: Int?,
        idHabilidad: Int?,
        estado: Int
    ) {
        this.idLista = idLista
        this.nombre = nombre
        this.cantidadDisponible = cantidadDisponible
        this.precioSinIva = precioSinIva
        this.precioConIva = precioConIva
        this.PrecioDolar = PrecioDolar
        this.idPokemon = idPokemon
        this.idTipo = idTipo
        this.idHabilidad = idHabilidad
        this.estado = estado
    }

    constructor()
}