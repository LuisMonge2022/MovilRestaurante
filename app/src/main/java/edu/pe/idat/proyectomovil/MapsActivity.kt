package edu.pe.idat.proyectomovil

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import edu.pe.idat.proyectomovil.databinding.ActivityMapsBinding
import kotlinx.android.synthetic.main.activity_direccion.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerDragListener(this)
        val direccion = LatLng(-12.010205, -77.002875)
        mMap.addMarker(MarkerOptions()
            .position(direccion)
            .title("Buscando direccion")
            .draggable(true))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(direccion, 16.0F))
    }

    override fun onMarkerDrag(p0: Marker) {
        var posicion = p0.position
        p0.snippet = posicion.latitude.toString()+ " / "+
                posicion.longitude.toString()
        p0.showInfoWindow()
        mMap.animateCamera(CameraUpdateFactory.newLatLng(posicion))
    }

    override fun onMarkerDragEnd(p0: Marker) {
        p0.title = "Nueva direccion"
        p0.showInfoWindow()
        mMap.animateCamera(CameraUpdateFactory.newLatLng(p0.position))
        var posicion = p0.position.toString()
        mostrarAlerta(posicion)
    }

    private fun mostrarAlerta(posicion: String) {
        var alerta =AlertDialog.Builder(this@MapsActivity)
        alerta.setTitle("Agregar Direccion")
        alerta.setMessage("Deseas Agregar esta Direccion $posicion")
            .setPositiveButton("SI",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    Toast.makeText(this@MapsActivity, "Se agrego direccion", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this@MapsActivity,
                        DireccionActivity::class.java).apply { putExtra("nuevadireccion",posicion)}
                    startActivity(intent)
                    finish()
                }

            }).setNegativeButton("No",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    Toast.makeText(this@MapsActivity,"selecciona otra direccion",Toast.LENGTH_SHORT).show()
                }

            }).show()
    }

    override fun onMarkerDragStart(p0: Marker) {
        p0.showInfoWindow()
    }


}