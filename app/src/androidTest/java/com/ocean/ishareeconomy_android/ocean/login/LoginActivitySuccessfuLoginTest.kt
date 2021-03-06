package com.ocean.ishareeconomy_android.ocean.login


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.ocean.ishareeconomy_android.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginActivitySuccessfulLoginTest {

    @Rule
    @JvmField
    var loginTestRule: LoginTestRule = LoginTestRule()

    @Test
    fun loginActivityNoPasswordTest() {
        val textInputEditText = onView(
            allOf(
                withId(R.id.username_input),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.username_inputLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("bobby"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.password_input),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.password_inputLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("mypasswordisbad"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.login_btn), withText("Login"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    @After
    fun cleanUp() {

    }
}
