package com.dsige.appapplus.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.*

import com.dsige.appapplus.ui.listeners.OnItemClickListener
import kotlinx.android.synthetic.main.cardview_ot_hoja.view.*

class OtHojaAdapter(private var listener: OnItemClickListener.OTHojaListener) :
    RecyclerView.Adapter<OtHojaAdapter.ViewHolder>() {

    private var ots = emptyList<Class<*>>()

    fun addItems(list: List<Class<*>>) {
        ots = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bind(ots[position], listener)
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
        internal fun bind(d: Class<*>, listener: OnItemClickListener.OTHojaListener) =
            with(itemView) {
                when (d) {
                    is OtHoja123 -> {
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
                    }
                    is OtHoja4 -> {
                        textView1.text = String.format("Item %s", d.hoja4Id)
                        textView2.text = String.format("Ubicación %s", d.ubicacion)
                        textView3.visibility = View.GONE
                    }
                    is OtHoja56 -> {
                        textView1.text = String.format("Item %s", d.hoja56Id)
                        textView2.text = String.format("Tipo Tablero %s", d.tipoTablero)
                        textView3.visibility = View.GONE
                    }
                }
                itemView.setOnClickListener { v -> listener.onItemClick(d, v, adapterPosition) }
            }
    }
}