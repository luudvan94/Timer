package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import java.io.Console;
import java.util.Timer;
import java.util.TimerTask;

import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.common.Constants;

class RunningState implements StopwatchState {

    public RunningState(final StopwatchSMStateView sm) {
        this.sm = sm;
        initialize();
    }

    private final StopwatchSMStateView sm;
    private int counter;
    private boolean shouldStartRunningState = false;

    private void initialize() {
        sm.actionPlaysound();
        sm.actionCancelTimer();
        sm.actionResetTimer();
        counter = 0;
    }


    @Override
    public void onClick() {
        sm.toStoppedState();
    }

    @Override
    public void onTick() {
        if (!shouldStartRunningState) {
            shouldStartRunningState = true;
            return;
        }
        counter = sm.actionDec();

        if (counter == 0) {
            sm.toAlarmingState();
        }
    }

    @Override
    public String getId() {
        return "RUNNING";
    }
}
