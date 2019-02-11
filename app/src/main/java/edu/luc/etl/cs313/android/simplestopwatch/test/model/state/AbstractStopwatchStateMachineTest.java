package edu.luc.etl.cs313.android.simplestopwatch.test.model.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchUIUpdateListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.state.StopwatchStateMachine;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;

/**
 * Testcase superclass for the stopwatch state machine model. Unit-tests the state
 * machine in fast-forward mode by directly triggering successive tick events
 * without the presence of a pseudo-real-time clock. Uses a single unified mock
 * object for all dependencies of the state machine model.
 *
 * @author laufer
 * @see http://xunitpatterns.com/Testcase%20Superclass.html
 */
public abstract class AbstractStopwatchStateMachineTest {

    public StopwatchStateMachine model;

    public UnifiedMockDependency dependency;

    @Before
    public void setUp() throws Exception {
        dependency = new UnifiedMockDependency();
    }

    @After
    public void tearDown() {
        dependency = null;
    }

    /**
     * Setter for dependency injection. Usually invoked by concrete testcase
     * subclass.
     *
     * @param model
     */
    protected void setModel(final StopwatchStateMachine model) {
        this.model = model;
        if (model == null)
            return;
        this.model.setUIUpdateListener(dependency);
        this.model.actionInit();
    }

    protected UnifiedMockDependency getDependency() {
        return dependency;
    }

}

/**
 * Manually implemented mock object that unifies the three dependencies of the
 * stopwatch state machine model. The three dependencies correspond to the three
 * interfaces this mock object implements.
 *
 * @author laufer
 */
class UnifiedMockDependency implements TimeModel, StopwatchUIUpdateListener {

    private int timeValue = -1;
    private String stateId = "STOPPED";
    public boolean justPlayedSound = false;
    public boolean justStopSound = false;

    private int runningTime = 0;

    private boolean started = false;

    public int getTime() {
        return timeValue;
    }

    public String getState() {
        return stateId;
    }

    public boolean isStarted() {
        return started;
    }

    @Override
    public void updateTime(final int timeValue) {
        this.timeValue = timeValue;
    }

    @Override
    public void resetRuntime() {
        this.runningTime = 0;
    }

    @Override
    public void incRuntime() {
        this.runningTime += 1;
    }

    @Override
    public void decRuntime() {
        this.runningTime -= 1;
    }

    @Override
    public int getRuntime() {
        return runningTime;
    }

    @Override
    public void stopSound() {
    }

    @Override
    public void isEditable(boolean editable) {

    }

    @Override
    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    @Override
    public boolean playSound() {
        justPlayedSound = true;
        return true;
    }
}
