package net.sebaorrego.evaluacion2.entities

open class PokemonApi {

    var number: Int = 0
    var name: String? = null
    var url: String? = null

    constructor()

    constructor(number: Int, name: String?, url: String?) {
        this.number = number
        this.name = name
        this.url = url
    }

    fun getFoto(): Int {
        val urlPartes = url?.split("/".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        return Integer.parseInt(urlPartes!![urlPartes.size - 1])
    }
}