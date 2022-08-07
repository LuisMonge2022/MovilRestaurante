package edu.pe.idat.proyectomovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import edu.pe.idat.proyectomovil.databinding.ActivityCarritoBinding

import edu.pe.idat.proyectomovil.model.*
import edu.pe.idat.proyectomovil.repository.Conexion
import kotlinx.android.synthetic.main.activity_carrito.*


class CarritoActivity : AppCompatActivity() , View.OnClickListener{

    private lateinit var binding: ActivityCarritoBinding

    val lista = ListaCarrito()
    var conexion = Conexion(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarritoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnCarritoComprar.setOnClickListener(this)
        binding.btnRegresarCategoria.setOnClickListener(this)
        binding.btnActualizarCarrito.setOnClickListener(this)
        for (item in  conexion.listarCarrito()){
            lista.add(item)
        }
        if (lista.isEmpty()){
            recyclerVacío()
        }else{
            traerRecycler(lista)
        }

    }


    override fun onClick(v: View) {
        when(v.id){

            binding.btnRegresarCategoria.id-> IrMenu()
            binding.btnCarritoComprar.id-> continuarCompra()
            binding.btnActualizarCarrito.id -> actualizarRecycler()
        }
    }

    private fun actualizarRecycler() {
        if (lista.isNotEmpty()){
            lista.clear()
        }else{
            for (item in  conexion.listarCarrito()){
                lista.add(item)
            }
            if (lista.isEmpty()){
                recyclerVacío()
            }else{
                traerRecycler(lista)
            }
        }
    }


    private fun continuarCompra() {
        val intent = Intent(this,
            PasarellaActivity::class.java)
        startActivity(intent)
    }

    private fun IrMenu() {
        val intent = Intent(this,
            MenuActivity::class.java)
        startActivity(intent)
    }

    fun traerRecycler(listaCarrito: ListaCarrito){
        rvCarrito.layoutManager = LinearLayoutManager(this)
        val adapter = CarritoAdapter()
        rvCarrito.adapter = adapter
        adapter.getList(listaCarrito)
        var suma=0.0
        for(irem in listaCarrito){
            suma= suma +irem.subtotal
        }
        binding.txtsubtotal.text=suma.toString()
    }

    fun recyclerVacío(){
        var listavacia = ListaCarrito()
        listavacia.add(Carrito(0,"Aún no agregas nada",
            "No hay nada para mostrar",0,0.0,0.0))
        traerRecycler(listavacia )
    }


}