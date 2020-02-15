package com.luisenricke.localwebpages.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luisenricke.localwebpages.R
import com.luisenricke.localwebpages.common.BaseFragment

class WebFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_web, container, false)
        WebViewHelper.dynamicLoad(view, database, "About")
        return view
    }
}
