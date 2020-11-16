package com.dsige.appapplus.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.InspeccionPhoto
import com.dsige.appapplus.helper.Util

import com.dsige.appapplus.ui.listeners.OnItemClickListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cardview_photo.view.*
import java.io.File

class InspeccionPhotoAdapter(private var listener: OnItemClickListener.InspeccionPhotoListener) :
    RecyclerView.Adapter<InspeccionPhotoAdapter.ViewHolder>() {

    private var ots = emptyList<InspeccionPhoto>()

    fun addItems(list: List<InspeccionPhoto>) {
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
            LayoutInflater.from(parent.context).inflate(R.layout.cardview_photo, parent, false)
        return ViewHolder(v!!)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal fun bind(
            o: InspeccionPhoto,
            listener: OnItemClickListener.InspeccionPhotoListener
        ) =
            with(itemView) {
                val url = File(Util.getFolder(itemView.context), o.fotoUrl)
                Picasso.get()
                    .load(url)
                    .into(imageViewPhoto, object : Callback {
                        override fun onSuccess() {
                            progress.visibility = View.GONE
                            imageViewPhoto.visibility = View.VISIBLE
                        }

                        override fun onError(e: Exception) {

                        }
                    })
                textViewObservacion.text = o.fotoUrl
                itemView.setOnClickListener { v -> listener.onItemClick(o, v, adapterPosition) }
            }
    }
}