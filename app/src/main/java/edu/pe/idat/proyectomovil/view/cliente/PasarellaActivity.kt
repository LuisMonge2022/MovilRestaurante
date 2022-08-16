package edu.pe.idat.proyectomovil.view.cliente


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.VolleyError
import com.google.gson.Gson
import edu.pe.idat.proyectomovil.R
import edu.pe.idat.proyectomovil.Service.PedidoService
import edu.pe.idat.proyectomovil.culqi.culqi_android.Culqi.*
import edu.pe.idat.proyectomovil.culqi.culqi_android.Validation.Validation
import edu.pe.idat.proyectomovil.model.Cliente
import edu.pe.idat.proyectomovil.model.Detalle
import edu.pe.idat.proyectomovil.model.ListaCarrito
import edu.pe.idat.proyectomovil.model.Pedido
import edu.pe.idat.proyectomovil.repository.Conexion
import edu.pe.idat.proyectomovil.utilitarios.RestEngine
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PasarellaActivity : AppCompatActivity() {
    private lateinit var listaCarrito: ListaCarrito
    private lateinit var cliente: Cliente



    private lateinit var validation: Validation

    private lateinit var progress: ProgressDialog
    private lateinit var progress2: ProgressDialog
    private lateinit var txtcardnumber: TextView
    private lateinit var txtcvv:TextView
    private lateinit var txtmonth:TextView
    private lateinit var txtyear:TextView
    private lateinit var txtemail:TextView
    private lateinit var kind_card:TextView
    private lateinit var result:TextView
    private lateinit var btnPay: Button
    private lateinit var txtmonto: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pasarella)

        cliente= Conexion(this).listarCliente()
        listaCarrito=Conexion(this).listarCarrito(cliente.codcliente)
        var monto= 0.0
        for (lista in listaCarrito){
            monto=monto+lista.subtotal
        }

        txtmonto = findViewById<View>(R.id.txtpasarellamonto) as TextView
        monto=Math.round(monto*100.00)/100.00
        txtmonto.text=monto.toString()

        validation = Validation()
        progress = ProgressDialog(this)
        progress.setMessage("Validando información de la tarjeta")
        progress.setCancelable(false)
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER)

        progress2 = ProgressDialog(this)
        progress2.setMessage("Realizando pago")
        progress2.setCancelable(false)
        progress2.setProgressStyle(ProgressDialog.STYLE_SPINNER)

        txtcardnumber = findViewById<View>(R.id.txt_cardnumber) as TextView

        txtcvv = findViewById<View>(R.id.txt_cvv) as TextView

        txtmonth = findViewById<View>(R.id.txt_month) as TextView

        txtyear = findViewById<View>(R.id.txt_year) as TextView

        txtemail = findViewById<View>(R.id.txt_email) as TextView

        kind_card = findViewById<View>(R.id.kind_card) as TextView

        result = findViewById<View>(R.id.token_id) as TextView

        btnPay = findViewById<View>(R.id.btn_pay) as Button

        //txtcvv.isEnabled = false

        txtcardnumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length == 0) {
                    txtcvv.isEnabled = true
                }
            }

            override fun afterTextChanged(s: Editable) {
                val text = txtcardnumber.text.toString()
                if (s.length == 0) {
                    txtcardnumber.setBackgroundResource(R.drawable.border_error)
                }


                if (validation.luhn(text)) {
                    txtcardnumber.setBackgroundResource(R.drawable.border_sucess)
                } else {
                    txtcardnumber.setBackgroundResource(R.drawable.border_error)
                }
                val cvv = validation.bin(text, kind_card)
                if (cvv > 0) {
                    txtcvv.filters = arrayOf<InputFilter>(LengthFilter(cvv))
                    txtcvv.isEnabled = true
                } else {
                    //txtcvv.isEnabled = false
                    txtcvv.text = ""
                }
            }
        })

        txtyear.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                val text = txtyear.text.toString()
                if (validation.year(text)) {
                    txtyear.setBackgroundResource(R.drawable.border_error)
                } else {
                    txtyear.setBackgroundResource(R.drawable.border_sucess)
                }
            }
        })

        txtmonth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                val text = txtmonth.text.toString()
                if (validation.month(text)) {
                    txtmonth.setBackgroundResource(R.drawable.border_error)
                } else {
                    txtmonth.setBackgroundResource(R.drawable.border_sucess)
                }
            }
        })


        btnPay.setOnClickListener {

            if (txtmonth.text.toString().isNullOrEmpty() || txtyear.text.toString().isNullOrEmpty()){
                Toast.makeText(applicationContext, "Complete los campos", Toast.LENGTH_SHORT)
                    .show()

            }else {
                progress.show()
                val card = Card(
                    txtcardnumber.text.toString(),
                    txtcvv.text.toString(),
                    txtmonth.text.toString().toInt(),
                    txtyear.text.toString().toInt(),
                    txtemail.text.toString()
                )

                /*var respuesta = crearToken(Token("pk_test_a465a141a8070f94"), card)
                if (respuesta != "0") {
                    realizarPago(
                        Cargo("sk_test_d3ea28093ab85909"),
                        respuesta,
                        monto,
                        txtemail.text.toString()
                    )
                }else{
                    Toast.makeText(applicationContext, "Que pena", Toast.LENGTH_SHORT)
                        .show()
                }*/
                val token = Token("pk_test_a465a141a8070f94")
                token.createToken(applicationContext, card, object : TokenCallback {

                    override fun onSuccess(token: JSONObject?) {

                        var montoString=monto.toString()
                        val lista = montoString.split(".")
                        var montoCulqui=lista[0]+lista[1]
                        var montoCulqui2=0
                        montoCulqui2=montoCulqui.toInt()*10
                        try {
                            var nvotoken= token?.get("id").toString()
                            val cargo = Cargo("sk_test_d3ea28093ab85909")
                            cargo.crearCargo(applicationContext,nvotoken,montoCulqui2,txtemail.text.toString(),object:
                                CargoCallback {
                                override fun onSuccess(respuesta: JSONObject?) {

                                    val gson = Gson()
                                    val cargoresponse: CargoResponse =
                                        gson.fromJson(respuesta.toString(),CargoResponse::class.java)
                                    if (cargoresponse != null) {
                                        val resp = cargoresponse.outcome.user_message
                                        Toast.makeText(applicationContext, "$resp", Toast.LENGTH_SHORT)
                                            .show()
                                        ingresarPedido()

                                    }else{
                                        Toast.makeText(applicationContext, "estás aqui", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                                override fun onError(error: VolleyError) {
                                    if (error.networkResponse.data !=null){
                                        val gson = Gson()
                                        var body = String(error.networkResponse.data)
                                        var errorCulqui: ErrorCulqui = gson.fromJson(body,ErrorCulqui::class.java)
                                        Toast.makeText(applicationContext," ${errorCulqui.user_message}",Toast.LENGTH_LONG).show()
                                    }else{
                                        Toast.makeText(applicationContext,"Error desconocido",Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        } catch (ex: Exception) {
                            progress.hide()
                        }
                        progress.hide()
                    }
                    override fun onError(error: VolleyError) {
                        if (error.networkResponse.data !=null){
                            val gson = Gson()
                            var body = String(error.networkResponse.data)
                            var errorCulqui: ErrorCulqui = gson.fromJson(body,ErrorCulqui::class.java)
                            Toast.makeText(applicationContext," ${errorCulqui.user_message}",Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(applicationContext,"Error desconocido",Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    }


    private fun ingresarPedido() {
        var listadetalle=ArrayList<Detalle>()
        var monto = 0.0
        for (carrito in listaCarrito){
            var detalle=Detalle(carrito.cantidad,0,carrito.codproducto,carrito.precio,carrito.subtotal)

            listadetalle.add(detalle)
            monto= monto +carrito.subtotal
        }
        val pedido = Pedido(cliente.codcliente,0,0, listadetalle,cliente.xdireccion,"","",monto)
        val pedidoService : PedidoService = RestEngine.getRestEngine().create(PedidoService::class.java)
        val result: Call<Void> = pedidoService.registrarPedido(pedido)
        result.enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Conexion(this@PasarellaActivity).eliminarTodoCarritoDB()
                val intent = Intent(applicationContext,
                    ResumencompraActivity::class.java)
                startActivity(intent)
                Toast.makeText(applicationContext, "Gracias por tu compra ", Toast.LENGTH_LONG).show()
                finish()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(applicationContext, "Fallo aqui ", Toast.LENGTH_LONG).show()
            }
        })

    }

}