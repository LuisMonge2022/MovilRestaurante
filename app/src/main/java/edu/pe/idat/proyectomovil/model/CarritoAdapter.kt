package edu.pe.idat.proyectomovil.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.pe.idat.proyectomovil.R
import kotlinx.android.synthetic.main.auxiliar_carrito.view.*

class CarritoAdapter (val listaCarrito: ListaCarrito): RecyclerView.Adapter<CarritoAdapter.CarritoHolder>(){

    class CarritoHolder(val view:View) :RecyclerView.ViewHolder(view){

        fun render(carrito: Carrito){
            view.txtnombrecarrito.text = carrito.nombre
            view.txtdescripcioncarrito.text = carrito.descripcion
            var cantidad = carrito.cantidad
            var subtotal = carrito.precio * cantidad
            val sp = view.spncantidadcarrito
            var lista = listOf<Int>(1,2,3,4,5,6,7,8,9,10)

            val adap = ArrayAdapter(view.context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,lista)

            sp.adapter=adap
            sp.setSelection(cantidad-1)
            view.txtpreciocarrito.text = subtotal.toString()
            sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view2: View?, position: Int, id: Long) {
                    sp.setSelection(position)
                    cantidad = position+1
                    subtotal = carrito.precio*cantidad
                    view.txtpreciocarrito.text = subtotal.toString()
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        }

        fun obtenerMonto():Double{
            var monto :Double=0.0

            return monto
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoAdapter.CarritoHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CarritoHolder(layoutInflater.inflate(R.layout.auxiliar_carrito, parent,false))
    }

    override fun onBindViewHolder(holder: CarritoAdapter.CarritoHolder, position: Int) {
        holder.render(listaCarrito[position])
    }

    override fun getItemCount(): Int =listaCarrito.size

}