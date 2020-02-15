package com.luisenricke.localwebpages.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.luisenricke.localwebpages.Constants
import com.luisenricke.localwebpages.Constants.INDEX_HTML
import com.luisenricke.localwebpages.Constants.WEB_ROUTE
import com.luisenricke.localwebpages.R
import com.luisenricke.localwebpages.data.WebDatabase

@SuppressLint("SetJavaScriptEnabled")
object WebViewHelper {
    fun dynamicLoad(
        activity: AppCompatActivity,
        database: WebDatabase,
        reference: String,
        htmlFile: String = INDEX_HTML
    ): WebView? {
        val webView = activity.findViewById<WebView>(R.id.web) ?: return null
        webView.settings.simple()
        webView.webViewClient = object : WebViewClient() {}
        webView.loadUrl("${WEB_ROUTE}/$htmlFile")
        webView.addJavascriptInterface(
            JavascriptRender(activity, database, reference),
            Constants.REFERENCE_JS
        )
        return webView
    }

    fun dynamicLoad(
        view: View,
        database: WebDatabase,
        reference: String,
        htmlFile: String = INDEX_HTML
    ): WebView? {
        val webView = view.findViewById<WebView>(R.id.web) ?: return null
        webView.settings.simple()
        webView.webViewClient = object : WebViewClient() {}
        webView.loadUrl("${WEB_ROUTE}/$htmlFile")
        webView.addJavascriptInterface(
            JavascriptRender(view.context, database, reference),
            Constants.REFERENCE_JS
        )
        return webView
    }

    fun WebSettings.simple() {
        this.javaScriptEnabled = true
        this.domStorageEnabled = true

        this.setSupportZoom(true)
        this.builtInZoomControls = true
        this.displayZoomControls = false

        /*
        this.allowFileAccess = true
        this.allowFileAccessFromFileURLs = true
        this.allowUniversalAccessFromFileURLs = true

        this.useWideViewPort = true
        this.loadWithOverviewMode = true

        isHorizontalScrollBarEnabled = false
        isScrollbarFadingEnabled = true
        scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
        overScrollMode = View.OVER_SCROLL_NEVER
        */
    }
}