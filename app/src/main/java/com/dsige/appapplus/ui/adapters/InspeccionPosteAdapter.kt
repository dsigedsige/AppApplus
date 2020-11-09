package com.dsige.appapplus.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.InspeccionPoste

import com.dsige.appapplus.ui.listeners.OnItemClickListener
import kotlinx.android.synthetic.main.cardview_inspeccion_ot.view.*

class InspeccionPosteAdapter(private var listener: OnItemClickListener.InspeccionListener) :
    RecyclerView.Adapter<InspeccionPosteAdapter.ViewHolder>() {

    private var ots = emptyList<InspeccionPoste>()

    fun addItems(list: List<InspeccionPoste>) {
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
                .inflate(R.layout.cardview_inspeccion_ot, parent, false)
        return ViewHolder(v!!)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal fun bind(o: InspeccionPoste, listener: OnItemClickListener.InspeccionListener) =
            with(itemView) {
                textView1.text = String.format("Item General :%s", o.itemGeneral)
                textView2.text = o.direccion
                textView3.text = o.distritoCod
                textView4.text = o.codPoste
                textView5.text = o.tipoCablePre
                textView6.text = String.format("Cantidad de Cables : %s", o.cantCable)
                textView7.text = o.nombreEstado
                imgMap.setOnClickListener { v -> listener.onItemClick(o, v, adapterPosition) }
                itemView.setOnClickListener { v -> listener.onItemClick(o, v, adapterPosition) }
            }
    }
}