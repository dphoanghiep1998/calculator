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
    private var srcImage: Int = 0
    private var special: Boolean = false
    private var expand: Boolean = false

    init {
        binding = ItemCalculatorBinding.inflate(LayoutInflater.from(context), this, false)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ButtonCalculator)
        textButton = try {
            typedArray.getText(R.styleable.ButtonCalculator_buttonText).toString()
        } catch (e: Exception) {
            null
        }
        srcImage = typedArray.getResourceId(R.styleable.ButtonCalculator_src, 0)

        special = typedArray.getBoolean(R.styleable.ButtonCalculator_special, false)

        expand = typedArray.getBoolean(R.styleable.ButtonCalculator_expand, false)

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

        if (srcImage == 0) {
            binding.imvButton.hide()
        } else {
            binding.imvButton.show()
            binding.imvButton.setImageResource(srcImage)
        }

        if (special) {
            binding.root.setBackgroundResource(R.drawable.bg_item_calculator_special)
        } else {
            binding.root.setBackgroundResource(R.drawable.bg_item_calculator_normal)
        }

        if(expand){
            binding.root.background = null
            binding.tvButton.setTextColor(context.getColor(R.color.neutral_05))
        }
    }
}