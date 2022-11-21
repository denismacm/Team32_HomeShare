package com.hfad.team32_homeshare;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.hfad.team32_homeshare.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class successfulAcceptResponseTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void successfulAcceptResponse() {
         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(500);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }

        ViewInteraction appCompatEditText = onView(
allOf(withId(R.id.emailEt),
childAtPosition(
childAtPosition(
withId(R.id.emailTil),
0),
0),
isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
allOf(withId(R.id.emailEt),
childAtPosition(
childAtPosition(
withId(R.id.emailTil),
0),
0),
isDisplayed()));
        appCompatEditText2.perform(replaceText("test@email.com"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatEditText3 = onView(
allOf(withId(R.id.passwordEt),
childAtPosition(
childAtPosition(
withId(R.id.passwordTil),
0),
0),
isDisplayed()));
        appCompatEditText3.perform(replaceText("PASSword"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
allOf(withId(R.id.loginBtn), withText("Login"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
5),
isDisplayed()));
        materialButton.perform(click());

         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(7000);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }

        pressBack();

        ViewInteraction bottomNavigationItemView = onView(
allOf(withId(R.id.nav_responses), withContentDescription("Responses"),
childAtPosition(
childAtPosition(
withId(R.id.navigation),
0),
1),
isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction recyclerView = onView(
allOf(withId(R.id.response_1),
childAtPosition(
withId(R.id.swipeRefreshLayoutResponses),
0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction materialButton2 = onView(
allOf(withId(R.id.dismiss), withText("Dismiss"),
childAtPosition(
childAtPosition(
withClassName(is("android.widget.ScrollView")),
0),
14)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction materialButton3 = onView(
allOf(withId(R.id.yesResponse),
childAtPosition(
childAtPosition(
withClassName(is("androidx.cardview.widget.CardView")),
0),
1),
isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction appCompatEditText4 = onView(
allOf(withId(R.id.respondEt),
childAtPosition(
childAtPosition(
withId(R.id.respondTil),
0),
0),
isDisplayed()));
        appCompatEditText4.perform(click());

        ViewInteraction appCompatEditText5 = onView(
allOf(withId(R.id.respondEt),
childAtPosition(
childAtPosition(
withId(R.id.respondTil),
0),
0),
isDisplayed()));
        appCompatEditText5.perform(replaceText("Hey Michael, I would love to live with you next year. Once I finalize"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
allOf(withId(R.id.respondEt), withText("Hey Michael, I would love to live with you next year. Once I finalize"),
childAtPosition(childAtPosition(withId(R.id.respondTil), 0), 0), isDisplayed()));
        appCompatEditText6.perform(click());

        ViewInteraction appCompatEditText7 = onView(
allOf(withId(R.id.respondEt), withText("Hey Michael, I would love to live with you next year. Once I finalize"), childAtPosition(childAtPosition(withId(R.id.respondTil), 0), 0), isDisplayed()));
        appCompatEditText7.perform(replaceText("Hey Michael, I would love to live with you next year. Once I finalize everything I will let you know. Thanks!"));

        ViewInteraction appCompatEditText8 = onView(
allOf(withId(R.id.respondEt), withText("Hey Michael, I would love to live with you next year. Once I finalize everything I will let you know. Thanks!"), childAtPosition(childAtPosition(withId(R.id.respondTil), 0), 0), isDisplayed()));
        appCompatEditText8.perform(closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(allOf(withId(R.id.submitMessage), withText("Accept Response"), childAtPosition(allOf(withId(R.id.respondTil), childAtPosition(withClassName(is("android.widget.LinearLayout")), 0)), 1), isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction relativeLayout = onView(allOf(withParent(withParent(withId(R.id.response_1))), isDisplayed()));
        relativeLayout.check(matches(isDisplayed()));
        }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
    }
