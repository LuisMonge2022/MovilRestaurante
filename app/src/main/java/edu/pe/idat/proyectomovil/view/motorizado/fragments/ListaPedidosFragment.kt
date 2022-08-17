package edu.pe.idat.proyectomovil.view.motorizado.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.pe.idat.proyectomovil.R

import edu.pe.idat.proyectomovil.Service.PedidoService
import edu.pe.idat.proyectomovil.databinding.FragmentListaPedidosBinding
import edu.pe.idat.proyectomovil.model.Detalle
import edu.pe.idat.proyectomovil.model.Pedido
import edu.pe.idat.proyectomovil.utilitarios.RestEngine

import edu.pe.idat.proyectomovil.view.motorizado.PedidoActivity
import edu.pe.idat.proyectomovil.view.motorizado.adapter.ListaPedidoAdapter
import kotlinx.android.synthetic.main.fragment_lista_pedidos.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListaPedidosFragment : Fragment() {

    private lateinit var adaptador: ListaPedidoAdapter
    private lateinit var recycler: RecyclerView
    private var lista2= ArrayList<Pedido>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =inflater.inflate(R.layout.fragment_lista_pedidos, container, false)
        recycler = view.findViewById<RecyclerView>(R.id.rvlistapedidos)
        traerPedidos (0)

        return view
    }

    private fun traerRecycler() {
        recycler.layoutManager= LinearLayoutManager(requireActivity())
        adaptador = ListaPedidoAdapter(lista2)
        recycler.adapter = adaptador
    }

    private fun traerPedidos(i: Int) {
        val pedidoService : PedidoService = RestEngine.getRestEngine().create(
            PedidoService::class.java)

        var result: Call<ArrayList<Pedido>> = pedidoService.listarPedidosporEnvio(i)
        result.enqueue(object :Callback<ArrayList<Pedido>>{

            override fun onResponse(
                call: Call<ArrayList<Pedido>>,
                response: Response<ArrayList<Pedido>>
            ) {
                lista2 = response.body()!!
                traerRecycler ()
            }

            override fun onFailure(call: Call<ArrayList<Pedido>>, t: Throwable) {
                lista2.add(Pedido(12,1,2, listOf(Detalle(1,2,2,25.0,25.0)),"aqui","biwn","20-10-022","","",45.0))
                traerRecycler ()
            }

        })

    }


}
