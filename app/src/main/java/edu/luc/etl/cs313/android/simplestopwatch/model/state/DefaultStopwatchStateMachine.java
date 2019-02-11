package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import java.util.Timer;
import java.util.TimerTask;

import edu.luc.etl.cs313.android.simplestopwatch.common.Constants;
import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchUIUpdateListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;

/**
 * An implementation of the state machine for the stopwatch.
 *
 * @author laufer
 */
public class DefaultStopwatchStateMachine implements StopwatchStateMachine {

    public DefaultStopwatchStateMachine(final TimeModel timeModel) {
        this.timeModel = timeModel;
    }

    private final TimeModel timeModel;

    private Timer timer;

    /**
     * The internal state of this adapter component. Required for the State pattern.
     */
    private StopwatchState state;

    private StopwatchUIUpdateListener uiUpdateListener;

    @Override
    public void setUIUpdateListener(final StopwatchUIUpdateListener uiUpdateListener) {
        this.uiUpdateListener = uiUpdateListener;
    }

    // transitions
    // isEditable: only enable input of StopWatchAdapter when application in STOPPED STATE
    @Override public void toRunningState()    {
        this.state = new RunningState(this);
        this.uiUpdateListener.isEditable(false);
    }
    @Override public void toStoppedState()    {

        this.state = new StoppedState(this);
        this.uiUpdateListener.isEditable(true);
    }
    @Override public void toCountingState() {
        this.state = new CountingState(this);
        this.uiUpdateListener.isEditable(false);
    }
    @Override public void toAlarmingState() {
        this.state = new AlarmingState(this);
        this.uiUpdateListener.isEditable(false);
    }

    // actions
    /**
     * Action called when activity start
     */
    @Override public void actionInit() {

        toStoppedState();
    }

    /**
     * Action called when activity send the event to increase counter timer
     */
    @Override public int actionInc() {
        timeModel.incRuntime();
        uiUpdateListener.updateTime(timeModel.getRuntime());

        return timeModel.getRuntime();
    }

    /**
     * Action called when activity need to reset timer
     */
    @Override
    public void actionResetTimer() {
        if (timer == null) {
            timer = new Timer();
        }


        timer.schedule(new TimerTask() {
            @Override public void run() {
                state.onTick();
            }}, 0, Constants.SEC_TICK);
    }

    /**
     * Action called when activity need to cancel timer
     */
    @Override
    public void actionCancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * Action called when activity send the event to decrease counter timer
     */
    @Override
    public int actionDec() {
        timeModel.decRuntime();
        uiUpdateListener.updateTime(timeModel.getRuntime());

        return timeModel.getRuntime();
    }

    /**
     * Action called when the states need activity play sound
     */
    @Override
    public void actionPlaysound() {
        this.uiUpdateListener.playSound();
    }

    @Override
    public void actionStopPlaying() {

    }

    /**
     * Action called when the states need reset run time
     */
    @Override
    public void actionResetRunningTime() {
        timeModel.resetRuntime();
        uiUpdateListener.updateTime(timeModel.getRuntime());
    }

    /**
     * Action called when the states need to set time
     */
    @Override
    public void actionSetTime(int time) {
        timeModel.setRunningTime(time);
        this.uiUpdateListener.updateTime(timeModel.getRuntime());
        toRunningState();
    }

    /**
     * Action called when the activity send the event onclick
     */
    @Override
    public void onClick() {
        this.state.onClick();
    }

    @Override
    public String currentStateId() {
        return this.state.getId();
    }

    @Override
    public int currentTime() {
        return this.timeModel.getRuntime();
    }
}
