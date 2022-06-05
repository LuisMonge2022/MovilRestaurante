package edu.pe.idat.proyectomovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import edu.pe.idat.proyectomovil.databinding.ActivityMainBinding
import edu.pe.idat.proyectomovil.model.Cliente
import edu.pe.idat.proyectomovil.model.ClienteItem
import edu.pe.idat.proyectomovil.model.ClienteService
import edu.pe.idat.proyectomovil.utilitarios.RestEngine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val clienteService : ClienteService = RestEngine.getRestEngine().create(ClienteService::class.java)
        val result: Call<List<ClienteItem>> = clienteService.listaclientes()

        result.enqueue(object  : Callback<List<ClienteItem>>{
            override fun onFailure(call: Call<List<ClienteItem>>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<ClienteItem>>,
                response: Response<List<ClienteItem>>
            ) {
                Toast.makeText(this@LoginActivity, "OK", Toast.LENGTH_LONG).show()
            }
        })

        /*val intent = Intent(this,
            RegistroActivity::class.java)
        startActivity(intent)*/
    }

    private fun irMenu() {
        val intent = Intent(this,
            MenuActivity::class.java)
        startActivity(intent)
    }
}