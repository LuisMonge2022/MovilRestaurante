package edu.pe.idat.proyectomovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import androidx.recyclerview.widget.LinearLayoutManager
import edu.pe.idat.proyectomovil.databinding.ActivityCarritoBinding

import edu.pe.idat.proyectomovil.model.*
import edu.pe.idat.proyectomovil.repository.Conexion
import kotlinx.android.synthetic.main.activity_carrito.*
import kotlinx.android.synthetic.main.auxiliar_producto.*


class CarritoActivity : AppCompatActivity() , View.OnClickListener {

    private lateinit var binding: ActivityCarritoBinding

    val lista = ListaCarrito()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarritoBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.btnCarritoComprar.setOnClickListener(this)
        binding.ibregresarMenu.setOnClickListener(this)

        var conexion = Conexion(this)
        var db = conexion.writableDatabase
        var sql= "Select * from carrito"
        var respuesta = db.rawQuery(sql,null)
        if (respuesta.moveToFirst()){
            do {

                var cod= respuesta.getInt(1)
                var nombre= respuesta.getString(2)
                var descr=   respuesta.getString(3)
                var precio= respuesta.getDouble(4)
                if (nombre == null){
                    nombre = "vacio"
                    descr = "vacio"
                    precio= 0.00
                }
                var canti =respuesta.getInt(5)
                var sub = 0.0
                lista.add(Carrito(cod,nombre,descr,canti,precio,sub))


            }while (respuesta.moveToNext())
        }

        rvCarrito.layoutManager = LinearLayoutManager(this)


        val adapter = CarritoAdapter(lista)
        rvCarrito.adapter = adapter
        binding.txtsubtotal.setText(obtenersubtotal().toString())
    }

    private fun  obtenersubtotal(): Double{
        var suma:Double=0.0
        if (lista != null) {
            for (car : Carrito in lista){


            suma= suma + car.subtotal
            }
        }
        return suma
    }

    override fun onClick(v: View) {
        when(v.id){
            binding.btnCarritoComprar.id-> IrMenu()
            binding.btnCarritoComprar.id-> continuarCompra()
            binding.ibregresarMenu.id -> regresarMenu()
        }
    }

    private fun regresarMenu() {
        val intent = Intent(this,
            MenuActivity::class.java)
        startActivity(intent)
    }

    private fun continuarCompra() {
        val intent = Intent(this,
            DireccionActivity::class.java)
        startActivity(intent)
    }

    private fun IrMenu() {
        val intent = Intent(this,
            MenuActivity::class.java)
        startActivity(intent)
    }

    /*fun traerRecycler(listaCarrito: List<Carrito>){
        rvCarrito.layoutManager = LinearLayoutManager(this)
        val adapter = CarritoAdapter(listaCarrito)
        rvCarrito.adapter = adapter
    }*/
}