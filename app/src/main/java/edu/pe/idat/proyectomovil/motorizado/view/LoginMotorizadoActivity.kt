package edu.pe.idat.proyectomovil.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.pe.idat.proyectomovil.R
import edu.pe.idat.proyectomovil.RegistroActivity
import edu.pe.idat.proyectomovil.databinding.ActivityLoginMotorizadoBinding
import edu.pe.idat.proyectomovil.utilitarios.AppMensaje
import edu.pe.idat.proyectomovil.utilitarios.TipoMensaje


class LoginMotorizadoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginMotorizadoBinding
    //private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginMotorizadoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //authViewModel = ViewModelProvider(this)
            //.get(AuthViewModel::class.java)
        binding.btnregistrar.setOnClickListener(this)
        binding.btnlogin.setOnClickListener(this)
        //authViewModel.responseLogin.observe(this,
        //Observer {
        //    obtenerDatosLogin(it!!)
        //})

    }

    private fun obtenerDatosLogin() {

        /*if (responseLogin.rpta){
            startActivity(Intent(applicationContext,
                MotorizadoActivity::class.java))
            finish()
        }else{
            AppMensaje.enviarMensaje(binding.root,
            "Usuario o password incorrecto",
            TipoMensaje.ERROR)
        }
        binding.btnlogin.isEnabled = true*/
        startActivity(Intent(applicationContext,
            MotorizadoActivity::class.java))

    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btnlogin -> obtenerDatosLogin()
            R.id.btnregistrar -> irRegistroUsuario()

        }
    }

    private fun irRegistroUsuario() {
        startActivity(Intent(applicationContext,
        RegistroMotorizadoActivity::class.java))
    }

    private fun autenticarUsuario() {
        binding.btnlogin.isEnabled = false
        var okLogin = true
        if (binding.etusuario.text.toString().trim().isEmpty()){
            binding.etusuario.isFocusableInTouchMode = true
            binding.etusuario.requestFocus()
            okLogin = false
        }else if(binding.etpassword.text.toString().trim().isEmpty()) {
            binding.etpassword.isFocusableInTouchMode = true
            binding.etpassword.requestFocus()
            okLogin = false
        }
        /*if (okLogin){
            authViewModel.autenticarUsuario(binding.etusuario.text.toString(),
                binding.etpassword.text.toString())
        }else{
            binding.btnlogin.isEnabled = true
            AppMensaje.enviarMensaje(binding.root,
                "Ingrese su usuario y password",
                TipoMensaje.ERROR)
        }*/

    }

}