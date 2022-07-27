package edu.pe.idat.proyectomovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import edu.pe.idat.proyectomovil.Service.ClienteService
import edu.pe.idat.proyectomovil.databinding.ActivityMenuBinding
import edu.pe.idat.proyectomovil.databinding.ActivityRegistroBinding
import edu.pe.idat.proyectomovil.model.Cliente
import edu.pe.idat.proyectomovil.utilitarios.RestEngine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body

class RegistroActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding: ActivityRegistroBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // setContentView(R.layout.activity_registro)

        binding.btnregresar.setOnClickListener(this)
        binding.btnregistrado.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        var nombre=binding.etnombre.text.toString()
        var apellido=binding.etapellido.text.toString()
        var dni= binding.etDNI.text.toString()
        var direccion= binding.etdireccion.text.toString()
        var telefono=binding.ettelefono.text.toString()
        var correo=binding.etcorreo .text.toString()
        var contrasenia = binding.etcontraseA.text.toString()

        when (v.id) {
            R.id.btnregresar->  irLogin()
            R.id.btnregistrado -> registrarCliente(nombre,apellido,dni,
            direccion,telefono,correo,contrasenia)

        }
    }

    private fun registrarCliente(nombre: String, apellido: String, dni: String, direccion: String,
                                 telefono: String, correo: String, contrasenia: String) {
        if (validarVacios(nombre, apellido, dni, direccion, telefono, correo, contrasenia)){
            if (validarNombreApellido(nombre, apellido)){
                if (validardni(dni)){
                    if (validartelefono(telefono)){
                        if (validaCorreo(correo)){
                            val cliente= Cliente(0,apellido,contrasenia,direccion,dni,correo,"ACTIVO",nombre,telefono)
                            val clienteService : ClienteService = RestEngine.getRestEngine().create(ClienteService::class.java)
                            val call = clienteService.registrarCliente(cliente)
                            call.enqueue(object: Callback<Void>{
                                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                    Toast.makeText(this@RegistroActivity,"Bien Hecho, por favor ingresa con el usuario creado",
                                        Toast.LENGTH_SHORT).show()
                                    limpiar()
                                }

                                override fun onFailure(call: Call<Void>, t: Throwable) {

                                    Toast.makeText(this@RegistroActivity,"Error",Toast.LENGTH_LONG).show()
                                }

                            })
                        }else{
                            Toast.makeText(this@RegistroActivity,"Email incorrecto",Toast.LENGTH_LONG).show()
                        }
                    }else{
                        Toast.makeText(this@RegistroActivity,"Telefono Incorrecto",Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(this@RegistroActivity,"Dni Incorrecto",Toast.LENGTH_LONG).show()
                }
            }else {
                Toast.makeText(
                    this@RegistroActivity,
                    "Nombre o Apellido incorrectos",
                    Toast.LENGTH_LONG
                ).show()
            }
        }else{
            Toast.makeText(this@RegistroActivity,"Complete los campos requeridos",Toast.LENGTH_LONG).show()
        }
    }
    private fun validarVacios(nombre: String, apellido: String, dni: String, direccion: String,
                              telefono: String, correo: String, contrasenia: String) :Boolean {
        var rpta: Boolean = true
        if (nombre== null || apellido == null  || dni==null || direccion==null || telefono==null || correo==null || contrasenia==null
            || nombre== "" || apellido == ""  || dni=="" || direccion=="" || telefono=="" || correo=="" || contrasenia==""){
            rpta=false
        }
        return rpta

    }

    private fun validarNombreApellido(nombre: String, apellido: String):Boolean{
        if (nombre.length<3 || apellido.length<3 ){
            return false
        }
        for (letra in nombre){
            if (letra.isDigit()) {
                return false
                break
            }
        }

        for (letra in apellido){
            if (letra.isDigit()) {
                return false
                break
            }
        }
        return true
    }

    private fun validardni(dni:String) : Boolean{
        var rpta: Boolean = true
        if (dni.length != 8){
            rpta  = false
        }
        return rpta
    }

    private fun validartelefono(telefono: String):Boolean{
        for (dig in telefono) {
            if (dig.isLetter()) {
                return false
                break
            }
        }
        return true
    }
    private fun validaCorreo(correo : String) : Boolean{
        var rpta: Boolean = true
        if (!correo.contains("@",true)){
            rpta  = false
        }
        return rpta
    }

    private fun irLogin() {
        val intent = Intent(this,
            LoginActivity::class.java)
        startActivity(intent)

        finish()
    }

    private fun limpiar(){
        binding.etnombre.setText("")
        binding.etapellido.setText("")
        binding.etDNI.setText("")
        binding.etcorreo.setText("")
        binding.etdireccion.setText("")
        binding.etcontraseA.setText("")
        binding.ettelefono.setText("")
    }
}