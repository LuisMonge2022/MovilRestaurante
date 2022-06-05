package edu.pe.idat.proyectomovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import edu.pe.idat.proyectomovil.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ///setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btningresar.setOnClickListener(this)
        binding.btnregistrarse.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btningresar -> irMenu()
            R.id.btnregistrarse -> irRegistrarse()

        }

    }

    private fun irRegistrarse() {
        val intent = Intent(this,
            RegistroActivity::class.java)
        startActivity(intent)
    }

    private fun irMenu() {
        val intent = Intent(this,
            MenuActivity::class.java)
        startActivity(intent)
    }
}