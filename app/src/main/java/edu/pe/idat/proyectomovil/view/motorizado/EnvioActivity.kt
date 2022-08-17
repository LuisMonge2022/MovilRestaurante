package edu.pe.idat.proyectomovil.view.motorizado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import edu.pe.idat.proyectomovil.R
import edu.pe.idat.proyectomovil.databinding.ActivityEnvioBinding
import edu.pe.idat.proyectomovil.databinding.ActivityLoginMotorizadoBinding
import edu.pe.idat.proyectomovil.repository.Conexion

class EnvioActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityEnvioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEnvioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var codigo = binding.edtcodigoenvio.text.toString()
        binding.btnbuscarEnvio.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var codigo = binding.edtcodigoenvio.text.toString()
        if (codigo.isNullOrEmpty()){
            Toast.makeText(this@EnvioActivity,"Complete los datos", Toast.LENGTH_LONG).show()
        }else{
            startActivity(
                Intent(applicationContext,
                    MotorizadoActivity::class.java)
            )
        }


    }
}