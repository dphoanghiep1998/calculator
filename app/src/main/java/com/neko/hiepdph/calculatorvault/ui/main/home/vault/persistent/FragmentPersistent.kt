package com.neko.hiepdph.calculatorvault.ui.main.home.vault.persistent

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.activities.HomeActivity
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
        (requireActivity() as HomeActivity).supportActionBar?.title = args.title
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initToolBar()
    }


    private fun initView() {
        binding.floatingActionButton.setOnClickListener {
            val action =
                FragmentPersistentDirections.actionFragmentPersistentToFragmentAddFile(args.type)
            navigateToPage(R.id.fragmentPersistent, action)
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {

    }
    private fun initToolBar() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                return true
            }

        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}