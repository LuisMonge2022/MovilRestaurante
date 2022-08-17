package edu.pe.idat.proyectomovil.view.motorizado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import edu.pe.idat.proyectomovil.R
import edu.pe.idat.proyectomovil.Service.PedidoService
import edu.pe.idat.proyectomovil.databinding.ActivityEntregaBinding
import edu.pe.idat.proyectomovil.databinding.ActivityNoEntregaBinding
import edu.pe.idat.proyectomovil.model.Pedido
import edu.pe.idat.proyectomovil.utilitarios.RestEngine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoEntregaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityNoEntregaBinding
    private  var codigo :Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityNoEntregaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnguardarNoentrega.setOnClickListener(this)


        codigo = intent.getIntExtra("codigopedido",0)

        val sp = binding.spNoentrega
        var lista = listOf<String>(
            "Cliente ausente","Dirección no encontrada","Cliente rechaza pedido","Fuera de cobertura","Empaque dañado","Otros")

        val adap = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,lista)
        sp.adapter=adap
        sp.setSelection(0)
    }

    override fun onClick(v: View?) {
        var motivo = binding.spNoentrega.selectedItem.toString()+ "/ "+binding.edtcomentario.text.toString()
        traerPedido(codigo, motivo)
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
                    pedido.estadopedido = "NO ENTREGADO"
                    registrarEntrega(pedido)
                }
            }
            override fun onFailure(call: Call<Pedido>, t: Throwable) {
                Toast.makeText(this@NoEntregaActivity, "Algo falló", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun registrarEntrega(pedido: Pedido) {
        val pedidoService: PedidoService = RestEngine.getRestEngine().create(
            PedidoService::class.java)
        var result: Call<Void> = pedidoService.actualizarPedido(pedido)

        result.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(this@NoEntregaActivity, "PEDIDO NO ENTREGADO", Toast.LENGTH_LONG).show()
                val intent = Intent(this@NoEntregaActivity,
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