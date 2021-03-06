package com.dsige.appapplus.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.MenuPrincipal

import com.dsige.appapplus.ui.listeners.OnItemClickListener
import kotlinx.android.synthetic.main.cardview_combo.view.*

class ComboAdapter(var listener: OnItemClickListener.MenuListener) :
        RecyclerView.Adapter<ComboAdapter.ViewHolder>() {

    private var menus = emptyList<MenuPrincipal>()

    fun addItems(list: List<MenuPrincipal>) {
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
        fun bind(m: MenuPrincipal, listener: OnItemClickListener.MenuListener) = with(itemView) {
            textViewNombre.text = m.title
            itemView.setOnClickListener { v -> listener.onItemClick(m, v, adapterPosition) }
        }
    }
}