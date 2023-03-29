package com.neko.hiepdph.calculatorvault.ui.main.home.vault

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.activities.MainActivity
import com.neko.hiepdph.calculatorvault.common.Constant
import com.neko.hiepdph.calculatorvault.common.enums.Order
import com.neko.hiepdph.calculatorvault.common.enums.Sort
import com.neko.hiepdph.calculatorvault.common.extensions.SnackBarType
import com.neko.hiepdph.calculatorvault.common.extensions.clickWithDebounce
import com.neko.hiepdph.calculatorvault.common.extensions.navigateToPage
import com.neko.hiepdph.calculatorvault.common.extensions.showSnackBar
import com.neko.hiepdph.calculatorvault.common.utils.ICreateFile
import com.neko.hiepdph.calculatorvault.common.utils.IDeleteFile
import com.neko.hiepdph.calculatorvault.data.model.CustomFolder
import com.neko.hiepdph.calculatorvault.databinding.FragmentHomeBinding
import com.neko.hiepdph.calculatorvault.databinding.LayoutMenuOptionBinding
import com.neko.hiepdph.calculatorvault.dialog.*
import com.neko.hiepdph.calculatorvault.ui.main.home.adapter.AdapterFolder
import com.neko.hiepdph.calculatorvault.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FragmentVault : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var popupWindow: PopupWindow
    private lateinit var adapter: AdapterFolder
    private val viewModel: HomeViewModel by activityViewModels()
    private var rootView: View? = null

    companion object {
        var sortType: Sort = Sort.NAME
        var order: Order = Order.ASC
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        rootView = binding.root
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
            Log.d("TAG", "observeListFile: " + sortList(it))
            adapter.setData(sortList(it))
        }
    }


    private fun initView() {
        initRecyclerView()
        initPopupWindow()
        initButton()
        initToolBar()
    }

    private fun initToolBar() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.toolbar_menu_vault, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                val menuItemView = requireActivity().findViewById<View>(R.id.option)
                when (menuItem.itemId) {
                    R.id.add_folder -> showAddFolderDialog()
                    R.id.option -> showOptionDialog(menuItemView)
                    R.id.navigate_calculator -> navigateToCalculator()
                }
                return true
            }

        })
    }

    private fun navigateToCalculator() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun initRecyclerView() {
        adapter = AdapterFolder(requireContext(), onItemPress = {
            when (it.type) {
                Constant.TYPE_PICTURE -> {
                    val action = FragmentVaultDirections.actionFragmentVaultToFragmentPersistent(
                        it.type, getString(R.string.pictures),it.path
                    )
                    navigateToPage(R.id.fragmentVault, action)
                }
                Constant.TYPE_VIDEOS -> {
                    val action = FragmentVaultDirections.actionFragmentVaultToFragmentPersistent(
                        it.type, getString(R.string.videos),it.path
                    )
                    navigateToPage(R.id.fragmentVault, action)
                }
                Constant.TYPE_AUDIOS -> {
                    val action = FragmentVaultDirections.actionFragmentVaultToFragmentPersistent(
                        it.type, getString(R.string.audios),it.path
                    )
                    navigateToPage(R.id.fragmentVault, action)
                }
                Constant.TYPE_FILE -> {
                    val action = FragmentVaultDirections.actionFragmentVaultToFragmentPersistent(
                        it.type, getString(R.string.files),it.path
                    )
                    navigateToPage(R.id.fragmentVault, action)
                }
                else -> {
                    val action = FragmentVaultDirections.actionFragmentVaultToFragmentPersistent(
                        it.type, it.name,it.path
                    )
                    navigateToPage(R.id.fragmentVault, action)
                }
            }
        }, onDeletePress = {
            val dialogConfirm = DialogConfirm(object : ConfirmDialogCallBack {
                override fun onPositiveClicked() {
                    val callback = object : IDeleteFile {
                        override fun onSuccess() {
                            viewModel.getListFolderVault(
                                requireContext(), requireActivity().filesDir
                            )
                            lifecycleScope.launch(Dispatchers.Main) {
                                showSnackBar(
                                    getString(R.string.delete_success), SnackBarType.SUCCESS
                                )
                            }

                        }

                        override fun onFailed() {
                            showSnackBar(
                                getString(R.string.delete_failed), SnackBarType.FAILED
                            )
                        }

                    }
                    viewModel.deleteFolder(it.path, callback)
                }
            }, DialogConfirmType.DELETE, it.name)

            dialogConfirm.show(parentFragmentManager, dialogConfirm.tag)

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

    private fun showOptionDialog(menuItemView: View) {
        popupWindow.showAsDropDown(menuItemView, 0, 0)
    }

    private fun showAddFolderDialog() {
        val dialogAddNewFolder = DialogAddNewFolder(object : AddNewFolderDialogCallBack {
            override fun onPositiveClicked(name: String) {
                val callback = object : ICreateFile {
                    override fun onSuccess() {

                        lifecycleScope.launch(Dispatchers.Main) {
                            showSnackBar(
                                getString(R.string.create_success), SnackBarType.SUCCESS
                            )
                        }
                        viewModel.getListFolderVault(requireContext(), requireActivity().filesDir)
                    }

                    override fun onFailed() {
                        lifecycleScope.launch(Dispatchers.Main) {
                            showSnackBar(
                                getString(R.string.create_failed), SnackBarType.FAILED
                            )
                        }
                    }

                }
                viewModel.createFolder(requireActivity().filesDir, name, callback)
            }

        })
        dialogAddNewFolder.show(parentFragmentManager, dialogAddNewFolder.tag)
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
            val dialogSort = DialogSort(callBack = object : SortDialogCallBack {
                override fun onPositiveClicked(mSortType: Sort, mOrder: Order) {
                    sortType = mSortType
                    order = mOrder
                    viewModel.listFolder.postValue(viewModel.listFolder.value)
                }
            }, sortType, order)
            dialogSort.show(parentFragmentManager, dialogSort.tag)
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

    private fun sortList(mList: MutableList<CustomFolder>): MutableList<CustomFolder> {
        when (order) {
            Order.ASC -> {
                when (sortType) {
                    Sort.RANDOM -> {
                        mList.shuffle()
                    }
                    Sort.NAME -> {
                        mList.sortBy { it.name }
                    }
                    Sort.SIZE -> {
                        mList.sortBy { it.itemCount }
                    }
                }
            }
            Order.DES -> {
                when (sortType) {
                    Sort.RANDOM -> {
                        mList.shuffle()
                    }
                    Sort.NAME -> {
                        mList.sortByDescending { it.name }
                    }
                    Sort.SIZE -> {
                        mList.sortByDescending { it.itemCount }
                    }
                }
            }
        }
        return mList
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}