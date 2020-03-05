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
import java.util.concurrent.ThreadLocalRandom

@LargeTest
@RunWith(AndroidJUnit4::class)
class ProfileUpdateTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashScreenActivity::class.java)

    @Test
    fun profileUpdateTest() {
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

        val constraintLayout = onView(
            allOf(
                withId(R.id.accountSettingItemLayout),
                childAtPosition(
                    allOf(
                        withId(R.id.accountRecyclerview),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        constraintLayout.perform(click())

        val newName = "DRAGON SLAYER" + ThreadLocalRandom.current().nextInt(10,100).toString()

        val textInputEditText = onView(
            withId(R.id.profileFullNameText)
        )

        textInputEditText.perform(scrollTo(), replaceText(newName))



        val materialButton4 = onView(
            allOf(
                withId(R.id.saveProfileButton), withText("SAVE PROFILE"),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayout2),
                        childAtPosition(
                            withId(R.id.frameLayout),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        val textView2 = onView(
            allOf(
                withId(R.id.accountName),
                isDisplayed()
            )
        )
        textView2.waitUntilVisible(3000).check(matches(withText(newName)))

        val constraintLayout2 = onView(
            allOf(
                withId(R.id.accountSettingItemLayout),
                childAtPosition(
                    allOf(
                        withId(R.id.accountRecyclerview),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        constraintLayout2.perform(click())

        val materialButton5 = onView(
            allOf(
                withId(R.id.cancelButton), withText("Cancel"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.custom),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())

        val constraintLayout3 = onView(
            allOf(
                withId(R.id.accountSettingItemLayout),
                childAtPosition(
                    allOf(
                        withId(R.id.accountRecyclerview),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            1
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        constraintLayout3.perform(click())

        val materialButton6 = onView(
            allOf(
                withId(R.id.cancelButton), withText("Cancel"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.custom),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton6.perform(click())

        val constraintLayout4 = onView(
            allOf(
                withId(R.id.accountSettingItemLayout),
                childAtPosition(
                    allOf(
                        withId(R.id.accountRecyclerview),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            1
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        constraintLayout4.perform(click())

        val materialButton7 = onView(
            allOf(
                withId(android.R.id.button2), withText("Cancel"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    2
                )
            )
        )
        materialButton7.perform(scrollTo(), click())
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
