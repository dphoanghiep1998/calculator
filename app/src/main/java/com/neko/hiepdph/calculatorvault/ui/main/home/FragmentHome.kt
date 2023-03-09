package com.neko.hiepdph.calculatorvault.ui.main.home

import android.content.Context
import android.graphics.Insets
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.common.extensions.clickWithDebounce
import com.neko.hiepdph.calculatorvault.common.utils.buildMinVersionR
import com.neko.hiepdph.calculatorvault.databinding.FragmentHomeBinding
import com.neko.hiepdph.calculatorvault.databinding.LayoutMenuOptionBinding


class FragmentHome : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var popupWindow: PopupWindow

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        initPopupWindow()
        initButton()
    }

    private fun initButton() {
        binding.imvOption.clickWithDebounce {
            popupWindow.showAsDropDown(binding.imvOption, 0, 0)
        }
    }

    private fun initPopupWindow() {
        val inflater: LayoutInflater =
            (requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?)!!
        val bindingLayout = LayoutMenuOptionBinding.inflate(inflater, null, false)

        val width = if (buildMinVersionR()) {
            val windowMetrics: WindowMetrics = requireActivity().windowManager.currentWindowMetrics
            val insets: Insets =
                windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right

        } else {
            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
        popupWindow =
            PopupWindow(bindingLayout.root, width, LinearLayout.LayoutParams.WRAP_CONTENT, true)
        bindingLayout.root.clickWithDebounce {
            popupWindow.dismiss()
        }
        bindingLayout.tvSort.clickWithDebounce {

            popupWindow.dismiss()
        }
        bindingLayout.tvSort.clickWithDebounce {

            popupWindow.dismiss()
        }

    }


}