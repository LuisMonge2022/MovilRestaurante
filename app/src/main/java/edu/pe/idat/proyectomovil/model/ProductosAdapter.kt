package edu.pe.idat.proyectomovil.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import edu.pe.idat.proyectomovil.R
import kotlinx.android.synthetic.main.auxiliar_producto.view.*


class ProductosAdapter (val listaprod:List<Producto>):RecyclerView.Adapter<ProductosAdapter.ProductoHolder>() {


    class ProductoHolder(val view:View) :RecyclerView.ViewHolder(view){
        fun render(producto: Producto){


            val cadena: String="c"+producto.codproducto.toString()
           //view.imgProducto.setImageResource(R.drawable.brasa_familiar_3)
            view.imgProducto.setImageURI(
               Uri.parse("android.resource://edu.pe.idat.proyectomovil/drawable/"+cadena))

            view.txtNombre.text = producto.nombre
            view.txtDescripcion.text = producto.descripcion
            view.txtPrecio.text = producto.precio.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductoHolder(layoutInflater.inflate(R.layout.auxiliar_producto,parent,false))
    }

    override fun onBindViewHolder(holder: ProductoHolder, position: Int) {
        holder.render(listaprod[position])
    }

    override fun getItemCount(): Int = listaprod.size


}