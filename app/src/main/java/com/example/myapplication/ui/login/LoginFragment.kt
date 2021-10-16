package com.example.myapplication.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.factory.LoginViewModelFactory
import com.example.myapplication.model.Login
import com.example.myapplication.repositori.MainRepository


class LoginFragment : Fragment() {


    private var _binding:FragmentLoginBinding? = null
    private  lateinit var  viewModel:LoginViewModel
    private  val binding get() = _binding!!
    private var sesion=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = context?.getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE)
        val getSesion = sharedPreferences?.getBoolean("ISLOGED", false)
        if (getSesion == true) {
            findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        val view = binding.root
        return  view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = MainRepository()
        val viewModelFactory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(LoginViewModel::class.java)
       binding.buttonLogin.setOnClickListener {
           if (binding.textEmail.text!!.isEmpty() ){
               Toast.makeText(context, "Email Requerido", Toast.LENGTH_SHORT).show()
               return@setOnClickListener
           }
           if(binding.textPassword.text!!.isEmpty()){
               Toast.makeText(context, "Password Requerida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
           }

               Makelogin()

       }
    }

    private fun Makelogin() {


        //Creacion de login mediante viewModel, lo ideal es que la api tenga autenticacion por token, pero esta no posee
        val email = binding.textEmail.text.toString()
        val password = binding.textPassword.text.toString()
        val login = Login(email,password)
        Toast.makeText(context, "$email", Toast.LENGTH_SHORT).show()
        viewModel.LoginUser(login)
        viewModel.myResponse.observe(viewLifecycleOwner, Observer {
            response ->
            if (response.isSuccessful){
              //Guardamos sesion
                val sharedPreferences = context?.getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences?.edit()
                editor?.apply {
                    val isLogged = true
                    putBoolean("ISLOGED",isLogged)
                }?.apply()
               GotoHome()
            }
            else{
                Toast.makeText(context, "Credenciales Incorrectas", Toast.LENGTH_SHORT).show()

            }
        })

    }


    private  fun GotoHome(){
        if (findNavController().currentDestination?.id == R.id.loginFragment)
        findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
    }

    private fun verifySesion():Boolean{
        val prefs:SharedPreferences = requireContext().getSharedPreferences(getString(R.string.prefs),Context.MODE_PRIVATE)
        val dataSesion = prefs.getBoolean("sesion",false)
        return dataSesion
    }


}