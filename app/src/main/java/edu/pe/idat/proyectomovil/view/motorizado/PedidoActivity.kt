package edu.pe.idat.proyectomovil.view.motorizado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import edu.pe.idat.proyectomovil.R
import edu.pe.idat.proyectomovil.Service.ClienteService
import edu.pe.idat.proyectomovil.Service.PedidoService
import edu.pe.idat.proyectomovil.databinding.ActivityPedidoBinding
import edu.pe.idat.proyectomovil.databinding.ActivityRegistroBinding
import edu.pe.idat.proyectomovil.model.Cliente
import edu.pe.idat.proyectomovil.model.Detalle
import edu.pe.idat.proyectomovil.model.Pedido
import edu.pe.idat.proyectomovil.utilitarios.RestEngine
import edu.pe.idat.proyectomovil.view.cliente.adapter.ResumenCompraAdapter
import kotlinx.android.synthetic.main.activity_pedido.*
import kotlinx.android.synthetic.main.activity_resumencompra.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PedidoActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityPedidoBinding
    private var pedido:Pedido?= null

    private lateinit var cliente: Cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //(R.layout.activity_pedido)

        binding = ActivityPedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnentrega.setOnClickListener(this)
        binding.btnnoentrega.setOnClickListener(this)
        var codigo = intent.getIntExtra("codigo",0)

        traerPedido(codigo)

    }

    private fun traerPedido(codigo: Int) {
        val pedidoService : PedidoService = RestEngine.getRestEngine().create(
            PedidoService::class.java)
        var result: Call<Pedido> = pedidoService.buscarPedido(codigo)
        result.enqueue(object : Callback<Pedido> {
            override fun onResponse(call: Call<Pedido>, response: Response<Pedido>) {
                pedido = response.body()

                if (pedido?.codpedido != null) {
                    val listadetalle = ArrayList<Detalle>()
                    for (detalle in pedido?.detalle!!) {
                        var d = Detalle(
                            detalle.cantidad,
                            detalle.codpedido,
                            detalle.codproducto,
                            detalle.precio,
                            detalle.subtotal
                        )
                        listadetalle.add(d)
                    }
                    traerRecycler(listadetalle)
                    binding.txtPedido.text= "PEDIDO N.- "+pedido?.codpedido.toString()
                    binding.txtdireccionPedido.text= pedido?.direccion
                    pedido?.codcliente?.let { traerCliente(it) }
                }
            }
            override fun onFailure(call: Call<Pedido>, t: Throwable) {
                Toast.makeText(this@PedidoActivity, "Algo falló", Toast.LENGTH_LONG).show()
            }
        })
    }

   private fun traerCliente(codcliente: Int) {
        val clienteService : ClienteService = RestEngine.getRestEngine().create(ClienteService::class.java)
        val result: Call<Cliente> = clienteService.buscarCliente(codcliente)
        result.enqueue(object :Callback<Cliente>{
            override fun onResponse(call: Call<Cliente>, response: Response<Cliente>) {
                cliente= response.body()!!

                binding.txtclientePedido.text=cliente.xnombre+ " " +cliente.xapellido

                binding.txttelefonoPedido.text = cliente.xtelefono
            }

            override fun onFailure(call: Call<Cliente>, t: Throwable) {
                binding.txtclientePedido.text="Nadie"
                binding.txtdireccionPedido.text = "Ningún"
                binding.txttelefonoPedido.text = "Nada"
            }

        })
    }

    private fun traerRecycler(listadetalle: List<Detalle>) {
        rvdetallePedido.layoutManager= LinearLayoutManager(this)
        val adapter = ResumenCompraAdapter(listadetalle)
        rvdetallePedido.adapter = adapter
    }

    override fun onClick(v: View) {
        when (v.id){
            binding.btnnoentrega.id -> startActivity(Intent(applicationContext,
                NoEntregaActivity::class.java).apply  { putExtra("codigopedido",pedido?.codpedido) }
            )

            binding.btnentrega.id -> startActivity(
                Intent(applicationContext,
                    EntregaActivity::class.java).apply  { putExtra("codigopedido",pedido?.codpedido) }
            )
        }
    }
}