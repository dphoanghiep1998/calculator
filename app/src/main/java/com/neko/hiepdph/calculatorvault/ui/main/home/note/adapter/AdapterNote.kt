package com.neko.hiepdph.calculatorvault.ui.main.home.note.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neko.hiepdph.calculatorvault.common.utils.DateTimeUtils
import com.neko.hiepdph.calculatorvault.data.model.NoteModel
import com.neko.hiepdph.calculatorvault.databinding.LayoutItemNoteBinding

class AdapterNote : RecyclerView.Adapter<AdapterNote.NoteViewHolder>() {

    private var listOfNote: MutableList<NoteModel> = mutableListOf()

    fun setData(listNote: List<NoteModel>) {
        listOfNote.clear()
        listOfNote.addAll(listNote)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(val binding: LayoutItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = LayoutItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listOfNote.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        with(holder){
            val item = listOfNote[adapterPosition]
            binding.tvTitle.text = item.title
            binding.tvContent.text = item.content
            binding.tvDate.text = DateTimeUtils.getDateConverted(item.date)
        }
    }


}