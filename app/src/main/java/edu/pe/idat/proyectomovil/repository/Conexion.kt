package edu.pe.idat.proyectomovil.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import edu.pe.idat.proyectomovil.model.Carrito
import edu.pe.idat.proyectomovil.model.ListaCarrito


class Conexion (var context: Context): SQLiteOpenHelper(context,"datoscliente",null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        var tablacarrito = "CREATE TABLE carrito" +
                "(id INTEGER not null primary key autoincrement, idproducto INTEGER,nombreproducto text,descripcion text, cantidad Integer, precio real, sub real)"
        db?.execSQL(tablacarrito)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }


}