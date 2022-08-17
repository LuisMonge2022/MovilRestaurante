package edu.pe.idat.proyectomovil.model

data class Pedido(
    var codcliente: Int,
    val codenvio: Int,
    var codpedido: Int,
    val detalle: List<Detalle>,
    val direccion: String,
    val ubicacion: String,
    var estadopedido: String,
    var motivo: String,
    val fechacreacion: String,
    val monto: Double
)

data class Detalle(
    val cantidad: Int,
    val codpedido: Int,
    var codproducto: Int,
    val precio: Double,
    val subtotal: Double
)