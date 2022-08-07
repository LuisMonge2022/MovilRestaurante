package edu.pe.idat.proyectomovil.culqi.culqi_android.Culqi

import org.json.JSONObject
import java.lang.Exception

/**
 * Created by culqi on 2/7/17.
 */
interface TokenCallback {
    fun onSuccess(token: JSONObject?)
    fun onError(error: Exception?)
}