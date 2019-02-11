package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.R;

class StoppedState implements StopwatchState {

    public StoppedState(final StopwatchSMStateView sm) {
        this.sm = sm;
        initialize();
    }

    private final StopwatchSMStateView sm;

    private void initialize() {
        sm.actionResetRunningTime();
        sm.actionCancelTimer();
    }

    @Override
    public void onClick() {
        sm.toCountingState();
    }

    @Override
    public void onTick() {

    }

    @Override
    public String getId() {
        return "STOPPED";
    }
}
