package com.fhanjacson.amca.wheels


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
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
        bottomNavigationItemView2.waitUntilVisible(5000).perform(click())

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

        val chip = onView(
            allOf(
                withText("KIA"),
                childAtPosition(
                    allOf(
                        withId(R.id.brand_chipGroup),
                        childAtPosition(
                            withId(R.id.brandFilterLayout),
                            1
                        )
                    ),
                    7
                )
            )
        )
        chip.perform(scrollTo(), click())

        val materialButton4 = onView(
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
        materialButton4.perform(click())

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
        cardView.waitUntilVisible(5000).perform(click())


        val materialButton5 = onView(
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
        materialButton5.perform(click())

        val materialButton6 = onView(
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
        materialButton6.perform(scrollTo(), click())

        val checkableImageButton = onView(
            allOf(
                withId(R.id.mtrl_picker_header_toggle), withContentDescription("Switch to text input mode"),
                childAtPosition(
                    allOf(
                        withId(R.id.mtrl_picker_header),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        checkableImageButton.waitUntilVisible(5000).perform(click())

        val textInputEditText = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.mtrl_picker_text_input_range_start),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(click())

        val textInputEditText2 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.mtrl_picker_text_input_range_start),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("09/09/20"), closeSoftKeyboard())

        val textInputEditText3 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.mtrl_picker_text_input_range_end),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("11/09/20"), closeSoftKeyboard())

        val textInputEditText4 = onView(
            allOf(
                withText("11/09/20"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.mtrl_picker_text_input_range_end),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(pressImeActionButton())

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
        materialButton7.perform(click())

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
        materialButton8.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.textView3), withText("Payment Complete"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Payment Complete")))

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
        materialButton9.perform(click())

        val textView2 = onView(
            allOf(
                withId(R.id.vehiclename_text), withText("KIA SPORTAGE"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cardView2),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("KIA SPORTAGE")))

        val textView3 = onView(
            allOf(
                withId(R.id.startdate_text), withText("Wed, 9 September 2020"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cardView2),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Wed, 9 September 2020")))

        val textView4 = onView(
            allOf(
                withId(R.id.enddate_text), withText("Mon, 9 November 2020"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cardView2),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Mon, 9 November 2020")))
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
