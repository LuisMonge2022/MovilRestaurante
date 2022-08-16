package edu.pe.idat.proyectomovil.view.motorizado.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import edu.pe.idat.proyectomovil.R
import edu.pe.idat.proyectomovil.Service.ClienteService
import edu.pe.idat.proyectomovil.Service.PedidoService
import edu.pe.idat.proyectomovil.databinding.FragmentListaPedidosBinding
import edu.pe.idat.proyectomovil.model.Detalle
import edu.pe.idat.proyectomovil.model.Pedido
import edu.pe.idat.proyectomovil.utilitarios.RestEngine
import edu.pe.idat.proyectomovil.view.motorizado.adapter.ListaPedidoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListaPedidosFragment : Fragment() {

    private var _binding: FragmentListaPedidosBinding?= null

    private val binding get() = _binding!!

    private val lista2= ArrayList<Pedido>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaPedidosBinding.inflate(inflater,container, false)
        traerPedidos (0)
        binding.rvlistapedidos.layoutManager = LinearLayoutManager(requireActivity())
        //binding.rvlistapedidos.adapter = ListaPedidoAdapter(lista)
        return binding.root
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
                var lista = response.body()
                if (lista?.isEmpty()==false){
                    binding.rvlistapedidos.adapter = ListaPedidoAdapter(lista)
                }else{
                    lista2.add(Pedido(12,1,2, listOf(Detalle(1,2,2,25.0,25.0)),"aqui","biwn","20-10-022",45.0))
                    binding.rvlistapedidos.adapter = ListaPedidoAdapter(lista2)
                }
            }

            override fun onFailure(call: Call<ArrayList<Pedido>>, t: Throwable) {
                Toast.makeText(context,"NOOO",Toast.LENGTH_LONG).show()
            }

        })

    }


}