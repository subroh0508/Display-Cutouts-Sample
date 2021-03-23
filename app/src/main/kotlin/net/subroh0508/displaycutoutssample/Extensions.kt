package net.subroh0508.displaycutoutssample

import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

@Suppress("Deprecation")
private val Window.isVisibleStatusBar get() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        decorView.rootWindowInsets.isVisible(WindowInsets.Type.statusBars())
    else
        decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0


val AppCompatActivity.isVisibleStatusBar get() = window.isVisibleStatusBar
val Fragment.isVisibleStatusBar get() = activity?.window?.isVisibleStatusBar ?: false

@Suppress("Deprecation")
private fun Window.hideStatusBarAndFullscreen() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        insetsController?.hide(WindowInsets.Type.statusBars())
        insetsController?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        setDecorFitsSystemWindows(false)
        return
    }

    decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
}

@Suppress("Deprecation")
private fun Window.showStatusBarAndFullscreen() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        insetsController?.show(WindowInsets.Type.statusBars())
        insetsController?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_BARS_BY_TOUCH
        setDecorFitsSystemWindows(false)
        return
    }

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

@Suppress("Deprecation")
private val AppCompatActivity.screenWidth get() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        windowManager.currentWindowMetrics.let { metrics ->
            val insets = metrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())

            metrics.bounds.width() - insets.left - insets.right
        }
    else
        DisplayMetrics().also(windowManager.defaultDisplay::getMetrics).widthPixels

fun AppCompatActivity.isDisplayCutoutCenter(insets: WindowInsets): Boolean {
    val center = insets.topDisplayCutoutRect().let { (it.right + it.left) / 2 }

    return center in (screenWidth / 5).let { (it * 2)..(it * 3) }
}
