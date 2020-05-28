package com.dsige.appapplus.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.Ot

import com.dsige.appapplus.ui.listeners.OnItemClickListener
import kotlinx.android.synthetic.main.cardview_ot.view.*

class OTAdapter(private var listener: OnItemClickListener.OTListener) :
    RecyclerView.Adapter<OTAdapter.ViewHolder>() {

    private var ots = emptyList<Ot>()

    fun addItems(list: List<Ot>) {
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
            LayoutInflater.from(parent.context).inflate(R.layout.cardview_ot, parent, false)
        return ViewHolder(v!!)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal fun bind(o: Ot, listener: OnItemClickListener.OTListener) =
            with(itemView) {
                textView1.text = o.nroOt
                textView2.text = o.estado
                textView3.text = o.nombre
                textView4.text = o.distrito
                textView5.text = o.fechaRecepcion
                textView6.text = String.format("Dia Vcto: %s", o.diasVencimiento)
                itemView.setOnClickListener { v -> listener.onItemClick(o, v, adapterPosition) }
            }
    }
}