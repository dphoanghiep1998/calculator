package com.neko.hiepdph.calculatorvault.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.common.customview.CalculatorFunction
import com.neko.hiepdph.calculatorvault.common.extensions.clickWithDebounce
import com.neko.hiepdph.calculatorvault.common.extensions.navigateToPage
import com.neko.hiepdph.calculatorvault.common.utils.EMPTY
import com.neko.hiepdph.calculatorvault.databinding.FragmentCalculatorBinding
import com.neko.hiepdph.calculatorvault.dialog.DialogChangeTheme


class FragmentCalculator : Fragment() {
    private lateinit var binding: FragmentCalculatorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        initCalculator()
        initResultView()
        initInputView()
        initButton()
    }

    private fun initButton() {
        binding.imvChangeTheme.clickWithDebounce {
            val dialogChangeTheme = DialogChangeTheme()
            dialogChangeTheme.show(childFragmentManager, dialogChangeTheme.tag)
        }
    }

    private fun initInputView() {
        binding
    }

    private fun initResultView() {
        binding.tvResult.text = String.EMPTY
    }

    private fun appendTextToInput(input: String) {
        val cursorPositionStart = binding.tvInput.selectionStart
        val cursorPositionEnd = binding.tvInput.selectionEnd
        navigateToPage(R.id.fragmentCalculator,R.id.action_fragmentCalculator_to_fragmentHome)
//        val subText =
//            binding.tvInput.text.toString().substring(cursorPositionStart, cursorPositionEnd)

//        binding.tvInput.text.toString().replace(cursorPositionStart, cursorPositionEnd, subText + input)
    }

    private fun removeLastCharacter() {
        val lastText = binding.tvInput.text.toString()
        if (lastText.isNotBlank()) {
            val subText = lastText.substring(0, lastText.length - 1)
            binding.tvInput.setText(subText)
        }
    }
    private fun checkSecretKey(){
        if(binding.tvInput.text == "1234%"){
            navigateToPage(R.id.fragmentCalculator,R.id.action_fragmentCalculator_to_fragmentHome)
        }
    }


    private fun initCalculator() {
        binding.layoutCalculator.setCalculatorMainCallback(object : CalculatorFunction {
            override fun onPressButton0() {
                appendTextToInput("0")
            }

            override fun onPressButton1() {
                appendTextToInput("1")
            }

            override fun onPressButton2() {
                appendTextToInput("2")
            }

            override fun onPressButton3() {
                appendTextToInput("3")

            }

            override fun onPressButton4() {
                appendTextToInput("4")
            }

            override fun onPressButton5() {
                appendTextToInput("5")
            }

            override fun onPressButton6() {
                appendTextToInput("6")
            }

            override fun onPressButton7() {
                appendTextToInput("7")
            }

            override fun onPressButton8() {
                appendTextToInput("8")
            }

            override fun onPressButton9() {
                appendTextToInput("9")
            }

            override fun onPressButtonComma() {
                appendTextToInput(",")
            }

            override fun onPressButtonPlus() {
                appendTextToInput("+")
            }

            override fun onPressButtonSubtract() {
                appendTextToInput("-")
            }

            override fun onPressButtonTimes() {
                appendTextToInput("x")
            }

            override fun onPressButtonDivide() {
                appendTextToInput("÷")
            }

            override fun onPressButtonEqual() {
                appendTextToInput("0")
            }

            override fun onPressButtonE() {
                appendTextToInput("0")
            }

            override fun onPressButtonSwitch() {
                appendTextToInput("0")
            }

            override fun onPressButtonReset() {
                appendTextToInput("0")
            }

            override fun onPressButtonBackspace() {
                removeLastCharacter()
            }

            override fun onPressButtonPercent() {
                appendTextToInput("0")
            }

            override fun onPressButtonPi() {
                appendTextToInput("0")
            }

            override fun onPressButtonInverse() {
                appendTextToInput("0")
            }

            override fun onPressButtonExponent() {
                appendTextToInput("0")
            }

            override fun onPressButtonSqrt() {
                appendTextToInput("0")
            }

            override fun onPressButtonPow() {
                appendTextToInput("0")
            }

            override fun onPressButtonLg() {
                appendTextToInput("0")
            }

            override fun onPressButtonLn() {
                appendTextToInput("0")
            }

            override fun onPressButtonOpenBracket() {
                appendTextToInput("(")
            }

            override fun onPressButtonCloseBracket() {
                appendTextToInput(")")
            }

            override fun onPressButton2nd() {
                appendTextToInput("0")
            }

            override fun onPressButtonDeg() {
                appendTextToInput("0")
            }

            override fun onPressButtonRad() {
                appendTextToInput("0")
            }

            override fun onPressButtonSin() {
                appendTextToInput("0")
            }

            override fun onPressButtonCos() {
                appendTextToInput("0")
            }

            override fun onPressButtonTan() {
                appendTextToInput("0")
            }

            override fun onPressButtonArcSin() {
                appendTextToInput("0")
            }

            override fun onPressButtonArcCos() {
                appendTextToInput("0")
            }

            override fun onPressButtonArcTan() {
                appendTextToInput("0")
            }

        })
    }


}