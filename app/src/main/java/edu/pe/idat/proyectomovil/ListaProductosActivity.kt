package edu.pe.idat.proyectomovil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import edu.pe.idat.proyectomovil.databinding.ActivityListaProductosBinding
import edu.pe.idat.proyectomovil.databinding.ActivityMainBinding
import edu.pe.idat.proyectomovil.model.Producto
import edu.pe.idat.proyectomovil.model.ProductosAdapter
import kotlinx.android.synthetic.main.activity_lista_productos.*

class ListaProductosActivity : AppCompatActivity() {



    val listaprod = listOf(
        Producto(1,1,"Pollo rico y sabroso","Pollo",20.50),
        Producto(1,2,"Pollo rico y  no sabroso","Pollo feo",0.50),
        Producto(1,3,"Pollo cocción a la inglesa","Pollo crudo",10.50),
        Producto(1,4,"Pollo cocción a la bolgnesa","Pollo rojo",10.50),
        Producto(1,5,"Pollo cocción a la olla","Pollo sillao",10.50),
        Producto(1,6,"Pollo cocción a la sarten","Pollo frito",10.50),
        Producto(1,78,"Pollo cocción al vapor","Pollo sancochado",10.50)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_productos)
        traerRecycler()
        //binding.btningresar.setOnClickListener(this)
        //binding.btnregistrarse.setOnClickListener(this)
    }

    fun traerRecycler(){
        rvProductos.layoutManager = LinearLayoutManager(this)
        val adapter = ProductosAdapter(listaprod)
        rvProductos.adapter = adapter
    }
}