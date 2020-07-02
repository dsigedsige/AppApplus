package com.dsige.appapplus.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.Supervisor

import com.dsige.appapplus.ui.listeners.OnItemClickListener
import kotlinx.android.synthetic.main.cardview_combo.view.*

class SupervisorAdapter(var listener: OnItemClickListener.SupervisorListener) :
        RecyclerView.Adapter<SupervisorAdapter.ViewHolder>() {

    private var menus = emptyList<Supervisor>()

    fun addItems(list: List<Supervisor>) {
        menus = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_combo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(menus[position], listener)
    }

    override fun getItemCount(): Int {
        return menus.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(s: Supervisor, listener: OnItemClickListener.SupervisorListener) = with(itemView) {
            textViewNombre.text = s.nombre
            itemView.setOnClickListener { v -> listener.onItemClick(s, v, adapterPosition) }
        }
    }
}