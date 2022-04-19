package com.tolodev.createshortenlinks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.tolodev.createshortenlinks.databinding.FragmentLinkDetailBinding
import com.tolodev.createshortenlinks.ui.adapter.LinkHistoryAdapter
import com.tolodev.createshortenlinks.ui.viewModel.LinkDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LinkDetailFragment : Fragment() {

    private var binding: FragmentLinkDetailBinding? = null

    private val viewModel by viewModels<LinkDetailViewModel>()

    private val linkHistoryAdapter: LinkHistoryAdapter by lazy {
        LinkHistoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLinkDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribe()
    }

    private fun initView() {
        binding?.recyclerViewLinkHistory?.adapter = linkHistoryAdapter
        binding?.recyclerViewLinkHistory?.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun subscribe() {
        with(viewModel) {
            loadShortenLinkListObserver().observe(viewLifecycleOwner) {
                linkHistoryAdapter.setLinks(it)
            }
        }
    }
}