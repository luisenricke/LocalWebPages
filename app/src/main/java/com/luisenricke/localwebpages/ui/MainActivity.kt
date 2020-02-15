package com.luisenricke.localwebpages.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.widget.NestedScrollView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.*
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView
import com.luisenricke.localwebpages.R
import com.luisenricke.localwebpages.common.extension.moveTop
import com.luisenricke.localwebpages.data.WebDatabase
import com.luisenricke.localwebpages.data.dao.WebPageDAO
import timber.log.Timber

@Suppress("MemberVisibilityCanBePrivate")
class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    // Toolbar
    lateinit var toolbar: Toolbar
    lateinit var appBar: AppBarLayout

    // Navigation Component
    lateinit var drawer: DrawerLayout
    lateinit var nestedScroll: NestedScrollView
    lateinit var appConfig: AppBarConfiguration
    lateinit var navController: NavController
    lateinit var navView: NavigationView
    val homeNavigation: Set<Int> = setOf(
        R.id.menu_about, R.id.menu_experience,
        R.id.menu_work, R.id.menu_contact
    )

    // Database
    lateinit var database: WebDatabase
    lateinit var webPage: WebPageDAO

    lateinit var titlesHtml: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbarConfig()
        navigationComponentConfig()
        nestedScroll = findViewById<NestedScrollView>(R.id.layout_nested_scroll)

        database = WebDatabase.getInstance(this)
        webPage = database.webPage()

        titlesHtml = webPage.getTitles()
        Timber.i("pages: ${titlesHtml.size}")
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer.closeDrawers()
        if (item.isChecked) return true

        item.isChecked = true
        if (titlesHtml.contains(item.title.toString())) {
            nestedScroll.moveTop()
            toolbar.title = item.title.toString()
        }

        val reference = when (item.itemId) {
            R.id.menu_about -> "About"
            R.id.menu_experience -> "Experience"
            R.id.menu_work -> "Work"
            R.id.menu_contact -> "Contact"
            else -> return super.onOptionsItemSelected(item)
        }

        WebViewHelper.dynamicLoad(this, database, reference)
        toolbar.title = reference

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appConfig) ||
                super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val webView: WebView? = findViewById(R.id.web) ?: null
        if (keyCode == KeyEvent.KEYCODE_BACK && webView?.canGoBack() == true) {
            webView.goBack()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    private fun toolbarConfig() {
        appBar = findViewById(R.id.layout_app_bar)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun navigationComponentConfig() {
        drawer = findViewById<DrawerLayout>(R.id.layout_drawer)
        navView = findViewById<NavigationView>(R.id.navigation)
        navController = Navigation.findNavController(this, R.id.fragment_host)
        appConfig = AppBarConfiguration.Builder(R.id.webFragment).setDrawerLayout(drawer).build()

        NavigationUI.setupActionBarWithNavController(this, navController, appConfig)
        NavigationUI.setupWithNavController(navView, navController)

        navView.setNavigationItemSelectedListener(this)
        navView.setCheckedItem(R.id.menu_about)
    }
}
