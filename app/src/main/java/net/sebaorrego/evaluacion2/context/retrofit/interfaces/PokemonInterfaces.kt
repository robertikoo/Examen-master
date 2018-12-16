package net.sebaorrego.evaluacion2.context.retrofit.interfaces

import net.sebaorrego.evaluacion2.entities.PokemonRespuestaApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonInterfaces {

    @GET("pokemon")
    fun obtenerListaPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<PokemonRespuestaApi>

}