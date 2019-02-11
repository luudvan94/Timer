package edu.luc.etl.cs313.android.simplestopwatch.test.model.state;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.model.state.DefaultStopwatchStateMachine;


import static org.junit.Assert.assertEquals;

/**
 * Concrete testcase subclass for the default stopwatch state machine
 * implementation.
 *
 * @author laufer
 * @see http://xunitpatterns.com/Testcase%20Superclass.html
 */
public class DefaultStopwatchStateMachineTest extends AbstractStopwatchStateMachineTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        setModel(new DefaultStopwatchStateMachine(getDependency()));
    }

    @After
    public void tearDown() {
        setModel(null);
        super.tearDown();
    }

    /**
     * Verifies that we're initially in the stopped state (and told the listener
     * about it).
     */
    @Test
    public void testPreconditions() {
        assertEquals("STOPPED", model.currentStateId());
        assertEquals(0, model.currentTime());
    }

    /**
     * Verifies that we're at CountingState after 1-click
     */
    @Test
    public void testCountingState() {
        model.onClick();

        assertEquals("COUNTING", model.currentStateId());
        assertEquals(1, model.currentTime());

    }

    /**
     * Verifies that counter increases 5 after clicking 5 times
     */
    @Test
    public void testIncrementCounter() {
        onRepeatedClick(5);

        assertEquals(5, model.currentTime());

    }

    /**
     * Verifies that we're at RunningState after 3 seconds elapse
     */
    @Test
    public void testRunningState() throws InterruptedException {
        onRepeatedClick(3);
        Thread.sleep(3000);

        assertEquals("RUNNING", model.currentStateId());

    }

    /**
     * Verifies that counter decrease by 5 after 5 seconds running
     */
    @Test
    public void testDecreaseCounter() throws InterruptedException {
        onRepeatedClick(5);
        Thread.sleep(3000);
        Thread.sleep(5000);

        assertEquals(0, model.currentTime());

    }

    /**
     * Verifies that counter maximum limit of 99
     * if counter greater than 99, it reset to 0
     */
    @Test
    public void testCounterLimit() throws InterruptedException {
        onRepeatedClick(100);
        assertEquals(0, model.currentTime());
    }

    /**
     * Verifies that counter back to STOPPED state after click button while in RUNNING STATE
     */
    @Test
    public void testBackToStoppedState() throws InterruptedException {
        onRepeatedClick(3);
        Thread.sleep(3000);
        Thread.sleep(1000);

        model.onClick();
        assertEquals("STOPPED", model.currentStateId());

    }

    /**
     * Verifies that counter are at ALARMING STATE
     */
    @Test
    public void testAlarmingState() throws InterruptedException {
        onRepeatedClick(3);
        Thread.sleep(3000);
        Thread.sleep(1000);
        Thread.sleep(3000);

        assertEquals("ALARMING", model.currentStateId());

    }

    /**
     * Verifies that we're at correct states after user input 5 on edit text and click lap/reset button
     */
    @Test
    public void testStatesWithTimeRequestFromUser() throws InterruptedException {
        assertEquals("STOPPED", model.currentStateId());

        // get input and click reset/lap button
        model.actionSetTime(5);

        assertEquals("RUNNING", model.currentStateId());
        Thread.sleep(1000);
        Thread.sleep(5000);

        assertEquals("ALARMING", model.currentStateId());

        model.onClick();
        assertEquals("STOPPED", model.currentStateId());
    }

    @Test
    public void testStateWithTimeFromButton()  throws InterruptedException {
        assertEquals("STOPPED", model.currentStateId());

        onRepeatedClick(3);
        assertEquals("COUNTING", model.currentStateId());

        Thread.sleep(3000);

        assertEquals("RUNNING", model.currentStateId());
        Thread.sleep(1000);
        Thread.sleep(3000);

        assertEquals("ALARMING", model.currentStateId());

        model.onClick();
        assertEquals("STOPPED", model.currentStateId());
    }

    protected void onRepeatedClick(int times) {
        for (int i = 0; i < times; i++) {
            model.onClick();
        }
    }

}
