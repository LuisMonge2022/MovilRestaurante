package edu.pe.idat.proyectomovil.view.motorizado.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.pe.idat.proyectomovil.databinding.AuxiliarListapedidoBinding
import edu.pe.idat.proyectomovil.databinding.FragmentListaPedidosBinding
import edu.pe.idat.proyectomovil.model.Pedido

class ListaPedidoAdapter (private var lispedido: List<Pedido>): RecyclerView.Adapter<ListaPedidoAdapter.PedidoHolder>(){

    inner class PedidoHolder (val binding: AuxiliarListapedidoBinding): RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoHolder {
       val binding= AuxiliarListapedidoBinding
           .inflate(LayoutInflater.from(parent.context),parent,false)
        return PedidoHolder(binding)
    }

    override fun onBindViewHolder(holder: PedidoHolder, position: Int) {
       with(holder){
           with(lispedido[position]){
               binding.tvnombre.text = codcliente.toString()
               binding.tvdireccion.text=direccion
               binding.tvmonto.text= monto.toString()
               binding.tvtelefono.text="espera"
           }
       }
    }

    override fun getItemCount() = lispedido.size


}