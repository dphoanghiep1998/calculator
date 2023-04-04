package com.neko.hiepdph.calculatorvault.ui.main.home.note

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.activities.HomeActivity
import com.neko.hiepdph.calculatorvault.common.extensions.changeBackPressCallBack
import com.neko.hiepdph.calculatorvault.common.extensions.clickWithDebounce
import com.neko.hiepdph.calculatorvault.databinding.FragmentNoteBinding
import com.neko.hiepdph.calculatorvault.ui.main.home.note.adapter.AdapterNote
import com.neko.hiepdph.calculatorvault.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

interface ToolbarChangeListener {
    fun setToolBarNavigationIcon(drawable: Drawable)
    fun setOnClickToNavigationIcon(callback: () -> Unit)
}

@AndroidEntryPoint
class FragmentNote : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NoteViewModel>()
    private var adapterNote: AdapterNote? = null
    private var listener: ToolbarChangeListener? = null
    private var normalMenuProvider: MenuProvider? = null
    private var listIdNoteSelected = mutableListOf<Int>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ToolbarChangeListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        changeBackPressCallBack {
            requireActivity().finishAffinity()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initToolBar()
        observeNote()
    }

    private fun observeNote() {
        viewModel.getAllNote().observe(viewLifecycleOwner) {
            adapterNote?.setData(it)
        }
    }

    private fun initView() {
        initRecyclerView()
        initButton()
    }

    private fun initButton() {
        binding.floatingActionButton.clickWithDebounce {
            addNote()
        }
    }

    private fun initRecyclerView() {
        adapterNote = AdapterNote(onClickItem = {}, onLongClickItem = {
            initToolBar()
            editView()
        }, onEditItem = {
            listIdNoteSelected.clear()
            listIdNoteSelected.addAll(it)
        })
        binding.rcvItemNote.adapter = adapterNote

        if (AdapterNote.isSwitchView) {
            val gridLayoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            binding.rcvItemNote.layoutManager = gridLayoutManager
        } else {
            val linearLayoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.rcvItemNote.layoutManager = linearLayoutManager
        }
    }


    private fun initToolBar() {
        normalMenuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                if (AdapterNote.editMode) {
                    menuInflater.inflate(R.menu.toolbar_menu_edit_note, menu)
                } else {
                    menuInflater.inflate(R.menu.toolbar_menu_note, menu)
                    if (AdapterNote.isSwitchView) {
                        menu[0].icon =
                            ContextCompat.getDrawable(requireContext(), R.drawable.ic_list)
                    } else {
                        menu[0].icon =
                            ContextCompat.getDrawable(requireContext(), R.drawable.ic_grid_layout)
                    }
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (AdapterNote.editMode) {
                    when (menuItem.itemId) {
                        R.id.delete -> deleteNote()
                        R.id.check_box -> checkAllNote()
                    }

                } else {
                    when (menuItem.itemId) {
                        R.id.layout -> changeLayout(menuItem)
                        R.id.search -> changeToSearchView()
                    }
                }
                return true
            }
        }
        (requireActivity() as HomeActivity)?.getToolbar()?.menu?.clear()
        (requireActivity() as HomeActivity)?.addMenuProvider(
            normalMenuProvider as MenuProvider, viewLifecycleOwner, Lifecycle.State.CREATED
        )
    }

    private fun checkAllNote() {

    }

    private fun editView() {
        listener?.setOnClickToNavigationIcon {
            adapterNote?.changeToNormalView()
            requireActivity().invalidateOptionsMenu()
        }
        listener?.setToolBarNavigationIcon(
            ContextCompat.getDrawable(
                requireContext(), R.drawable.ic_exit
            )!!
        )
    }


    private fun changeLayout(item: MenuItem) {
        AdapterNote.isSwitchView = !AdapterNote.isSwitchView
        if (AdapterNote.isSwitchView) {
            val gridLayoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            binding.rcvItemNote.layoutManager = gridLayoutManager
            item.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_list)
        } else {
            val linearLayoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.rcvItemNote.layoutManager = linearLayoutManager
            item.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_grid_layout)
        }
    }

    private fun changeToSearchView() {

    }

    private fun editNote() {

    }

    private fun deleteNote() {

    }

    private fun addNote() {
        findNavController().navigate(R.id.action_fragmentNote_to_fragmentAddNote)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}