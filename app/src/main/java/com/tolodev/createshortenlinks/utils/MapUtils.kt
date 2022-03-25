@file:JvmName("MapUtils")

package com.tolodev.createshortenlinks.utils

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import java.io.Serializable

fun bundleToMap(bundle: Bundle): Map<String, Any?> {
    val map: MutableMap<String, Any?> = HashMap()
    val keySet = bundle.keySet()
    val iterator: Iterator<String> = keySet.iterator()
    while (iterator.hasNext()) {
        val key = iterator.next()
        map[key] = bundle.get(key)
    }
    return map
}

fun mapToBundle(map: Map<String, Any?>): Bundle {
    val bundle = Bundle()
    for ((key, value) in map.entries) {
        when (value) {
            null -> bundle.putString(key, null) // Any nullable type will suffice.

            // Scalars
            is Boolean -> bundle.putBoolean(key, value)
            is Byte -> bundle.putByte(key, value)
            is Char -> bundle.putChar(key, value)
            is Double -> bundle.putDouble(key, value)
            is Float -> bundle.putFloat(key, value)
            is Int -> bundle.putInt(key, value)
            is Long -> bundle.putLong(key, value)
            is Short -> bundle.putShort(key, value)

            // References
            is Bundle -> bundle.putBundle(key, value)
            is CharSequence -> bundle.putCharSequence(key, value)
            is Parcelable -> bundle.putParcelable(key, value)

            // Scalar arrays
            is BooleanArray -> bundle.putBooleanArray(key, value)
            is ByteArray -> bundle.putByteArray(key, value)
            is CharArray -> bundle.putCharArray(key, value)
            is DoubleArray -> bundle.putDoubleArray(key, value)
            is FloatArray -> bundle.putFloatArray(key, value)
            is IntArray -> bundle.putIntArray(key, value)
            is LongArray -> bundle.putLongArray(key, value)
            is ShortArray -> bundle.putShortArray(key, value)

            // Reference arrays
            is Array<*> -> {
                val componentType = value::class.java.componentType!!
                @Suppress("UNCHECKED_CAST") // Checked by reflection.
                when {
                    Parcelable::class.java.isAssignableFrom(componentType) -> {
                        bundle.putParcelableArray(key, value as Array<Parcelable>)
                    }
                    String::class.java.isAssignableFrom(componentType) -> {
                        bundle.putStringArray(key, value as Array<String>)
                    }
                    CharSequence::class.java.isAssignableFrom(componentType) -> {
                        bundle.putCharSequenceArray(key, value as Array<CharSequence>)
                    }
                    Serializable::class.java.isAssignableFrom(componentType) -> {
                        bundle.putSerializable(key, value)
                    }
                    else -> {
                        val valueType = componentType.canonicalName
                        throw IllegalArgumentException(
                            "Illegal value array type $valueType for key \"$key\""
                        )
                    }
                }
            }

            // Last resort. Also we must check this after Array<*> as all arrays are serializable.
            is Serializable -> bundle.putSerializable(key, value)

            else -> {
                if (Build.VERSION.SDK_INT >= 21 && value is Size) {
                    bundle.putSize(key, value)
                } else if (Build.VERSION.SDK_INT >= 21 && value is SizeF) {
                    bundle.putSizeF(key, value)
                } else {
                    val valueType = value.javaClass.canonicalName
                    throw IllegalArgumentException("Illegal value type $valueType for key \"$key\"")
                }
            }
        }
    }
    return bundle
}
