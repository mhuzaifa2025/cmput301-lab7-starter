package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new
            ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void testAddCity() {
        // Click on Add City button
        onView(withId(R.id.button_add)).perform(click());
        // Type "Edmonton"
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        // Click on Confirm
        onView(withId(R.id.button_confirm)).perform(click());
        // Check if text "Edmonton" is displayed
        onView(withText("Edmonton")).check(matches(isDisplayed()));
    }

    @Test
    public void testClearCity() {
        // Add first city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());
        // Add second city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Vancouver"));
        onView(withId(R.id.button_confirm)).perform(click());
        // Clear the list
        onView(withId(R.id.button_clear)).perform(click());
        // Check they are gone
        onView(withText("Edmonton")).check(doesNotExist());
        onView(withText("Vancouver")).check(doesNotExist());
    }

    @Test
    public void testListView() {
        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Check if "Edmonton" is at position 0 in the list
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .check(matches((withText("Edmonton"))));
    }

    @Test
    public void testActivitySwitch() {
        // Add a city so we have something to click
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Toronto"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click on the first item in the list
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());

        // Check if we are in ShowActivity by looking for the TextView specific to that activity
        onView(withId(R.id.tv_city_name)).check(matches(isDisplayed()));
    }

    @Test
    public void testCityNameConsistency() {
        // Add "Edmonton"
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click on "Edmonton" in the list
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());

        // Check if the TextView in ShowActivity displays "Edmonton"
        onView(withId(R.id.tv_city_name)).check(matches(withText("Edmonton")));
    }

    @Test
    public void testBackButton() {
        // Add a city and navigate to ShowActivity
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Calgary"));
        onView(withId(R.id.button_confirm)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());

        // Click the Back button
        onView(withId(R.id.btn_back)).perform(click());

        // Check if we are back in MainActivity (check if the Add button is visible)
        onView(withId(R.id.button_add)).check(matches(isDisplayed()));
    }

}
