package com.tolodev.createshortenlinks.ui.viewModel

import androidx.annotation.CheckResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tolodev.createshortenlinks.ui.models.GenericItem
import com.tolodev.createshortenlinks.useCases.GetShortenLinkListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LinkDetailViewModel @Inject constructor(getShortenLinkListUseCase: GetShortenLinkListUseCase) :
    ViewModel() {

    private val _loadShortenLinkList = MutableLiveData<List<GenericItem>>()

    init {
        _loadShortenLinkList.value = getShortenLinkListUseCase.invoke()
    }

    @CheckResult
    fun loadShortenLinkListObserver(): LiveData<List<GenericItem>> = _loadShortenLinkList
}