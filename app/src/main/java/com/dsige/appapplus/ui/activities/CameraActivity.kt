package com.dsige.appapplus.ui.activities

import android.os.Bundle
import com.dsige.appapplus.R
import com.dsige.appapplus.ui.fragments.CameraFragment
import dagger.android.support.DaggerAppCompatActivity

class CameraActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        val b = intent.extras
        if (b != null) {
            savedInstanceState ?: supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    CameraFragment.newInstance(
                        b.getInt("formatoId"),
                        b.getInt("usuarioId")
                    )
                )
                .commit()
        }
    }
}