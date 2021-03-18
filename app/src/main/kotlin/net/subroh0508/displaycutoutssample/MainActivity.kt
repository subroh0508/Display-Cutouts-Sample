package net.subroh0508.displaycutoutssample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
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

        insertCustomStatusBar()
        setOnStatusBarToggleListener()

        displayFragment(R.id.fragment_container, MainFragment())
    }

    private fun insertCustomStatusBar() {
        if (binding.appBarLayout.children.first() == customStatusBar) {
            return
        }

        binding.appBarLayout.addView(customStatusBar, 0)
        if (isVisibleStatusBar) {
            customStatusBar.height = 0
        }
    }

    private fun setOnStatusBarToggleListener() {
        // For below API 29 ?
        window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                customStatusBar.height = 0
            }
        }

        binding.appBarLayout.setOnApplyWindowInsetsListener { _, insets ->
            customStatusBar.gravity =
                if (isDisplayCutoutCenter(insets))
                    Gravity.CENTER_VERTICAL or Gravity.END
                else
                    Gravity.CENTER
            customStatusBar.height = insets.systemWindowInsetTop

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
}
