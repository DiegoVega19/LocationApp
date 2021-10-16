package com.example.myapplication.ui.evaluation

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEvaluationBinding
import com.example.myapplication.databinding.FragmentHeaderEvaluationBinding
import com.example.myapplication.factory.EvaluationViewModelFactory
import com.example.myapplication.get
import com.example.myapplication.getObject
import com.example.myapplication.model.Evaluation
import com.example.myapplication.model.EvaluationRequest
import com.example.myapplication.myAppPreferences
import com.example.myapplication.repositori.MainRepository
import com.example.myapplication.ui.login.LoginViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.*


class EvaluationFragment : Fragment() {

    companion object {
        private val REQUEST_PERMISSION_REQUEST_CODE = 2020
    }

    ////Declarar Variables para ser utilizadas en el scope
    //NavArgs
    val args: EvaluationFragmentArgs by navArgs()

    //Datos Evaluacion
    private var placeId: Int? = null
    private var placeName: String? = null
    private var fecha: String? = null
    private var nombre: String? = null
    private var answer1: String? = "No"
    private var answer2: String? = "No"
    private var answer3: String? = "No"
    private var answer4: String? = "No"
    private var answer5: String? = "No"
    private var answer6: String? = "No"
    private var answer7: String? = null
    private var answer8: String? = null

    private  var REALTIMELOCATION:String?=null


    //Bindings
    private var _binding: FragmentEvaluationBinding? = null

    //View Model
    private lateinit var viewModel: EvaluationViewModel
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEvaluationBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*Primero revisa los permisos del usario (por si el usuario los desactivo o si no ha entrado al
       mapa que muestra se localizacion en tiempo real*/
        checkPermision();
        //Cuando se crea la vista se obtiene los datos de la evaluacion anteriores
        retrieveUserHeader()
        //Declaracion de los repositorios y el view Model
        val repository = MainRepository()
        val viewModelFactory = EvaluationViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EvaluationViewModel::class.java)
        //Inicializar Listeners
        setupListeners()
    }

    private fun setupListeners() {
        //Este metodo guarda la informacion de los checkbox segun lo que selecciono el usuario

        binding.switchQuestion1.setOnClickListener {
            answer1 = if (binding.switchQuestion1.isChecked) "Si" else "No"
        }
        binding.switchQuestion2.setOnClickListener {
            answer2 = if (binding.switchQuestion2.isChecked) "Si" else "No"
        }
        binding.switchQuestion3.setOnClickListener {
            answer3 = if (binding.switchQuestion3.isChecked) "Si" else "No"
        }
        binding.switchQuestion4.setOnClickListener {
            answer4 = if (binding.switchQuestion4.isChecked) "Si" else "No"
        }
        binding.switchQuestion5.setOnClickListener {
            answer5 = if (binding.switchQuestion5.isChecked) "Si" else "No"
        }
        binding.switchQuestion6.setOnClickListener {
            answer6 = if (binding.switchQuestion6.isChecked) "Si" else "No"
        }
        binding.buttonFinish.setOnClickListener {
            validateInputs()
        }

    }

    private fun retrieveUserHeader() {
        //Obtencion de datos mediante safe args
        placeId = args.placeID
        fecha = args.dateEvaluation
        nombre = args.evaluatedName
        placeName = args.placeName

    }


    private fun validateInputs() {
        //Validaciones de Inputs
        if (binding.eTCustomerAtenden.text!!.isEmpty()) {
            Toast.makeText(context, "El nombre del Cliente es Requerido", Toast.LENGTH_SHORT).show()

        } else if (binding.eTCustomerAtenden.text!!.isEmpty()) {
            Toast.makeText(context, "El nombre del Cliente es Requerido", Toast.LENGTH_SHORT).show()
        } else {
            saveEvaluation()
        }
    }

    private fun saveEvaluation() {

        answer7 = binding.eTCustomerAtenden.text.toString()
        answer8 = binding.etServices.text.toString()



        /*     Una vez revisado procede a obtener tu direccion en tiempo real
               En este caso la aplicacion obtiene tu ubicacion y verifica si tu ubicacion coincide con el lugar, si es igual a la ubicacion deja guardar datos
               Mientras no permite guardar datos Para pruebas se dejo que realice la prueba si tu ubicacion es en Nicaragua / Managua en general
               Cambiando la localizacion a otro Pais como por Ejemplo Honduras desde las settings del emulador, este no deja ingresar datos.*/


        var testLocation = "Managua, Nicaragua"
        //Verifica si la direccion obtenida anteriormente con la api de google maps es igual que la localizacion predefinida para test
        if (REALTIMELOCATION?.contains(testLocation.toString()) == true) {
            //Guardar informacion
            val evaluation = EvaluationRequest(
                placeId!!,
                fecha!!,
                nombre!!,
                answer1!!,
                answer2!!,
                answer3!!,
                answer4!!,
                answer5!!,
                answer6!!,
                answer7!!,
                answer8!!
            )
            Toast.makeText(requireContext(), "evaluationRequest $evaluation", Toast.LENGTH_SHORT)
                .show()
            viewModel.postEvaluation(evaluation)
            viewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->
                if (response.isSuccessful) {
                    findNavController().navigate(R.id.action_evaluationFragment_to_navigation_home)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Error Verifique los campos!!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
        else {
            //No se encuentra en la ruta especificada o a veces (casi siempre la primera vez que entra a la app no toma tu ubicacion) por ende muestra el siguiente mensaje y se tiene que cerrar la app
            Toast.makeText(
                requireContext(),
                "No se encuentra en la ruta especificada o No esta Tomando su localizacion, cierre la app y vuelta a intentarlo",
                Toast.LENGTH_SHORT
            ).show()

        }

    }

    private fun checkPermision() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_PERMISSION_REQUEST_CODE
            )
        } else {
            //Si todos los permisos estan bien procede a obtener la localizacion del usuario
            getCurrentLocation()
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_REQUEST_CODE && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                Toast.makeText(requireActivity(), "Permiso Denegado!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCurrentLocation() {
        var locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        //now getting address from latitude and longitude

        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
        var addresses: List<Address>


        LocationServices.getFusedLocationProviderClient(requireActivity())
            .requestLocationUpdates(locationRequest, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    LocationServices.getFusedLocationProviderClient(requireActivity())
                        .removeLocationUpdates(this)
                    if (locationResult != null && locationResult.locations.size > 0) {
                        var locIndex = locationResult.locations.size - 1
                        var latitude = locationResult.locations.get(locIndex).latitude
                        var longitude = locationResult.locations.get(locIndex).longitude
                        addresses = geocoder.getFromLocation(latitude, longitude, 1)
                      var address: String = addresses[0].getAddressLine(0)
                       REALTIMELOCATION = address
                    }
                }

            }, Looper.getMainLooper())

    }

}