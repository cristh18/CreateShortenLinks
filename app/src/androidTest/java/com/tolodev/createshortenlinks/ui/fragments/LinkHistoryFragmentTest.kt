package com.tolodev.createshortenlinks.ui.fragments

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tolodev.createshortenlinks.R
import com.tolodev.createshortenlinks.data.repositories.LocalLinkGeneratorRepository
import com.tolodev.createshortenlinks.data.repositories.MockRemoteLinkGeneratorRepository
import com.tolodev.createshortenlinks.data.server.models.LinkRequest
import com.tolodev.createshortenlinks.ui.activities.MainActivity
import com.tolodev.createshortenlinks.util.generateShortenLinkBlocking
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LinkHistoryFragmentTest {

    private lateinit var remoteRepository: MockRemoteLinkGeneratorRepository

    @Inject
    lateinit var localRepository: LocalLinkGeneratorRepository

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        remoteRepository = MockRemoteLinkGeneratorRepository()

        hiltRule.inject()
    }

    @Test
    fun showShortenLinks() {

        val originalLink = "https://plugins.jetbrains.com/plugin/10761-detekt"
        val serverShortenLink =
            remoteRepository.generateShortenLinkBlocking(LinkRequest(originalLink))
        localRepository.saveShortenLink(serverShortenLink)

        launchActivity()

        onView(withId(R.id.editText_type_link)).check(
            matches(withText(""))
        )

        onView(withId(R.id.recyclerView_link_history)).check(matches(isDisplayed()))
        Thread.sleep(1000)
    }

    @Test
    fun generateAndShowShortenLink() {

        val originalLink = "https://plugins.jetbrains.com/plugin/10761-detekt"

        launchActivity()
        // launchFragmentInHiltContainer<LinkHistoryFragment>(null, R.style.Theme_CreateShortenLinks)

        onView(withId(R.id.editText_type_link)).perform(typeText(originalLink))
        onView(withId(R.id.imageButton_generate_shorten_link)).check(
            matches(
                withEffectiveVisibility(
                    Visibility.VISIBLE
                )
            )
        )
        onView(withId(R.id.imageView_illustration)).check(matches(isDisplayed()))
        onView(withId(R.id.imageButton_generate_shorten_link)).perform(click())

        onView(withId(R.id.editText_type_link)).check(
            matches(withText(""))
        )

        Thread.sleep(5000)
        onView(withId(R.id.recyclerView_link_history)).check(matches(isDisplayed()))
    }

    @Test
    fun invalidTypedLink() {

        launchActivity()

        onView(withId(R.id.editText_type_link)).perform(typeText("originalLink"))

        onView(withId(R.id.imageButton_generate_shorten_link)).check(
            matches(
                withEffectiveVisibility(
                    Visibility.VISIBLE
                )
            )
        )
        onView(withId(R.id.imageButton_generate_shorten_link)).perform(click())
        onView(withId(R.id.editText_type_link)).check(matches(withHint("Please add a link here")))
        onView(withId(R.id.imageView_illustration)).check(matches(isDisplayed()))

        Thread.sleep(1000)
    }

    private fun launchActivity(): ActivityScenario<MainActivity>? {
        val activityScenario = launch(MainActivity::class.java)
        activityScenario.onActivity { activity ->
            (activity.findViewById(R.id.recyclerView_link_history) as RecyclerView).itemAnimator =
                null
        }
        return activityScenario
    }
}