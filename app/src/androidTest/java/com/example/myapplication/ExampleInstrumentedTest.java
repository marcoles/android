package com.example.myapplication;

import static  androidx.test.espresso.Espresso.*;
import static  androidx.test.espresso.matcher.ViewMatchers.*;
import static  androidx.test.espresso.action.ViewActions.*;
import static  androidx.test.espresso.assertion.ViewAssertions.*;

import android.content.Context;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private final String customMessage = "Hello!";
    private final String defaultMessage = "Enter a message";

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void clickTheButtonWithDefaultMessage() {
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText(defaultMessage)));
    }

    @Test
    public void clickTheButtonWithCustomMessage() {
        onView(withId(R.id.editTextTextPersonName))
                .perform(clearText(), typeText(customMessage));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText(customMessage)));
    }
}