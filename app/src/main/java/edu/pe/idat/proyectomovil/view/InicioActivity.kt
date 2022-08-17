package edu.pe.idat.proyectomovil.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import edu.pe.idat.proyectomovil.databinding.ActivityInicioBinding
import edu.pe.idat.proyectomovil.view.cliente.LoginActivity

import edu.pe.idat.proyectomovil.view.motorizado.SplashActivity

class InicioActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityInicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgCliente.setOnClickListener(this)
        binding.ingEmpleado.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        when(v.id){
            binding.imgCliente.id -> irLoginCliente()
            binding.ingEmpleado.id -> irLoginEmpleado()
        }
    }

    private fun irLoginEmpleado() {
        val intent = Intent(this,
            SplashActivity::class.java)
        startActivity(intent)
    }

    private fun irLoginCliente() {
        val intent = Intent(this,
            LoginActivity::class.java)
        startActivity(intent)
    }
}