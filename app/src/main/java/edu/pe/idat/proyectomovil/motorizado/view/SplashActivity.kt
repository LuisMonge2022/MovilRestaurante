package edu.pe.idat.proyectomovil.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import edu.pe.idat.proyectomovil.LoginActivity
import edu.pe.idat.proyectomovil.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            startActivity(Intent(this, LoginMotorizadoActivity::class.java))
            finish()
        }, 6000)
    }
}