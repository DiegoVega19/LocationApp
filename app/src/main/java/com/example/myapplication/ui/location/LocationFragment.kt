package com.example.myapplication.ui.location

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEvaluationBinding
import com.example.myapplication.databinding.FragmentLocationBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import androidx.core.view.get
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class LocationFragment : Fragment(),GoogleMap.OnMarkerClickListener {

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }
    private lateinit var map:GoogleMap
    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient:FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentMap.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        binding.fragmentMap.getMapAsync{
         //La llamada a este callback es similar a la implementacion del onMapReady, configura el mapa y aplicas codigo aqui cuando el mapa este listo
           map = it
            map.uiSettings.isZoomControlsEnabled = true
           //Llamo la funcion que Habilita mi localizacion mediante los permisos necesarios
            enableMyLocation()
            map.setOnMarkerClickListener(this)
          //Creo
            createMarker()

        }

    }

    private fun createMarker() {
        //Configuracion para crear mi marcador de ubicacion en tiempo real
        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->
            if (location!=null){
                lastLocation = location
                val currentLatLong = LatLng(location.latitude,location.longitude)
                placeMarkerOnMap(currentLatLong)
                //Creo una animacion de camara
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong,12f))
            }
        }
    }

    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")
        map.addMarker(markerOptions)
    }


    //-------------------------CREACION DE PERMISOS-------------------------------------------
    private fun isPermissionsGranted() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (isPermissionsGranted()) {
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(requireContext(), "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled = true
            }else{
                Toast.makeText(requireContext(), "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }



    override fun onResume() {
        super.onResume()
        binding.fragmentMap.onResume()
        if (!::map.isInitialized) return
        if(!isPermissionsGranted()){
            map.isMyLocationEnabled = false
            Toast.makeText(requireContext(), "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
    }
//Sobreescribir el ciclo de vida del fragment map para poder cargarlo dentro de otro fragment
    override fun onStart() {
        super.onStart()
        binding.fragmentMap .onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.fragmentMap.onStop()
    }


    override fun onPause() {
        super.onPause()
        binding.fragmentMap.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.fragmentMap.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.fragmentMap.onSaveInstanceState(outState)
    }

    override fun onMarkerClick(p0: Marker?) =false

    }


