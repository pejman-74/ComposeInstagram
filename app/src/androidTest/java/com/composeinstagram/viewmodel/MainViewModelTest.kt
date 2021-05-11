package com.composeinstagram.viewmodel


import com.composeinstagram.helper.FakeIGClientHelper
import com.composeinstagram.helper.IGClientHelperInterface
import com.composeinstagram.wrapper.CachedIGClientState
import com.composeinstagram.wrapper.LoginState
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var igClientHelper: IGClientHelperInterface

    private val fakeIGClientHelper get() = igClientHelper as FakeIGClientHelper

    @Inject
    @Named("IODispatcher")
    lateinit var ioDispatcher: CoroutineDispatcher

    @Inject
    @Named("MainDispatcher")
    lateinit var mainDispatcher: CoroutineDispatcher

    lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = MainViewModel(igClientHelper, mainDispatcher, ioDispatcher)
    }


    @After
    fun tearDown() {
        (ioDispatcher as TestCoroutineDispatcher).cleanupTestCoroutines()
        (mainDispatcher as TestCoroutineDispatcher).cleanupTestCoroutines()
    }

    /*
    * cachedIGClientState should be Success when the user login before
    * */
    @Test
    fun t1() {
        assertThat(viewModel.cachedIGClientState is CachedIGClientState.Success).isTrue()
    }

    /*
    * cachedIGClientState should be Fail when the user NOT login before
    * */
    @Test
    fun t2() {
        fakeIGClientHelper.shouldCachedIGClientReturnNull = true
        viewModel = MainViewModel(igClientHelper, mainDispatcher, ioDispatcher)
        assertThat(viewModel.cachedIGClientState is CachedIGClientState.Fail).isTrue()
    }

    /*
    * loginState should be Success when the user login
    * */
    @Test
    fun t3() {
        viewModel.login("test", "1234")
        assertThat(viewModel.loginState is LoginState.Success).isTrue()
    }

    /*
    * loginState should be Fail when the user login failed
    * */
    @Test
    fun t4() {
        fakeIGClientHelper.shouldLoginReturnNull = true
        viewModel.login("test", "1234")
        assertThat(viewModel.loginState is LoginState.Fail).isTrue()
    }

}