package com.neko.hiepdph.calculatorvault.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.common.extensions.clickWithDebounce
import com.neko.hiepdph.calculatorvault.common.extensions.hide
import com.neko.hiepdph.calculatorvault.common.extensions.show
import com.neko.hiepdph.calculatorvault.databinding.DialogChoseThemeBinding
import java.lang.String


class DialogChangeTheme : DialogFragment() {
    private lateinit var binding: DialogChoseThemeBinding
    private var customEnabled = false
    private var rValue = 0
    private var gValue = 0
    private var bValue = 0


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val root = ConstraintLayout(requireContext())
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(requireContext().getColor(R.color.transparent)))

        dialog.window!!.setLayout(
            (requireContext().resources.displayMetrics.widthPixels),
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogChoseThemeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initButton()
        initSeekBar()
        initData()
    }

    private fun initData() {
        val currentThemeColor = requireActivity().getColor(R.color.theme_default)
        rValue = getRGB(currentThemeColor)[0]
        gValue = getRGB(currentThemeColor)[1]
        bValue = getRGB(currentThemeColor)[2]

        binding.seekB.progress = bValue
        binding.seekR.progress = rValue
        binding.seekG.progress = gValue
        updateTheme()
    }

    private fun getRGB(hex: Int): IntArray {
        val r = hex and 0xFF0000 shr 16
        val g = hex and 0xFF00 shr 8
        val b = hex and 0xFF
        return intArrayOf(r, g, b)
    }

    private fun initSeekBar() {
        binding.seekG.max = 255
        binding.seekR.max = 255
        binding.seekB.max = 255

        binding.seekR.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                rValue = p1
                updateTheme()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        binding.seekG.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                gValue = p1
                updateTheme()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        binding.seekB.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                bValue = p1
                updateTheme()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
    }

    private fun updateTheme() {
        binding.tvValueR.text = rValue.toString()
        binding.tvValueG.text = gValue.toString()
        binding.tvValueB.text = bValue.toString()
        binding.edtValue.setText(String.format("#%02X%02X%02X", rValue, gValue, bValue))
        binding.themeShowcase.setBackgroundColor(Color.rgb(rValue, gValue, bValue))
    }

    private fun initButton() {
        binding.containerMain.clickWithDebounce { }

        binding.tvCustom.clickWithDebounce {
            customEnabled = !customEnabled
            if (customEnabled) {
                binding.containerChoose.hide()
                binding.containerCustom.show()
                binding.tvCustom.text = getString(R.string.back)
                binding.tvTitle.text = getString(R.string.custom)
            } else {
                binding.containerChoose.show()
                binding.containerCustom.hide()
                binding.tvCustom.text = getString(R.string.custom)
                binding.tvTitle.text = getString(R.string.choose_theme)
            }

        }
        binding.tvCancel.clickWithDebounce {
            dismiss()
        }
        binding.root.clickWithDebounce {
            dismiss()
        }
        binding.tvOk.clickWithDebounce {
            dismiss()
        }
    }


}