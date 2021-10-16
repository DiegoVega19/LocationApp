package com.example.myapplication.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentListBinding
import com.example.myapplication.factory.ListViewModelFactory
import com.example.myapplication.repositori.MainRepository
import com.example.myapplication.ui.adapters.ListAdapter


class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel
    private var _binding: FragmentListBinding? = null
    private val myAdapter by lazy { ListAdapter() }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        val repository = MainRepository()
        val viewModelFactory = ListViewModelFactory(repository)
        listViewModel = ViewModelProvider(this, viewModelFactory).get(ListViewModel::class.java)
        //Obtener todas las evaluaciones a traves del viewmodel
        listViewModel.getAllEvaluations()
        listViewModel.allList.observe(viewLifecycleOwner, Observer { response ->
            //Si la respuesta es satisfactoria y no es nula que se carguen los datos en el recycler view adapter
            if (response.isSuccessful) {
                response.body()?.let { myAdapter.setData(it) }
            } else {
                Toast.makeText(requireContext(), "Error al cargar datos", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView() {
        binding.rvEvaluations.adapter = myAdapter
        binding.rvEvaluations.layoutManager = LinearLayoutManager(requireContext())
    }
}