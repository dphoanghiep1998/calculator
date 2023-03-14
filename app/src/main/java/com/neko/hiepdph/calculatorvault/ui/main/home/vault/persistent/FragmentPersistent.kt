package com.neko.hiepdph.calculatorvault.ui.main.home.vault.persistent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.neko.hiepdph.calculatorvault.databinding.FragmentPersistentBinding

enum class PersistentType {
    VIDEOS, AUDIOS, DOCUMENTS, PICTURES, OTHER
}

class FragmentPersistent : Fragment() {
    private var _binding: FragmentPersistentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersistentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}