package edu.pe.idat.proyectomovil.Service

import edu.pe.idat.proyectomovil.model.ListaProductos
import edu.pe.idat.proyectomovil.model.Producto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductoService {

    @GET(value ="productos")
    fun listarProductos() :Call  <ListaProductos>

    @GET(value ="productos/listarxCategoria")
    fun listarProductosxCategoria(@Query (value="codcategoria") codigo: Int) :Call  <ListaProductos>

    @GET(value ="productos/listarxPalabra")
    fun listarProductosxPalabra(@Query (value="palabra") palabra: String) :Call  <ListaProductos>

    @GET(value ="productos/buscar")
    fun listarProductosxCodigo(@Query (value="codproducto") codigo: Int) :Call <Producto>
}