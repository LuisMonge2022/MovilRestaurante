package edu.pe.idat.proyectomovil.model

import android.view.View

class ListaProductos : ArrayList<Producto>()

data class Producto(
    val codcategoria: Int,
    val codproducto: Int,
    val descripcion: String,
    val nombre: String,
    val precio: Double

)