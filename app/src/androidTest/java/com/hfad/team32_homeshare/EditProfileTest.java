package com.hfad.team32_homeshare;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EditProfileTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void editProfileTest() {
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

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.emailEt), withText("test@email.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.emailTil),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(pressImeActionButton());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.passwordEt),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.passwordTil),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("PASSword"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.passwordEt), withText("PASSword"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.passwordTil),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText5.perform(pressImeActionButton());

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

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.nav_settings), withContentDescription("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.editProfileBtn), withText("Edit Profile"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.frame_layout),
                                        0),
                                0),
                        isDisplayed()));
        materialButton2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        pressBack();

//        ViewInteraction editText = onView(
//                allOf(withId(R.id.fullNameEt), withText("Test User, Full Name"),
//                        withParent(withParent(withId(R.id.fullNameTil))),
//                        isDisplayed()));
//        editText.check(matches(withText("Test User, Full Name")));
//
//        ViewInteraction editText2 = onView(
//                allOf(withId(R.id.emailEt), withText("test@email.com, Email"),
//                        withParent(withParent(withId(R.id.emailTil))),
//                        isDisplayed()));
//        editText2.check(matches(withText("test@email.com, Email")));
//
//        ViewInteraction editText3 = onView(
//                allOf(withId(R.id.phoneNumEt), withText("1234567890, Phone Number"),
//                        withParent(withParent(withId(R.id.phoneNumTil))),
//                        isDisplayed()));
//        editText3.check(matches(withText("1234567890, Phone Number")));
//
//        ViewInteraction textView = onView(
//                allOf(withId(android.R.id.text1), withText("freshman"),
//                        withParent(allOf(withId(R.id.class_spinner),
//                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
//                        isDisplayed()));
//        textView.check(matches(withText("freshman")));
//
//        ViewInteraction textView2 = onView(
//                allOf(withId(android.R.id.text1), withText("non-binary"),
//                        withParent(allOf(withId(R.id.gender_spinner),
//                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
//                        isDisplayed()));
//        textView2.check(matches(withText("non-binary")));
//
//        ViewInteraction editText4 = onView(
//                allOf(withId(R.id.biographyEt), withText("Test biography, Biography"),
//                        withParent(withParent(withId(R.id.biographyTil))),
//                        isDisplayed()));
//        editText4.check(matches(withText("Test biography, Biography")));

//        ViewInteraction appCompatEditText6 = onView(
//                allOf(withId(R.id.fullNameEt),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.fullNameTil),
//                                        0),
//                                0)));
//        appCompatEditText6.perform(scrollTo(), click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.fullNameEt),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fullNameTil),
                                        0),
                                0)));
        appCompatEditText7.perform(scrollTo(), replaceText("Test User 1"), closeSoftKeyboard());

//        ViewInteraction appCompatEditText8 = onView(
//                allOf(withId(R.id.fullNameEt), withText("Test User 1"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.fullNameTil),
//                                        0),
//                                0),
//                        isDisplayed()));
//        appCompatEditText8.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.phoneNumEt),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.phoneNumTil),
                                        0),
                                0)));
        appCompatEditText9.perform(scrollTo(), replaceText("12345678901"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText10 = onView(
//                allOf(withId(R.id.phoneNumEt), withText("12345678901"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.phoneNumTil),
//                                        0),
//                                0),
//                        isDisplayed()));
//        appCompatEditText10.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.class_spinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4)));
        appCompatSpinner.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        appCompatCheckedTextView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.gender_spinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                5)));
        appCompatSpinner2.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(0);
        appCompatCheckedTextView2.perform(click());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.biographyEt),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.biographyTil),
                                        0),
                                0)));
        appCompatEditText11.perform(scrollTo(), replaceText("Test biography 1"));

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.biographyEt),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.biographyTil),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText12.perform(closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.submitBtn),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        materialButton3.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.nav_settings), withContentDescription("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.editProfileBtn), withText("Edit Profile"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.frame_layout),
                                        0),
                                0),
                        isDisplayed()));
        materialButton4.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.fullNameEt),
                        withParent(withParent(withId(R.id.fullNameTil))),
                        isDisplayed()));
        editText5.check(matches(withText("Test User 1")));

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.phoneNumEt),
                        withParent(withParent(withId(R.id.phoneNumTil))),
                        isDisplayed()));
        editText6.check(matches(withText("12345678901")));

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.text1),
                        withParent(allOf(withId(R.id.class_spinner),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView3.check(matches(withText("junior")));

        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.text1),
                        withParent(allOf(withId(R.id.gender_spinner),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView4.check(matches(withText("male")));

        ViewInteraction editText7 = onView(
                allOf(withId(R.id.biographyEt),
                        withParent(withParent(withId(R.id.biographyTil))),
                        isDisplayed()));
        editText7.check(matches(withText("Test biography 1")));

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.fullNameEt),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fullNameTil),
                                        0),
                                0)));
        appCompatEditText13.perform(scrollTo(), click());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.fullNameEt),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fullNameTil),
                                        0),
                                0)));
        appCompatEditText14.perform(scrollTo(), replaceText("Test User"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText15 = onView(
//                allOf(withId(R.id.fullNameEt),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.fullNameTil),
//                                        0),
//                                0),
//                        isDisplayed()));
//        appCompatEditText15.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.phoneNumEt),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.phoneNumTil),
                                        0),
                                0)));
        appCompatEditText16.perform(scrollTo(), replaceText("1234567890"), closeSoftKeyboard());

//        ViewInteraction appCompatEditText17 = onView(
//                allOf(withId(R.id.phoneNumEt),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.phoneNumTil),
//                                        0),
//                                0),
//                        isDisplayed()));
//        appCompatEditText17.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        DataInteraction appCompatCheckedTextView3 = onData(anything())
//                .inAdapterView(childAtPosition(
//                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
//                        0))
//                .atPosition(2);
//        appCompatCheckedTextView3.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.class_spinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4)));
        appCompatSpinner3.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DataInteraction appCompatCheckedTextView4 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(0);
        appCompatCheckedTextView4.perform(click());
//

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.gender_spinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                5)));
        appCompatSpinner4.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DataInteraction appCompatCheckedTextView5 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        appCompatCheckedTextView5.perform(click());

        ViewInteraction appCompatEditText18 = onView(
                allOf(withId(R.id.biographyEt),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.biographyTil),
                                        0),
                                0)));
        appCompatEditText18.perform(scrollTo(), replaceText("Test biography"), closeSoftKeyboard());

//        ViewInteraction appCompatEditText19 = onView(
//                allOf(withId(R.id.biographyEt), withText("Test biography"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.biographyTil),
//                                        0),
//                                0),
//                        isDisplayed()));
//        appCompatEditText19.perform(closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.submitBtn), withText("Submit Changes"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        materialButton5.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.nav_settings), withContentDescription("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.logoutBtn), withText("Log out"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.frame_layout),
                                        0),
                                3),
                        isDisplayed()));
        materialButton6.perform(click());
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
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
