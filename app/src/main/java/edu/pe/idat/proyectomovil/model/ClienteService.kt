package edu.pe.idat.proyectomovil.model

import retrofit2.Call
import retrofit2.http.GET

interface ClienteService {
    @GET(value = "clientes")
    fun listaclientes (): Call<List<ClienteItem>>
}