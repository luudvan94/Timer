package edu.luc.etl.cs313.android.simplestopwatch.test.model.time;

import static edu.luc.etl.cs313.android.simplestopwatch.common.Constants.SEC_PER_HOUR;
import static edu.luc.etl.cs313.android.simplestopwatch.common.Constants.SEC_PER_MIN;
import static edu.luc.etl.cs313.android.simplestopwatch.common.Constants.SEC_PER_TICK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;

/**
 * Testcase superclass for the time model abstraction.
 * This is a simple unit test of an object without dependencies.
 *
 * @author laufer
 * @see http://xunitpatterns.com/Testcase%20Superclass.html
 */
public abstract class AbstractTimeModelTest {

    private TimeModel model;

    /**
     * Setter for dependency injection. Usually invoked by concrete testcase
     * subclass.
     *
     * @param model
     */
    protected void setModel(final TimeModel model) {
        this.model = model;
    }

    /**
     * Verifies that runtime and laptime are initially 0 or less.
     */
    @Test
    public void testPreconditions() {
        assertEquals(0, model.getRuntime());
        assertTrue(model.getRuntime() <= 0);
    }

    /**
     * Verifies that runtime is incremented correctly.
     */
    @Test
    public void testIncrementRuntimeOne() {
        model.incRuntime();
        assertEquals(1, model.getRuntime());
    }

    /**
     * Verifies that runtime turns over correctly.
     */
    @Test
    public void testIncrementRuntimeMany() {
        for (int i = 0; i < 3600; i ++) {
            model.incRuntime();
        }
        assertEquals(3600, model.getRuntime());
    }


    /**
     * Verifies that runtime is decreamented correctly.
     */
    @Test
    public void testDecrementRuntimeOne() {
        assertEquals(0, model.getRuntime());

        model.incRuntime();
        assertEquals(1, model.getRuntime());

        model.decRuntime();
        assertEquals(0, model.getRuntime());
    }

    /**
     * Verifies that runtime turns over correctly.
     */
    @Test
    public void testDecrementRuntimeMany() {
        assertEquals(0, model.getRuntime());

        for (int i = 0; i < 3600; i ++) {
            model.incRuntime();
        }
        assertEquals(3600, model.getRuntime());

        for (int i = 0; i < 3600; i ++) {
            model.decRuntime();
        }
        assertEquals(0, model.getRuntime());
    }

    /**
     * Verifies that runtime is reset correctly
     */
    @Test
    public void testResetRunTime() {
        assertEquals(0, model.getRuntime());

        for (int i = 0; i < 3600; i ++) {
            model.incRuntime();
        }
        assertEquals(3600, model.getRuntime());

        model.resetRuntime();
        assertEquals(0, model.getRuntime());
    }

    /**
     * Verifies that runtime is set correctly
     */
    @Test
    public void testSetRunTime() {
        assertEquals(0, model.getRuntime());

        model.setRunningTime(3600);
        assertEquals(3600, model.getRuntime());
    }
}
