package com.luisenricke.localwebpages.common.extension

import android.view.View
import androidx.core.widget.NestedScrollView

@Suppress("unused")
fun NestedScrollView.moveTop() = this.fullScroll(View.FOCUS_UP)