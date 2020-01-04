package com.ocean.ishareeconomy_android.ocean.login

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.ocean.ishareeconomy_android.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginActivityNoUsernameTest {

    @Rule
    @JvmField
    var loginTestRule: LoginTestRule = LoginTestRule()

    @Test
    fun loginActivityNoPasswordTest() {
        val textInputEditText = onView(
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
        textInputEditText.perform(replaceText("test"), closeSoftKeyboard())

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

        val textView = onView(
            allOf(
                withId(R.id.textinput_error), withText("Username can't be empty"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Username can't be empty")))

        val editText = onView(
            allOf(
                withId(R.id.password_input), withText("test"),
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
        editText.check(matches(withText("test")))

        val textView2 = onView(
            allOf(
                withId(R.id.textinput_error), withText("Password can't be empty"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView2.check(doesNotExist())
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
}
