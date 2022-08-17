package edu.pe.idat.proyectomovil.view.cliente

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import edu.pe.idat.proyectomovil.R
import edu.pe.idat.proyectomovil.databinding.ActivityMainBinding
import edu.pe.idat.proyectomovil.model.Cliente
import edu.pe.idat.proyectomovil.Service.ClienteService
import edu.pe.idat.proyectomovil.repository.Conexion
import edu.pe.idat.proyectomovil.utilitarios.RestEngine
import edu.pe.idat.proyectomovil.view.InicioActivity
import edu.pe.idat.proyectomovil.view.motorizado.SplashActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var progress: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ///setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btningresar.setOnClickListener(this)
        binding.btnregistrarse.setOnClickListener(this)
        binding.btnloginmotorizado.setOnClickListener(this)

        progress = ProgressDialog(this)
        progress.setMessage("Buscando datos")
        progress.setCancelable(false)
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER)
    }

    override fun onClick(v: View) {

        var email=binding.etemail.text.toString()
        var pasword = binding.etpassword.text.toString()

        when (v.id) {
            R.id.btningresar ->  irMenu(email, pasword )
            R.id.btnregistrarse -> irRegistrarse()
            R.id.btnloginmotorizado -> irLoginMotorizado()

        }

    }

    private fun irLoginMotorizado() {
        val intent = Intent(this,
            InicioActivity::class.java)
        startActivity(intent)

        finish()
    }

    private fun irRegistrarse() {

        val intent = Intent(this,
            RegistroActivity::class.java).apply { putExtra("codigo",0) }
        startActivity(intent)

        finish()
    }

    private fun irMenu(email:String, pasword: String) {

        if(email==null || pasword==null || email==""|| pasword=="") {
            Toast.makeText(this@LoginActivity, "Complete los campos requeridos", Toast.LENGTH_LONG).show()

        }else{
            progress.show()
            val clienteService : ClienteService = RestEngine.getRestEngine().create(ClienteService::class.java)
            val result: Call<Cliente> = clienteService.buscarxEmail(email)

            result.enqueue(object :Callback<Cliente>{
                override fun onResponse(call: Call<Cliente>, response: Response<Cliente>) {
                    progress.hide()
                    val clienteItem = response.body()
                    if (clienteItem?.codcliente!= null) {
                        validarUsuario( clienteItem)
                    }else{
                        Toast.makeText(this@LoginActivity, "Usuario no existe", Toast.LENGTH_LONG).show()
                        limpiarCampos()

                    }
                }

                override fun onFailure(call: Call<Cliente>, t: Throwable) {
                    progress.hide()
                    Toast.makeText(this@LoginActivity, "Sin conexion, contáctate con nosotros", Toast.LENGTH_LONG).show()
                    limpiarCampos()
                }

            })
        }

    }

    private fun validarUsuario(clienteItem: Cliente) {

        if (clienteItem.xcontrasenia!=binding.etpassword.text.toString()){
            Toast.makeText(this@LoginActivity, "Contraseña incorrecta", Toast.LENGTH_LONG).show()
        }else{
            var conexion =Conexion(this)
            conexion.eliminarTodoClienteDB()
            var resultado =conexion.guardarClienteDB(Cliente(clienteItem.codcliente,clienteItem.xapellido,
                                                            clienteItem.xcontrasenia,clienteItem.xdireccion,
                                                            clienteItem.xdni,clienteItem.xemail,"",
                                                            clienteItem.xnombre,clienteItem.xtelefono))
            if (resultado>0){
                var mensaje = clienteItem.xnombre + " " + clienteItem.xapellido
                val intent = Intent(this,
                    MenuActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@LoginActivity, "Bienvenido $mensaje", Toast.LENGTH_LONG).show()
                limpiarCampos()
                finish()
            }else{
                Toast.makeText(this@LoginActivity, "Ocurrió un error", Toast.LENGTH_LONG).show()
            }
        }

    }



    private fun limpiarCampos(){
        binding.etemail.setText("")
        binding.etpassword.setText("")
        binding.etemail.isFocusableInTouchMode= true
        binding.etemail.requestFocus()

    }
}