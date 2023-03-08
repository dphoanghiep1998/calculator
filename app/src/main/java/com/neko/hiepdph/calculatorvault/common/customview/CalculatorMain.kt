package com.neko.hiepdph.calculatorvault.common.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.neko.hiepdph.calculatorvault.databinding.CalculatorViewBinding

class CalculatorMain @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: CalculatorViewBinding
    init {
        binding = CalculatorViewBinding.inflate(LayoutInflater.from(context),this,false)
    }
}