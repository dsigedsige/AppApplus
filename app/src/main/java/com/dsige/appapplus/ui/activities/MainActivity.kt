package com.dsige.appapplus.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dsige.appapplus.R
import com.dsige.appapplus.data.local.model.Usuario
import com.dsige.appapplus.data.viewModel.UsuarioViewModel
import com.dsige.appapplus.data.viewModel.ViewModelFactory
import com.dsige.appapplus.helper.Permission
import com.dsige.appapplus.helper.Util
import com.dsige.appapplus.ui.fragments.InspeccionPosteFragment
import com.dsige.appapplus.ui.fragments.MainFragment
import com.dsige.appapplus.ui.services.SocketServices
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import java.io.File
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var usuarioViewModel: UsuarioViewModel
    lateinit var builder: AlertDialog.Builder
    var dialog: AlertDialog? = null
    var usuarioId: Int = 0
    var perfilId : Int =0
    var logout: String = "off"
    var link: String = ""
    var name: String = ""

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu, menu)
//        return true
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindUI()
    }

    private fun bindUI() {
        usuarioViewModel =
            ViewModelProvider(this, viewModelFactory).get(UsuarioViewModel::class.java)
        usuarioViewModel.user.observe(this, { u ->
            if (u != null) {
                setSupportActionBar(toolbar)
                val toggle = ActionBarDrawerToggle(
                    this@MainActivity,
                    drawerLayout,
                    toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close
                )
                drawerLayout.addDrawerListener(toggle)
                toggle.syncState()
                navigationView.setNavigationItemSelectedListener(this@MainActivity)
                getUser(u)
                fragmentByDefault()
                message()
                startService(Intent(this, SocketServices::class.java))
                val instance = FirebaseRemoteConfig.getInstance()
                instance.setConfigSettingsAsync(
                    FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(0)
                        .build()
                )
                val cacheExpiration = instance.info.configSettings.minimumFetchIntervalInSeconds
                instance.fetch(cacheExpiration)
                    .addOnCompleteListener(this) { t ->
                        if (t.isSuccessful) {
                            instance.activate()
                            val isUpdate = instance.getBoolean(Util.KEY_UPDATE_ENABLE)
                            if (isUpdate) {
                                val version = instance.getString(Util.KEY_UPDATE_VERSION)
                                val appVersion = Util.getVersion(this)
                                val url = instance.getString(Util.KEY_UPDATE_URL)
                                val name = instance.getString(Util.KEY_UPDATE_NAME)

                                if (version != appVersion) {
                                    updateAndroid(url, name, version)
                                }
                            }
                        }
                    }
            } else {
                goLogin()
            }
        })
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sync -> sendTrabajos(4)
            R.id.orden -> changeFragment(
                MainFragment.newInstance(perfilId, usuarioId),
                "Ordenes de Trabajo"
            )
            R.id.poste -> changeFragment(
                InspeccionPosteFragment.newInstance(perfilId, usuarioId),
                "Postes Asignado al Trabajador"
            )
            R.id.logout -> dialogLogout()
            R.id.envio -> sendTrabajos(1)
            R.id.enviOT -> sendTrabajos(2)
            R.id.envioPD -> sendTrabajos(3)
            R.id.envioInspecciones -> sendTrabajos(5)
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun goActivity(i: Intent) {
        startActivity(i)
    }

    private fun load(title: String) {
        builder = AlertDialog.Builder(ContextThemeWrapper(this@MainActivity, R.style.AppTheme))
        @SuppressLint("InflateParams") val view =
            LayoutInflater.from(this@MainActivity).inflate(R.layout.dialog_login, null)
        builder.setView(view)
        val textViewTitle: TextView = view.findViewById(R.id.textView)
        textViewTitle.text = title
        dialog = builder.create()
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setCancelable(false)
        dialog!!.show()
    }

    private fun closeLoad() {
        if (dialog != null) {
            if (dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        }
    }

    private fun changeFragment(fragment: Fragment, title: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content_frame, fragment)
            .commit()
        supportActionBar!!.title = title
    }

    private fun fragmentByDefault() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content_frame, MainFragment.newInstance(perfilId, usuarioId))
            .commit()
        supportActionBar!!.title = "Ordenes de Trabajo"
        navigationView.menu.getItem(1).isChecked = true
    }

    private fun getUser(u: Usuario) {
        val header = navigationView.getHeaderView(0)
        header.textViewName.text = u.nombres
        header.textViewEmail.text = String.format("Cod : %s", u.email)
        header.textViewVersion.text = String.format("Version : %s", Util.getVersion(this))
        usuarioId = u.usuarioId
        perfilId = u.perfilId
    }

    private fun goLogin() {
        if (logout == "off") {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun message() {
        usuarioViewModel.success.observe(this, { s ->
            if (s != null) {
                closeLoad()
                if (s == "Close") {
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    Util.toastMensaje(this, s)
                }
            }
        })
        usuarioViewModel.error.observe(this@MainActivity, { s ->
            if (s != null) {
                closeLoad()
                Util.snackBarMensaje(window.decorView, s)
            }
        })
    }

    private fun dialogLogout() {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Mensaje")
            .setMessage("Deseas Salir ?")
            .setPositiveButton("SI") { dialog, _ ->
                logout = "on"
                load("Cerrando Session")
                usuarioViewModel.logout(this)
                dialog.dismiss()
            }
            .setNegativeButton("NO") { dialog, _ ->
                dialog.cancel()
            }
        dialog.show()
    }

    private fun sendTrabajos(tipo: Int) {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Mensaje")
            .setMessage(
                when (tipo) {
                    1 -> "Deseas Enviar los Trabajos Pendientes ?"
                    2 -> "Deseas Enviar las reasignaciones Pendientes ?"
                    3 -> "Deseas Enviar los Parte Diarios ?"
                    4 -> "Deseas Sincronizar ?"
                    else -> "Deseas Enviar los Inspecciones ?"
                }
            )
            .setPositiveButton("SI") { dialog, _ ->
                load("Enviando...")
                when (tipo) {
                    1 -> usuarioViewModel.sendTrabajo(this)
                    2 -> usuarioViewModel.sendReasignaciones()
                    3 -> usuarioViewModel.sendParteDiarios()
                    4 -> usuarioViewModel.sync(usuarioId, Util.getVersion(this))
                    5 -> usuarioViewModel.sendInspeccion(this)
                }
                dialog.dismiss()
            }
            .setNegativeButton("NO") { dialog, _ ->
                dialog.cancel()
            }
        dialog.show()
    }

    private fun updateAndroid(url: String, nombre: String, title: String) {
        val builderUpdate =
            AlertDialog.Builder(ContextThemeWrapper(this@MainActivity, R.style.AppTheme))
        @SuppressLint("InflateParams") val view =
            LayoutInflater.from(this@MainActivity).inflate(R.layout.dialog_new_version, null)
        val textViewTile = view.findViewById<TextView>(R.id.textViewTitle)
        val buttonUpdate = view.findViewById<MaterialButton>(R.id.buttonUpdate)
        builderUpdate.setView(view)
        val dialogUpdate = builderUpdate.create()
        dialogUpdate.setCanceledOnTouchOutside(false)
        dialogUpdate.setCancelable(false)
        dialogUpdate.show()
        textViewTile.text = String.format("%s %s", "Nueva Versión", title)
        buttonUpdate.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                download(url.trim { it <= ' ' }, nombre)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    Permission.WRITE_REQUEST
                )
            }
            dialogUpdate.dismiss()
        }
        link = url.trim { it <= ' ' }
        name = nombre
    }

    private fun download(url: String, name: String) {
        val file = File(Environment.getExternalStorageDirectory().toString() + "/download/" + name)
        if (file.exists()) {
            if (file.delete()) {
                Log.i("TAG", "deleted")
            }
        }
        val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        Log.i("TAG", url)
        val mUri = Uri.parse(url)
        val r = DownloadManager.Request(mUri)
        r.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        //r.setAllowedOverRoaming(false);
        //r.setVisibleInDownloadsUi(false)
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name)
        r.setTitle(name)
        r.setMimeType("application/vnd.android.package-archive")
        if (Build.VERSION.SDK_INT <= 25) {
            val downloadId = dm.enqueue(r)
            val onComplete = object : BroadcastReceiver() {
                override fun onReceive(ctxt: Context, intent: Intent) {
                    val uri =
                        Uri.fromFile(
                            File(
                                Environment.getExternalStorageDirectory()
                                    .toString() + "/download/" + name
                            )
                        )
                    val install = Intent(Intent.ACTION_VIEW)
                    install.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    install.setDataAndType(
                        uri,
                        dm.getMimeTypeForDownloadedFile(downloadId)
                    )
                    startActivity(install)
                    unregisterReceiver(this)
                    finish()
                }
            }
            registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
            Util.toastMensaje(this, getString(R.string.wait_download))
        } else {
            dm.enqueue(r)
        }
    }
}