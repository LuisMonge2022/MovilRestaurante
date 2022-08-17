package edu.pe.idat.proyectomovil.Service

import edu.pe.idat.proyectomovil.model.*
import retrofit2.Call
import retrofit2.http.*

interface MotorizadoService {

    @GET(value = "empleados")
    fun listaempleado (): Call<ListaEmpleado>

    @GET(value = "empleados/buscarxdni")
    fun buscarempleadoxdni(@Query(value="dni")dni:String):Call<Empleadom>



}