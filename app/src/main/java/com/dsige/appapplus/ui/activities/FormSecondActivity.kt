package com.dsige.appapplus.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dsige.appapplus.R
import com.dsige.appapplus.ui.fragments.BT1Fragment
import com.dsige.appapplus.ui.fragments.BT2Fragment
import com.dsige.appapplus.ui.fragments.MT1Fragment
import com.dsige.appapplus.ui.fragments.MT2Fragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_form_second.*

class FormSecondActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_second)
        val b = intent.extras
        if (b != null) {
            bindUI(
                b.getString("title")!!,
                b.getInt("id"),
                b.getString("codigo")!!,
                b.getInt("detalleId"),
                b.getInt("tipo")
            )
        }
    }

    private fun bindUI(title: String, id: Int, codigo: String, detalleId: Int, tipo: Int) {
        pager.adapter = ScreenSlidePagerAdapter(this, id, codigo, detalleId, tipo)
        pager.isUserInputEnabled = false
        //pager.isUserInputEnabled = true;
        setSupportActionBar(toolbar)
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private inner class ScreenSlidePagerAdapter(
        fa: AppCompatActivity,
        private val id: Int, private val codigo: String,
        private val detalleId: Int, private val tipo: Int
    ) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> when (tipo) {
                    4 -> MT1Fragment.newInstance(id, codigo, detalleId)
                    else -> BT1Fragment.newInstance(id, codigo, detalleId)
                }
                1 -> when (tipo) {
                    4 -> MT2Fragment.newInstance(id, codigo, detalleId)
                    else -> BT2Fragment.newInstance(id, codigo, detalleId)
                }
                else -> Fragment()
            }
        }
    }
}