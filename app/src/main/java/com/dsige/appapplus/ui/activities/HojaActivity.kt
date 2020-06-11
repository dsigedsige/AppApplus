package com.dsige.appapplus.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dsige.appapplus.R
import com.dsige.appapplus.data.viewModel.RegistroViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.ui.adapters.TabLayoutAdapter
import com.dsige.appapplus.ui.fragments.HojaMainFragment
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_hoja.*
import javax.inject.Inject

class HojaActivity : DaggerAppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View) {
        when (position) {
            1, 2, 3 -> {
                startActivity(
                    Intent(this, Hoja123Activity::class.java)
                        .putExtra("tipo", position)
                        .putExtra("formatoId", formatoId)
                        .putExtra("id", 0)
                        .putExtra("title", name)
                )
            }
            4 -> {
                startActivity(
                    Intent(this, Hoja4Activity::class.java)
                        .putExtra("formatoId", formatoId)
                        .putExtra("id", 0)
                        .putExtra("title", name)
                        .putExtra("tipo", position)
                )
            }
            5, 6, 7 -> {
                startActivity(
                    Intent(this, Hoja56Activity::class.java)
                        .putExtra("tipo", position)
                        .putExtra("formatoId", formatoId)
                        .putExtra("id", 0)
                        .putExtra("title", name)
                )
            }
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var registroViewModel: RegistroViewModel
    private var formatoId: Int = 0
    private var position: Int = 1
    private var name: String = "Celda 10KV"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hoja)
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

        fabAdd.setOnClickListener(this)

//        if (estado == 0) {
//            registroViewModel.generateCabecera(title, tipo, codigo, id, otId)
//        }

        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab9))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab10))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab11))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab12))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab13))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab14))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab15))

        val tabLayoutAdapter =
            TabLayoutAdapter.TabLayoutHoja(supportFragmentManager, tabLayout.tabCount, id)
        viewPager.adapter = tabLayoutAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                position = tab.position + 1
                viewPager.currentItem = tab.position
                when(position){
                    3,7 -> fabAdd.visibility =  View.GONE
                    else -> fabAdd.visibility =  View.VISIBLE
                }

                name = when (position) {
                    1 -> "Celda 10KV"
                    2 -> "Equipo 10KV"
                    3 -> "Protección"
                    4 -> "Transformadores"
                    5 -> "Servicio Particular"
                    6 -> "Alumbrado Publico"
                    else -> "Generales"
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}