package net.sebaorrego.evaluacion2.entities

import java.util.ArrayList

open class PokemonRespuestaApi {

    var results: ArrayList<PokemonApi>? = null

    constructor()

    constructor(results: ArrayList<PokemonApi>?) {
        this.results = results
    }
}