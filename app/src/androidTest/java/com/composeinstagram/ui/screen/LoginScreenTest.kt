package com.composeinstagram.ui.screen


import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.composeinstagram.MainActivity
import com.composeinstagram.R
import com.composeinstagram.helper.FakeIGClientHelper
import com.composeinstagram.helper.IGClientHelperInterface
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class LoginScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var igClientHelper: IGClientHelperInterface

    private val fakeIGClientHelper get() = igClientHelper as FakeIGClientHelper

    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    private var navToMainInvoked = false

    private fun setContent() {
        composeTestRule.activity.setContent {
            LoginScreen(navToMain = {
                navToMainInvoked = true
            })
        }
    }

    @Before
    fun setUp() {
        hiltRule.inject()
        setContent()
    }

    @After
    fun tearDown() {
        navToMainInvoked = false
    }

    /**
     * Success login then should navigate to the main screen
     * */
    @Test
    fun t1() {

        composeTestRule.onNodeWithTag("UserNameTextField").performTextInput("userName")
        composeTestRule.onNodeWithTag("UserNameTextField").assert(hasText("userName"))
        composeTestRule.onNodeWithTag("PasswordTextField").performTextInput("pass")
        composeTestRule.onNodeWithTag("PasswordTextField").assert(hasText("pass"))

        composeTestRule.onNodeWithTag("LoginButton").performClick()
        composeTestRule.waitForIdle()
        assertThat(navToMainInvoked).isTrue()

    }


    /**
     * Failure login then should show snack bar
     * */
    @Test
    fun t2() {
        fakeIGClientHelper.shouldLoginReturnNull = true
        composeTestRule.onNodeWithTag("UserNameTextField").performTextInput("userName")
        composeTestRule.onNodeWithTag("UserNameTextField").assert(hasText("userName"))
        composeTestRule.onNodeWithTag("PasswordTextField").performTextInput("pass")
        composeTestRule.onNodeWithTag("PasswordTextField").assert(hasText("pass"))

        composeTestRule.onNodeWithTag("LoginButton").performClick()

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.login_failed))
            .assertIsDisplayed()
    }

    /**
     * Failure login then should show snack bar,and after screen rotated not shown snack bar again
     * */
    @Test
    fun t3() {
        fakeIGClientHelper.shouldLoginReturnNull = true
        composeTestRule.onNodeWithTag("UserNameTextField").performTextInput("userName")
        composeTestRule.onNodeWithTag("UserNameTextField").assert(hasText("userName"))
        composeTestRule.onNodeWithTag("PasswordTextField").performTextInput("pass")
        composeTestRule.onNodeWithTag("PasswordTextField").assert(hasText("pass"))

        composeTestRule.onNodeWithTag("LoginButton").performClick()

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.login_failed))
            .assertIsDisplayed()

        device.setOrientationLeft()

        //set content again
        setContent()
        composeTestRule.onNodeWithTag("UserNameTextField").assert(hasText("userName"))

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.login_failed))
            .assertDoesNotExist()
    }
}