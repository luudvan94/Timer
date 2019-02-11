package edu.luc.etl.cs313.android.simplestopwatch.model.state;

public class AlarmingState implements StopwatchState {

    public AlarmingState(final StopwatchSMStateView sm) {
        this.sm = sm;
        initialize();
    }

    private final StopwatchSMStateView sm;

    private void initialize() {
        sm.actionResetTimer();
    }

    @Override
    public void onClick() {
        sm.actionStopPlaying();
        sm.toStoppedState();
    }

    @Override
    public void onTick() {
        sm.actionPlaysound();
    }

    @Override
    public String getId() {
        return "ALARMING";
    }
}
