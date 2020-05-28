package com.dsige.appapplus.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.OtDetalle

import com.dsige.appapplus.ui.listeners.OnItemClickListener
import kotlinx.android.synthetic.main.cardview_ot_detalle.view.*

class OtDetalleAdapter(private var listener: OnItemClickListener.OTDetalleListener) :
    RecyclerView.Adapter<OtDetalleAdapter.ViewHolder>() {

    private var ots = emptyList<OtDetalle>()

    fun addItems(list: List<OtDetalle>) {
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
            LayoutInflater.from(parent.context).inflate(R.layout.cardview_ot_detalle, parent, false)
        return ViewHolder(v!!)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal fun bind(d: OtDetalle, listener: OnItemClickListener.OTDetalleListener) =
            with(itemView) {
                textView1.text = d.codigoSoporte
                textView2.text = d.alim
                textView3.text = d.armado
                imageView5.setOnClickListener { v -> listener.onItemClick(d, v, adapterPosition) }
            }
    }
}