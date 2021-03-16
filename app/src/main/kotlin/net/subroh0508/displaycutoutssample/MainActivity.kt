package net.subroh0508.displaycutoutssample

import android.graphics.Rect
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.annotation.IdRes
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import net.subroh0508.displaycutoutssample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var statusBarHeight: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        setOnStatusBarToggleListener()

        displayFragment(R.id.fragment_container, MainFragment())
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (isVisibleStatusBar) {
            statusBarHeight = Rect().let { window.decorView.getWindowVisibleDisplayFrame(it); it.top }
        }
    }

    private fun setOnStatusBarToggleListener() {
        window.decorView.setOnSystemUiVisibilityChangeListener listener@ { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                binding.messageBehindStatusBar.text = ""
                binding.messageBehindStatusBar.setBackgroundColor(getColorAttributes(R.attr.colorPrimaryVariant))

                return@listener
            }

            binding.messageBehindStatusBar.setText(R.string.sample_message)
            binding.messageBehindStatusBar.setBackgroundColor(getColorAttributes(R.attr.colorSecondary))
        }

        binding.appBarLayout.setOnApplyWindowInsetsListener { _, insets ->
            showDisplayCutout(insets)
            binding.messageBehindStatusBar.updateLayoutParams<ViewGroup.LayoutParams> {
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
