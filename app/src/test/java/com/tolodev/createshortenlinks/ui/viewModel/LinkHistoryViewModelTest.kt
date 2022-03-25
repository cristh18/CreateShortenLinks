package com.tolodev.createshortenlinks.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tolodev.createshortenlinks.data.toDomainShortenLink
import com.tolodev.createshortenlinks.extensions.readFromJsonFile
import com.tolodev.createshortenlinks.ui.models.GenericItem
import com.tolodev.createshortenlinks.ui.models.UIStatus
import com.tolodev.createshortenlinks.ui.models.toUIShortenLink
import com.tolodev.createshortenlinks.useCases.DeleteAllShortenLinkUseCase
import com.tolodev.createshortenlinks.useCases.GenerateShortenLinkUseCase
import com.tolodev.createshortenlinks.useCases.GetShortenLinkByIdUseCase
import com.tolodev.createshortenlinks.useCases.GetShortenLinkListUseCase
import com.tolodev.createshortenlinks.useCases.SaveShortenLinkUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LinkHistoryViewModelTest {

    @MockK
    private lateinit var generateShortenLinkUseCase: GenerateShortenLinkUseCase

    @MockK
    private lateinit var saveShortenLinkUseCase: SaveShortenLinkUseCase

    @MockK
    private lateinit var getShortenLinkByIdUseCase: GetShortenLinkByIdUseCase

    @MockK
    private lateinit var getShortenLinkListUseCase: GetShortenLinkListUseCase

    @MockK
    private lateinit var deleteAllShortenLinkUseCase: DeleteAllShortenLinkUseCase

    @RelaxedMockK
    private lateinit var observer: Observer<UIStatus<GenericItem>>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `generate shorten link`() {
        val linkId = "70993"
        val originalLink = "https://plugins.jetbrains.com/plugin/10761-detekt"
        val serverShortenLink = readFromJsonFile("link_generator.json")
        serverShortenLink?.let {
            val shortenLink = it.toDomainShortenLink()
            val uiShortenLink = shortenLink.toUIShortenLink()
            every { generateShortenLinkUseCase.invoke(originalLink) } returns flow {
                emit(
                    uiShortenLink
                )
            }
            every { getShortenLinkListUseCase.invoke() } returns listOf(uiShortenLink)
            every { saveShortenLinkUseCase.invoke(uiShortenLink) } returns Unit
            every { getShortenLinkByIdUseCase.invoke(linkId) } returns uiShortenLink

            val viewModel = LinkHistoryViewModel(
                generateShortenLinkUseCase,
                saveShortenLinkUseCase,
                getShortenLinkByIdUseCase,
                getShortenLinkListUseCase,
                deleteAllShortenLinkUseCase
            )
            viewModel.generateShortenLink(originalLink)
            viewModel.uiStatusObserver().observeForever(observer)
            verify { observer.onChanged(UIStatus.Successful(uiShortenLink)) }
        }
    }
}