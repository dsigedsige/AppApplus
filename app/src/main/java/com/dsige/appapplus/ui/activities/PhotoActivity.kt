package com.dsige.appapplus.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.OtPhoto
import com.dsige.appapplus.data.viewModel.RegistroViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.ui.adapters.OtPhotoAdapter
import com.dsige.appapplus.ui.listeners.OnItemClickListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_photo.*
import javax.inject.Inject

class PhotoActivity : DaggerAppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View) {
        startActivity(
            Intent(this, CameraActivity::class.java)
                .putExtra("formatoId", formatoId)
                .putExtra("usuarioId", usuarioId)
        )
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var registroViewModel: RegistroViewModel
    private var formatoId: Int = 0
    private var usuarioId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        val b = intent.extras
        if (b != null) {
            bindUI(
                b.getString("title")!!, b.getInt("tipo"),
                b.getInt("id"), b.getString("codigo")!!,
                b.getInt("otId"), b.getInt("estado"), b.getInt("usuarioId")
            )
        }
    }

    private fun bindUI(
        title: String, tipo: Int, id: Int, codigo: String, otId: Int, estado: Int, usu: Int
    ) {
        registroViewModel =
            ViewModelProvider(this, viewModelFactory).get(RegistroViewModel::class.java)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        formatoId = id
        usuarioId = usu

        val photoAdapter = OtPhotoAdapter(object : OnItemClickListener.OtPhotoListener {
            override fun onItemClick(o: OtPhoto, view: View, position: Int) {
                val popupMenu = PopupMenu(this@PhotoActivity, view)
                popupMenu.menu.add(1, 1, 1, getText(R.string.delete))
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        1 -> deletePhoto(o)
                    }
                    false
                }
                popupMenu.show()
            }
        })

        recyclerView.itemAnimator = DefaultItemAnimator()
        val layoutManager = StaggeredGridLayoutManager(2, 1)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = photoAdapter

        registroViewModel.getPhotoById(id).observe(this, Observer { f ->
            if (f != null) {
                photoAdapter.addItems(f)
            }
        })
        fab.setOnClickListener(this)
    }

    private fun deletePhoto(o: OtPhoto) {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Mensaje")
            .setMessage("Deseas eliminar esta foto ?")
            .setPositiveButton("SI") { dialog, _ ->
                registroViewModel.deletePhoto(o)
                dialog.dismiss()
            }
            .setNegativeButton("NO") { dialog, _ ->
                dialog.cancel()
            }
        dialog.show()
    }
}
