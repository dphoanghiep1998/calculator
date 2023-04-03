package com.neko.hiepdph.calculatorvault.ui.main.home.note

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.common.extensions.clickWithDebounce
import com.neko.hiepdph.calculatorvault.databinding.FragmentNoteBinding
import com.neko.hiepdph.calculatorvault.ui.main.home.note.adapter.AdapterNote
import com.neko.hiepdph.calculatorvault.viewmodel.NoteViewModel

class FragmentNote : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NoteViewModel>()
    private var adapterNote: AdapterNote ?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initRecyclerView()
        initButton()
    }

    private fun initRecyclerView() {

    }

    private fun initButton() {
        binding.floatingActionButton.clickWithDebounce {
            addNote()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.toolbar_menu_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.layout -> changeLayout()
            R.id.search -> changeToSearchView()
            R.id.edit -> editNote()
        }
        return true
    }

    private fun changeLayout() {
    }

    private fun changeToSearchView() {

    }

    private fun editNote() {

    }

    private fun addNote() {
        findNavController().navigate(R.id.action_fragmentNote_to_fragmentAddNote)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}