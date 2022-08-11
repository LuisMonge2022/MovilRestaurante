package edu.pe.idat.proyectomovil.Service


import edu.pe.idat.proyectomovil.model.Cliente
import retrofit2.Call
import retrofit2.http.*

interface ClienteService {
    @GET(value = "clientes")
    fun listaclientes (): Call<List<Cliente>>

    @GET(value = "clientes/buscarxEmail/{email}")
    fun buscarxEmail(@Path("email")email:String):Call<Cliente>

    @POST(value = "clientes/registrar")
    fun registrarCliente(@Body cliente: Cliente):Call<Void>

    @PUT(value = "clientes")
    fun actualizarCliente(@Body cliente: Cliente):Call<Void>

}