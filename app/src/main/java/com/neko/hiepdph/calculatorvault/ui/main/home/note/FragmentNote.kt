package com.neko.hiepdph.calculatorvault.ui.main.home.note

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.databinding.FragmentNoteBinding

class FragmentNote : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}