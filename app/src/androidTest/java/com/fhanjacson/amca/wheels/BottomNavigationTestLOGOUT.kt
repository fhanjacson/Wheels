package com.fhanjacson.amca.wheels


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class BottomNavigationTestLOGOUT {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashScreenActivity::class.java)


    @Test
    fun bottomNavigationTest() {

        FirebaseAuth.getInstance().signOut()

        val floatingActionButton = onView(
            allOf(
                withId(R.id.filterFAB),
                childAtPosition(
                    allOf(
                        withId(R.id.fragment_search_layout),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val materialButton = onView(
            allOf(
                withId(R.id.resetFilterButton), withText("RESET FILTER"),
                childAtPosition(
                    allOf(
                        withId(R.id.bottomLayout),
                        childAtPosition(
                            withId(R.id.vehicleFilterLayout),
                            1
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val materialButton2 = onView(
            allOf(
                withId(R.id.viewResultsButton), withText("View Results"),
                childAtPosition(
                    allOf(
                        withId(R.id.bottomLayout),
                        childAtPosition(
                            withId(R.id.vehicleFilterLayout),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val textView = onView(
            allOf(
                withText("Search"),
                childAtPosition(
                    allOf(
                        withId(R.id.action_bar),
                        childAtPosition(
                            withId(R.id.action_bar_container),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Search")))

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.activity_navigation), withContentDescription("Activity"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val textView2 = onView(
            allOf(
                withText("Activities"),
                childAtPosition(
                    allOf(
                        withId(R.id.action_bar),
                        childAtPosition(
                            withId(R.id.action_bar_container),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Activities")))

        val textView3 = onView(
            allOf(
                withId(R.id.textView13), withText("Please login or sign up to continue"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.noUserLayout_activity),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Please login or sign up to continue")))

        val bottomNavigationItemView2 = onView(
            allOf(
                withId(R.id.account_navigation), withContentDescription("Account"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(click())

        val textView4 = onView(
            allOf(
                withText("Account"),
                childAtPosition(
                    allOf(
                        withId(R.id.action_bar),
                        childAtPosition(
                            withId(R.id.action_bar_container),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Account")))

        val textView5 = onView(
            allOf(
                withId(R.id.textView13), withText("Please login or sign up to continue"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.noUserLayout_account),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("Please login or sign up to continue")))
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
