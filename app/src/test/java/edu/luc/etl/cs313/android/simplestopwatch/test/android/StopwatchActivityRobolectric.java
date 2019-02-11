package edu.luc.etl.cs313.android.simplestopwatch.test.android;

import android.widget.Button;

import edu.luc.etl.cs313.android.simplestopwatch.BuildConfig;
import edu.luc.etl.cs313.android.simplestopwatch.android.StopwatchAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Concrete Robolectric test subclass. For the Gradle unitTest task to work,
 * the Robolectric dependency needs to be isolated here instead of being present in src/main.
 *
 * @author laufer
 * @see http://pivotal.github.com/robolectric
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class StopwatchActivityRobolectric extends AbstractStopwatchActivityTest {

    private static String TAG = "stopwatch-android-activity-robolectric";

    private StopwatchAdapter activity;

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        activity = Robolectric.buildActivity(StopwatchAdapter.class).create().start().visible().get();
    }

    @Override
    protected StopwatchAdapter getActivity() {
        return activity;
    }

    @Override
    protected void runUiThreadTasks() {
        // Robolectric requires us to run the scheduled tasks explicitly!
        org.robolectric.shadows.ShadowLooper.runUiThreadTasks();
    }

    @Test
    public void testActivityScenarioInit() throws Throwable {
        getActivity().runOnUiThread(() -> assertEquals("00", activity.ts.getText()));
    }

    /**
     * Verifies the following scenario: time is 0, click 5 times, wait 9 seconds
     * (3 seconds wait, 1 second beep, 5 second running)
     * expected timer equal to 00
     * @throws Throwable
     */
    @Test
    public void testActivityScenarioRun1() throws Throwable {
        assertEquals("00", activity.ts.getText());

        getActivity().runOnUiThread(() -> {
            for (int i = 0; i < 5; i++) {
                activity.lapButton.performClick();
            }
        });

        assertEquals("05", activity.ts.getText());
        Thread.sleep(9000);
        runUiThreadTasks();

        getActivity().runOnUiThread(() -> {
            assertEquals("00", activity.ts.getText());
        });
    }

    /**
     * Verifies the following scenario:
     * time is 0, click 10 times
     * * expected timer equal to 10
     * wait 6 seconds (3 seconds wait, 1 second beep, 2 seconds running)
     * click the button
     * expected timer equal to 00
     * @throws Throwable
     */
    @Test
    public void testActivityScenarioRun2() throws Throwable {
        assertEquals("00", activity.ts.getText());

        getActivity().runOnUiThread(() -> {
            for (int i = 0; i < 10; i++) {
                activity.lapButton.performClick();
            }
        });

        assertEquals("10", activity.ts.getText());
        Thread.sleep(6000);
        runUiThreadTasks();

        getActivity().runOnUiThread(() -> {
            activity.lapButton.performClick();
        });
        runUiThreadTasks();
        assertEquals("00", activity.ts.getText());
    }

    /**
     * Verifies the following scenario:
     * time is 0, input 10 on edit text area
     * click the button
     * wait 12 seconds (can't guess exactly when the timer stop)
     * expected timer equal to 00
     * @throws Throwable
     */
    @Test
    public void testActivityScenarioRun3() throws Throwable {
        assertEquals("00", activity.ts.getText());

        getActivity().runOnUiThread(() -> {
            activity.edittext.setText("10");
            activity.lapButton.performClick();
        });

        assertEquals("10", activity.ts.getText());
        Thread.sleep(12000);
        runUiThreadTasks();

        getActivity().runOnUiThread(() -> {
            assertEquals("00", activity.ts.getText());
        });
        runUiThreadTasks();
    }

    /**
     * Verifies the following scenario:
     * time is 0, input 10 on edit text area
     * click the button
     * wait 6 seconds running
     * expect timer less than 5
     * click the button
     * expected timer equal to 00
     * @throws Throwable
     */
    @Test
    public void testActivityScenarioRun4() throws Throwable {
        assertEquals("00", activity.ts.getText());

        getActivity().runOnUiThread(() -> {
            activity.edittext.setText("10");
            activity.lapButton.performClick();
        });

        assertEquals("10", activity.ts.getText());
        Thread.sleep(6000);
        runUiThreadTasks();

        assertTrue(tvToInt(activity.ts) <= 5);
        getActivity().runOnUiThread(() -> {
            activity.lapButton.performClick();
        });
        runUiThreadTasks();

        getActivity().runOnUiThread(() -> {
            assertEquals("00", activity.ts.getText());
        });
        runUiThreadTasks();

    }
}
