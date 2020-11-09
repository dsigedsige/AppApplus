package com.dsige.appapplus.ui.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dsige.appapplus.R
import com.dsige.appapplus.ui.fragments.*
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_form_poste.*

class FormPosteActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_poste)
        val b = intent.extras
        if (b != null) {
            bindUI(b.getString("title")!!)
            when (b.getInt("tipo")) {
                1 -> replaceFragment(savedInstanceState, Poste1Fragment.newInstance(b.getInt("id")))
                2 -> replaceFragment(savedInstanceState, Poste2Fragment.newInstance(b.getInt("id")))
                3 -> replaceFragment(savedInstanceState, Poste3Fragment.newInstance(b.getInt("id")))
                4 -> replaceFragment(savedInstanceState, Poste4Fragment.newInstance(b.getInt("id")))
                5 -> replaceFragment(savedInstanceState, Poste5Fragment.newInstance(b.getInt("id")))
                6 -> replaceFragment(savedInstanceState, Poste6Fragment.newInstance(b.getInt("id")))
                7 -> replaceFragment(savedInstanceState, Poste7Fragment.newInstance(b.getInt("id")))
                8 -> replaceFragment(savedInstanceState, Poste8Fragment.newInstance(b.getInt("id")))
                9 -> replaceFragment(savedInstanceState, Poste9Fragment.newInstance(b.getInt("id")))
            }
        }
    }

    private fun bindUI(title: String) {
        setSupportActionBar(toolbar)
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun replaceFragment(savedInstanceState: Bundle?, f: Fragment) {
        savedInstanceState ?: supportFragmentManager.beginTransaction()
            .replace(R.id.container, f).commit()
    }
}