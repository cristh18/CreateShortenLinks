package com.tolodev.createshortenlinks.ui.viewModel

import android.webkit.URLUtil
import androidx.annotation.CheckResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tolodev.createshortenlinks.ui.models.UIShortenLink
import com.tolodev.createshortenlinks.ui.models.UIStatus
import com.tolodev.createshortenlinks.useCases.GenerateShortenLinkUseCase
import com.tolodev.createshortenlinks.utils.getHttpErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LinkHistoryViewModel @Inject constructor(private val generateShortenLinkUseCase: GenerateShortenLinkUseCase) :
    ViewModel() {

    private val _uiStatus = MutableLiveData<UIStatus<UIShortenLink>>()

    fun generateShortenLink(link: String) {
        viewModelScope.launch {
            getShortenLinkFlow(link).collect {
                _uiStatus.value = UIStatus.Successful(it)
            }
        }
    }

    private fun getShortenLinkFlow(productName: String): Flow<UIShortenLink> =
        generateShortenLinkUseCase.invoke(productName)
            .onStart { _uiStatus.postValue(UIStatus.Loading(true)) }
            .flowOn(Dispatchers.IO)
            .catch { showError(it) }

    private fun showError(throwable: Throwable) {
        val errorMessage = getHttpErrorMessage(throwable)
        Timber.e(throwable, errorMessage)
        _uiStatus.value = UIStatus.Error(errorMessage, throwable)
    }

    fun isValidUrl(url: String): Boolean {
        return url.isNotEmpty() && URLUtil.isValidUrl(url)
    }

    @CheckResult
    fun uiStatusObserver(): LiveData<UIStatus<UIShortenLink>> = _uiStatus

}