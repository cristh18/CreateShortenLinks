package com.tolodev.createshortenlinks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.tolodev.createshortenlinks.R
import com.tolodev.createshortenlinks.databinding.FragmentLinkHistoryBinding
import com.tolodev.createshortenlinks.ui.adapter.LinkHistoryAdapter
import com.tolodev.createshortenlinks.ui.models.UIShortenLink
import com.tolodev.createshortenlinks.ui.models.UIStatus
import com.tolodev.createshortenlinks.ui.viewModel.LinkHistoryViewModel
import com.tolodev.createshortenlinks.ui.views.LinkGeneratorLoader
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LinkHistoryFragment : Fragment() {

    private var binding: FragmentLinkHistoryBinding? = null

    private val viewModel by viewModels<LinkHistoryViewModel>()

    private var defaultLoader: LinkGeneratorLoader? = null

    private val linkHistoryAdapter: LinkHistoryAdapter by lazy {
        LinkHistoryAdapter {
            showLinkDetail(
                it
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLinkHistoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        subscribe()
    }

    private fun subscribe() {
        viewModel.uiStatusObserver().observe(viewLifecycleOwner, ::handleUIStatus)
    }

    private fun initListeners() {
        binding?.imageButtonGenerate?.setOnClickListener {
            generateShortLink()
        }
    }

    private fun generateShortLink() {
        val url = binding?.editTextTypeLink?.text.toString()
        val isValidUrl = viewModel.isValidUrl(url)
        if (isValidUrl) {
            viewModel.generateShortenLink(url)
        }
        handleFormUI(isValidUrl)
    }

    private fun handleUIStatus(uiStatus: UIStatus<UIShortenLink>) {
        when (uiStatus) {
            is UIStatus.Successful -> showLinks(uiStatus.value)
            is UIStatus.Loading -> showLoading(uiStatus.show)
            is UIStatus.Error -> showError(uiStatus)
        }
    }

    private fun showLinks(uiShortenLink: UIShortenLink) {
        showLoading(false)
        binding?.recyclerViewLinkHistory?.adapter = linkHistoryAdapter
        binding?.recyclerViewLinkHistory?.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        linkHistoryAdapter.addLink(uiShortenLink)

//        binding?.recyclerViewLinkHistory?.isVisible = products.isNotEmpty()
//        binding?.groupLinks?.isVisible = products.isEmpty()
    }

    private fun handleFormUI(validUrlOrFocus: Boolean) {
        context?.let {
            binding?.editTextTypeLink?.let {editTextTypeLink ->
                with(editTextTypeLink){
                    if (validUrlOrFocus) {
                        setHintTextColor(
                            ContextCompat.getColor(
                                it,
                                R.color.light_gray
                            )
                        )
                        hint = getString(R.string.copy_type_link_hint)
                        text = null
                        background = ContextCompat.getDrawable(
                            it,
                            R.drawable.bg_input_box
                        )
                    } else {
                        background = ContextCompat.getDrawable(
                            it,
                            R.drawable.bg_outlined_input_box
                        )
                        text = null
                        setHintTextColor(ContextCompat.getColor(this@MainActivity, R.color.red))
                        hint = getString(R.string.copy_type_link)
                        clearFocus()
                        binding?.imageButtonGenerate?.requestFocus()
                    }
                }
            }
        }
    }

    private fun showError(uiStatus: UIStatus.Error) {
        showLoading(false)
        Toast.makeText(context, uiStatus.msg, Toast.LENGTH_LONG).show()
    }

    private fun showLinkDetail(linkId: String) {
        Timber.e("Show product detail")
    }

    private fun showLoading(showing: Boolean) {
        initLoading()
        Timber.d("Show loading: ".plus(showing))
        if (showing) {
            defaultLoader!!.showProgressBar()
        } else {
            defaultLoader!!.hideProgressBar()
        }
    }

    private fun initLoading() {
        if (defaultLoader == null) {
            val activity = requireActivity()
            defaultLoader = LinkGeneratorLoader(activity)
            activity.addContentView(
                defaultLoader,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
    }
}