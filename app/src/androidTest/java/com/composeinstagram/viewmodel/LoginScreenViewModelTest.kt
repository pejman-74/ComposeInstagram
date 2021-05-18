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
            LoginScreenViewModel(igClientHelper, testCoroutineDispatcher, testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    /**
     * loginState should be Success when the user login
     * */
    @Test
    fun t1() = testCoroutineDispatcher.runBlockingTest {
        viewModel.login("test", "1234")
        Truth.assertThat(viewModel.loginState.first() is LoginState.Success).isTrue()
    }

    /**
     * loginState should be Fail when the user login failed
     * */
    @Test
    fun t2() = testCoroutineDispatcher.runBlockingTest {
        fakeIGClientHelper.shouldLoginReturnNull = true
        viewModel.login("test", "1234")
        Truth.assertThat(viewModel.loginState.first() is LoginState.Fail).isTrue()
    }

}