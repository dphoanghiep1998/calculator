package com.neko.hiepdph.calculatorvault.ui.main.home.vault.addfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neko.hiepdph.calculatorvault.common.Constant
import com.neko.hiepdph.calculatorvault.databinding.FragmentAddFileBinding
import com.neko.hiepdph.calculatorvault.ui.main.home.vault.addfile.adapter.AdapterGroupItem
import com.neko.hiepdph.calculatorvault.viewmodel.HomeViewModel

class FragmentAddFile : Fragment() {
    private var _binding: FragmentAddFileBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterGroupItem
    private val viewModel: HomeViewModel by activityViewModels()
    val args:FragmentAddFileArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getImageGroupFile()
    }

    override fun onResume() {
        super.onResume()
        observeListGroupData()
    }

    private fun getDataFromArgs(){
        when(args.type){
            Constant.TYPE_PICTURE -> {}
            Constant.TYPE_AUDIOS -> {}
            Constant.TYPE_VIDEOS -> {}
            Constant.TYPE_DOCUMENT -> {}
            else -> {

            }
        }
    }


    private fun initView() {
        initRecyclerView()
    }
    private fun getImageGroupFile(){
        viewModel.getListFolderImage(requireContext())
    }

    private fun initRecyclerView() {
        adapter = AdapterGroupItem()
        binding.rcvGroupItem.adapter = adapter
        val gridLayoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        binding.rcvGroupItem.layoutManager = gridLayoutManager
    }
    private fun observeListGroupData(){
        viewModel.listGroupData.observe(viewLifecycleOwner){
            adapter.setData(it)
        }
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}