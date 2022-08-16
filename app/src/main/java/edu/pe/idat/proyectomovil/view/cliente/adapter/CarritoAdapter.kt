package edu.pe.idat.proyectomovil.view.cliente.adapter


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import edu.pe.idat.proyectomovil.view.cliente.CarritoSplashActivity
import edu.pe.idat.proyectomovil.R
import edu.pe.idat.proyectomovil.model.Carrito
import edu.pe.idat.proyectomovil.model.ListaCarrito
import edu.pe.idat.proyectomovil.repository.Conexion
import kotlinx.android.synthetic.main.auxiliar_carrito.view.*

class CarritoAdapter (): RecyclerView.Adapter<CarritoAdapter.CarritoHolder>(){
    private val listaCarrito= ListaCarrito()
    class CarritoHolder(var view:View) :RecyclerView.ViewHolder(view){
        fun render(carrito: Carrito){

            if(carrito.codproducto == 0){
                view.txtnombrecarrito.text = carrito.nombre
                view.txtdescripcioncarrito.text = carrito.descripcion
                view.txtpreciocarrito.text = carrito.precio.toString()
                view.spncantidadcarrito.isInvisible= true
                view.btnEliminarcarrito.isInvisible= true
            }else{
                view.btnEliminarcarrito.setOnClickListener {
                    mostrarAlertaEliminar(carrito)
                }
                view.txtnombrecarrito.text = carrito.nombre
                view.txtdescripcioncarrito.text = carrito.descripcion
                var cantidad = carrito.cantidad
                var subtotal = carrito.precio * cantidad
                subtotal=Math.round(subtotal*100.00)/100.00
                val sp = view.spncantidadcarrito
                var lista = listOf<Int>(1,2,3,4,5,6,7,8,9,10,11,12,13,14)
                val adap = ArrayAdapter(view.context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,lista)

                sp.adapter=adap
                sp.setSelection(cantidad-1)
                view.txtpreciocarrito.text = subtotal.toString()
                sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view2: View?, position: Int, id: Long) {
                        sp.setSelection(position)
                        var cantidad2 = position+1
                        if (cantidad2 != carrito.cantidad){
                            mostrarAlertaActualizar(carrito, cantidad2)
                        }
                        /*carrito.cantidad = cantidad
                        subtotal = carrito.precio*cantidad
                        view.txtpreciocarrito.text = subtotal.toString()
                        carrito.subtotal = subtotal
                        var conexion = Conexion(view.context)
                        conexion.actualizarCarrito(carrito)
                        */

                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }

            }
        }

        private fun mostrarAlertaActualizar(carrito: Carrito, cantidad :Int) {
            var alerta =AlertDialog.Builder(view.context)
            alerta.setTitle("Actualizar Cantidad")
            alerta.setMessage("¿Deseas cambiar la cantidad?")
                .setPositiveButton("SI",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                        carrito.cantidad = cantidad
                        var conexion = Conexion(view.context)
                        var resultado=conexion.actualizarCarrito(carrito)
                        if (resultado>0){
                            Toast.makeText(view.context,"Se actualizó la cantidad",Toast.LENGTH_SHORT).show()
                            val intent = Intent(view.context,
                                CarritoSplashActivity::class.java)
                            view.context.startActivity(intent)

                        }else{
                            Toast.makeText(view.context,"No se cambió",Toast.LENGTH_SHORT).show()
                        }
                    }

                }).setNegativeButton("No",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Toast.makeText(view.context,"No se cambió",Toast.LENGTH_SHORT).show()
                        view.spncantidadcarrito.setSelection(carrito.cantidad-1)
                    }

                }).show()
        }

        private fun mostrarAlertaEliminar(carrito: Carrito) {

            var alerta =AlertDialog.Builder(view.context)
            alerta.setTitle("Eliminar Productos")
            alerta.setMessage("Deseas eliminar estos productos")
                .setPositiveButton("SI",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        var conexion = Conexion(view.context)
                        var resultado=conexion.eliminarCarrito(carrito.codproducto)
                        if (resultado>0){
                            Toast.makeText(view.context,"Se eliminó de carrito",Toast.LENGTH_SHORT).show()
                            val intent = Intent(view.context,
                                CarritoSplashActivity::class.java)
                            view.context.startActivity(intent)

                         }else{
                             Toast.makeText(view.context,"No se eliminó",Toast.LENGTH_SHORT).show()
                         }
                    }

                }).setNegativeButton("No",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Toast.makeText(view.context,"No se eliminó",Toast.LENGTH_SHORT).show()
                    }

                }).show()

        }


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CarritoHolder(layoutInflater.inflate(R.layout.auxiliar_carrito, parent,false))
    }

    override fun onBindViewHolder(holder: CarritoHolder, position: Int) {
        holder.render(listaCarrito[position])
    }

    override fun getItemCount(): Int =listaCarrito.size

    fun getList(lista: ListaCarrito):Int{
        listaCarrito.addAll(lista)
        notifyDataSetChanged()
        return lista.size
    }


}