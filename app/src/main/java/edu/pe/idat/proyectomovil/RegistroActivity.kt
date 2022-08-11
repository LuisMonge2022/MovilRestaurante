package edu.pe.idat.proyectomovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import edu.pe.idat.proyectomovil.Service.ClienteService
import edu.pe.idat.proyectomovil.databinding.ActivityRegistroBinding
import edu.pe.idat.proyectomovil.model.Cliente
import edu.pe.idat.proyectomovil.repository.Conexion
import edu.pe.idat.proyectomovil.utilitarios.RestEngine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var clientebd:Cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var codigo = intent.getIntExtra("codigo",0)
        clientebd = Conexion(this).buscarCliente(codigo)

        if (clientebd.codcliente!=0){
            /*binding.etnombre.setText(cliente.xnombre)
            binding.etapellido.setText(cliente.xapellido)
            binding.etDNI.setText(cliente.xdni)
            binding.etdireccion.setText(cliente.xdireccion)
            binding.ettelefono.setText(cliente.xtelefono)
            binding.etcorreo.setText(cliente.xemail)
            binding.etcontraseA.setText(cliente.xcontrasenia)*/

            binding.btnregresar.setText("Regresar")

        }else{
            /*binding.etnombre.setText("")
            binding.etapellido.setText("")
            binding.etDNI.setText("")
            binding.etdireccion.setText("")
            binding.ettelefono.setText("")
            binding.etcorreo.setText("")
            binding.etcontraseA.setText("")*/

            binding.btnregresar.setText("IR AL LOGIN")
        }
       // setContentView(R.layout.activity_registro)
        binding.etnombre.setText(clientebd.xnombre)
        binding.etapellido.setText(clientebd.xapellido)
        binding.etDNI.setText(clientebd.xdni)
        binding.etdireccion.setText(clientebd.xdireccion)
        binding.ettelefono.setText(clientebd.xtelefono)
        binding.etcorreo.setText(clientebd.xemail)
        binding.etcontraseA.setText(clientebd.xcontrasenia)
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
            R.id.btnregresar->  irLogin(clientebd.codcliente)
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
                            val cliente= Cliente(clientebd.codcliente,apellido,contrasenia,direccion,dni,correo,"ACTIVO",nombre,telefono)
                            if (cliente.codcliente==0) {
                                val clienteService: ClienteService =
                                    RestEngine.getRestEngine().create(ClienteService::class.java)
                                val call = clienteService.registrarCliente(cliente)
                                call.enqueue(object : Callback<Void> {
                                    override fun onResponse(
                                        call: Call<Void>,
                                        response: Response<Void>
                                    ) {
                                        Toast.makeText(this@RegistroActivity, "Bien Hecho, por favor ingresa con el usuario creado", Toast.LENGTH_SHORT).show()
                                        limpiar()
                                    }
                                    override fun onFailure(call: Call<Void>, t: Throwable) {
                                        Toast.makeText(this@RegistroActivity, "Error", Toast.LENGTH_LONG).show()
                                    }
                                })
                            }else {
                                if (validarCambios(cliente)){
                                val clienteService: ClienteService =
                                    RestEngine.getRestEngine().create(ClienteService::class.java)
                                val call = clienteService.actualizarCliente(cliente)
                                call.enqueue(object : Callback<Void> {
                                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                        Toast.makeText(this@RegistroActivity, "Felicidades, ya casi se guardan los cambios", Toast.LENGTH_SHORT).show()
                                        var respuesta = Conexion(this@RegistroActivity).actualizarClienteDB(cliente)
                                        if (respuesta>0){
                                            var cli= Conexion(this@RegistroActivity).listarCliente()

                                            irLogin(cli.codcliente)
                                            Toast.makeText(this@RegistroActivity, "Felicidades, se guardaron los cambios ${cli.codcliente}", Toast.LENGTH_SHORT).show()
                                        }else{
                                            Toast.makeText(this@RegistroActivity, "Qué pena", Toast.LENGTH_SHORT).show()
                                        }

                                    }
                                    override fun onFailure(call: Call<Void>, t: Throwable) {
                                        Toast.makeText(this@RegistroActivity, "Error", Toast.LENGTH_LONG).show()
                                    }
                                })
                                }else {
                                    Toast.makeText(this@RegistroActivity, "No hizo ningún cambio en sus datos", Toast.LENGTH_LONG).show()
                                }
                            }
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
                Toast.makeText(this@RegistroActivity, "Nombre o Apellido incorrectos", Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this@RegistroActivity,"Complete los campos requeridos",Toast.LENGTH_LONG).show()
        }
    }

    private fun validarCambios(cliente: Cliente): Boolean {
        if (cliente.xnombre!=clientebd.xnombre) return true
        else if (cliente.xcontrasenia!=clientebd.xcontrasenia) return true
        else if (cliente.xapellido!=clientebd.xapellido) return true
        else if (cliente.xdireccion!=clientebd.xdireccion) return true
        else if (cliente.xdni!=clientebd.xdni) return true
        else if (cliente.xemail!=clientebd.xemail) return true
        else if (cliente.xtelefono!=clientebd.xtelefono) return true
        else return false
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

    private fun irLogin(codcliente: Int) {
        if (codcliente==0){
            val intent = Intent(this,
                LoginActivity::class.java)
            startActivity(intent)
        }else{
            val intent = Intent(this,
               MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
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