package net.sebaorrego.evaluacion2.services

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import net.sebaorrego.evaluacion2.entities.Pokemon
import net.sebaorrego.evaluacion2.entities.Habilidad
import net.sebaorrego.evaluacion2.entities.Lista
import net.sebaorrego.evaluacion2.entities.Tipo
import java.lang.Exception
import java.sql.SQLException

class ConexionSQL(val miContexto:Context,
                  val factory: SQLiteDatabase.CursorFactory?,
                  val version: Int):SQLiteOpenHelper(miContexto,"miDB",factory,version){


    override fun onCreate(db: SQLiteDatabase?) {
        val query1 = "CREATE TABLE tipo(idTipo INTEGER PRIMARY KEY AUTOINCREMENT,  nombreTipo TEXT,estado INTEGER )"
        db?.execSQL(query1)
        val query2 = "CREATE TABLE habilidad(idHabilidad INTEGER PRIMARY KEY AUTOINCREMENT,  nombreHabilidad TEXT,estado INTEGER )"
        db?.execSQL(query2)
        val query3 = "CREATE TABLE pokemon(idPokemon INTEGER PRIMARY KEY AUTOINCREMENT,  nombrePokemon TEXT, numero TEXT, fechaInscripcion TEXT, estado INTEGER )"
        db?.execSQL(query3)
        val query4 = "CREATE TABLE lista(idLista INTEGER PRIMARY KEY AUTOINCREMENT,  nombre TEXT, cantidadDisponible INTEGER, precioSinIva REAL, precioConIva REAL, precioDolar REAL, idPokemon INTEGER, idTipo INTEGER, idHabilidad, estado INTEGER )"
        db?.execSQL(query4)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val query1 = "DROP TABLE IF EXISTS tipo;"
        db?.execSQL(query1)
        val query2 = "DROP TABLE IF EXISTS pokemon;"
        db?.execSQL(query2)
        val query3 = "DROP TABLE IF EXISTS lista;"
        db?.execSQL(query3)
        onCreate(db)

    }

    //
    // AGREGAR
    //

    fun insertarTipo(tipo: Tipo){
        try {
            val db = this.writableDatabase
            var cv = ContentValues()
            cv.put("nombreTipo" , tipo.nombreTipo)
            cv.put("estado" , tipo.estado)
            val result = db.insert("tipo", null, cv)
            db.close()
            if (result == -1L){
                Toast.makeText(miContexto, "Tipo no agregado", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(miContexto, "Tipo agregado", Toast.LENGTH_SHORT).show()
            }
        }catch (e:Exception){
            Toast.makeText(miContexto,  "Error ${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

    fun insertarHabilidad(habilidad: Habilidad){
        try {
            val db = this.writableDatabase
            var cv = ContentValues()
            cv.put("nombreHabilidad" , habilidad.nombreHabilidad)
            cv.put("estado" , habilidad.estado)
            val result = db.insert("habilidad", null, cv)
            db.close()
            if (result == -1L){
                Toast.makeText(miContexto, "Habilidad no agregada", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(miContexto, "Habilidad agregada", Toast.LENGTH_SHORT).show()
            }
        }catch (e:Exception){
            Toast.makeText(miContexto,  "Error ${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

    fun insertarPokemon(poke: Pokemon){
        try {
            val db = this.writableDatabase
            var cv = ContentValues()

            cv.put("nombrePokemon" , poke.nombrePokemon)
            cv.put("numero" , poke.numero)
            cv.put("fechaInscripcion" , poke.fechaInscripcion)
            cv.put("estado" , poke.estado)

            val result = db.insert("pokemon", null, cv)
            db.close()
            if (result == -1L){
                Toast.makeText(miContexto, "Pokemon no agregado", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(miContexto, "Pokemon agregado", Toast.LENGTH_SHORT).show()
            }
        }catch (e:Exception){
            Toast.makeText(miContexto,  "Error ${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

    fun insertarLista(lis: Lista){
        try {
            val db = this.writableDatabase
            var cv = ContentValues()

            cv.put("nombre" , lis.nombre)
            cv.put("cantidadDisponible" , lis.cantidadDisponible)
            cv.put("precioSinIva" , lis.precioSinIva)
            cv.put("precioConIva" , lis.precioConIva)
            cv.put("precioDolar" , lis.PrecioDolar)
            cv.put("idPokemon" , lis.idPokemon)
            cv.put("idTipo" , lis.idTipo)
            cv.put("idHabilidad" , lis.idHabilidad)
            cv.put("estado" , lis.estado)

            val result = db.insert("lista", null, cv)
            db.close()
            if (result == -1L){
                Toast.makeText(miContexto, "Lista no agregada", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(miContexto, "Lista aceptada", Toast.LENGTH_SHORT).show()
            }
        }catch (e:Exception){
            Toast.makeText(miContexto,  "Error ${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

    //
    // LISTAR
    //

    fun listarTipo() : ArrayList<Tipo> {
        var listaa = ArrayList<Tipo>()
        try {
            val db = this.writableDatabase
            var cursor: Cursor? = null

            cursor = db.rawQuery("SELECT * FROM tipo", null)
            if (cursor?.moveToFirst() == true) {
                do {
                    val id = cursor.getInt(0)
                    val nombre = cursor.getString(1)
                    val estado = cursor.getInt(2)
                    val tip = Tipo(id, nombre, estado)
                    listaa.add(tip)
                } while (cursor.moveToNext())
            }
            return listaa
        } catch (ex: SQLException) {
            Toast.makeText(miContexto, "Error ${ex.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlListar", ex.message)
            return listaa
        }
    }

    fun listarHabilidad() : ArrayList<Habilidad> {

        var listaa = ArrayList<Habilidad>()
        try {
            val db = this.writableDatabase
            var cursor: Cursor? = null

            cursor = db.rawQuery("SELECT * FROM habilidad", null)
            if (cursor?.moveToFirst() == true) {
                do {
                    val id = cursor.getInt(0)
                    val nombre = cursor.getString(1)
                    val estado = cursor.getInt(2)
                    val hab = Habilidad(id, nombre, estado)
                    listaa.add(hab)
                } while (cursor.moveToNext())
            }
            return listaa
        } catch (ex: SQLException) {
            Toast.makeText(miContexto, "Error ${ex.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlListar", ex.message)
            return listaa
        }
    }

    fun listarPokemon() : ArrayList<Pokemon> {
        var lista = ArrayList<Pokemon>()
        try {
            val db = this.writableDatabase
            var cursor: Cursor? = null

            cursor = db.rawQuery("SELECT * FROM pokemon", null)
            if (cursor?.moveToFirst() == true) {
                do {
                    val id = cursor.getInt(0)
                    val nombrePokemon = cursor.getString(1)
                    val numero = cursor.getString(2)
                    val fechaInscripcion = cursor.getString(3)
                    val estado = cursor.getInt(4)


                    val lis =
                        Pokemon(id, nombrePokemon, numero, fechaInscripcion, estado)
                    lista.add(lis)
                } while (cursor.moveToNext())
            }
            return lista
        } catch (ex: SQLException) {
            Toast.makeText(miContexto, "Error ${ex.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlListar", ex.message)
            return lista
        }
    }

    fun listarLista() : ArrayList<Lista> {
        var lista = ArrayList<Lista>()
        try {
            val db = this.writableDatabase
            var cursor: Cursor? = null

            cursor = db.rawQuery("SELECT * FROM lista", null)
            if (cursor?.moveToFirst() == true) {
                do {
                    val id = cursor.getInt(0)
                    val nombre = cursor.getString(1)
                    val cantDisponible = cursor.getInt(2)
                    val precioS = cursor.getDouble(3)
                    val precioC = cursor.getDouble(4)
                    val precioD = cursor.getDouble(5)
                    val idPro = cursor.getInt(6)
                    val idTip = cursor.getInt(7)
                    val idHab = cursor.getInt(8)
                    val estado = cursor.getInt(9)

                    val lis = Lista(
                        id,
                        nombre,
                        cantDisponible,
                        precioS,
                        precioC,
                        precioD,
                        idPro,
                        idTip,
                        idHab,
                        estado
                    )
                    lista.add(lis)
                } while (cursor.moveToNext())
            }
            return lista
        } catch (ex: SQLException) {
            Toast.makeText(miContexto, "Error ${ex.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlListar", ex.message)
            return lista
        }
    }

    //
    //  ELIMINAR
    //

    fun eliminarTipo(id:Int){
        try {
            val db = this.writableDatabase
            val args = arrayOf(id.toString())
            val result = db.delete("tipo","idTipo=?",args)
            if (result ==0){
                Toast.makeText(miContexto,"Tipo no eliminado - $args",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(miContexto,"Tipo eliminado",Toast.LENGTH_SHORT).show()
            }
        }catch (ex: SQLException){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sqlEliminar", ex.message)
        }
    }

    fun eliminarHabilidad(id:Int){
        try {
            val db = this.writableDatabase
            val args = arrayOf(id.toString())
            val result = db.delete("habilidad","idHabilidad=?",args)
            if (result ==0){
                Toast.makeText(miContexto,"Habilidad no eliminada - $args",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(miContexto,"Habilidad eliminada",Toast.LENGTH_SHORT).show()
            }
        }catch (ex: SQLException){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sqlEliminar", ex.message)
        }
    }

    fun eliminarPokemon(id:Int){
        try {
            val db = this.writableDatabase
            val args = arrayOf(id.toString())
            val result = db.delete("pokemon","idPokemon=?",args)
            if (result ==0){
                Toast.makeText(miContexto,"Pokemon no eliminado- $args",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(miContexto,"Pokemon eliminado",Toast.LENGTH_SHORT).show()
            }
        }catch (ex: SQLException){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sqlEliminar", ex.message)
        }
    }

    fun eliminarLista(id:Int){
        try {
            val db = this.writableDatabase
            val args = arrayOf(id.toString())
            val result = db.delete("lista","idLista=?",args)
            if (result ==0){
                Toast.makeText(miContexto,"Lista no eliminada- $args",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(miContexto,"Lista eliminada",Toast.LENGTH_SHORT).show()
            }
        }catch (ex: SQLException){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sqlEliminar", ex.message)
        }
    }

    //
    // ACTUALIZAR
    //

    fun actualizarTipo(tip: Tipo){
        try{
            val db = this.writableDatabase
            var cv = ContentValues()

            val args = arrayOf(tip.idTipo.toString())
            cv.put("nombreTipo" , tip.nombreTipo)
            cv.put("estado" , tip.estado)

            val result = db.update("tipo",cv,"idTipo=?",args)
            db.close()
            if (result==0){
                Toast.makeText(miContexto,"Tipo no actualizado", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(miContexto,"Tipo actualizado", Toast.LENGTH_SHORT).show()
            }
        }catch (ex: Exception){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sqlActualizar",ex.message)
        }
    }

    fun actualizarHabilidad(hab: Habilidad){
        try{
            val db = this.writableDatabase
            val cv = ContentValues()

            val args = arrayOf(hab.idHabilidad.toString())
            cv.put("nombreHabilidad" , hab.nombreHabilidad)
            cv.put("estado" , hab.estado)

            val result = db.update("habilidad",cv,"idHabilidad=?",args)
            db.close()
            if (result==0){
                Toast.makeText(miContexto,"Habilidad no actualizada", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(miContexto,"Habilidad actualizada", Toast.LENGTH_SHORT).show()
            }
        }catch (ex: Exception){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sqlActualizar",ex.message)
        }
    }

    fun actualizarPokemon(poke: Pokemon){
        try{
            val db = this.writableDatabase
            var cv = ContentValues()

            val args = arrayOf(poke.idPokemon.toString())

            cv.put("nombrePokemon" , poke.nombrePokemon)
            cv.put("numero" , poke.numero)
            cv.put("fechaInscripcion" , poke.fechaInscripcion)
            cv.put("estado" , poke.estado)

            val result = db.update("pokemon",cv,"idPokemon=?",args)
            db.close()
            if (result==0){
                Toast.makeText(miContexto,"Pokemon no actualizado", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(miContexto,"Pokemon actualizado", Toast.LENGTH_SHORT).show()
            }
        }catch (ex: Exception){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sqlActualizar",ex.message)
        }
    }

    fun actualizarLista(lis: Lista){
        try{
            val db = this.writableDatabase
            var cv = ContentValues()

            val args = arrayOf(lis.idLista.toString())

            cv.put("nombre" , lis.nombre)
            cv.put("cantidadDisponible" , lis.cantidadDisponible)
            cv.put("precioSinIva" , lis.precioSinIva)
            cv.put("precioConIva" , lis.precioConIva)
            cv.put("precioDolar" , lis.PrecioDolar)
            cv.put("idPokemon" , lis.idPokemon)
            cv.put("idTipo" , lis.idTipo)
            cv.put("idHabilidad", lis.idHabilidad)
            cv.put("estado" , lis.estado)

            val result = db.update("lista",cv,"idLista=?",args)
            db.close()
            if (result==0){
                Toast.makeText(miContexto,"Lista no actualizada", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(miContexto,"Lista actualizada", Toast.LENGTH_SHORT).show()
            }
        }catch (ex: Exception){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sqlActualizar",ex.message)
        }
    }

    //
    // BUSCAR
    //

    fun buscarTipo(idTipo: Int) : Tipo?{
        var tip : Tipo? = null
        try{
            val db = this.writableDatabase
            var cursor: Cursor? = null

            cursor = db.rawQuery("SELECT * FROM tipo", null)
            if (cursor?.moveToFirst() == true) {
                do {
                    if(idTipo == cursor.getInt(0)){
                        val id = cursor.getInt(0)
                        val nombre = cursor.getString(1)
                        val estado = cursor.getInt(2)
                        tip = Tipo(id, nombre, estado)
                        break
                    }
                } while (cursor.moveToNext())
            }
            return tip
        }catch (ex: Exception){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sql Buscarr",ex.message)
            return  null
        }
    }

    fun buscarTipoNombre(nombre: String) : Tipo?{
        var tip : Tipo? = null
        try{
            val db = this.writableDatabase
            var cursor: Cursor? = null

            cursor = db.rawQuery("SELECT * FROM tipo", null)
            if (cursor?.moveToFirst() == true) {
                do {
                    if(nombre.equals(cursor.getString(1).toUpperCase())){
                        val id = cursor.getInt(0)
                        val nombre = cursor.getString(1)
                        val estado = cursor.getInt(2)
                        tip = Tipo(id, nombre, estado)
                        break
                    }
                } while (cursor.moveToNext())
            }
            return tip
        }catch (ex: Exception){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sql Buscarr",ex.message)
            return  null
        }
    }

    fun buscarHabilidad(idHabilidad: Int) : Habilidad?{
        var hab : Habilidad? = null
        try{
            val db = this.writableDatabase
            var cursor: Cursor? = null

            cursor = db.rawQuery("SELECT * FROM habilidad", null)
            if (cursor?.moveToFirst() == true) {
                do {
                    if(idHabilidad== cursor.getInt(0)){
                        val id = cursor.getInt(0)
                        val nombre = cursor.getString(1)
                        val estado = cursor.getInt(2)
                        hab = Habilidad(id, nombre, estado)
                        break
                    }
                } while (cursor.moveToNext())
            }
            return hab
        }catch (ex: Exception){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sql Buscarr",ex.message)
            return  null
        }
    }

    fun buscarHabilidadNombre(nombre: String) : Habilidad?{
        var hab : Habilidad? = null
        try{
            val db = this.writableDatabase
            var cursor: Cursor? = null

            cursor = db.rawQuery("SELECT * FROM habilidad", null)
            if (cursor?.moveToFirst() == true) {
                do {
                    if(nombre.equals(cursor.getString(1).toUpperCase())){
                        val id = cursor.getInt(0)
                        val nombre = cursor.getString(1)
                        val estado = cursor.getInt(2)
                        hab = Habilidad(id, nombre, estado)
                        break
                    }
                } while (cursor.moveToNext())
            }
            return hab

        }catch (ex: Exception){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sql Buscarr",ex.message)
            return  null
        }
    }

    fun buscarPokemon(idPokee: Int) : Pokemon?{
        var pokee : Pokemon? = null
        try{
            val db = this.writableDatabase
            var cursor: Cursor? = null

            cursor = db.rawQuery("SELECT * FROM pokemon", null)
            if (cursor?.moveToFirst() == true) {
                do {
                    if(idPokee == cursor.getInt(0)){

                        val id = cursor.getInt(0)
                        val nombrePokemon = cursor.getString(1)
                        val numero = cursor.getString(2)
                        val fechaInscripcion = cursor.getString(3)
                        val estado = cursor.getInt(4)

                        pokee = Pokemon(
                            id,
                            nombrePokemon,
                            numero,
                            fechaInscripcion,
                            estado
                        )

                    }
                } while (cursor.moveToNext())
            }
            return pokee
        }catch (ex: Exception){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sql Buscarr",ex.message)
            return  null
        }
    }


    fun buscarLista(idLista: Int) : Lista?{
        var lis : Lista? = null
        try{
            val db = this.writableDatabase
            var cursor: Cursor? = null

            cursor = db.rawQuery("SELECT * FROM lista", null)
            if (cursor?.moveToFirst() == true) {
                do {
                    if(idLista == cursor.getInt(0)){

                        val id = cursor.getInt(0)
                        val nombre = cursor.getString(1)
                        val cantDisponible = cursor.getInt(2)
                        val precioS = cursor.getDouble(3)
                        val precioC = cursor.getDouble(4)
                        val precioD = cursor.getDouble(5)
                        val idPro = cursor.getInt(6)
                        val idCate = cursor.getInt(7)
                        val idHab = cursor.getInt(8)
                        val estado = cursor.getInt(9)

                        lis = Lista(
                            id,
                            nombre,
                            cantDisponible,
                            precioS,
                            precioC,
                            precioD,
                            idPro,
                            idCate,
                            idHab,
                            estado
                        )
                        break

                    }
                } while (cursor.moveToNext())
            }
            return lis
        }catch (ex: Exception){
            Toast.makeText(miContexto,"error ${ex.message}",Toast.LENGTH_SHORT).show()
            Log.e("sql Buscar Lista",ex.message)
            return  null
        }
    }
}
