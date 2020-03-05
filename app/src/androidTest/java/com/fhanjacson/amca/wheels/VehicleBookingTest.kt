package com.fhanjacson.amca.wheels


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class VehicleBookingTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashScreenActivity::class.java)

    @Test
    fun vehicleBookingTest() {
        FirebaseAuth.getInstance().signOut()
        val bottomNavigationItemView = onView(
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
        bottomNavigationItemView.perform(click())

        val materialButton = onView(
            allOf(
                withId(R.id.loginOrSignupButton_account), withText("Login or Sign Up"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.noUserLayout_account),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val materialButton2 = onView(
            allOf(
                withId(R.id.getStarted_button), withText("Login or Sign Up"),
                childAtPosition(
                    allOf(
                        withId(R.id.getstarted_bottomlayout),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            3
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.login_email),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    0
                )
            )
        )
        appCompatEditText.perform(scrollTo(), replaceText("test@mail.com"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.login_password),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    1
                )
            )
        )
        appCompatEditText2.perform(scrollTo(), replaceText("password"), closeSoftKeyboard())

        val materialButton3 = onView(
            allOf(
                withId(R.id.loginButton), withText("LOG IN"),
                childAtPosition(
                    allOf(
                        withId(R.id.bottomLayout),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())


        onView(withId(R.id.accountEmail)).waitUntilVisible(3000).check(matches(withText("test@mail.com")))

        val bottomNavigationItemView2 = onView(
            allOf(
                withId(R.id.search_navigation), withContentDescription("Search"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.vehicleName), withText("PERODUA BEZZA"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.vehicleCard),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("PERODUA BEZZA")))

        val cardView = onView(
            allOf(
                withId(R.id.vehicleCard),
                childAtPosition(
                    allOf(
                        withId(R.id.vehicleRecyclerview),
                        childAtPosition(
                            withId(R.id.fragment_search_layout),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        cardView.perform(click())

        val materialButton4 = onView(
            allOf(
                withId(R.id.checkoutButton), withText("CHECKOUT"),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayout),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        val materialButton5 = onView(
            allOf(
                withId(R.id.changeDateButton), withText("Change"),
                childAtPosition(
                    allOf(
                        withId(R.id.tripDateLayout),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            2
                        )
                    ),
                    1
                )
            )
        )
        materialButton5.perform(scrollTo(), click())

//        val frameLayout = onView(
//            allOf(
//                withId(R.id.mtrl_calendar_frame),
//                childAtPosition(
//                    allOf(
//                        withId(R.id.mtrl_calendar_main_pane),
//                        childAtPosition(
//                            IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
//                            1
//                        )
//                    ),
//                    0
//                ),
//                isDisplayed()
//            )
//        )
//        frameLayout.waitUntilVisible(3000).check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withText("19"), withContentDescription("Thu, Mar 19"),
                isDisplayed()
            )
        )
        textView2.check(matches(isDisplayed()))

        textView2.waitUntilVisible(3000).perform(click())


//        val materialTextView = onData(anything())
//            .inAdapterView(
//                allOf(
//                    withId(R.id.month_grid),
//                    childAtPosition(
//                        withClassName(`is`("android.widget.LinearLayout")),
//                        1
//                    )
//                )
//            )
//            .atPosition(18)
//        materialTextView.perform(click())
//
        val textView3 = onView(
            allOf(
                withText("23"), withContentDescription("Mon, Mar 23"),
                isDisplayed()
            )
        )
        textView3.check(matches(isDisplayed()))
        textView3.waitUntilVisible(3000).perform(click())

        val materialButton7 = onView(
            allOf(
                withId(R.id.confirm_button), withText("OK"),
                childAtPosition(
                    allOf(
                        withId(R.id.date_picker_actions),
                        childAtPosition(
                            withId(R.id.mtrl_calendar_main_pane),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton7.waitUntilVisible(2000).perform(click())

        val materialButton8 = onView(
            allOf(
                withId(R.id.proceed_button), withText("PROCEED"),
                childAtPosition(
                    allOf(
                        withId(R.id.checkout_bottomLayout),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton8.waitUntilVisible(3000).perform(click())

        val materialButton9 = onView(
            allOf(
                withId(R.id.mytrip_button), withText("My Trips"),
                childAtPosition(
                    allOf(
                        withId(R.id.cardView),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton9.waitUntilVisible(5000).perform(click())

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
