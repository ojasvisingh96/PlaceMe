package com.example.ojasvisingh.placeme;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.DatePicker;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<AdminJob> mAdminJobTestRule = new ActivityTestRule<AdminJob>(AdminJob.class);

//    public static void setDate(int datePickerLaunchViewId, int year, int monthOfYear, int dayOfMonth) {
//        onView(withParent(withId(buttonContainer)), withId(datePickerLaunchViewId)).perform(click());
//        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, monthOfYear, dayOfMonth));
//        onView(withId(android.R.id.button1)).perform(click());
//    }

    @Test
    public void clickAddJobButton () throws Exception {
        onView(withId(R.id.floatingActionButton)).perform(click());

        onView(withId(R.id.jobCompanyAdmin)).perform(typeText("Facebook"));
        onView(withId(R.id.jobLocationAdmin)).perform(typeText("Menlo Park, CA"));
        onView(withId(R.id.jobProfileAdmin)).perform(typeText("UI Tester"));
        onView(withId(R.id.jobCtcAdmin)).perform(typeText("1 Crpa"));
//        /onView(withId(R.id.jobDateAdmin)).perform(typeText("05/05/2018"));
        onView(withId(R.id.jobDateAdmin)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2018, 05, 04));
        onView(withId(android.R.id.button1)).perform(click());
//        onView(withId(R.id.jobDateAdmin)).perform(PickerActions.setDate(2018, 05, 02));
        onView(withId(R.id.jobMinCGPA)).perform(typeText("7.0"));

        onView(withId(R.id.jobCreateAdmin)).perform(click());

//        onView(withId(R.id.floatingActionButton)).check(matches(isDisplayed()));

    }
}
