package com.example.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.factory.HomeViewModelFactory
import com.example.myapplication.repositori.MainRepository

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Inicializar View Model
        val repository = MainRepository()
        val viewModelFactory = HomeViewModelFactory(repository)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        getTotalPricesmart()
        getTotalPlazaOnce()
        //setup buttons click
        binding.buttonNewEvaluation.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_headerEvaluationFragment)
        }
        binding.cardPrice.setOnClickListener {
            val idLocal: Int = 1
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationPlaceDetail(idLocal)
            findNavController().navigate(action)
        }
        binding.CardPsol.setOnClickListener {
            val idLocal: Int = 2
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationPlaceDetail(idLocal)
            findNavController().navigate(action)
        }
    }

    private fun getTotalPlazaOnce() {

        //Obtiene el conteo total de datos desde la api a traves de view model
        homeViewModel.getPlazaOnceCount()
        homeViewModel.totalPlaceOnce.observe(viewLifecycleOwner, Observer { response ->
            //Si la respusta es satisfactoria que los datos del view model se carguen en la UI
            if (response.isSuccessful) {
                val total = "${response.body()?.totalEvaluacions.toString()} Evaluaciones"
                binding.textCountPsol.text = total
            } else {
                errorMsg()
            }
        })
    }

    private fun getTotalPricesmart() {

        //Obtiene el conteo total de datos desde la api view model
        homeViewModel.getPricemartCount()
        homeViewModel.totalPricesmart.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                val total = "${response.body()?.totalEvaluacions.toString()} Evaluaciones"
                binding.textCountPrice.text = total
            } else {
                errorMsg()
            }
        })
    }

    private fun errorMsg() {
        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show()
    }
}