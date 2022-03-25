package com.tolodev.createshortenlinks.utils

import android.content.Context
import android.text.SpannableStringBuilder
import androidx.annotation.DimenRes
import androidx.core.text.bold
import com.tolodev.createshortenlinks.extensions.fontSize
import com.tolodev.createshortenlinks.extensions.poppinsRegular

fun getBoldText(
    context: Context,
    text: String,
    @DimenRes textSize: Int
): SpannableStringBuilder {
    return SpannableStringBuilder()
        .bold {
            fontSize(context, textSize) {
                poppinsRegular { append(text) }
            }
        }
}

fun getNormalText(
    context: Context,
    text: String,
    @DimenRes textSize: Int
): SpannableStringBuilder {
    return SpannableStringBuilder()
        .fontSize(context, textSize) {
            poppinsRegular { append(text) }
        }
}

fun getCustomText(
    context: Context,
    firstText: String,
    secondaryText: String,
    @DimenRes firstTextSize: Int,
    @DimenRes secondaryTextSize: Int,
): SpannableStringBuilder {
    return SpannableStringBuilder()
        .bold {
            fontSize(context, firstTextSize) {
                poppinsRegular {
                    append(firstText)
                        .append("\n")
                }
            }
        }.fontSize(context, secondaryTextSize) {
            poppinsRegular {
                append(" ".plus(secondaryText))
            }
        }
}