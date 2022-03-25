package com.tolodev.createshortenlinks.ui.viewModel

import android.webkit.URLUtil
import androidx.annotation.CheckResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tolodev.createshortenlinks.ui.models.GenericItem
import com.tolodev.createshortenlinks.ui.models.UIStatus
import com.tolodev.createshortenlinks.useCases.DeleteAllShortenLinkUseCase
import com.tolodev.createshortenlinks.useCases.GenerateShortenLinkUseCase
import com.tolodev.createshortenlinks.useCases.GetShortenLinkByIdUseCase
import com.tolodev.createshortenlinks.useCases.GetShortenLinkListUseCase
import com.tolodev.createshortenlinks.useCases.SaveShortenLinkUseCase
import com.tolodev.createshortenlinks.utils.getHttpErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val KEY_LINK_LIST = "key_link_list"

@HiltViewModel
class LinkHistoryViewModel @Inject constructor(
    private val generateShortenLinkUseCase: GenerateShortenLinkUseCase,
    private val saveShortenLinkUseCase: SaveShortenLinkUseCase,
    private val getShortenLinkByIdUseCase: GetShortenLinkByIdUseCase,
    private val getShortenLinkListUseCase: GetShortenLinkListUseCase,
    private val deleteAllShortenLinkUseCase: DeleteAllShortenLinkUseCase
) :
    ViewModel() {

    private val _uiStatus = MutableLiveData<UIStatus<GenericItem>>()

    private val _loadShortenLinkList = MutableLiveData<List<GenericItem>>()

    init {
        _loadShortenLinkList.postValue(getShortenLinkListUseCase.invoke())
    }

    fun generateShortenLink(link: String) {
        viewModelScope.launch {
            getShortenLinkFlow(link).collect {
                saveShortenLinkUseCase.invoke(it)
                getShortenLinkByIdUseCase.invoke(it.id)?.let { generatedShortenLink ->
                    _uiStatus.value = UIStatus.Successful(generatedShortenLink)
                } ?: run {
                    _uiStatus.value = UIStatus.Error("Link not found")
                }
            }
        }
    }

    private fun getShortenLinkFlow(productName: String): Flow<GenericItem.UIShortenLink> =
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

    fun setInstanceState(): MutableMap<String, Any?> {
        val saveStateData = mutableMapOf<String, Any?>()
        saveStateData[KEY_LINK_LIST] = getShortenLinkListUseCase.invoke()
        return saveStateData
    }

    fun restoreInstanceState(instanceState: Map<String, Any?>) {
        if (instanceState[KEY_LINK_LIST] is List<*>) {
            val shortenLinkList = instanceState[KEY_LINK_LIST] as List<*>
            if (shortenLinkList.isNotEmpty()) {
                deleteAllShortenLinkUseCase.invoke()
                shortenLinkList.forEach {
                    if (it is GenericItem.UIShortenLink) {
                        saveShortenLinkUseCase.invoke(it)
                    }
                }
                _loadShortenLinkList.postValue(getShortenLinkListUseCase.invoke())
            }
        }
    }

    @CheckResult
    fun uiStatusObserver(): LiveData<UIStatus<GenericItem>> = _uiStatus

    @CheckResult
    fun loadShortenLinkListObserver(): LiveData<List<GenericItem>> = _loadShortenLinkList
}