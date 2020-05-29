package com.dsige.appapplus.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.OtEquipo

import com.dsige.appapplus.ui.listeners.OnItemClickListener
import kotlinx.android.synthetic.main.cardview_ot_cabecera.view.*

class OtEquipoAdapter(private var listener: OnItemClickListener.OTEquipoListener) :
    RecyclerView.Adapter<OtEquipoAdapter.ViewHolder>() {

    private var ots = emptyList<OtEquipo>()

    fun addItems(list: List<OtEquipo>) {
        ots = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ots[position], listener)
    }

    override fun getItemCount(): Int {
        return ots.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cardview_ot_equipo, parent, false)
        return ViewHolder(v!!)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal fun bind(d: OtEquipo, listener: OnItemClickListener.OTEquipoListener) =
            with(itemView) {
                textView1.text = d.equipoId.toString()
                textView2.text = d.nroKardex
                textView3.text = d.nroFabrica
                itemView.setOnClickListener { v -> listener.onItemClick(d, v, adapterPosition) }
            }
    }
}