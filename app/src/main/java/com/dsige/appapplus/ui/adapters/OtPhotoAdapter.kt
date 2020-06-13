package com.dsige.appapplus.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.OtPhoto
import com.dsige.appapplus.helper.Util

import com.dsige.appapplus.ui.listeners.OnItemClickListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cardview_photo.view.*
import java.io.File

class OtPhotoAdapter(private var listener: OnItemClickListener.OtPhotoListener) :
    RecyclerView.Adapter<OtPhotoAdapter.ViewHolder>() {

    private var ots = emptyList<OtPhoto>()

    fun addItems(list: List<OtPhoto>) {
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
        internal fun bind(o: OtPhoto, listener: OnItemClickListener.OtPhotoListener) =
            with(itemView) {
                val url = File(Util.getFolder(), o.fotoUrl)
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
                textViewObservacion.text = o.observacion
                itemView.setOnClickListener { v -> listener.onItemClick(o, v, adapterPosition) }
            }
    }
}