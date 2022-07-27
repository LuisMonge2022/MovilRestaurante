package edu.pe.idat.proyectomovil

<<<<<<< HEAD

=======
>>>>>>> 3b22fba8df6d804e00cdb792b0d05cd0c5957f3a
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.LinearLayoutManager
import edu.pe.idat.proyectomovil.Service.ProductoService
import edu.pe.idat.proyectomovil.databinding.ActivityCategoriaBinding
import edu.pe.idat.proyectomovil.databinding.ActivityListaProductosBinding
<<<<<<< HEAD
=======
import edu.pe.idat.proyectomovil.databinding.ActivityMainBinding
import edu.pe.idat.proyectomovil.databinding.ActivityMenuBinding
>>>>>>> 3b22fba8df6d804e00cdb792b0d05cd0c5957f3a
import edu.pe.idat.proyectomovil.model.ListaProductos
import edu.pe.idat.proyectomovil.model.Producto
import edu.pe.idat.proyectomovil.model.ProductosAdapter
import edu.pe.idat.proyectomovil.utilitarios.RestEngine
import kotlinx.android.synthetic.main.activity_lista_productos.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

<<<<<<< HEAD
class ListaProductosActivity : AppCompatActivity() , View.OnClickListener {


    private lateinit var binding:ActivityListaProductosBinding
=======
class ListaProductosActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityListaProductosBinding
>>>>>>> 3b22fba8df6d804e00cdb792b0d05cd0c5957f3a

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityListaProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegmenu.setOnClickListener(this)

        // JALA EL PUTEXTRA DE MENUACTIVITY
        val codigocategoria = intent.getSerializableExtra("codigocategoria")
        binding= ActivityListaProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        traerListaProductos(codigocategoria as Int)
        binding.btnRegmenu.setOnClickListener(this)
        binding.btnBuscarpor.setOnClickListener(this)


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
                    }else{
                        recyclerVacío()
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
                    }else{
                        recyclerVacío()
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
        when (v.id) {
            R.id.btnRegmenu->  irMenu()
            R.id.btnBuscarpor-> Buscador()
        }
    }

    private fun irMenu() {
        val intent = Intent(this,
            MenuActivity::class.java)

        startActivity(intent)
    }

<<<<<<< HEAD
    private fun Buscador() {
        val buscar =binding.etBuscarpor.text.toString()
        if (buscar.trim()== "" || buscar.trim()== null){
            recyclerVacío()
        }else{
            val productoService : ProductoService = RestEngine.getRestEngine().create(ProductoService::class.java)
            if (buscar.isDigitsOnly()){
                val codigo:Int = buscar.toInt()
                val result: Call<Producto> = productoService.listarProductosxCodigo(codigo)
                result.enqueue(object : Callback <Producto> {
                    override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                        val producto= response.body()
                        if (producto!= null){
                            var listavacia = ListaProductos()
                            listavacia.add(producto)
                            traerRecycler(listavacia)
                        }else{
                            recyclerVacío()
                        }
                    }
                    override fun onFailure(call: Call<Producto>, t: Throwable) {
                        Toast.makeText(this@ListaProductosActivity,"FATAL",Toast.LENGTH_SHORT).show()
                    }
                })
            }else{
                val result: Call<ListaProductos> = productoService.listarProductosxPalabra(buscar)
                result.enqueue(object : Callback<ListaProductos>{
                    override fun onResponse(
                        call: Call<ListaProductos>,
                        response: Response<ListaProductos>
                    ) {
                        val listaProductos= response.body()
                        if (listaProductos != null) {
                            traerRecycler(listaProductos)
                        }else{
                            recyclerVacío()
                        }
                    }
                    override fun onFailure(call: Call<ListaProductos>, t: Throwable) {
                        Toast.makeText(this@ListaProductosActivity,"FATAL",Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }
    }

    fun recyclerVacío(){
        var listavacia = ListaProductos()
        listavacia.add(Producto(0,0,
            "No hay nada para mostrar","Producto no encontrado",0.00))
        traerRecycler(listavacia )
    }
=======
    override fun onClick(v: View) {
        when(v.id) {
            R.id.btnRegmenu -> irMenu()
        }
    }
>>>>>>> 3b22fba8df6d804e00cdb792b0d05cd0c5957f3a

    private fun irMenu() {
        val intent = Intent(this,
            MenuActivity::class.java)
        startActivity(intent)
    }
}