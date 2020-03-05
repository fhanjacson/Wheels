package com.fhanjacson.amca.wheels


import android.view.View
import android.view.ViewGroup
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
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class FirebaseLoginTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashScreenActivity::class.java)

    @Test
    fun firebaseLoginTest() {
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

        val email = "test@mail.com"
        val pass = "password"

        appCompatEditText.perform(scrollTo(), replaceText(email), closeSoftKeyboard())

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
        appCompatEditText2.perform(scrollTo(), replaceText(pass), closeSoftKeyboard())

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

        val textView = onView(
            allOf(
                withId(R.id.accountEmail),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.accountCard),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        textView.waitUntilVisible(5000).check(matches(withText(email)))

        val textView2 = onView(
            allOf(
                withId(R.id.accountsetting_textview), withText("Change Password"),
                childAtPosition(
                    allOf(
                        withId(R.id.accountSettingItemLayout),
                        childAtPosition(
                            withId(R.id.accountRecyclerview),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Change Password")))
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
