package com.neko.hiepdph.calculatorvault.ui.main.home.vault.persistent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.common.extensions.navigateToPage
import com.neko.hiepdph.calculatorvault.databinding.FragmentPersistentBinding
import com.neko.hiepdph.calculatorvault.viewmodel.HomeViewModel

enum class PersistentType {
    VIDEOS, AUDIOS, DOCUMENTS, PICTURES;

    companion object {
        @JvmStatic
        fun fromString(value: String): PersistentType {
            return when (value) {
                "VIDEOS" -> VIDEOS
                "AUDIOS" -> AUDIOS
                "DOCUMENTS" -> DOCUMENTS
                "PICTURES" -> PICTURES
                else -> throw IllegalArgumentException("Invalid enum value: $value")
            }
        }
    }
}

class FragmentPersistent : Fragment() {
    private var _binding: FragmentPersistentBinding? = null
    private val binding get() = _binding!!
    private val args: FragmentPersistentArgs by navArgs()
    private val viewModel: HomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersistentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun initView() {
        binding.tvTest.setOnClickListener {
            val action =
                FragmentPersistentDirections.actionFragmentPersistentToFragmentAddFile(args.type)
            navigateToPage(R.id.fragmentPersistent, action)
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}