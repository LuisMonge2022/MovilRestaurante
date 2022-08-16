package edu.pe.idat.proyectomovil.view.cliente

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import edu.pe.idat.proyectomovil.R

class CarritoSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito_splash)
        Handler().postDelayed({
            startActivity(Intent(this, CarritoActivity::class.java))
            finish()
        },2000)
    }
}