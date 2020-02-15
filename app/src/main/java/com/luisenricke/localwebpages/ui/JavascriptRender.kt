package com.luisenricke.localwebpages.ui

import android.content.Context
import android.webkit.JavascriptInterface
import com.luisenricke.localwebpages.data.WebDatabase
import com.luisenricke.localwebpages.data.entity.WebPage
import timber.log.Timber

@Suppress("unused")
class JavascriptRender(
    private val context: Context,
    private val database: WebDatabase,
    private val reference: String
) {
    @JavascriptInterface
    fun setCustomHtml(): String {
        val row: WebPage = database.webPage().getByTitle(reference)
        Timber.v("body: \n[ \n${row.title}: ${row.body} \n]")
        return if (!row.body.isNullOrEmpty()) row.body
        else "<p>404 not found</p>"
    }
}