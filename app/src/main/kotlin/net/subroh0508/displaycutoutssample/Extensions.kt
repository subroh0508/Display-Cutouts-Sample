package net.subroh0508.displaycutoutssample

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.Window
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

private val Window.isVisibleStatusBar get() = decorView.systemUiVisibility == View.SYSTEM_UI_FLAG_VISIBLE

val AppCompatActivity.isVisibleStatusBar get() = window.isVisibleStatusBar
val Fragment.isVisibleStatusBar get() = activity?.window?.isVisibleStatusBar ?: false

@ColorInt
fun Context.getColorAttributes(@AttrRes id: Int): Int {
    val typedValue = TypedValue().also { v ->
        theme.resolveAttribute(id, v, true)
    }

    return getColor(if (typedValue.resourceId != 0) typedValue.resourceId else typedValue.data)
}
