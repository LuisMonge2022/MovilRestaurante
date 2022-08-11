package edu.pe.idat.proyectomovil.model

class ListaClientes : ArrayList<Cliente>()

data class Cliente(
    var codcliente: Int,
    val xapellido: String,
    val xcontrasenia: String,
    val xdireccion: String,
    val xdni: String,
    val xemail: String,
    val xestado: String,
    var xnombre: String,
    val xtelefono: String
)