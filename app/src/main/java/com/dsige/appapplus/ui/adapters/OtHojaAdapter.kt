package com.dsige.appapplus.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.*

import com.dsige.appapplus.ui.listeners.OnItemClickListener
import com.google.gson.Gson
import kotlinx.android.synthetic.main.cardview_ot_hoja.view.*

class OtHojaAdapter(
    private var tipo: Int, private var listener: OnItemClickListener.OTHojaListener
) :
    RecyclerView.Adapter<OtHojaAdapter.ViewHolder>() {

    private var ots = emptyList<Class<*>>()

    fun addItems(list: List<Class<*>>) {
        ots = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (tipo) {
            1, 2, 3 -> holder.bind123(ots[position] as OtHoja123, listener)
            4 -> holder.bind4(ots[position] as OtHoja4, listener)
            else -> holder.bind567(ots[position] as OtHoja56, listener)
        }
    }

    override fun getItemCount(): Int {
        return ots.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cardview_ot_hoja, parent, false)
        return ViewHolder(v!!)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal fun bind123(d: OtHoja123, listener: OnItemClickListener.OTHojaListener) =
            with(itemView) {
                when (d.item) {
                    1 -> {
                        textView1.text = String.format("Item %s", d.hoja123Id)
                        textView2.text = String.format("N° Celda %s", d.nroCelda)
                        textView3.text = String.format("Funcion %s", d.funcion)
                    }
                    else -> {
                        textView1.text = String.format("Item %s", d.hoja123Id)
                        textView2.text = String.format("Equipo %s", d.equipo)
                        textView3.visibility = View.GONE
                    }
                }
                itemView.setOnClickListener { v -> listener.onItemClick123(d, v, adapterPosition) }
            }

        internal fun bind4(d: OtHoja4, listener: OnItemClickListener.OTHojaListener) =
            with(itemView) {
                textView1.text = String.format("Item %s", d.hoja4Id)
                textView2.text = String.format("Ubicación %s", d.ubicacion)
                textView3.visibility = View.GONE
                itemView.setOnClickListener { v -> listener.onItemClick4(d, v, adapterPosition) }
            }

        internal fun bind567(d: OtHoja56, listener: OnItemClickListener.OTHojaListener) =
            with(itemView) {
                when (d.item) {
                    5, 6 -> {
                        textView1.text = String.format("Item %s", d.hoja56Id)
                        textView2.text = String.format("Tipo Tablero %s", d.tipoTablero)
                        textView3.visibility = View.GONE
                    }
                    7 -> {
                        textView1.text = String.format("Item %s", d.hoja56Id)
                        textView2.text = String.format("Nro Medido %s", d.nroMedidor)
                        textView3.visibility = View.GONE
                    }
                }
                itemView.setOnClickListener { v -> listener.onItemClick56(d, v, adapterPosition) }
            }
    }
}