package com.example.myapplication.ui.placedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentPlacedetailBinding
import com.example.myapplication.factory.PlaceDetailFactory
import com.example.myapplication.repositori.MainRepository
import com.example.myapplication.ui.adapters.ListAdapter

class PlaceDetailFragment : Fragment() {

    //Este fragment carga el detalle de los lugares mostrando una recycler view con datos de los evaluados, fecha y lugar
    private lateinit var placeDetailViewModel: PlaceDetailViewModel
    private var _binding: FragmentPlacedetailBinding? = null
    val args:PlaceDetailFragmentArgs by navArgs()
    private  val myAdapter by lazy { ListAdapter() }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentPlacedetailBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idValue = args.id
        setupRecyclerView()
        val repository = MainRepository()
        val viewModelFactory = PlaceDetailFactory(repository)
        placeDetailViewModel = ViewModelProvider(this,viewModelFactory).get(PlaceDetailViewModel::class.java)
        placeDetailViewModel.getEvaluationsById(idValue)
        placeDetailViewModel.evaluations.observe(viewLifecycleOwner, Observer { response ->
            if(response.isSuccessful){
                response.body()?.let { myAdapter.setData(it) }
            }
            else{
                Toast.makeText(requireContext(), "Error al cargar datos", Toast.LENGTH_SHORT).show()
            }

        })
    }
    private fun setupRecyclerView(){
        binding.rvDetail.adapter = myAdapter
        binding.rvDetail.layoutManager = LinearLayoutManager(requireContext())
    }
}