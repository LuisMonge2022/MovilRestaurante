package edu.pe.idat.proyectomovil


import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.util.JsonReader
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import edu.pe.idat.proyectomovil.Service.ClienteService
import edu.pe.idat.proyectomovil.culqi.culqi_android.Culqi.*
import edu.pe.idat.proyectomovil.culqi.culqi_android.Validation.Validation
import edu.pe.idat.proyectomovil.model.Cliente
import edu.pe.idat.proyectomovil.utilitarios.RestEngine
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create


class PasarellaActivity : AppCompatActivity() {

    private lateinit var validation: Validation

    private lateinit var progress: ProgressDialog

    private lateinit var txtcardnumber: TextView
    private lateinit var txtcvv:TextView
    private lateinit var txtmonth:TextView
    private lateinit var txtyear:TextView
    private lateinit var txtemail:TextView
    private lateinit var kind_card:TextView
    private lateinit var result:TextView
    private lateinit var btnPay: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pasarella)


        validation = Validation()
        progress = ProgressDialog(this)
        progress.setMessage("Validando informacion de la tarjeta")
        progress.setCancelable(false)
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER)

        txtcardnumber = findViewById<View>(R.id.txt_cardnumber) as TextView

        txtcvv = findViewById<View>(R.id.txt_cvv) as TextView

        txtmonth = findViewById<View>(R.id.txt_month) as TextView

        txtyear = findViewById<View>(R.id.txt_year) as TextView

        txtemail = findViewById<View>(R.id.txt_email) as TextView

        kind_card = findViewById<View>(R.id.kind_card) as TextView

        result = findViewById<View>(R.id.token_id) as TextView

        btnPay = findViewById<View>(R.id.btn_pay) as Button

        txtcvv.isEnabled = false

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


                /*if (validation.luhn(text)) {
                    txtcardnumber.setBackgroundResource(R.drawable.border_sucess)
                } else {
                    txtcardnumber.setBackgroundResource(R.drawable.border_error)
                }*/
                val cvv = validation.bin(text, kind_card)
                if (cvv > 0) {
                    txtcvv.filters = arrayOf<InputFilter>(LengthFilter(cvv))
                    txtcvv.isEnabled = true
                } else {
                    txtcvv.isEnabled = false
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
            progress.show()
            val card = Card(
                txtcardnumber.text.toString(),
                txtcvv.text.toString(),
                txtmonth.text.toString().toInt(),
                txtyear.text.toString().toInt(),
                txtemail.text.toString()
            )
            val token = Token("pk_test_a465a141a8070f94")
            token.createToken(applicationContext, card, object : TokenCallback {

                override fun onSuccess(token: JSONObject?) {
                    try {
                        var monto= 5000
                        var nvotoken= token?.get("id").toString()
                        val cargo = Cargo("sk_test_d3ea28093ab85909")
                        cargo.crearCargo(applicationContext,nvotoken,monto,txtemail.text.toString(),object:
                            CargoCallback {
                            override fun onSuccess(respuesta: JSONObject?) {
                                val gson = Gson()
                                val lista: CargoResponse =
                                    gson.fromJson(respuesta.toString(),CargoResponse::class.java)
                                val resp= lista.outcome.user_message
                                Toast.makeText(applicationContext,"$resp",Toast.LENGTH_SHORT).show()
                            }

                            override fun onError(error: java.lang.Exception?) {
                                Log.v("","Error: +${error}")
                                Toast.makeText(applicationContext,"Fallaste otra vez",Toast.LENGTH_SHORT).show()
                            }
                        })

                    } catch (ex: Exception) {
                        progress.hide()
                    }
                    progress.hide()
                }
                override fun onError(error: Exception?) {
                    progress.hide()
                }
            })
        }
    }

}