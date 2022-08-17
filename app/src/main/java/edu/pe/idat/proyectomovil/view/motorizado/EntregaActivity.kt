package edu.pe.idat.proyectomovil.view.motorizado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import edu.pe.idat.proyectomovil.R
import edu.pe.idat.proyectomovil.Service.PedidoService
import edu.pe.idat.proyectomovil.databinding.ActivityEntregaBinding
import edu.pe.idat.proyectomovil.databinding.ActivityPedidoBinding
import edu.pe.idat.proyectomovil.model.Detalle
import edu.pe.idat.proyectomovil.model.Pedido
import edu.pe.idat.proyectomovil.utilitarios.RestEngine
import edu.pe.idat.proyectomovil.view.cliente.RegistroActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EntregaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityEntregaBinding
    private  var codigo :Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntregaBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_entrega)
        setContentView(binding.root)

        binding.btnguardarEntrega.setOnClickListener(this)


        codigo = intent.getIntExtra("codigopedido",0)

    }

    override fun onClick(v: View) {
        var receptor =binding.edtreceptor.text.toString()
        var documento = binding.edtdocumento.text.toString()
       if (receptor.isNullOrEmpty() || documento.isNullOrEmpty()){
           Toast.makeText(this@EntregaActivity,"Complete los datos",Toast.LENGTH_LONG).show()
       }else{
           traerPedido(codigo,receptor +" / "+ documento)

       }
    }


    private fun traerPedido(codigo: Int, motivo: String) {
        val pedidoService : PedidoService = RestEngine.getRestEngine().create(
            PedidoService::class.java)
        var result: Call<Pedido> = pedidoService.buscarPedido(codigo)
        result.enqueue(object : Callback<Pedido> {
            override fun onResponse(call: Call<Pedido>, response: Response<Pedido>) {
                val pedido = response.body()

                if (pedido?.codpedido != null) {
                    pedido.motivo = motivo
                    pedido.estadopedido = "ENTREGADO"
                    registrarEntrega(pedido)
                }
            }
            override fun onFailure(call: Call<Pedido>, t: Throwable) {
                Toast.makeText(this@EntregaActivity, "Algo falló", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun registrarEntrega(pedido: Pedido) {
        val pedidoService: PedidoService = RestEngine.getRestEngine().create(
            PedidoService::class.java)
        var result: Call<Void> = pedidoService.actualizarPedido(pedido)

        result.enqueue(object: Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(this@EntregaActivity, "PEDIDO ENTREGADO", Toast.LENGTH_LONG).show()
                val intent = Intent(this@EntregaActivity,
                    MotorizadoActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
}