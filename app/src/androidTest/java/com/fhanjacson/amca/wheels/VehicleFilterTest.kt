package com.fhanjacson.amca.wheels


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class VehicleFilterTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashScreenActivity::class.java)

    @Test
    fun vehicleFilterTest() {
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

        val chip = onView(
            allOf(
                withText("TOYOTA"),
                childAtPosition(
                    allOf(
                        withId(R.id.brand_chipGroup),
                        childAtPosition(
                            withId(R.id.brandFilterLayout),
                            1
                        )
                    ),
                    13
                )
            )
        )
        chip.perform(scrollTo(), click())

        val chip2 = onView(
            allOf(
                withText("AT"),
                childAtPosition(
                    allOf(
                        withId(R.id.transmission_chipGroup),
                        childAtPosition(
                            withId(R.id.transmissionFilterLayout),
                            1
                        )
                    ),
                    1
                )
            )
        )
        chip2.perform(scrollTo(), click())

        val chip3 = onView(
            allOf(
                withText("Sedan"),
                childAtPosition(
                    allOf(
                        withId(R.id.type_chipGroup),
                        childAtPosition(
                            withId(R.id.typeFilterLayout),
                            1
                        )
                    ),
                    1
                )
            )
        )
        chip3.perform(scrollTo(), click())

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

        val chip4 = onView(
            allOf(
                withText("Sedan"),
                childAtPosition(
                    allOf(
                        withId(R.id.type_chipGroup),
                        childAtPosition(
                            withId(R.id.typeFilterLayout),
                            1
                        )
                    ),
                    1
                )
            )
        )
        chip4.perform(scrollTo(), click())

        val chip5 = onView(
            allOf(
                withText("TOYOTA"),
                childAtPosition(
                    allOf(
                        withId(R.id.brand_chipGroup),
                        childAtPosition(
                            withId(R.id.brandFilterLayout),
                            1
                        )
                    ),
                    13
                )
            )
        )
        chip5.perform(scrollTo(), click())

        val chip6 = onView(
            allOf(
                withText("AT"),
                childAtPosition(
                    allOf(
                        withId(R.id.transmission_chipGroup),
                        childAtPosition(
                            withId(R.id.transmissionFilterLayout),
                            1
                        )
                    ),
                    1
                )
            )
        )
        chip6.perform(scrollTo(), click())

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
                withId(R.id.vehicleName), withText("TOYOTA VIOS"),
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
        textView.check(matches(withText("TOYOTA VIOS")))

//        val textView2 = onView(
//            allOf(
//                withId(R.id.vehiclePrice), withText("MYR 296.0"),
//                childAtPosition(
//                    childAtPosition(
//                        withId(R.id.vehicleCard),
//                        0
//                    ),
//                    2
//                ),
//                isDisplayed()
//            )
//        )
//        textView2.check(matches(withText("MYR 296.0")))

        val floatingActionButton2 = onView(
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
        floatingActionButton2.perform(click())

        val chip7 = onView(
            allOf(
                withText("NISSAN"),
                childAtPosition(
                    allOf(
                        withId(R.id.brand_chipGroup),
                        childAtPosition(
                            withId(R.id.brandFilterLayout),
                            1
                        )
                    ),
                    10
                )
            )
        )
        chip7.perform(scrollTo(), click())

        val chip8 = onView(
            allOf(
                withText("MT"),
                childAtPosition(
                    allOf(
                        withId(R.id.transmission_chipGroup),
                        childAtPosition(
                            withId(R.id.transmissionFilterLayout),
                            1
                        )
                    ),
                    2
                )
            )
        )
        chip8.perform(scrollTo(), click())

        val materialButton3 = onView(
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
        materialButton3.perform(click())

        val textView3 = onView(
            allOf(
                withId(R.id.textView), withText("No results found."),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("No results found.")))

        val floatingActionButton3 = onView(
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
        floatingActionButton3.perform(click())

        val chip9 = onView(
            allOf(
                withText("JEEP"),
                childAtPosition(
                    allOf(
                        withId(R.id.brand_chipGroup),
                        childAtPosition(
                            withId(R.id.brandFilterLayout),
                            1
                        )
                    ),
                    6
                )
            )
        )
        chip9.perform(scrollTo(), click())

        val chip10 = onView(
            allOf(
                withText("Jeep"),
                childAtPosition(
                    allOf(
                        withId(R.id.type_chipGroup),
                        childAtPosition(
                            withId(R.id.typeFilterLayout),
                            1
                        )
                    ),
                    6
                )
            )
        )
        chip10.perform(scrollTo(), click())

        val chip11 = onView(
            allOf(
                withText("All Transmissions"),
                childAtPosition(
                    allOf(
                        withId(R.id.transmission_chipGroup),
                        childAtPosition(
                            withId(R.id.transmissionFilterLayout),
                            1
                        )
                    ),
                    0
                )
            )
        )
        chip11.perform(scrollTo(), click())

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

        val textView4 = onView(
            allOf(
                withId(R.id.vehicleName), withText("JEEP WRANGLER"),
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
        textView4.check(matches(withText("JEEP WRANGLER")))
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
