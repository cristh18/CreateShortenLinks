package com.tolodev.createshortenlinks.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat
import com.tolodev.createshortenlinks.R

class LinkGeneratorLoader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    val progressBar: ProgressBar

    init {
        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layoutParams.addRule(CENTER_IN_PARENT, TRUE)
        progressBar = ProgressBar(context)
        addView(progressBar, layoutParams)
        setBackgroundColor(ContextCompat.getColor(context, R.color.white_transparent))
        isClickable = true
    }

    fun setState(state: Boolean) {
        if (state) {
            showProgressBar()
        } else {
            hideProgressBar()
        }
    }

    fun showProgressBar() {
        this.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        this.visibility = View.GONE
        progressBar.visibility = View.GONE
    }
}
