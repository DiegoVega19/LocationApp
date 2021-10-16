package com.example.myapplication.ui.evaluation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapplication.DatePickerFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHeaderEvaluationBinding
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.myAppPreferences
import com.example.myapplication.set

class HeaderEvaluationFragment : Fragment() {

    private var _binding: FragmentHeaderEvaluationBinding? = null
    private var nombre:String? = null
    private  var fecha:String? = null
    private  var locacion:Int? = null
    private var nameLocacion:String ?= null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHeaderEvaluationBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinners()
      //Setup Listeners
        binding.textDate.setOnClickListener {
            setupDatePicker()
        }
        binding.spinnerPlace.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
             locacion =  binding.spinnerPlace.selectedItemPosition + 1
                nameLocacion = binding.spinnerPlace.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.buttonInit.setOnClickListener {
            if (binding.textNameEval.text!!.isEmpty() ){
                Toast.makeText(context, "Nombre Requerido", Toast.LENGTH_SHORT).show()
                 return@setOnClickListener
            }
            if(binding.textDate.text!!.isEmpty()){
                Toast.makeText(context, "Fecha es Requerida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

                saveHeader()
        }
    }

    private fun saveHeader() {
        nombre = binding.textNameEval.text.toString()
        fecha = binding.textDate.text.toString()
        //Pass data throug safe args
        val action = HeaderEvaluationFragmentDirections.actionHeaderEvaluationFragmentToEvaluationFragment(locacion!!,nombre!!,fecha!!,nameLocacion!!)
        findNavController().navigate(action)
    }

    private fun initSpinners() {
        val list = resources.getStringArray(R.array.locaciones)
        val placeAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,list)
        binding.spinnerPlace.adapter = placeAdapter
    }

    private fun setupDatePicker() {
        val datePicker = DatePickerFragment { year, month, day -> onDateSelected(year, month, day) }
        datePicker.show(parentFragmentManager, "datePicker")
    }
    private fun onDateSelected(year: Int,month: Int, day: Int  ) {
        binding.textDate.setText("$year-$month-$day")
    }

}