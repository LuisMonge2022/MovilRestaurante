package edu.pe.idat.proyectomovil.view.cliente

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import edu.pe.idat.proyectomovil.R
import edu.pe.idat.proyectomovil.Service.PedidoService
import edu.pe.idat.proyectomovil.databinding.ActivityResumencompraBinding
import edu.pe.idat.proyectomovil.model.Cliente
import edu.pe.idat.proyectomovil.model.Detalle
import edu.pe.idat.proyectomovil.model.Pedido
import edu.pe.idat.proyectomovil.repository.Conexion
import edu.pe.idat.proyectomovil.utilitarios.RestEngine
import edu.pe.idat.proyectomovil.view.cliente.adapter.ProductosAdapter
import edu.pe.idat.proyectomovil.view.cliente.adapter.ResumenCompraAdapter
import kotlinx.android.synthetic.main.activity_lista_productos.*
import kotlinx.android.synthetic.main.activity_resumencompra.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResumencompraActivity : AppCompatActivity() , View.OnClickListener{

    private lateinit var binding:ActivityResumencompraBinding
    private lateinit var cliente: Cliente




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumencompra)

        binding = ActivityResumencompraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnInicio.setOnClickListener(this)
        cliente= Conexion(this@ResumencompraActivity).listarCliente()

        traerUltimoPedido(cliente.codcliente)
        binding.txtnombreResumen.text=cliente.xnombre + " " + cliente.xapellido
        binding.txttelefonoResumen.text=cliente.xtelefono
        binding.btnInicio.setOnClickListener(this)



    }

    private fun traerRecyclerResumen(detalle: List<Detalle>) {


        rvdetalleResumenCompra.layoutManager= LinearLayoutManager(this)
        val adapter = ResumenCompraAdapter(detalle)
        rvdetalleResumenCompra.adapter = adapter

    }

    private fun traerUltimoPedido(codcliente: Int) {
        val pedidoService: PedidoService =
            RestEngine.getRestEngine().create(PedidoService::class.java)
        val call = pedidoService.listarUltimoPedido(codcliente)
        call.enqueue( object :Callback<Pedido>{
            override fun onResponse(call: Call<Pedido>, response: Response<Pedido>) {
                val pedido = response.body()
                if (pedido?.codpedido!=null){
                    var listadetalle= ArrayList<Detalle>()
                    for (detalle in pedido.detalle){
                        var d=Detalle(detalle.cantidad,detalle.codpedido,detalle.codproducto,detalle.precio,detalle.subtotal)
                        listadetalle.add(d)
                    }
                    traerRecyclerResumen(listadetalle)
                    binding.txtfechaResumen.text=pedido?.fechacreacion
                    binding.txtdireccionResumen.text=pedido?.direccion
                    binding.txtcodpedido.text=pedido?.codpedido.toString()
                    //suma=Math.round(suma*100.00)/100.00
                    var subtotal = pedido.monto/1.18
                    subtotal=Math.round(subtotal*100.00)/100.00
                    var total = pedido.monto
                    total=Math.round(total*100.00)/100.00
                    var igv = total-subtotal
                    igv=Math.round(igv*100.00)/100.00
                    binding.tvsubtotalResumen.text=subtotal.toString()
                    binding.tvtotalResumen.text=total.toString()
                    binding.tvIGV.text=igv.toString()
                }else{
                    binding.txtfechaResumen.text="pedido?.fechacreacion"
                    binding.tvsubtotalResumen.text="pedido.fechacreacion"
                    binding.tvtotalResumen.text="pedido.monto.toString()"
                    binding.txtdireccionResumen.text="pedido.direccion"
                    binding.txtcodpedido.text="pedido?.codpedido.toString()"
                }

            }

            override fun onFailure(call: Call<Pedido>, t: Throwable) {
                binding.txtfechaResumen.text="pedido?.fechacreacion"
                binding.tvsubtotalResumen.text="pedido.fechacreacion"
                binding.tvtotalResumen.text="pedido.monto.toString()"
                binding.txtdireccionResumen.text="pedido.direccion"
                binding.txtcodpedido.text="pedido?.codpedido.toString()"
            }
        })
    }

    override fun onClick(v: View?) {
        val intent = Intent(this,
            MenuActivity::class.java)
        startActivity(intent)
    }
}