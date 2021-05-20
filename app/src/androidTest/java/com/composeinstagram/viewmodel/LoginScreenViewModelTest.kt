package com.composeinstagram.viewmodel

import com.composeinstagram.helper.FakeIGClientHelper
import com.composeinstagram.helper.IGClientHelperInterface
import com.composeinstagram.wrapper.LoginState
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
class LoginScreenViewModelTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var igClientHelper: IGClientHelperInterface

    private val fakeIGClientHelper get() = igClientHelper as FakeIGClientHelper

    @Inject
    @Named("TestDispatcher")
    lateinit var testCoroutineDispatcher: TestCoroutineDispatcher

    lateinit var viewModel: LoginScreenViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel =
            LoginScreenViewModel(testCoroutineDispatcher, testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        testCoroutineDispatcher.cleanupTestCoroutines()
    }



}