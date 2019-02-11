package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.common.Constants;

public class CountingState implements StopwatchState {

    public CountingState(final StopwatchSMStateView sm) {
        this.sm = sm;
        initialize();
    }

    private final StopwatchSMStateView sm;
    private int tickCount;
    private int counter;
    private boolean shouldStartRunningState = false;

    private void initialize() {
        counter = 0;
        tickCount = Constants.TIME_OUT;
        sm.actionResetTimer();
        counter = sm.actionInc();
    }

    @Override
    public void onClick() {
        // set limit of counter to 99
        if (counter >= 99) {
            return;
        }

        counter = sm.actionInc();

        // counter == 99 => then beep once and go to running state
        if (counter == 99) {
            sm.toRunningState();
            return;
        }

        sm.actionCancelTimer();
        tickCount = Constants.TIME_OUT;
        sm.actionResetTimer();


    }

    @Override
    public void onTick() {
        if (tickCount == 1) {
            sm.toRunningState();
        } else {
            tickCount -= 1;
        }


    }

    @Override
    public String getId() {
        return "COUNTING";
    }
}
