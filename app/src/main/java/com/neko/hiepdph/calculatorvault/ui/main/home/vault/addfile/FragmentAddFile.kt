package com.neko.hiepdph.calculatorvault.ui.main.home.vault.addfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.activities.HomeActivity
import com.neko.hiepdph.calculatorvault.common.Constant
import com.neko.hiepdph.calculatorvault.common.extensions.navigateToPage
import com.neko.hiepdph.calculatorvault.databinding.FragmentAddFileBinding
import com.neko.hiepdph.calculatorvault.ui.main.home.vault.addfile.adapter.AdapterGroupItem
import com.neko.hiepdph.calculatorvault.viewmodel.HomeViewModel

class FragmentAddFile : Fragment() {
    private var _binding: FragmentAddFileBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterGroupItem
    private val viewModel: HomeViewModel by activityViewModels()
    private val args: FragmentAddFileArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getDataFromArgs()
        observeListGroupData()
    }

    override fun onResume() {
        super.onResume()
//        observeListGroupData()
    }

    private fun getDataFromArgs() {
        getDataGroupFile(args.type)
    }


    private fun initView() {
        initRecyclerView()
    }

    private fun getDataGroupFile(type: String) {
        viewModel.getListFolderItem(requireContext(), type)
    }

    private fun initRecyclerView() {
        adapter = AdapterGroupItem(onClickFolderItem = {
            val action = FragmentAddFileDirections.actionFragmentAddFileToFragmentListItem(it)
            navigateToPage(R.id.fragmentAddFile,action)
        })
        binding.rcvGroupItem.adapter = adapter
        val gridLayoutManager = if (args.type == Constant.TYPE_FILE) {
            GridLayoutManager(requireContext(), 4, RecyclerView.VERTICAL, false)
        } else {
            GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        }
        binding.rcvGroupItem.layoutManager = gridLayoutManager
    }

    private fun observeListGroupData() {
        when (args.type) {
            Constant.TYPE_PICTURE -> viewModel.listGroupImageData.observe(viewLifecycleOwner) {
                adapter.setData(it, args.type)
            }
            Constant.TYPE_AUDIOS -> viewModel.listGroupAudioData.observe(viewLifecycleOwner) {
                adapter.setData(it, args.type)
            }
            Constant.TYPE_VIDEOS -> viewModel.listGroupVideoData.observe(viewLifecycleOwner) {
                adapter.setData(it, args.type)
            }
            Constant.TYPE_FILE -> viewModel.listGroupFileData.observe(viewLifecycleOwner) {
                adapter.setData(it, args.type)
            }
        }

    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}