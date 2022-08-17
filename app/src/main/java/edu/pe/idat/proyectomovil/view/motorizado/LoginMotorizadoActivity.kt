package edu.pe.idat.proyectomovil.view.motorizado

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import edu.pe.idat.proyectomovil.R
import edu.pe.idat.proyectomovil.Service.ClienteService
import edu.pe.idat.proyectomovil.Service.MotorizadoService
import edu.pe.idat.proyectomovil.databinding.ActivityLoginMotorizadoBinding

import edu.pe.idat.proyectomovil.model.Empleadom
import edu.pe.idat.proyectomovil.repository.Conexion
import edu.pe.idat.proyectomovil.utilitarios.RestEngine
import edu.pe.idat.proyectomovil.view.InicioActivity
import edu.pe.idat.proyectomovil.view.cliente.MenuActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginMotorizadoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginMotorizadoBinding
    private lateinit var progress: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginMotorizadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnregistrar.setOnClickListener(this)
        binding.btnlogin.setOnClickListener(this)

        progress = ProgressDialog(this)
        progress.setMessage("Buscando datos")
        progress.setCancelable(false)
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER)


    }

    override fun onClick(p0: View) {

        var dni=binding.etusuario.text.toString()
        var pasword = binding.etpassword.text.toString()


        when(p0.id){
            R.id.btnlogin -> iniciarSesion(dni,pasword)
            R.id.btnregistrar -> irRegistroUsuario()

        }
    }

    private fun iniciarSesion(dni: String, pasword: String) {

        if(dni==null || pasword==null || dni==""|| pasword=="") {
            Toast.makeText(this@LoginMotorizadoActivity, "Complete los campos requeridos", Toast.LENGTH_LONG).show()

        }else{
            progress.show()
            val motorizadoService : MotorizadoService= RestEngine.getRestEngine().create(MotorizadoService::class.java)
            val result: Call<Empleadom> = motorizadoService.buscarempleadoxdni(dni)

            result.enqueue(object : Callback<Empleadom> {
                override fun onResponse(call: Call<Empleadom>, response: Response<Empleadom>) {
                    progress.hide()
                    val empleadoItem = response.body()
                    if (empleadoItem?.codempleado!= null) {
                        validarUsuario( empleadoItem)
                    }else{
                        Toast.makeText(this@LoginMotorizadoActivity, "Usuario no existe", Toast.LENGTH_LONG).show()
                        limpiarCampos()

                    }
                }

                override fun onFailure(call: Call<Empleadom>, t: Throwable) {
                    progress.hide()
                    Toast.makeText(this@LoginMotorizadoActivity, "Sin conexion, contáctate con nosotros", Toast.LENGTH_LONG).show()
                    limpiarCampos()
                }

            })
        }

    }

    private fun validarUsuario(empleadoItem: Empleadom) {

        if (empleadoItem.contrasenia!=binding.etpassword.text.toString()){

            Toast.makeText(this@LoginMotorizadoActivity, "Contraseña incorrecta ${binding.etpassword.text.toString()}"+
                    "bd ${empleadoItem.contrasenia}"
                , Toast.LENGTH_LONG).show()
        }else{
            var conexion = Conexion(this)
            conexion.eliminarTodoEmpleadoDB()
            var resultado =conexion.guardarEmpleadoDB(
                Empleadom(empleadoItem.apellido,empleadoItem.codarea,empleadoItem.codcargo,empleadoItem.codempleado,
                empleadoItem.contrasenia,empleadoItem.dni,empleadoItem.fecha_ingreso,empleadoItem.nombre)
            )
            if (resultado>0){
                var mensaje = empleadoItem.nombre + " " + empleadoItem.apellido
                val intent = Intent(this,
                    EnvioActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@LoginMotorizadoActivity, "Bienvenido $mensaje", Toast.LENGTH_LONG).show()
                limpiarCampos()
                finish()
            }else{
                Toast.makeText(this@LoginMotorizadoActivity, "Ocurrió un error", Toast.LENGTH_LONG).show()
            }
        }



    }

    private fun irRegistroUsuario() {


        startActivity(Intent(applicationContext,
        InicioActivity::class.java))
    }

    private fun limpiarCampos(){
        binding.etusuario.setText("")
        binding.etpassword.setText("")
        binding.etusuario.isFocusableInTouchMode= true
        binding.etusuario.requestFocus()

    }



}