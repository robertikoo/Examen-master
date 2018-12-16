package net.sebaorrego.evaluacion2.entities


 class Lista(var idLista: Int, var nombre: String,
               var cantidadDisponible: Int =1, var precioSinIva : Double=0.0,
               var precioConIva : Double=0.0, var PrecioDolar: Double=0.0,
               var idPokemon: Int,  var idTipo: Int, var idHabilidad: Int, var estado: Int=1)