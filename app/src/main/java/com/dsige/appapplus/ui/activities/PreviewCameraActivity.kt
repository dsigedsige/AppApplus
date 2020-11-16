package com.dsige.appapplus.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.InspeccionPhoto
import com.dsige.appapplus.data.local.model.OtPhoto
import com.dsige.appapplus.data.viewModel.InspeccionViewModel
import com.dsige.appapplus.data.viewModel.RegistroViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_preview_camera.*
import java.io.File
import javax.inject.Inject

class PreviewCameraActivity : DaggerAppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fabClose -> if (galery) {
                finish()
            } else {
                startActivity(
                    Intent(this, CameraActivity::class.java)
                        .putExtra("formatoId", formatoId)
                        .putExtra("usuarioId", usuarioId)
                        .putExtra("tipo", tipo)
                )
                finish()
            }
            R.id.fabOk -> formOtPhoto()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var registroViewModel: RegistroViewModel
    lateinit var inspeccionViewModel: InspeccionViewModel

    private var formatoId: Int = 0
    private var usuarioId: Int = 0
    private var nameImg: String = ""
    private var galery: Boolean = false
    private var tipo: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_camera)
        val b = intent.extras
        if (b != null) {
            formatoId = b.getInt("formatoId")
            usuarioId = b.getInt("usuarioId")
            nameImg = b.getString("nameImg")!!
            galery = b.getBoolean("galery")
            tipo = b.getInt("tipo")
            bindUI(b.getString("nameImg")!!)
        }
    }

    private fun bindUI(name: String) {
        registroViewModel =
            ViewModelProvider(this, viewModelFactory).get(RegistroViewModel::class.java)
        inspeccionViewModel =
            ViewModelProvider(this, viewModelFactory).get(InspeccionViewModel::class.java)

        fabClose.setOnClickListener(this)
        fabOk.setOnClickListener(this)
        textViewImg.text = nameImg

        if (tipo == 1) {
            editTextObservacion.visibility = View.GONE
        }

        Handler().postDelayed({
            val f = File(Util.getFolder(this), name)
            Picasso.get().load(f)
                .into(imageView, object : Callback {
                    override fun onSuccess() {
                        progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        Log.i("TAG", e.toString())
                    }
                })
        }, 200)
        registroViewModel.success.observe(this, {
            finish()
        })
        registroViewModel.error.observe(this, {
            Util.toastMensaje(this, it)
        })
        inspeccionViewModel.success.observe(this, {
            finish()
        })
        inspeccionViewModel.error.observe(this, {
            Util.toastMensaje(this, it)
        })
    }

    private fun formOtPhoto() {
        if (tipo == 1) {
            val o = InspeccionPhoto()
            o.inspeccionCampoId = formatoId
            o.fotoUrl = nameImg
            o.estado = 1
            o.usuarioId = usuarioId
            o.fecha = Util.getFecha()
            o.active = 1
            inspeccionViewModel.insertInspeccionPhoto(o)
        } else {
            val o = OtPhoto()
            o.formatoId = formatoId
            o.fotoUrl = nameImg
            o.observacion = editTextObservacion.text.toString()
            o.estado = 1
            o.usuarioId = usuarioId
            o.fecha = Util.getFecha()
            registroViewModel.validateOtPhoto(o)
        }
    }
}