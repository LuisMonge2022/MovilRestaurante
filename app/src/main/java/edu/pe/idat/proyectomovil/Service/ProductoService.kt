package edu.pe.idat.proyectomovil.Service

import edu.pe.idat.proyectomovil.model.ListaProductos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductoService {

    @GET(value ="productos")
    fun listarProductos() :Call  <ListaProductos>

    @GET(value ="productos/listarxCategoria")
    fun listarProductosxCategoria(@Query (value="codcategoria") codigo: Int) :Call  <ListaProductos>
}