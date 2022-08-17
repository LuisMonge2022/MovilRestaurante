package edu.pe.idat.proyectomovil.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import edu.pe.idat.proyectomovil.model.*


class Conexion (var context: Context): SQLiteOpenHelper(context,"datoscliente",null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        var tablacarrito = "CREATE TABLE carrito" +
                "(id INTEGER not null primary key autoincrement, idproducto INTEGER,nombreproducto text,descripcion text, cantidad Integer, precio real, sub real,codcliente integer)"
        db?.execSQL(tablacarrito)

        var tablacliente = "CREATE TABLE CLIENTE (id INTEGER not null primary key autoincrement, " +
                "idcliente INTEGER, apellido text, contrasenia text, direccion text, dni text ,correo text, nombre text, telefono integer)"
        db?.execSQL(tablacliente)

        var tablaempleado = "CREATE TABLE EMPLEADO (id INTEGER not null primary key autoincrement, " +
                "idempleado INTEGER,nombre text, apellido text, dni text , codenvio integer)"
        db?.execSQL(tablaempleado)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun listarCarrito(codigo: Int):ListaCarrito{

        val lis2=ListaCarrito()
        var db= this.writableDatabase
        //var db= cone.writableDatabase
        var sql= "Select * from carrito where codcliente="+codigo
        var respuesta = db.rawQuery(sql,null)
        if (respuesta.moveToFirst()) {
            do {
                var cod = respuesta.getInt(1)
                var nombre = respuesta.getString(2)
                var descr = respuesta.getString(3)
                var precio = respuesta.getDouble(5)
                if (nombre == null) {
                    nombre = "vacio"
                    descr = "vacio"
                    precio = 0.00
                }
                var canti = respuesta.getInt(4)
                var sub = respuesta.getDouble(6)
                var codi = respuesta.getInt(7)


                lis2.add(Carrito(cod, nombre, descr, canti, precio, sub, codi))
            } while (respuesta.moveToNext())
        }
        return lis2
    }

    fun buscarCarrito(codigo:Int):Carrito{
        var db= this.writableDatabase
        var sql= "Select * from carrito where idproducto = "+codigo
        var respuesta = db.rawQuery(sql,null)
        if (respuesta.moveToFirst()){
            var cod= respuesta.getInt(1)
            var nombre= respuesta.getString(2)
            var descr=   respuesta.getString(3)
            var precio= respuesta.getDouble(5)
            var canti =respuesta.getInt(4)
            var sub = precio *canti
            var codi = respuesta.getInt(7)
            val carrito= Carrito(cod,nombre,descr,canti,precio,sub,codi)
            db.close()
            return carrito
        }else{
            db.close()
            val carrit=Carrito(0,"nada","nada",0,0.0,0.0,0)
            return carrit
        }
    }

    fun guardarCarrito(carrito: Carrito): Long {
            var db = this.writableDatabase
            val registro= ContentValues()
            registro.put("idproducto",carrito.codproducto)
            registro.put("nombreproducto",carrito.nombre)
            registro.put("descripcion",carrito.descripcion)
            registro.put("cantidad",carrito.cantidad)
            registro.put("precio",carrito.precio)
            registro.put("sub",carrito.precio * carrito.cantidad)
            registro.put("codcliente",carrito.codcliente)
            var resultado=db.insert("carrito",null,registro)
            db.close()
            return resultado
    }

    fun actualizarCarrito(carrito: Carrito): Int{
        var db = this.writableDatabase
        val registro= ContentValues()
        registro.put("idproducto",carrito.codproducto)
        registro.put("nombreproducto",carrito.nombre)
        registro.put("descripcion",carrito.descripcion)
        registro.put("cantidad",carrito.cantidad)
        registro.put("precio",carrito.precio)
        registro.put("sub",carrito.precio * carrito.cantidad)
        registro.put("codcliente",carrito.codcliente)
        var args : Array<String> = arrayOf<String>(carrito.codproducto.toString())
        val resultado = db.update("carrito",registro,"idproducto=?",args)
        db.close()
        return resultado
    }

    fun eliminarCarrito(codigo: Int):Int{
        var db = this.writableDatabase
        var resultado = db.delete("carrito","idproducto="+codigo, null)
        db.close()
        if (resultado>0){
            return resultado
        }else{
            return 0
        }
    }

    fun eliminarTodoCarritoDB(){
        var db = this.writableDatabase
        db.execSQL("Delete from carrito",)
        db.close()
    }

    fun guardarClienteDB(cliente: Cliente): Long {
        var db = this.writableDatabase
        val registro= ContentValues()
        registro.put("idcliente",cliente.codcliente)
        registro.put("apellido", cliente.xapellido)
        registro.put("contrasenia", cliente.xcontrasenia)
        registro.put("direccion", cliente.xdireccion)
        registro.put("dni", cliente.xdni)
        registro.put("correo", cliente.xemail)
        registro.put("nombre", cliente.xnombre)
        registro.put("telefono", cliente.xtelefono)

        var resultado=db.insert("cliente",null,registro)
        db.close()
        return resultado
    }

    fun buscarCliente(codigo:Int):Cliente{
        var db= this.writableDatabase
        var sql= "Select * from cliente where idcliente = "+codigo
        var respuesta = db.rawQuery(sql,null)
        if (respuesta.moveToFirst()){
            var cod= respuesta.getInt(1)
            var ape=   respuesta.getString(2)
            var contra = respuesta.getString(3)
            var dir= respuesta.getString(4)
            var dni = respuesta.getString(5)
            var correo= respuesta.getString(6)
            var nomb = respuesta.getString(7)
            var tel= respuesta.getInt(8)
            val cliente= Cliente(cod,ape,contra,dir, dni,correo,"",nomb, tel.toString())
            return cliente
        }else{
            val cliente=Cliente(0,"","", "", "","","","","")
            return cliente
        }
    }

    fun eliminarClienteDB(codigo: Int):Int{
        var db = this.writableDatabase
        var resultado = db.delete("cliente","idcliente="+codigo, null)
        db.close()
        if (resultado>0){
            return resultado
        }else{
            return 0
        }
    }
    fun eliminarTodoClienteDB(){
        var db = this.writableDatabase
        db.execSQL("Delete from cliente",)
        db.close()
    }

    fun listarCliente():Cliente{
        var db= this.writableDatabase
        var sql= "Select * from cliente"
        var respuesta = db.rawQuery(sql,null)
        if (respuesta.moveToFirst()){
                var cod= respuesta.getInt(1)
                var ape=   respuesta.getString(2)
                var contra = respuesta.getString(3)
                var dir= respuesta.getString(4)
                var dni = respuesta.getString(5)
                var correo= respuesta.getString(6)
                var nomb = respuesta.getString(7)
                var tel= respuesta.getInt(8)
                var cliente =Cliente(cod,ape,contra,dir,dni,correo,"",nomb,tel.toString())
                return cliente
        }else{
            return Cliente(0,"","","","","","","","")
        }
    }

    fun actualizarClienteDB(cliente: Cliente): Int{
        var db = this.writableDatabase
        val registro= ContentValues()
        registro.put("idcliente",cliente.codcliente)
        registro.put("apellido", cliente.xapellido)
        registro.put("contrasenia", cliente.xcontrasenia)
        registro.put("direccion", cliente.xdireccion)
        registro.put("dni", cliente.xdni)
        registro.put("correo", cliente.xemail)
        registro.put("nombre", cliente.xnombre)
        registro.put("telefono", cliente.xtelefono)
        var args : Array<String> = arrayOf<String>(cliente.codcliente.toString())
        val resultado = db.update("cliente",registro,"idcliente=?",args)
        db.close()
        return resultado
    }


    fun guardarEmpleadoDB(empleado: Empleadom): Long {
        var db = this.writableDatabase
        val registro= ContentValues()
        registro.put("idempleado",empleado.codempleado)
        registro.put("nombre", empleado.nombre)
        registro.put("apellido", empleado.apellido)
        registro.put("dni", empleado.dni)
        registro.put("codenvio", 0)

        var resultado=db.insert("empleado",null,registro)
        db.close()
        return resultado
    }

    fun eliminarTodoEmpleadoDB(){
        var db = this.writableDatabase
        db.execSQL("Delete from empleado",)
        db.close()
    }

    fun actualizarEmpleadoDB(empleado: Empleadom): Int{
        var db = this.writableDatabase
        val registro= ContentValues()
        registro.put("idempleado",empleado.codempleado)
        registro.put("nombre", empleado.nombre)
        registro.put("apellido", empleado.apellido)
        registro.put("dni", empleado.dni)
        registro.put("codenvio", 0)
        var args : Array<String> = arrayOf<String>(empleado.codempleado.toString())
        val resultado = db.update("empleado",registro,"idempleado=?",args)
        db.close()
        return resultado
    }

    fun listarEmpleado():Empleadom{
        var db= this.writableDatabase
        var sql= "Select * from empleado"
        var respuesta = db.rawQuery(sql,null)
        if (respuesta.moveToFirst()){
            var id= respuesta.getInt(1)
            var nomb=   respuesta.getString(2)
            var apel = respuesta.getString(3)
            var dni= respuesta.getString(4)
            var envio = respuesta.getInt(5)

            var empleado =Empleadom(apel,0,0,id,"",dni,"",nomb )
            return empleado
        }else{
            return Empleadom("",0,0,0,"","","","")
        }
    }
}