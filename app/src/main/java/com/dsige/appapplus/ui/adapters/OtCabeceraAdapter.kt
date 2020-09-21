package com.dsige.appapplus.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.OtCabecera

import com.dsige.appapplus.ui.listeners.OnItemClickListener
import kotlinx.android.synthetic.main.cardview_ot_cabecera.view.*

class OtCabeceraAdapter(private var listener: OnItemClickListener.OTCabeceraListener) :
    RecyclerView.Adapter<OtCabeceraAdapter.ViewHolder>() {

    private var ots = emptyList<OtCabecera>()

    fun addItems(list: List<OtCabecera>) {
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
                .inflate(R.layout.cardview_ot_cabecera, parent, false)
        return ViewHolder(v!!)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal fun bind(d: OtCabecera, listener: OnItemClickListener.OTCabeceraListener) =
            with(itemView) {
                textView1.text = d.nombreTipoFormato
                textView2.text = if (d.active == 1) "Ejecutado" else "Cerrado"
                textView3.text = String.format("Nro Sed : %s", d.sed)
                textView4.text = String.format("A: %s", d.alimentador)
                textView5.text = String.format("Fecha Registro : %s", d.fechaRegistro)
                itemView.setOnClickListener { v -> listener.onItemClick(d, v, adapterPosition) }
            }
    }
}