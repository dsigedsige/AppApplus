package com.dsige.appapplus.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.dsige.appapplus.R
import com.dsige.appapplus.data.viewModel.RegistroViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.ui.adapters.TabLayoutAdapter
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_equipo_main.*
import javax.inject.Inject

class EquipoMainActivity : DaggerAppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View) {
        startActivity(
            Intent(this@EquipoMainActivity, EquipoSecondActivity::class.java)
                .putExtra("tipo", position)
                .putExtra("formatoId", formatoId)
                .putExtra("equipoId", 0)
                .putExtra("title", name)
        )
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var registroViewModel: RegistroViewModel
    private var name: String = "Transformadores"
    private var position: Int = 1
    private var formatoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipo_main)
        val b = intent.extras
        if (b != null) {
            bindUI(
                b.getString("title")!!, b.getInt("tipo"),
                b.getInt("id"), b.getString("codigo")!!,
                b.getInt("otId"), b.getInt("estado")
            )
        }
    }

    private fun bindUI(title: String, tipo: Int, id: Int, codigo: String, otId: Int, estado: Int) {
        registroViewModel =
            ViewModelProvider(this, viewModelFactory).get(RegistroViewModel::class.java)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        formatoId = id

        if (estado == 0) {
            registroViewModel.generateCabecera(title, tipo, codigo, id, otId)
        }

        fabAdd.setOnClickListener(this)
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab1))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab2))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab3))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab4))

        val tabLayoutAdapter =
            TabLayoutAdapter.TabLayoutEquipo(supportFragmentManager, tabLayout.tabCount, id)
        viewPager.adapter = tabLayoutAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                position = tab.position + 1
                viewPager.currentItem = tab.position

                if (position == 3)
                    fabAdd.visibility = View.GONE
                else
                    fabAdd.visibility = View.VISIBLE

                name = when (position) {
                    1 -> "Transformadores"
                    2 -> "Maniobra"
                    3 -> "Proteccion"
                    else -> "Redes Areas"
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}