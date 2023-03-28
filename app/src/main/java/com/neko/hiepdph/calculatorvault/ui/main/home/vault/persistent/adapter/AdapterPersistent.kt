package com.neko.hiepdph.calculatorvault.ui.main.home.vault.persistent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.common.Constant
import com.neko.hiepdph.calculatorvault.data.model.*
import com.neko.hiepdph.calculatorvault.databinding.*


class AdapterPersistent(private val onClickItem: (ObjectItem) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listItem = mutableListOf<ObjectItem>()
    private var mType: String = Constant.TYPE_PICTURE

    fun setData(listDataItem: List<ObjectItem>, type: String) {
        mType = type
        listItem = listDataItem.toMutableList()
        notifyDataSetChanged()
    }

    inner class ItemPictureViewHolder(val binding: LayoutItemPictureBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ItemVideoViewHolder(val binding: LayoutItemVideosBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ItemAudioViewHolder(val binding: LayoutItemAudiosBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ItemFileViewHolder(val binding: LayoutItemFileBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ItemOtherFileViewHolder(val binding: LayoutItemOtherFileBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            0 -> {
                val binding = LayoutItemPictureBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return ItemPictureViewHolder(binding)
            }
            1 -> {
                val binding = LayoutItemVideosBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return ItemVideoViewHolder(binding)
            }
            2 -> {
                val binding = LayoutItemAudiosBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return ItemAudioViewHolder(binding)
            }

            3 -> {
                val binding = LayoutItemFileBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return ItemFileViewHolder(binding)
            }
            else -> {
                val binding = LayoutItemOtherFileBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return ItemOtherFileViewHolder(binding)
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
        return listItem.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                with(holder as ItemPictureViewHolder) {
                    val item = listItem[adapterPosition] as ImageItem
                    var requestOptions = RequestOptions()
                    requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(10))
                    Glide.with(itemView.context).load(item.imagePath).apply(requestOptions)
                        .error(R.drawable.ic_delete).into(binding.imvThumb)
                }
            }
            1 -> {
                with(holder as ItemVideoViewHolder) {
                    val item = listItem[adapterPosition] as VideoItem
                    var requestOptions = RequestOptions()
                    requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(10))
                    Glide.with(itemView.context).load(item.videoPath).apply(requestOptions)
                        .error(R.drawable.ic_delete).into(binding.imvThumb)
                    binding.tvDuration.text = item.videoDuration.toString()
                }
            }
            2 -> {
                with(holder as ItemAudioViewHolder) {
                    val item = listItem[adapterPosition] as AudioItem
                    var requestOptions = RequestOptions()
                    requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(10))
                    Glide.with(itemView.context).asBitmap().load(item.thumbBitmap)
                        .apply(requestOptions).error(R.drawable.ic_delete).into(binding.imvThumb)

                    binding.tvNameAudio.text = item.audioName
                    binding.tvDurationAuthor.text = item.audioDuration.toString()
                }
            }
            3 -> {
                with(holder as ItemFileViewHolder) {
                    val item = listItem[adapterPosition] as FileItem
                    Glide.with(itemView.context).load(getImageForItemFile(item))
                        .error(R.drawable.ic_delete).into(binding.imvThumb)

                    binding.tvNameDocument.text = item.fileName
                    binding.tvSize.text = item.fileSize.toString()
                }
            }
            4 -> {

            }
        }
    }

    private fun getImageForItemFile(item: FileItem): Int {
        return when (item.fileType) {
            Constant.TYPE_WORDX -> R.drawable.ic_docx
            Constant.TYPE_WORD -> R.drawable.ic_doc
            Constant.TYPE_CSV -> R.drawable.ic_csv
            Constant.TYPE_PPT -> R.drawable.ic_ppt
            Constant.TYPE_TEXT -> R.drawable.ic_txt
            Constant.TYPE_ZIP -> R.drawable.ic_zip
            Constant.TYPE_PPTX -> R.drawable.ic_pptx
            Constant.TYPE_EXCEL -> R.drawable.ic_excel
            Constant.TYPE_PDF -> R.drawable.ic_pdf
            else -> 0
        }
    }
}