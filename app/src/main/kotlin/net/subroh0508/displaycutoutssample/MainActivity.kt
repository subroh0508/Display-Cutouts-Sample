package net.subroh0508.displaycutoutssample

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.view.isVisible
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

        setOnSystemUiVisibilityChangeListener()

        displayFragment(R.id.fragment_container, MainFragment())
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (window.decorView.systemUiVisibility == View.SYSTEM_UI_FLAG_VISIBLE) {
            statusBarHeight = Rect().let { window.decorView.getWindowVisibleDisplayFrame(it); it.top }
        }
    }

    private fun setOnSystemUiVisibilityChangeListener() {
        window.decorView.setOnSystemUiVisibilityChangeListener listener@ { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                binding.messageBehindStatusBar.isVisible = false

                return@listener
            }

            binding.messageBehindStatusBar.isVisible = true
            binding.messageBehindStatusBar.updateLayoutParams<ViewGroup.LayoutParams> {
                height = statusBarHeight
            }
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
