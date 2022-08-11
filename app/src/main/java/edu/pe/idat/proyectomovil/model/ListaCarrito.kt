package edu.pe.idat.proyectomovil.model

class ListaCarrito : ArrayList<Carrito>()

data class Carrito(

    var codproducto: Int,
    var nombre: String,
    var descripcion: String,
    var cantidad: Int,
    var precio: Double,
    var subtotal: Double,
    var codcliente: Int
    )