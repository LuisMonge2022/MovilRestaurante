package edu.pe.idat.proyectomovil.view.cliente.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import edu.pe.idat.proyectomovil.R
import edu.pe.idat.proyectomovil.Service.ClienteService
import edu.pe.idat.proyectomovil.Service.ProductoService
import edu.pe.idat.proyectomovil.model.Cliente
import edu.pe.idat.proyectomovil.model.Detalle
import edu.pe.idat.proyectomovil.model.Producto
import edu.pe.idat.proyectomovil.utilitarios.RestEngine
import edu.pe.idat.proyectomovil.view.cliente.DescripcionActivity
import kotlinx.android.synthetic.main.auxiliar_producto.view.*
import kotlinx.android.synthetic.main.auxiliar_resumen.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResumenCompraAdapter (val listadetalle:List<Detalle>): RecyclerView.Adapter<ResumenCompraAdapter.ResumenHolder>()  {


    inner class ResumenHolder(val view: View) :RecyclerView.ViewHolder(view) {

        init {

        }
        fun render(detalle: Detalle){
            /*if (detalle.codproducto==0){
                view.setOnClickListener{
                    Toast.makeText(view.context,"NADA QUE MOSTRAR", Toast.LENGTH_SHORT).show()
                }
            }else {
                view.setOnClickListener {
                    var intent = Intent(view.context, DescripcionActivity::class.java)
                    intent.putExtra("codigo", producto.codproducto)
                    view.context.startActivity(intent)
                }
            }*/
            //val cadena: String="img"+producto.codproducto.toString()
            //view.imgProducto.setImageURI(
                //Uri.parse("android.resource://edu.pe.idat.proyectomovil/drawable/"+cadena))

            val codigoproducto = detalle.codproducto.toString().toInt()
            val productoService : ProductoService = RestEngine.getRestEngine().create(ProductoService::class.java)
            val result: Call<Producto> = productoService.listarProductosxCodigo(codigoproducto)
            result.enqueue(object : Callback<Producto>{
                override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                    val productoItem = response.body()
                    if (productoItem?.codproducto!= null) {
                        view.tvNombreProducto.text=productoItem.nombre
                    }else{
                        view.tvNombreProducto.text="Sin datos"
                    }
                }
                override fun onFailure(call: Call<Producto>, t: Throwable) {
                    view.tvNombreProducto.text="Sin datos"
                }
            })
            view.tvidProducto.text = detalle.codproducto.toString()
            view.tvCantidad.text =detalle.cantidad.toString()
            view.tvPrecio.text=detalle.precio.toString()

        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResumenCompraAdapter.ResumenHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ResumenHolder(layoutInflater.inflate(R.layout.auxiliar_resumen,parent,false))
    }

    override fun onBindViewHolder(holder: ResumenCompraAdapter.ResumenHolder, position: Int) {
        holder.render(listadetalle[position])
    }

    override fun getItemCount(): Int =listadetalle.size
}