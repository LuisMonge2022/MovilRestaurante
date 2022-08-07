package edu.pe.idat.proyectomovil.culqi.culqi_android.Culqi

import org.json.JSONObject
import java.lang.Exception

interface CargoCallback {

    fun onSuccess(respuesta: JSONObject?)
    fun onError(error: Exception?)

}