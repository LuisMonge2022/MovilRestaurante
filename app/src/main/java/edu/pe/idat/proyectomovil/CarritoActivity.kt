package edu.pe.idat.proyectomovil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import edu.pe.idat.proyectomovil.databinding.ActivityCarritoBinding
import edu.pe.idat.proyectomovil.databinding.ActivityMenuBinding
import edu.pe.idat.proyectomovil.model.*
import kotlinx.android.synthetic.main.activity_carrito.*
import kotlinx.android.synthetic.main.activity_lista_productos.*

class CarritoActivity : AppCompatActivity() , View.OnClickListener{

    private lateinit var binding: ActivityCarritoBinding


    val listaCarrito = listOf<Carrito>(Carrito(1,"Pollo","Rico y sabroso", 2,50.0, 15.0),
        Carrito(2,"Pollo","Rico y sabroso", 2,50.0, 15.0),
        Carrito(3,"Arroz","Rico ", 2,50.0, 15.0),
        Carrito(4,"AZUCAR","Rico Y DULCE", 2,50.0, 15.0),)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarritoBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.btnCarritoComprar.setOnClickListener(this)
        //val subtotal = rvCarrito.findViewHolderForItemId(R.id.txtpreciocarrito)

       // traerRecycler(listaCarrito)

        rvCarrito.layoutManager = LinearLayoutManager(this)
        val adapter = CarritoAdapter(listaCarrito)
        rvCarrito.adapter = adapter
        binding.txtsubtotal.setText(obtenersubtotal().toString())
        //val un = rvCarrito.get(4)
        //Toast.makeText(applicationContext, "En desarrollo ",Toast.LENGTH_LONG).show()
    }

    private fun  obtenersubtotal(): Double{
        var suma:Double=0.0
        for (car : Carrito in listaCarrito){
            suma= suma + car.subtotal
        }
        return suma
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }


    /*fun traerRecycler(listaCarrito: List<Carrito>){
        rvCarrito.layoutManager = LinearLayoutManager(this)
        val adapter = CarritoAdapter(listaCarrito)
        rvCarrito.adapter = adapter
    }*/
}