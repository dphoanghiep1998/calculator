package com.neko.hiepdph.calculatorvault.ui.main.home.vault.addfile.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.common.Constant
import com.neko.hiepdph.calculatorvault.common.extensions.clickWithDebounce
import com.neko.hiepdph.calculatorvault.data.model.FileDirItem
import com.neko.hiepdph.calculatorvault.data.model.GroupItem
import com.neko.hiepdph.calculatorvault.databinding.LayoutItemAddFileAudioBinding
import com.neko.hiepdph.calculatorvault.databinding.LayoutItemAddFileFileBinding
import com.neko.hiepdph.calculatorvault.databinding.LayoutItemAddFilePictureBinding
import com.neko.hiepdph.calculatorvault.databinding.LayoutItemAddFileVideoBinding

class AdapterGroupItem(
    private val onClickFolderItem: (GroupItem, type: String?) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listGroupItem = mutableListOf<GroupItem>()
    private var fileDataFolder = mutableListOf<String>()
    private var mType: String = Constant.TYPE_PICTURE
    fun setData(listDataItem: List<GroupItem>, type: String) {
        mType = type
        listGroupItem = listDataItem.toMutableList()
        if (mType == Constant.TYPE_FILE) {
            if (listGroupItem.isNotEmpty()) {
                if (listGroupItem[0].dataTypeList?.isNotEmpty() == true) {
                    listDataItem[0].dataTypeList?.toMutableList()?.let {
                        fileDataFolder.clear()
                        fileDataFolder.addAll(it)
                    }
                }

            }
        }
        notifyDataSetChanged()
    }


    inner class GroupItemPictureViewHolder(val binding: LayoutItemAddFilePictureBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class GroupItemVideoViewHolder(val binding: LayoutItemAddFileVideoBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class GroupItemAudioViewHolder(val binding: LayoutItemAddFileAudioBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class GroupItemFileViewHolder(val binding: LayoutItemAddFileFileBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            0 -> {
                val binding = LayoutItemAddFilePictureBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return GroupItemPictureViewHolder(binding)
            }
            1 -> {
                val binding = LayoutItemAddFileVideoBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return GroupItemVideoViewHolder(binding)
            }
            2 -> {
                val binding = LayoutItemAddFileAudioBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return GroupItemAudioViewHolder(binding)
            }

            3 -> {
                val binding = LayoutItemAddFileFileBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return GroupItemFileViewHolder(binding)
            }
            else -> {
                val binding = LayoutItemAddFileFileBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return GroupItemFileViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when (mType) {
        Constant.TYPE_PICTURE -> 0
        Constant.TYPE_VIDEOS -> 1
        Constant.TYPE_AUDIOS -> 2
        Constant.TYPE_FILE -> 3
        else -> 4
    }

    override fun getItemCount(): Int {
        if (mType != Constant.TYPE_FILE) {
            return listGroupItem.size
        } else {
            if (listGroupItem.isNotEmpty()) {
                return listGroupItem[0].dataTypeList?.size ?: 0
            } else {
                return 0
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                with(holder as GroupItemPictureViewHolder) {
                    val model = listGroupItem[adapterPosition]
                    Glide.with(itemView.context).load(model.dataList[0]).centerCrop()
                        .error(ContextCompat.getDrawable(itemView.context, R.drawable.ic_delete))
                        .into(binding.imvThumb)
                    binding.tvNameQuantity.text = "${model.name} (${model.itemCount})"

                    binding.root.clickWithDebounce {
                        onClickFolderItem.invoke(listGroupItem[adapterPosition], null)
                    }
                }
            }
            1 -> {
                with(holder as GroupItemVideoViewHolder) {
                    val model = listGroupItem[adapterPosition]
                    Glide.with(itemView.context).load(model.dataList[0]).centerCrop()
                        .error(ContextCompat.getDrawable(itemView.context, R.drawable.ic_delete))
                        .into(binding.imvThumb)
                    binding.tvNameQuantity.text = "${model.name} (${model.itemCount})"
                    binding.root.clickWithDebounce {
                        onClickFolderItem.invoke(listGroupItem[adapterPosition], null)
                    }
                }
            }
            2 -> {
                with(holder as GroupItemAudioViewHolder) {
                    val model = listGroupItem[adapterPosition]
                    Glide.with(itemView.context).asBitmap().load(model.dataThumb[0]).centerCrop()
                        .error(ContextCompat.getDrawable(itemView.context, R.drawable.ic_delete))
                        .into(binding.imvThumb)
                    binding.tvNameQuantity.text = "${model.name} (${model.itemCount})"
                    binding.root.clickWithDebounce {
                        onClickFolderItem.invoke(listGroupItem[adapterPosition], null)
                    }
                }
            }
            3 -> {
                with(holder as GroupItemFileViewHolder) {

                    when (fileDataFolder[adapterPosition]) {
                        Constant.TYPE_PDF -> {
                            binding.imvThumb.setImageResource(R.drawable.ic_pdf_album)
                            binding.tvName.text = itemView.context.getString(R.string.pdf)
                        }
                        Constant.TYPE_PPT, Constant.TYPE_PPTX -> {
                            binding.imvThumb.setImageResource(R.drawable.ic_ppt_album)
                            binding.tvName.text = itemView.context.getString(R.string.ppt)
                        }
                        Constant.TYPE_WORD, Constant.TYPE_WORDX -> {
                            binding.imvThumb.setImageResource(R.drawable.ic_word_album)
                            binding.tvName.text = itemView.context.getString(R.string.word)
                        }
                        Constant.TYPE_EXCEL -> {
                            binding.imvThumb.setImageResource(R.drawable.ic_excel_album)
                            binding.tvName.text = itemView.context.getString(R.string.excel)
                        }
                        Constant.TYPE_TEXT -> {
                            binding.imvThumb.setImageResource(R.drawable.ic_txt_album)
                            binding.tvName.text = itemView.context.getString(R.string.text)
                        }
                        Constant.TYPE_CSV -> {
                            binding.imvThumb.setImageResource(R.drawable.ic_csv_album)
                            binding.tvName.text = itemView.context.getString(R.string.csv)
                        }
                        Constant.TYPE_ZIP -> {
                            binding.imvThumb.setImageResource(R.drawable.ic_zip_album)
                            binding.tvName.text = itemView.context.getString(R.string.zip)
                        }
                    }
                    binding.root.clickWithDebounce {
                        onClickFolderItem.invoke(listGroupItem[0], fileDataFolder[adapterPosition])
                    }

                }
            }
        }

    }

}