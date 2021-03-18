package net.subroh0508.displaycutoutssample

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import net.subroh0508.displaycutoutssample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val customStatusBar: TextView by lazy {
        layoutInflater.inflate(R.layout.text_view_custom_status_bar, null) as TextView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        insertCustomStatusBar()
        setOnStatusBarToggleListener()

        displayFragment(R.id.fragment_container, MainFragment())
    }

    private fun insertCustomStatusBar() {
        if (binding.appBarLayout.children.first() == customStatusBar) {
            return
        }

        binding.appBarLayout.addView(customStatusBar, 0)
    }

    private fun setOnStatusBarToggleListener() {
        binding.appBarLayout.setOnApplyWindowInsetsListener { _, insets ->
            showDisplayCutout(insets)
            println("system window inset top: ${insets.systemWindowInsetTop}")
            customStatusBar.updateLayoutParams<LinearLayout.LayoutParams> {
                height = insets.systemWindowInsetTop
            }

            insets.consumeSystemWindowInsets()
        }
    }

    private fun displayFragment(
            @IdRes frame: Int,
            fragment: Fragment,
    ) {
        val fragmentTag =  fragment::class.java.simpleName
        val displayFragment = supportFragmentManager.findFragmentByTag(fragmentTag) ?: fragment

        supportFragmentManager.commit {
            replace(frame, displayFragment, fragmentTag)
        }
    }

    private fun showDisplayCutout(insets: WindowInsets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val cutout = insets.displayCutout

            println("cutout: ${cutout?.toString()}")

            val left = cutout?.safeInsetLeft ?: 0
            val top = cutout?.safeInsetTop ?: 0
            val right = cutout?.safeInsetRight ?: 0
            val bottom = cutout?.safeInsetBottom ?: 0

            println("safe area: ($left, $top, $right, $bottom)")

            cutout?.boundingRects?.forEach { println("bounding rect: $it") }
            return
        }

        println("safe area: null)")
    }
}
