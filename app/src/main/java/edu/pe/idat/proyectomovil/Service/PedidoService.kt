package edu.pe.idat.proyectomovil.Service

import edu.pe.idat.proyectomovil.model.Cliente
import edu.pe.idat.proyectomovil.model.Pedido
import retrofit2.Call
import retrofit2.http.*

interface PedidoService {

    @POST(value = "pedido/registrar")
    fun registrarPedido(@Body pedido: Pedido): Call<Void>

    @GET(value = "pedido/listarultimoxCliente")
    fun listarUltimoPedido(@Query(value="codcliente")codcliente:Int):Call<Pedido>

    @GET(value = "pedido/listarxEnvio")
    fun listarPedidosporEnvio(@Query(value="codenvio")codcliente:Int):Call<ArrayList<Pedido>>

    @GET(value = "pedido/buscar")
    fun buscarPedido(@Query(value="codpedido")codpedido:Int):Call<Pedido>

    @PUT(value = "pedido")
    fun actualizarPedido(@Body pedido: Pedido): Call<Void>
}