package com.tolodev.createshortenlinks.utils

import java.text.DecimalFormat


private const val FORMAT_WITHOUT_POINTS = "#,##0"

fun withoutDecimals(numericDouble: Double): String {
    return decimalFormat(numericDouble, FORMAT_WITHOUT_POINTS)
}

fun decimalFormat(numericDouble: Double, format: String?): String {
    return DecimalFormat(format).format(numericDouble)
}

