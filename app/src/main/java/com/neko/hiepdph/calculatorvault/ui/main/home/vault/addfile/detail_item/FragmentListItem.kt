package com.neko.hiepdph.calculatorvault.ui.main.home.vault.addfile.detail_item

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neko.hiepdph.calculatorvault.common.Constant
import com.neko.hiepdph.calculatorvault.databinding.FragmentListItemBinding
import com.neko.hiepdph.calculatorvault.ui.main.home.vault.addfile.detail_item.adapter.AdapterListItem
import com.neko.hiepdph.calculatorvault.viewmodel.HomeViewModel

class FragmentListItem : Fragment() {
    private var _binding: FragmentListItemBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by activityViewModels()
    private var adapterListItem: AdapterListItem? = null
    private val args: FragmentListItemArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        initRecycleView()
    }

    private fun observeData() {
        when (args.folder.type) {
            Constant.TYPE_PICTURE -> {
                viewModel.getImageChildFromFolder(requireContext(), args.folder.folderPath)
                viewModel.listImageItem.observe(viewLifecycleOwner) {
                    Log.d("TAG", "observeData: "+args.folder.type)
                    adapterListItem?.setData(it, Constant.TYPE_PICTURE)
                }
            }
            Constant.TYPE_VIDEOS -> {
                viewModel.getVideoChildFromFolder(requireContext(), args.folder.folderPath)
                viewModel.listVideoItem.observe(viewLifecycleOwner) {
                    Log.d("TAG", "observeData: "+args.folder.type)

                    adapterListItem?.setData(it, Constant.TYPE_VIDEOS)
                }
            }
            Constant.TYPE_AUDIOS -> {

                viewModel.getAudioChildFromFolder(requireContext(), args.folder.folderPath)
                viewModel.listAudioItem.observe(viewLifecycleOwner) {
                    Log.d("TAG", "observeData: "+args.folder.type)

                    adapterListItem?.setData(it, Constant.TYPE_AUDIOS)
                }
            }
            Constant.TYPE_FILE -> {
                viewModel.getFileChildFromFolder(requireContext(), args.folder.folderPath)
                viewModel.listFileItem.observe(viewLifecycleOwner) {
                    adapterListItem?.setData(it, Constant.TYPE_FILE)
                }
            }
        }

    }

    private fun initRecycleView() {
        adapterListItem = AdapterListItem()
        binding.rcvGroupItem.adapter = adapterListItem
        when (args.folder.type) {
            Constant.TYPE_PICTURE, Constant.TYPE_VIDEOS -> {
                val gridLayoutManager =
                    GridLayoutManager(requireContext(), 4, RecyclerView.VERTICAL, false)
                binding.rcvGroupItem.layoutManager = gridLayoutManager
            }
            Constant.TYPE_AUDIOS, Constant.TYPE_FILE -> {
                val linearLayoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                binding.rcvGroupItem.layoutManager = linearLayoutManager
            }

        }
    }
}