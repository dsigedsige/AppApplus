package com.dsige.appapplus.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.dsige.appapplus.R
import com.dsige.appapplus.data.viewModel.InspeccionViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Util
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_registro_inspeccion.*
import javax.inject.Inject

class RegistroInspeccionActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var inspeccionViewModel: InspeccionViewModel
    private var inspeccionId: Int = 0

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_inspecciones, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fab1 -> goPosteActivity(1,item.title.toString())
            R.id.fab2 -> goPosteActivity(2,item.title.toString())
            R.id.fab3 -> goPosteActivity(3,item.title.toString())
            R.id.fab4 -> goPosteActivity(4,item.title.toString())
            R.id.fab5 -> goPosteActivity(5,item.title.toString())
            R.id.fab6 -> goPosteActivity(6,item.title.toString())
            R.id.fab7 -> goPosteActivity(7,item.title.toString())
            R.id.fab8 -> goPosteActivity(8,item.title.toString())
            R.id.fab9 -> goPosteActivity(9,item.title.toString())
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_inspeccion)
        val b = intent.extras
        if (b != null) {
            bindUI(b.getInt("id"))
        }
    }

    private fun bindUI(id: Int) {
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Datos Generales"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        inspeccionId = id
        inspeccionViewModel =
            ViewModelProvider(this, viewModelFactory).get(InspeccionViewModel::class.java)

        inspeccionViewModel.getInspeccionById(id).observe(this, {
            if (it != null) {
                editTextGeneralId.setText(String.format("Item General :%s", it.itemGeneral))
                editTextNPoste.setText(it.codPoste)
            }
        })
    }

    private fun goPosteActivity(tipo: Int,title:String) {
        startActivity(
            Intent(this, FormPosteActivity::class.java)
                .putExtra("tipo", tipo)
                .putExtra("id", inspeccionId)
                .putExtra("title", title)
        )
    }
}