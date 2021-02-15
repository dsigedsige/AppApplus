package com.dsige.appapplus.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.InspeccionPhoto
import com.dsige.appapplus.data.viewModel.InspeccionViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Permission
import com.dsige.appapplus.helper.Util
import com.dsige.appapplus.ui.adapters.InspeccionPhotoAdapter
import com.dsige.appapplus.ui.listeners.OnItemClickListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_photo_inspecciones.*
import javax.inject.Inject

class PhotoInspeccionesActivity : DaggerAppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View) {
        inspeccionViewModel.setError("Maximo ${(maxPhotos - limit)} Fotos")
        when (v.id) {
            R.id.fabCamara -> startActivity(
                Intent(this, CameraActivity::class.java)
                    .putExtra("formatoId", inspeccionId)
                    .putExtra("usuarioId", usuarioId)
                    .putExtra("tipo", 1)
            )
            R.id.fabGaleria -> goGalery()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var inspeccionViewModel: InspeccionViewModel
    private var inspeccionId: Int = 0
    private var usuarioId: Int = 0
    private var limit: Int = 0
    private var maxPhotos: Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_inspecciones)
        val b = intent.extras
        if (b != null) {
            bindUI(
                b.getInt("id"), b.getInt("usuarioId")
            )
        }
    }

    private fun bindUI(id: Int, usu: Int) {
        inspeccionViewModel =
            ViewModelProvider(this, viewModelFactory).get(InspeccionViewModel::class.java)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Fotos"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        inspeccionId = id
        usuarioId = usu

        val photoAdapter =
            InspeccionPhotoAdapter(object : OnItemClickListener.InspeccionPhotoListener {
                override fun onItemClick(i: InspeccionPhoto, view: View, position: Int) {
                    val popupMenu = PopupMenu(this@PhotoInspeccionesActivity, view)
                    popupMenu.menu.add(1, 1, 1, getText(R.string.delete))
                    popupMenu.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            1 -> deletePhoto(i)
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

        inspeccionViewModel.getInspeccionPhotoById(id).observe(this, {
            limit = it.size
            if (it.size == maxPhotos) {
                fabMenu.visibility = View.GONE
            } else {
                fabMenu.visibility = View.VISIBLE
            }
            photoAdapter.addItems(it)
        })
        fabCamara.setOnClickListener(this)
        fabGaleria.setOnClickListener(this)

        inspeccionViewModel.success.observe(this, {
            if (it != null) {
                startActivity(
                    Intent(this, PreviewCameraActivity::class.java)
                        .putExtra("formatoId", inspeccionId)
                        .putExtra("usuarioId", usuarioId)
                        .putExtra("nameImg", it)
                        .putExtra("galery", true)
                        .putExtra("tipo", 1)
                )
            }
        })
        inspeccionViewModel.error.observe(this, {
            Util.toastMensajeShort(this, it)
        })
    }

    private fun deletePhoto(o: InspeccionPhoto) {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Mensaje")
            .setMessage("Deseas eliminar esta foto ?")
            .setPositiveButton("SI") { dialog, _ ->
                inspeccionViewModel.deletePhoto(o, this)
                dialog.dismiss()
            }
            .setNegativeButton("NO") { dialog, _ ->
                dialog.cancel()
            }
        dialog.show()
    }

    private fun goGalery() {
        val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(i, Permission.GALERY_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Permission.GALERY_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                inspeccionViewModel.generarArchivo(
                    Util.getFechaActualForPhoto(inspeccionId), this, data
                )
            }
        }
    }
}