package edu.pe.idat.proyectomovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import edu.pe.idat.proyectomovil.databinding.ActivityMainBinding
import edu.pe.idat.proyectomovil.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        ///setContentView(R.layout.activity_main)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnloguiarse.setOnClickListener(this)
        binding.btnCarrito.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btnloguiarse -> irLogin()
            R.id.btnCarrito -> irCarrito()

        }
    }

    private fun irCarrito() {
        Toast.makeText(applicationContext, "En desarrollo",Toast.LENGTH_LONG).show()
    }

    private fun irLogin() {

        val intent = Intent(this,
            LoginActivity::class.java)
        startActivity(intent)
    }

}