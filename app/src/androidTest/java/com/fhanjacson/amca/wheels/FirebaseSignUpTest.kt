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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class FirebaseSignUpTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashScreenActivity::class.java)

    @Test
    fun firebaseSignUpTest() {
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

        val tabView = onView(
            allOf(
                withContentDescription("SIGN UP"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.onboardingTablayout),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        tabView.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.signup_fullname),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nestedScrollView),
                        0
                    ),
                    0
                )
            )
        )
        val name = "jake tanwij"
        val email = "jake@email.com"
        val pass = "password"

        appCompatEditText.perform(scrollTo(), replaceText(name), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.signup_email),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nestedScrollView),
                        0
                    ),
                    1
                )
            )
        )
        appCompatEditText2.perform(scrollTo(), replaceText(email), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.signup_password),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nestedScrollView),
                        0
                    ),
                    2
                )
            )
        )
        appCompatEditText3.perform(scrollTo(), replaceText(pass), closeSoftKeyboard())

        val materialButton3 = onView(
            allOf(
                withId(R.id.signup_button), withText("SIGN UP"),
                childAtPosition(
                    allOf(
                        withId(R.id.bottomLayout),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
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

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            user.delete().addOnSuccessListener {

            }
        }
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
