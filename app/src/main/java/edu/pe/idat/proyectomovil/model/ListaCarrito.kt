package edu.pe.idat.proyectomovil.model

class ListaCarrito : ArrayList<Carrito>()

data class Carrito(
    val codproducto: Int,
    val nombre: String,
    val descripcion: String,
    val cantidad: Int,
    val precio: Double,
    val subtotal: Double
    )