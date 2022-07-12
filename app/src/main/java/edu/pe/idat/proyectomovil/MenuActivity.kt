package edu.pe.idat.proyectomovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import edu.pe.idat.proyectomovil.Service.ClienteService
import edu.pe.idat.proyectomovil.Service.ProductoService
import edu.pe.idat.proyectomovil.databinding.ActivityMainBinding
import edu.pe.idat.proyectomovil.databinding.ActivityMenuBinding
import edu.pe.idat.proyectomovil.model.Cliente
import edu.pe.idat.proyectomovil.model.ListaProductos
import edu.pe.idat.proyectomovil.utilitarios.RestEngine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        ///setContentView(R.layout.activity_main)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnloguiarse.setOnClickListener(this)
        binding.btnCarrito.setOnClickListener(this)
        binding.btnCombos.setOnClickListener(this)
        binding.btnBrasa.setOnClickListener(this)
        binding.btnBroaster.setOnClickListener(this)
        binding.brnCriollo.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btnloguiarse -> irLogin()
            R.id.btnCarrito -> irCarrito()
            R.id.btnCombos -> verProductos(1)
            R.id.btnBrasa -> verProductos(2)
            R.id.btnBroaster -> verProductos(3)
            R.id.brnCriollo -> verProductos(4)

        }
    }
    //SE AGREGO PARAMETRO CODIGO
    private fun verProductos(codigocategoria :Int ) {

        //SE AGREGO PUT EXTRA
        val intent = Intent(this,
            ListaProductosActivity::class.java).apply { putExtra("codigocategoria",codigocategoria) }
        startActivity(intent)
    }

    private fun irCarrito() {

        val intent = Intent(this,
            CarritoActivity::class.java)
        //Toast.makeText(applicationContext, "En desarrollo",Toast.LENGTH_LONG).show()
        startActivity(intent)
    }

    private fun irLogin() {

        val intent = Intent(this,
            LoginActivity::class.java)
        startActivity(intent)
    }

}