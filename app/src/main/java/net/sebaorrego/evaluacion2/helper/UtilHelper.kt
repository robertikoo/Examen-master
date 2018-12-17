package net.sebaorrego.evaluacion2.helper

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

object UtilHelper {

    private var date: Date? = null

    fun getFecha(): String {
        date = Date()
        @SuppressLint("SimpleDateFormat") val format = SimpleDateFormat("dd/MM/yyyy")
        return format.format(date)
    }

    fun getFechaActual(): String {
        date = Date()
        @SuppressLint("SimpleDateFormat") val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        return format.format(date)
    }

    fun getHoraActual(): String {
        date = Date()
        @SuppressLint("SimpleDateFormat") val format = SimpleDateFormat("HH:mm:ss aaa")
        return format.format(date)
    }

    fun toastMensaje(c: Context, mensaje: String) {
        Toast.makeText(c, mensaje, Toast.LENGTH_LONG).show()
    }

}