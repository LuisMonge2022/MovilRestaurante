package edu.pe.idat.proyectomovil.view.cliente


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import edu.pe.idat.proyectomovil.R
import edu.pe.idat.proyectomovil.databinding.ActivityDireccionBinding
import edu.pe.idat.proyectomovil.model.Cliente
import edu.pe.idat.proyectomovil.repository.Conexion


class DireccionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDireccionBinding
    private lateinit var cliente: Cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direccion)

        cliente = Conexion(this).listarCliente()
        binding = ActivityDireccionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btndireccion.setOnClickListener(this)

        binding.ibregresarCarrito.setOnClickListener(this)
        binding.btnpagar.setOnClickListener(this)


        binding.tvnombre.text= cliente.xnombre+ " "+cliente.xapellido
        binding.tvdireccionA.text=cliente.xdireccion
        var nuevadireccion = intent.getStringExtra("nuevadireccion")
        if (nuevadireccion.isNullOrBlank()){
            nuevadireccion = "-"
        }else{
            nuevadireccion = nuevadireccion.substring(9)
        }
        binding.tvdireccion.text = nuevadireccion
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btndireccion ->  irMapa()
            R.id.ibregresarCarrito -> irCarrito()
            R.id.btnpagar -> escogerFormarPago()
        }
    }

    private fun escogerFormarPago() {
        val intent = Intent(this,
            PasarellaActivity::class.java)
        startActivity(intent)
    }

    private fun irCarrito() {
        val intent = Intent(this,
            CarritoActivity::class.java)
        startActivity(intent)
    }

    private fun irMapa() {
        val intent = Intent(this,
            MapsActivity::class.java)
        startActivity(intent)

    }
}