package edu.pe.idat.proyectomovil.view.cliente

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import edu.pe.idat.proyectomovil.R
import edu.pe.idat.proyectomovil.databinding.ActivityMenuBinding
import edu.pe.idat.proyectomovil.model.Cliente
import edu.pe.idat.proyectomovil.repository.Conexion

class MenuActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var cliente:Cliente
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cliente = Conexion(this).listarCliente()
        binding.txtusuario.text= cliente.xnombre +" " +cliente.xapellido
        binding.btnloguiarse.setOnClickListener(this)
        binding.btnCarrito.setOnClickListener(this)
        binding.btnCombos.setOnClickListener(this)
        binding.btnBrasa.setOnClickListener(this)
        binding.btnBroaster.setOnClickListener(this)
        binding.brnCriollo.setOnClickListener(this)
        binding.imgMisDatos.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btnloguiarse -> irLogin()
            R.id.btnCarrito -> irCarrito()
            R.id.btnCombos -> verProductos(1)
            R.id.btnBrasa -> verProductos(2)
            R.id.btnBroaster -> verProductos(3)
            R.id.brnCriollo -> verProductos(4)
            R.id.imgMisDatos -> verMisDatos()

        }
    }

    private fun verMisDatos() {
        var codigo= cliente.codcliente
        val intent = Intent(this,
            RegistroActivity::class.java).apply { putExtra("codigo",codigo) }
        startActivity(intent)
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
        startActivity(intent)
    }

    private fun irLogin() {
        Conexion(this).eliminarTodoClienteDB()
            val intent = Intent(this,
                LoginActivity::class.java)
            startActivity(intent)
            finish()

    }

}