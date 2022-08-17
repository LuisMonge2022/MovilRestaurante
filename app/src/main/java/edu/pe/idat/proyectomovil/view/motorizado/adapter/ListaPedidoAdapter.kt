package edu.pe.idat.proyectomovil.view.motorizado.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import edu.pe.idat.proyectomovil.R
import edu.pe.idat.proyectomovil.Service.ClienteService
import edu.pe.idat.proyectomovil.model.Cliente
import edu.pe.idat.proyectomovil.model.Pedido
import edu.pe.idat.proyectomovil.utilitarios.RestEngine
import edu.pe.idat.proyectomovil.view.motorizado.PedidoActivity
import kotlinx.android.synthetic.main.auxiliar_listapedido.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaPedidoAdapter (private var lispedido: List<Pedido>): RecyclerView.Adapter<ListaPedidoAdapter.PedidoHolder>(){

    inner class PedidoHolder (val view:View) :RecyclerView.ViewHolder(view) {

        init {
        }
        fun render(pedido: Pedido){

            if (pedido.codpedido==0){
                view.setOnClickListener{
                    Toast.makeText(view.context,"NADA QUE MOSTRAR", Toast.LENGTH_SHORT).show()
                }
            }else {
                view.setOnClickListener {
                    var intent = Intent(view.context, PedidoActivity::class.java)
                    intent.putExtra("codigo", pedido.codpedido)
                    view.context.startActivity(intent)

                }
            }
            val clienteService : ClienteService = RestEngine.getRestEngine().create(ClienteService::class.java)
            val result: Call<Cliente> = clienteService.buscarCliente(pedido.codcliente)

            result.enqueue(object : Callback<Cliente> {
                override fun onResponse(call: Call<Cliente>, response: Response<Cliente>) {
                    var cliente= response.body()

                    if (cliente != null) {
                        view.tvnombre.text = cliente.xnombre + " " +cliente.xapellido
                        view.tvubicacion.text = pedido.direccion
                        view.tvtelefono.text = cliente.xtelefono
                        view.tvmonto.text = pedido.monto.toString()
                    }
                    view.tvubicacion.text = pedido.direccion
                    view.tvtelefono.text = pedido.fechacreacion
                    view.tvmonto.text = pedido.monto.toString()
                }

                override fun onFailure(call: Call<Cliente>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
            view.tvnombre.text = pedido.codcliente.toString()
            view.tvubicacion.text = pedido.direccion
            view.tvtelefono.text = pedido.fechacreacion

        }



    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PedidoHolder(layoutInflater.inflate(R.layout.auxiliar_listapedido,parent,false))
    }

    override fun onBindViewHolder(holder: PedidoHolder, position: Int) {
        holder.render(lispedido[position])
    }

    override fun getItemCount(): Int = lispedido.size



}