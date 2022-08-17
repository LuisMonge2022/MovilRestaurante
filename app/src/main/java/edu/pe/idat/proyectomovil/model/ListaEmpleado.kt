package edu.pe.idat.proyectomovil.model

class ListaEmpleado: ArrayList<Empleadom>()

data class Empleadom(
    val apellido: String,
    val codarea: Int,
    val codcargo: Int,
    val codempleado: Int,
    val contrasenia: String,
    val dni: String,
    val fecha_ingreso: String,
    val nombre: String
)