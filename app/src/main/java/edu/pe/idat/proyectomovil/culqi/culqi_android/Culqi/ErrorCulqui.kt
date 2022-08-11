package edu.pe.idat.proyectomovil.culqi.culqi_android.Culqi

data class ErrorCulqui
    (
    val charge_id: String,
    val codigo: String,
    val decline_code: String,
    val merchant_message: String,
    val `object`: String,
    val type: String,
    val user_message: String
)