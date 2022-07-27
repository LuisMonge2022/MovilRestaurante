package edu.pe.idat.proyectomovil.model

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import edu.pe.idat.proyectomovil.DescripcionActivity
import edu.pe.idat.proyectomovil.R
import kotlinx.android.synthetic.main.activity_descripcion.view.*

import kotlinx.android.synthetic.main.auxiliar_producto.view.*



class ProductosAdapter (val listaprod:List<Producto>):RecyclerView.Adapter<ProductosAdapter.ProductoHolder>() {

    inner class ProductoHolder(val view:View) :RecyclerView.ViewHolder(view) {

        init {

        }
        fun render(producto: Producto){

            if (producto.codproducto==0){
                view.setOnClickListener{
                    Toast.makeText(view.context,"NADA QUE MOSTRAR",Toast.LENGTH_SHORT).show()
                }
            }else {
                view.setOnClickListener {
                    var intent = Intent(view.context, DescripcionActivity::class.java)
                    intent.putExtra("codigo", producto.codproducto)
                    view.context.startActivity(intent)
                }
            }
            val cadena: String="img"+producto.codproducto.toString()
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

    override fun onBindViewHolder(holder:ProductoHolder, position: Int) {
        holder.render(listaprod[position])
    }




    override fun getItemCount(): Int = listaprod.size


}