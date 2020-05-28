package com.dsige.appapplus.ui.activities

import android.content.Intent
import android.os.Bundle
import com.dsige.appapplus.R
import com.dsige.appapplus.ui.adapters.TabLayoutAdapter
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_equipo_main.*

class EquipoMainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipo_main)
        val b = intent.extras
        if (b != null) {
            bindUI(b.getString("title")!!, b.getInt("id"), b.getString("codigo")!!)
        }
    }

    private fun bindUI(title: String, id: Int, codigo: String) {
        setSupportActionBar(toolbar)
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab1))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab2))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab3))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab4))

        val tabLayoutAdapter =
            TabLayoutAdapter.TabLayoutEquipo(supportFragmentManager, tabLayout.tabCount, 1)
        viewPager.adapter = tabLayoutAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                viewPager.currentItem = position

                val name = when (position) {
                    0 -> "Transformadores"
                    1 -> "Maniobra"
                    2 -> "Proteccion"
                    else -> "Redes Areas"
                }

                fabAdd.setOnClickListener {
                    startActivity(
                        Intent(this@EquipoMainActivity, EquipoSecondActivity::class.java)
                            .putExtra("tipo", position)
                            .putExtra("title", name)
                    )
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}