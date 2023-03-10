package com.neko.hiepdph.calculatorvault.ui.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.common.Constant
import com.neko.hiepdph.calculatorvault.data.model.CustomFolder
import com.neko.hiepdph.calculatorvault.databinding.ItemHomeGridLayoutBinding
import com.neko.hiepdph.calculatorvault.databinding.ItemHomeListLayoutBinding


class AdapterFolder : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        var isSwitchView = false
    }

    private val LIST_ITEM = 0
    private val GRID_ITEM = 1

    inner class FolderViewHolderList(val binding: ItemHomeListLayoutBinding) :
        ViewHolder(binding.root)

    inner class FolderViewHolderGrid(val binding: ItemHomeGridLayoutBinding) :
        ViewHolder(binding.root)


    private var listFolder = mutableListOf<CustomFolder>()

    fun setData(list: List<CustomFolder>) {
        listFolder.clear()
        listFolder.addAll(list.toMutableList())
        notifyItemRangeChanged(0, listFolder.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == LIST_ITEM) {
            val binding = ItemHomeListLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            FolderViewHolderList(binding)
        } else {
            val binding = ItemHomeGridLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            FolderViewHolderGrid(binding)
        }
    }

    override fun getItemCount(): Int {
        return listFolder.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val customFolder = listFolder[position]
        when (holder.itemViewType) {
            LIST_ITEM -> {
                with(holder as FolderViewHolderList) {
                    binding.imvLogo.setImageResource(getImageLogo(customFolder.type))
                    binding.tvName.text = customFolder.name
                    binding.imvOption.visibility =
                        if (customFolder.type == Constant.TYPE_ADD_MORE) View.VISIBLE else View.GONE
                    binding.tvCount.text =
                        customFolder.itemCount.toString() + " " + itemView.context.getString(R.string.item)
                }
            }
            GRID_ITEM -> {
                with(holder as FolderViewHolderGrid) {
                    binding.imvLogo.setImageResource(getImageLogo(customFolder.type))
                    binding.tvName.text = customFolder.name
                    binding.imvOption.visibility =
                        if (customFolder.type == Constant.TYPE_ADD_MORE) View.VISIBLE else View.GONE
                    binding.tvCount.text = customFolder.itemCount.toString()


                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (!isSwitchView) {
            LIST_ITEM
        } else {
            GRID_ITEM
        }
    }

    private fun getImageLogo(type: String): Int {
        return when (type) {
            Constant.TYPE_PICTURE -> R.drawable.ic_item_pictures
            Constant.TYPE_VIDEOS -> R.drawable.ic_item_videos
            Constant.TYPE_AUDIOS -> R.drawable.ic_item_audios
            Constant.TYPE_DOCUMENT -> R.drawable.ic_item_files
            else -> R.drawable.ic_item_files
        }
    }
}