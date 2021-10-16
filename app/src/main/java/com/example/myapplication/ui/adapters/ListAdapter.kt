package com.example.myapplication.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.DashboardItem2Binding
import com.example.myapplication.model.Evaluation

class ListAdapter() : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var evaluationList = emptyList<Evaluation>()

    //Adaptador principal con ViewBindings
    class MyViewHolder(val binding: DashboardItem2Binding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.MyViewHolder {

        return MyViewHolder(
            DashboardItem2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListAdapter.MyViewHolder, position: Int) {
        val currentItem = evaluationList[position]
        holder.binding.textName.text = currentItem.nombreEvaluado.toString()
        holder.binding.textDateCreated.text = currentItem.fechaDeEvaluacion.toString()
        holder.binding.textLocation.text = currentItem.nombreLocal.toString()
    }

    override fun getItemCount(): Int {
        return evaluationList.size
    }

    fun setData(list: List<Evaluation>) {
        this.evaluationList = list
        notifyDataSetChanged()
    }

}