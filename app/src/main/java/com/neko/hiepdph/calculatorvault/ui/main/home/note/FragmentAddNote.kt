package com.neko.hiepdph.calculatorvault.ui.main.home.note

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.activities.HomeActivity
import com.neko.hiepdph.calculatorvault.common.extensions.SnackBarType
import com.neko.hiepdph.calculatorvault.common.extensions.showSnackBar
import com.neko.hiepdph.calculatorvault.data.model.NoteModel
import com.neko.hiepdph.calculatorvault.databinding.FragmentAddNoteBinding
import com.neko.hiepdph.calculatorvault.viewmodel.NoteViewModel
import java.util.Calendar


class FragmentAddNote : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NoteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        initToolBar()
        return binding.root
    }

    private fun initToolBar() {
        (requireActivity() as HomeActivity)?.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.toolbar_menu_add_note, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {

                    R.id.check -> saveNote()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.CREATED)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.toolbar_menu_add_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.check -> saveNote()
        }
        return true
    }

    private fun saveNote() {
        if (binding.edtTitle.text.isBlank() || binding.edtContent.text.isBlank()) {
            showSnackBar(getString(R.string.title_and_content_required), SnackBarType.FAILED)
            return
        }
        val title = binding.edtTitle.text.toString()
        val content = binding.edtContent.text.toString()
        val date = Calendar.getInstance().timeInMillis
        viewModel.insertNewNote(NoteModel(-1, title, content, date))
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}