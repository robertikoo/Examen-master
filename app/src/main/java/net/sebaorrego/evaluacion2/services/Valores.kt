package net.sebaorrego.evaluacion2.services

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class Valores{



    //var poke : String = ""
    //class Habilidades(var ability: String, var name: String, var url: String)
    //class Estatus(var stat: String, var name: String, var habilidades:ArrayList<Habilidades>)



    //constructor() {
      //  val url = "https://pokeapi.co/"
        //val request = Request.Builder().url(url).build()
        //val cliente = OkHttpClient()

        //cliente.newCall(request).enqueue(object :Callback{
          //  override fun onFailure(call: Call?, e: IOException?) {
            //    Log.e("json","no se encontraron estos datos ;)")
            //}

            //override fun onResponse(call: Call?, response: Response?) {
              //  val respuesta = response?.body()?.string()
                //val gson = GsonBuilder().create()
                //val estatus =gson.fromJson(respuesta, Estatus::class.java)

                //poke=estatus.habilidades.get(0).ability
            //}
        //})


    var dolar : Float = 700f

    class Serie(var fecha:String,var valor:Float){}
    class Indicador(var codigo:String, var serie:ArrayList<Serie>){}

    constructor() {
        val url = "https://mindicador.cl/api/dolar"
        val request = Request.Builder().url(url).build()
        val cliente = OkHttpClient()

        cliente.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                Log.e("json","no obtuvo respuesta, redondeando dolar a 700 pesos ;)")
            }

            override fun onResponse(call: Call?, response: Response?) {
                val respuesta = response?.body()?.string()
                val gson = GsonBuilder().create()
                val indicador =gson.fromJson(respuesta, Indicador::class.java)

                dolar=indicador.serie.get(0).valor
            }
        })
    }
}