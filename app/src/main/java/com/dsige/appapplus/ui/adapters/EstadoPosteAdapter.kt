package com.dsige.appapplus.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.EstadoPoste

import com.dsige.appapplus.ui.listeners.OnItemClickListener
import kotlinx.android.synthetic.main.cardview_combo.view.*

class EstadoPosteAdapter(var listener: OnItemClickListener.EstadoPosteListener) :
    RecyclerView.Adapter<EstadoPosteAdapter.ViewHolder>() {

    private var menus = emptyList<EstadoPoste>()

    fun addItems(list: List<EstadoPoste>) {
        menus = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cardview_combo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(menus[position], listener)
    }

    override fun getItemCount(): Int {
        return menus.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(m: EstadoPoste, listener: OnItemClickListener.EstadoPosteListener) =
            with(itemView) {
                textViewNombre.text = m.descripcion
                itemView.setOnClickListener { v -> listener.onItemClick(m, v, adapterPosition) }
            }
    }
}