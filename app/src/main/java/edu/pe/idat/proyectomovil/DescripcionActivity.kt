package edu.pe.idat.proyectomovil

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.R
import edu.pe.idat.proyectomovil.Service.ProductoService
import edu.pe.idat.proyectomovil.databinding.ActivityDescripcionBinding
import edu.pe.idat.proyectomovil.model.Carrito
import edu.pe.idat.proyectomovil.model.Producto
import edu.pe.idat.proyectomovil.repository.Conexion
import edu.pe.idat.proyectomovil.utilitarios.RestEngine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DescripcionActivity : AppCompatActivity() , View.OnClickListener{

    private lateinit var binding: ActivityDescripcionBinding

    private lateinit var producto: Producto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDescripcionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sp = binding.spncantidaddescripcion
        var lista = listOf<Int>(1,2,3,4,5,6,7,8,9,10)

        val adap = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,lista)
        sp.adapter=adap
        sp.setSelection(0)

        binding.btnAgregarCarrito .setOnClickListener(this)
        binding.btnregrecat.setOnClickListener(this)

        var codigo = intent.getIntExtra("codigo",0)

        if (codigo.equals(0)){
            binding.txtNombreDescripcion.text="vacio"
        }else{
            val productoService : ProductoService = RestEngine.getRestEngine().create(ProductoService::class.java)
            val result: Call<Producto> = productoService.listarProductosxCodigo(codigo)
            result.enqueue(object : Callback<Producto> {
                override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                    val prod= response.body()
                    if (prod!= null){
                        producto = prod
                        val cadena: String="img"+prod.codproducto.toString()
                        binding.imgdescrip.setImageURI(
                            Uri.parse("android.resource://edu.pe.idat.proyectomovil/drawable/"+cadena))
                        binding.txtNombreDescripcion.text=prod.nombre
                        binding.txtDescripcionDescripcion.text=prod.descripcion
                        binding.txtPrecioDescripcion.text=prod.precio.toString()

                    }else{
                    }
                }
                override fun onFailure(call: Call<Producto>, t: Throwable) {
                    Toast.makeText(this@DescripcionActivity,"Terrible", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            binding.btnregrecat.id -> regresarMenu()
            binding.btnAgregarCarrito.id -> agregarCarrito(producto)
        }
    }

    private fun agregarCarrito(producto: Producto) {
        var cantidad = binding.spncantidaddescripcion.selectedItem.toString().toInt()
        val carrito=Carrito(producto.codproducto,producto.nombre,producto.descripcion,cantidad,producto.precio, cantidad * producto.precio)
        var conexion = Conexion(this)
        var verificar= conexion.buscarCarrito(producto.codproducto)
        if (verificar.cantidad>0) {
            carrito.cantidad=carrito.cantidad+verificar.cantidad
            var resultado1= conexion.actualizarCarrito(carrito)
            if (resultado1 >0) {
                Toast.makeText(this@DescripcionActivity, "Producto ingresado $resultado1", Toast.LENGTH_SHORT)
                    .show()
            }else{
                Toast.makeText(this@DescripcionActivity, "No se pudo agregar al carrito", Toast.LENGTH_SHORT)
                    .show()
            }
        }else{
            var resultado=conexion.guardarCarrito(carrito)
            if (resultado >0) {
                Toast.makeText(this@DescripcionActivity, "Producto ingresado $resultado", Toast.LENGTH_SHORT)
                    .show()
            }else{
                Toast.makeText(this@DescripcionActivity, "No se pudo agregar al carrito", Toast.LENGTH_SHORT)
                    .show()
            }
        }


    }

    private fun regresarMenu() {
        val intent = Intent(this,
            MenuActivity::class.java)
        startActivity(intent)
        finish()

    }
}