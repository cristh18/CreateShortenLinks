package com.tolodev.createshortenlinks.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.AbsoluteSizeSpan
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.text.style.TypefaceSpan
import android.view.View
import androidx.annotation.DimenRes
import androidx.core.text.inSpans

inline fun SpannableStringBuilder.fontSize(
    context: Context,
    @DimenRes fontSizeRes: Int,
    builderAction: SpannableStringBuilder.() -> Unit
) = inSpans(AbsoluteSizeSpan(context.resources.getDimensionPixelSize(fontSizeRes)), builderAction)

inline fun SpannableStringBuilder.poppinsRegular(
    builderAction: SpannableStringBuilder.() -> Unit
) = inSpans(TypefaceSpan("poppins_regular"), builderAction)