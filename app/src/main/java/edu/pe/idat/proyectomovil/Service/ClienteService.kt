package edu.pe.idat.proyectomovil.Service

import edu.pe.idat.proyectomovil.model.Cliente
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ClienteService {
    @GET(value = "clientes")
    fun listaclientes (): Call<List<Cliente>>

    @GET(value = "clientes/buscarxEmail/{email}")
    fun buscarxEmail(@Path("email")email:String):Call<Cliente>
}