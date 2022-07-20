package edu.pe.idat.proyectomovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import edu.pe.idat.proyectomovil.Service.ProductoService
import edu.pe.idat.proyectomovil.databinding.ActivityCategoriaBinding
import edu.pe.idat.proyectomovil.databinding.ActivityListaProductosBinding
import edu.pe.idat.proyectomovil.databinding.ActivityMainBinding
import edu.pe.idat.proyectomovil.databinding.ActivityMenuBinding
import edu.pe.idat.proyectomovil.model.ListaProductos
import edu.pe.idat.proyectomovil.model.Producto
import edu.pe.idat.proyectomovil.model.ProductosAdapter
import edu.pe.idat.proyectomovil.utilitarios.RestEngine
import kotlinx.android.synthetic.main.activity_lista_productos.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaProductosActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityListaProductosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityListaProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegmenu.setOnClickListener(this)

        // JALA EL PUTEXTRA DE MENUACTIVITY
        val codigocategoria = intent.getSerializableExtra("codigocategoria")
        setContentView(R.layout.activity_lista_productos)
        traerListaProductos(codigocategoria as Int)

        //traerRecycler()
        //binding.btningresar.setOnClickListener(this)
        //binding.btnregistrarse.setOnClickListener(this)
    }

    //LLAMARA AL SERVICE DE ACUERDO AL CODIGO INGRESADO
    private fun traerListaProductos(codigo: Int) {

        val productoService : ProductoService = RestEngine.getRestEngine().create(ProductoService::class.java)
        if (codigo == 0){
            val result: Call<ListaProductos> = productoService.listarProductos()
            result.enqueue(object : Callback<ListaProductos>{
                override fun onResponse(call: Call<ListaProductos>, response: Response<ListaProductos>) {
                    val listaProductos= response.body()
                    if (listaProductos != null) {
                        traerRecycler(listaProductos)
                    }

                }

                override fun onFailure(call: Call<ListaProductos>, t: Throwable) {
                    Toast.makeText(this@ListaProductosActivity,"SUCEDIÓ UN ERROR",Toast.LENGTH_SHORT).show()
                }
            })
        }else{
            val result: Call<ListaProductos> = productoService.listarProductosxCategoria(codigo)
            result.enqueue(object : Callback<ListaProductos>{
                override fun onResponse(call: Call<ListaProductos>, response: Response<ListaProductos>) {
                    val listaProductos= response.body()
                    if (listaProductos != null) {
                        traerRecycler(listaProductos)
                    }
                }
                override fun onFailure(call: Call<ListaProductos>, t: Throwable) {
                    Toast.makeText(this@ListaProductosActivity,"FATAL",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
 //MOSTRARA EL RECYCLER CON LA LISTA ENVIADA
    fun traerRecycler(listaprod : ListaProductos){
        rvProductos.layoutManager = LinearLayoutManager(this)
        val adapter = ProductosAdapter(listaprod)
        rvProductos.adapter = adapter
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btnRegmenu -> irMenu()
        }
    }

    private fun irMenu() {
        val intent = Intent(this,
            MenuActivity::class.java)
        startActivity(intent)
    }
}