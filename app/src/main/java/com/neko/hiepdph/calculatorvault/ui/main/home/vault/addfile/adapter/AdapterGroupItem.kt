package com.neko.hiepdph.calculatorvault.ui.main.home.vault.addfile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.common.Constant
import com.neko.hiepdph.calculatorvault.data.model.GroupFile
import com.neko.hiepdph.calculatorvault.databinding.LayoutItemAddFileBinding

class AdapterGroupItem : RecyclerView.Adapter<AdapterGroupItem.GroupItemViewHolder>() {
    private var listGroupItem = mutableListOf<GroupFile>()
    private var mType: String = Constant.TYPE_PICTURE
    fun setData(listDataItem: List<GroupFile>, type: String) {
        listGroupItem = listDataItem.toMutableList()
        mType = type
        notifyDataSetChanged()
    }

    inner class GroupItemViewHolder(val binding: LayoutItemAddFileBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupItemViewHolder {
        val binding =
            LayoutItemAddFileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listGroupItem.size
    }

    override fun onBindViewHolder(holder: GroupItemViewHolder, position: Int) {
        with(holder) {
            val model = listGroupItem[adapterPosition]
            Glide.with(itemView.context).load(model.dataPathList[0]).centerCrop()
                .error(itemView.context.getDrawable(R.drawable.ic_delete)).into(binding.imvThumb)
            binding.tvNameQuantity.text = "${model.name} (${model.itemCount})"


//            binding.imvThumb.setBackgroundResource(model.)
        }
    }

}