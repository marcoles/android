package com.example.myapplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SdkSuppress;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class UIAutomatorTests {

    private static final String BASIC_SAMPLE_PACKAGE = "com.example.myapplication";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String DEFAULT_MESSAGE = "Enter a message";
    private static final String CUSTOM_MESSAGE = "Hello!";
    private UiDevice device;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        device.pressHome();

        // Wait for launcher
        final String launcherPackage = device.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the app
        Context context = ApplicationProvider.getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
                LAUNCH_TIMEOUT);
    }

    @Test
    public void clickTheButtonWithDefaultMessage() {
        device.findObject(By.res(BASIC_SAMPLE_PACKAGE, "button")).click();
        String displayedText =
                device.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "textView")),
                        500).getText();
        assertThat("Displayed text does not match the default message",
                displayedText, is(equalTo(DEFAULT_MESSAGE)));
    }

    @Test
    public void clickTheButtonWithCustomMessage() {
        device.findObject(By.res(
                BASIC_SAMPLE_PACKAGE, "editTextTextPersonName")).setText(CUSTOM_MESSAGE);
        device.findObject(By.res(BASIC_SAMPLE_PACKAGE, "button")).click();
        String displayedText =
                device.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "textView")),
                        500).getText();
        assertThat("Displayed text does not match the custom message",
                displayedText, is(equalTo(CUSTOM_MESSAGE)));
    }

    @Test
    public void getToTheSecondScreen() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, DisplayMessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        String displayedText =
                device.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "textView")),
                        500).getText();
        assertThat("Displayed text does not match the custom message",
                displayedText, is(equalTo("TextView")));

    }
}
