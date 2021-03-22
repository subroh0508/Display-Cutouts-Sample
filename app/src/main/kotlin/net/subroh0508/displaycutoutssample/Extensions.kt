package net.subroh0508.displaycutoutssample

import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

private val Window.isVisibleStatusBar get() = decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0

val AppCompatActivity.isVisibleStatusBar get() = window.isVisibleStatusBar
val Fragment.isVisibleStatusBar get() = activity?.window?.isVisibleStatusBar ?: false

private fun Window.hideStatusBarAndFullscreen() {
    decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
}
private fun Window.showStatusBarAndFullscreen() {
    decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_VISIBLE
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
}

fun AppCompatActivity.hideStatusBarAndFullscreen() = window.hideStatusBarAndFullscreen()
fun AppCompatActivity.showStatusBarAndFullscreen() = window.showStatusBarAndFullscreen()
fun Fragment.hideStatusBarAndFullscreen() = activity?.window?.hideStatusBarAndFullscreen()
fun Fragment.showStatusBarAndFullscreen() = activity?.window?.showStatusBarAndFullscreen()

private fun WindowInsets.topDisplayCutoutRect(): Rect {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        return displayCutout?.boundingRects?.find { it.top == 0 } ?: Rect(0, 0, 0, 0)
    }

    return Rect(0, 0, 0, 0)
}

fun AppCompatActivity.isDisplayCutoutCenter(insets: WindowInsets): Boolean {
    val point = Point().apply { windowManager.defaultDisplay.getSize(this) }

    val center = insets.topDisplayCutoutRect().let { (it.right + it.left) / 2 }

    return center in (point.x / 5).let { (it * 2)..(it * 3) }
}
