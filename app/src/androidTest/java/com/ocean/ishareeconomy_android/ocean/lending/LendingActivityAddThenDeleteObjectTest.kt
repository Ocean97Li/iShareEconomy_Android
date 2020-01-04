package com.ocean.ishareeconomy_android.ocean.lending

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.ocean.ishareeconomy_android.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LendingActivityAddThenDeleteObjectTest {

    @Rule
    @JvmField
    var loginTestRule: LendingTestRule = LendingTestRule()

    @Test
    fun lendingActivityAddThenDeleteObjectTest() {
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

        val materialButtonLogin = onView(
            allOf(
                withId(R.id.login_btn), withText("Login"),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButtonLogin.perform(ViewActions.click())

        Thread.sleep(100)

        val actionMenuItemView = onView(
            allOf(
                withId(R.id.add_lending_object_button), withText("Share new"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(ViewActions.click())

        val appCompatImageButton = onView(
            allOf(
                withId(R.id.tool_button),
                withContentDescription("The button for type tool"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.type_buttons),
                        0
                    ),
                    0
                )
            )
        )
        appCompatImageButton.perform(ViewActions.scrollTo(), ViewActions.click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.name_input),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("android.widget.ScrollView")),
                        0
                    ),
                    4
                )
            )
        )
        appCompatEditText.perform(
            ViewActions.scrollTo(),
            replaceText("object title"),
            closeSoftKeyboard()
        )

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.name_input), withText("object title"),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("android.widget.ScrollView")),
                        0
                    ),
                    4
                )
            )
        )
        appCompatEditText2.perform(ViewActions.pressImeActionButton())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.description_input),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("android.widget.ScrollView")),
                        0
                    ),
                    6
                )
            )
        )
        appCompatEditText3.perform(
            ViewActions.scrollTo(),
            replaceText("object desc"),
            closeSoftKeyboard()
        )

        val button = onView(
            allOf(
                withId(R.id.button2),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.ScrollView::class.java),
                        0
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        button.check(ViewAssertions.matches(isDisplayed()))

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.description_input), withText("object desc"),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("android.widget.ScrollView")),
                        0
                    ),
                    6
                )
            )
        )
        appCompatEditText4.perform(ViewActions.pressImeActionButton())

        val materialButton = onView(
            allOf(
                withId(R.id.button2), withText("Share"),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("android.widget.ScrollView")),
                        0
                    ),
                    7
                )
            )
        )
        materialButton.perform(ViewActions.scrollTo(), ViewActions.click())

        Thread.sleep(100)

        val objectCard = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recycler_view_lending),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        objectCard.check(ViewAssertions.matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.title_text), withText("object title"),
                isDescendantOfA(withId(R.id.recycler_view_lending)),
                childAtPosition(
                    allOf(
                        withId(R.id.lend_object_main_body),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(ViewAssertions.matches(withText("object title")))

        val textView2 = onView(
            allOf(
                withId(R.id.description_text), withText("object desc"),
                isDescendantOfA(withId(R.id.recycler_view_lending)),
                childAtPosition(
                    allOf(
                        withId(R.id.lend_object_main_body),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView2.check(ViewAssertions.matches(withText("object desc")))

        val textView3 = onView(
            allOf(
                withId(R.id.users_number), withText("0"),
                childAtPosition(
                    allOf(
                        isDescendantOfA(withId(R.id.recycler_view_lending)),
                        withId(R.id.linearLayout2),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                            2
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView3.check(ViewAssertions.matches(withText("0")))

        val actionMenuItemView2 = onView(
            allOf(
                withId(R.id.remove_lending_object_button),
                isDisplayed()
            )
        )
        actionMenuItemView2.perform(ViewActions.click())

        Thread.sleep(100)

        objectCard.perform(ViewActions.swipeRight())

        Thread.sleep(100)

        objectCard.check(ViewAssertions.doesNotExist())

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
