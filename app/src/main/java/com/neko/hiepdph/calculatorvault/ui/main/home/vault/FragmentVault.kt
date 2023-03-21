package com.neko.hiepdph.calculatorvault.ui.main.home.vault

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.activities.MainActivity
import com.neko.hiepdph.calculatorvault.common.Constant
import com.neko.hiepdph.calculatorvault.common.extensions.SnackBarType
import com.neko.hiepdph.calculatorvault.common.extensions.clickWithDebounce
import com.neko.hiepdph.calculatorvault.common.extensions.navigateToPage
import com.neko.hiepdph.calculatorvault.common.extensions.showSnackBar
import com.neko.hiepdph.calculatorvault.common.utils.ICreateFile
import com.neko.hiepdph.calculatorvault.common.utils.IDeleteFile
import com.neko.hiepdph.calculatorvault.databinding.FragmentHomeBinding
import com.neko.hiepdph.calculatorvault.databinding.LayoutMenuOptionBinding
import com.neko.hiepdph.calculatorvault.dialog.*
import com.neko.hiepdph.calculatorvault.ui.main.home.adapter.AdapterFolder
import com.neko.hiepdph.calculatorvault.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentVault : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var popupWindow: PopupWindow
    private lateinit var adapter: AdapterFolder
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeListFile()
        viewModel.getListFolderVault(requireContext(), requireActivity().filesDir)
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
//        initToolBar()
    }


//    private fun initToolBar() {
//        requireActivity().addMenuProvider(object : MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menuInflater.inflate(R.menu.toolbar_menu_vault, menu)
//            }
//
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                when (menuItem.itemId) {
//                    R.id.menu -> Log.d("TAG", "onMenuItemSelected: ")
//                    R.id.add_folder -> showAddFolderDialog()
//                    R.id.option -> showOptionDialog()
//                    R.id.navigate_calculator -> navigateToCalculator()
//                }
//                return true
//            }
//
//        })
//    }


    private fun navigateToCalculator() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun initRecyclerView() {
        adapter = AdapterFolder(requireContext(), onItemPress = {
            val action = FragmentVaultDirections.actionFragmentVaultToFragmentPersistent(it.type)
            when (it.type) {
                Constant.TYPE_PICTURE -> {
                    navigateToPage(R.id.fragmentVault, action)
                }
                Constant.TYPE_VIDEOS -> {
                    navigateToPage(R.id.fragmentVault, action)
                }
                Constant.TYPE_AUDIOS -> {
                    navigateToPage(R.id.fragmentVault, action)
                }
                Constant.TYPE_DOCUMENT -> {
                    navigateToPage(R.id.fragmentVault, action)
                }
                else -> {}
            }
        }, onDeletePress = {
            val dialogConfirm = DialogConfirm(object : ConfirmDialogCallBack {
                override fun onPositiveClicked() {
                    val callback = object : IDeleteFile {
                        override fun onSuccess() {
                            viewModel.getListFolderVault(
                                requireContext(), requireActivity().filesDir
                            )
                            showSnackBar(
                                getString(R.string.delete_success), SnackBarType.SUCCESS
                            )
                        }

                        override fun onFailed() {
                            showSnackBar(getString(R.string.delete_failed), SnackBarType.FAILED)
                        }

                    }
                    viewModel.deleteFolder(it.path, callback)
                }
            }, DialogConfirmType.DELETE, it.name)
            dialogConfirm.show(childFragmentManager, dialogConfirm.tag)
        }, onRenamePress = {

        })
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

    }

    private fun showOptionDialog() {
//        popupWindow.showAsDropDown(binding.too, 0, 0)
    }

    private fun showAddFolderDialog() {
        val dialogAddNewFolder = DialogAddNewFolder(object : AddNewFolderDialogCallBack {
            override fun onPositiveClicked(name: String) {
                val callback = object : ICreateFile {
                    override fun onSuccess() {
                        showSnackBar(getString(R.string.create_success), SnackBarType.SUCCESS)
                        viewModel.getListFolderVault(requireContext(), requireActivity().filesDir)
                    }

                    override fun onFailed() {
                        showSnackBar(getString(R.string.create_failed), SnackBarType.FAILED)
                    }

                }
                viewModel.createFolder(requireActivity().filesDir, name, callback)
            }

        })
        dialogAddNewFolder.show(childFragmentManager, dialogAddNewFolder.tag)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}