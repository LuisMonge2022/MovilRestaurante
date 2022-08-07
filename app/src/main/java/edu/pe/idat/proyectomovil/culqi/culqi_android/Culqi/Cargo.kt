package edu.pe.idat.proyectomovil.culqi.culqi_android.Culqi

import android.content.Context
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.lang.Exception
import java.util.HashMap

/*data class Cargo(val amount: Int,
                 val currency_code: String,
                 val email:String,
                 val source_id: String
)*/


class Cargo(private val api_key: String) {



    var config = Config()

    private var listener: CargoCallback? = null

    fun crearCargo(context: Context,token:String,monto: Int, email:String,listener: CargoCallback){

        this.listener = listener
        val requestQueue = Volley.newRequestQueue(context)
        var jsonBody = JSONObject()
        try {

            jsonBody = JSONObject()
            jsonBody.put("amount", monto)
            jsonBody.put("currency_code", "PEN")
            jsonBody.put("email", email)
            jsonBody.put("source_id", token)



        } catch (ex: Exception) {
            Log.v("", "ERROR: " + ex.message)
        }

        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
            Method.POST,
            config.url_base + URL,
            jsonBody,
            Response.Listener { response ->
                try {
                    listener.onSuccess(response)
                } catch (ex: Exception) {
                    listener.onError(ex)
                    Log.v("", "ERROR: " + ex.message)
                }
            },
            Response.ErrorListener { error -> listener.onError(error) }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                headers["Content-Type"] = "application/json"
                //headers["Content-Type"] = "application/json; charset=utf-8"
                headers["Authorization"] = "Bearer " + api_key
                return headers
            }
        }
        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue.add(jsonObjectRequest)

    }
    companion object {
        private const val URL = "/charges/"
    }
}