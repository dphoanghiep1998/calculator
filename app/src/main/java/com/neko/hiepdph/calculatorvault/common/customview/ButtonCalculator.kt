package com.neko.hiepdph.calculatorvault.common.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.databinding.ItemCalculatorBinding
import com.neko.hiepdph.magic.extension.hide
import com.neko.hiepdph.magic.extension.show

interface IButtonCalculator {

}

class ButtonCalculator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding: ItemCalculatorBinding
    private var textButton: String? = null
    private var srcImage: Drawable? = null

    init {
        binding = ItemCalculatorBinding.inflate(LayoutInflater.from(context), this, false)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ButtonCalculator)
        textButton = try {
            typedArray.getText(R.styleable.ButtonCalculator_buttonText).toString()
        } catch (e: Exception) {
            null
        }
        srcImage = try {
            typedArray.getDrawable(R.styleable.ButtonCalculator_src)
        } catch (e: Exception) {
            null
        }
        initView()
        addView(binding.root)
        typedArray.recycle()
    }

    private fun initView() {
        if (textButton == null) {
            binding.tvButton.hide()
        } else {
            binding.tvButton.show()
            binding.tvButton.text = textButton
        }

        if (srcImage == null) {
            binding.imvButton.hide()
        } else {
            binding.imvButton.show()
            binding.imvButton.setImageDrawable(srcImage)
        }
    }
}