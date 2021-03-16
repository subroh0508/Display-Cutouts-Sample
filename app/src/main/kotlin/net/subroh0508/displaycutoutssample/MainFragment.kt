package net.subroh0508.displaycutoutssample

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import net.subroh0508.displaycutoutssample.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private val isVisibleStatusBar get() = activity?.window?.decorView?.systemUiVisibility == View.SYSTEM_UI_FLAG_VISIBLE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        binding.changeStatusBar.setOnClickListener {
            if (isVisibleStatusBar) hideStatusBar() else showStatusBar()
        }
    }

    private fun hideStatusBar() {
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        //activity?.actionBar?.hide()
    }

    private fun showStatusBar() {
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        //activity?.actionBar?.show()
    }
}
