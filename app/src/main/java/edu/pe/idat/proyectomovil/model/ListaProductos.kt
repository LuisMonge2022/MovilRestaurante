package edu.pe.idat.proyectomovil.model

class ListaProductos : ArrayList<Producto>()

data class Producto(
    val codcategoria: Int,
    val codproducto: Int,
    val descripcion: String,
    val nombre: String,
    val precio: Double
)