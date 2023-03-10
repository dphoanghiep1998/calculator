package com.neko.hiepdph.calculatorvault.ui.main.home

import android.content.Context
import android.graphics.Insets
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.common.extensions.clickWithDebounce
import com.neko.hiepdph.calculatorvault.common.extensions.navigateToPage
import com.neko.hiepdph.calculatorvault.common.utils.buildMinVersionR
import com.neko.hiepdph.calculatorvault.databinding.FragmentHomeBinding
import com.neko.hiepdph.calculatorvault.databinding.LayoutMenuOptionBinding
import com.neko.hiepdph.calculatorvault.dialog.AddNewFolderDialogCallBack
import com.neko.hiepdph.calculatorvault.dialog.DialogAddNewFolder
import com.neko.hiepdph.calculatorvault.ui.main.home.adapter.AdapterFolder
import com.neko.hiepdph.calculatorvault.ui.main.home.adapter.GridSpacingItemDecoration
import com.neko.hiepdph.calculatorvault.viewmodel.AppViewModel
import com.neko.hiepdph.calculatorvault.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FragmentHome : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var popupWindow: PopupWindow
    private lateinit var adapter: AdapterFolder
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initView()
        observeListFile()
        return binding.root
    }

    private fun observeListFile() {
        viewModel.listFolder.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    private fun initView() {
        initRecyclerView()
        initPopupWindow()
        initButton()
        viewModel.getListFolder(requireContext(), requireActivity().filesDir)
    }

    private fun initRecyclerView() {
        adapter = AdapterFolder()
        binding.rcvFolder.adapter = adapter
        if (!AdapterFolder.isSwitchView) {
            val gridLayoutManager = GridLayoutManager(requireContext(), 1)
            binding.rcvFolder.layoutManager = gridLayoutManager
        } else {
            val gridLayoutManager = GridLayoutManager(requireContext(), 2)
            binding.rcvFolder.layoutManager = gridLayoutManager

        }

    }

    private fun initButton() {
        binding.imvOption.clickWithDebounce {
            popupWindow.showAsDropDown(binding.imvOption, 0, 0)
        }
        binding.imvCalculator.clickWithDebounce {
            navigateToPage(R.id.fragmentHome, R.id.action_fragmentHome_to_fragmentCalculator)
        }
        binding.imvNewFolder.clickWithDebounce {
            val dialogAddNewFolder = DialogAddNewFolder(object : AddNewFolderDialogCallBack {
                override fun onPositiveClicked(name: String) {
                    viewModel.createFolder(requireActivity().filesDir, name)
                    viewModel.getListFolder(requireContext(), requireActivity().filesDir)
                }

            })
            dialogAddNewFolder.show(childFragmentManager, dialogAddNewFolder.tag)
        }
    }

    private fun initPopupWindow() {
        val inflater: LayoutInflater =
            (requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?)!!
        val bindingLayout = LayoutMenuOptionBinding.inflate(inflater, null, false)

        popupWindow = PopupWindow(
            bindingLayout.root,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        )
        bindingLayout.root.clickWithDebounce {
            popupWindow.dismiss()
        }
        bindingLayout.tvSort.clickWithDebounce {
            popupWindow.dismiss()
        }
        if (!AdapterFolder.isSwitchView) {
            val gridLayoutManager = GridLayoutManager(requireContext(), 2)
            binding.rcvFolder.layoutManager = gridLayoutManager
            bindingLayout.tvList.setText(R.string.grid)
            bindingLayout.tvList.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(
                    requireContext(), R.drawable.ic_grid_layout
                ), null, null, null
            )
        } else {
            val gridLayoutManager = GridLayoutManager(requireContext(), 2)
            binding.rcvFolder.layoutManager = gridLayoutManager
            bindingLayout.tvList.setText(R.string.list)
            bindingLayout.tvList.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(
                    requireContext(), R.drawable.ic_list
                ), null, null, null
            )
        }
        bindingLayout.tvList.clickWithDebounce {
            AdapterFolder.isSwitchView = !AdapterFolder.isSwitchView
            if (!AdapterFolder.isSwitchView) {
                bindingLayout.tvList.setText(R.string.grid)
                bindingLayout.tvList.setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.ic_grid_layout
                    ), null, null, null
                )
            } else {
                bindingLayout.tvList.setText(R.string.list)
                bindingLayout.tvList.setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.ic_list
                    ), null, null, null
                )
            }
            changeLayoutRecyclerView()
            popupWindow.dismiss()
        }
    }

    private fun changeLayoutRecyclerView() {
        if (!AdapterFolder.isSwitchView) {
            val gridLayoutManager = GridLayoutManager(requireContext(), 1)
            binding.rcvFolder.layoutManager = gridLayoutManager
        } else {
            val gridLayoutManager = GridLayoutManager(requireContext(), 2)
            binding.rcvFolder.layoutManager = gridLayoutManager
        }
    }


}