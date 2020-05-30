package com.dsige.appapplus.ui.activities

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.dsige.appapplus.R
import com.dsige.appapplus.data.viewModel.RegistroViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.ui.adapters.TabLayoutAdapter
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_protocolo.*
import javax.inject.Inject

class ProtocoloActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var registroViewModel: RegistroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_protocolo)

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

        if (estado == 0) {
            registroViewModel.generateCabecera(title, tipo, codigo, id, otId)
        }

        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab5))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab6))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab7))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab8))

        val tabLayoutAdapter =
            TabLayoutAdapter.TabLayoutProtocolo(supportFragmentManager, tabLayout.tabCount, id)
        viewPager.adapter = tabLayoutAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}