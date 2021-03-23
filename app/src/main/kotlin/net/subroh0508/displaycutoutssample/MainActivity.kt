package net.subroh0508.displaycutoutssample

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.WindowInsets
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.view.children
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

        showStatusBarAndFullscreen()
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
        window.decorView.setOnApplyWindowInsetsListener { _, insets ->
            customStatusBar.gravity =
                if (isDisplayCutoutCenter(insets))
                    Gravity.CENTER_VERTICAL or Gravity.END
                else
                    Gravity.CENTER

            setCustomStatusBarHeight(insets)

            window.decorView.onApplyWindowInsets(insets)
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

    @Suppress("Deprecation")
    private fun setCustomStatusBarHeight(insets: WindowInsets) {
        customStatusBar.height =
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q)
                insets.systemWindowInsetTop
            else
                insets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars()).top
    }
}
