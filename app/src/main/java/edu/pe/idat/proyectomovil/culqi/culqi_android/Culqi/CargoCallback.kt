package edu.pe.idat.proyectomovil.culqi.culqi_android.Culqi

import com.android.volley.VolleyError
import org.json.JSONObject
import java.lang.Exception

interface CargoCallback {

    fun onSuccess(respuesta: JSONObject?)
    fun onError(error: VolleyError)
    //fun OnSuccesError(respuesta: JSONObject?)

}