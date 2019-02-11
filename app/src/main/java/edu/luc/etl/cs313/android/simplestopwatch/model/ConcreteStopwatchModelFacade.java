package edu.luc.etl.cs313.android.simplestopwatch.model;

import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchUIUpdateListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.state.DefaultStopwatchStateMachine;
import edu.luc.etl.cs313.android.simplestopwatch.model.state.StopwatchStateMachine;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.DefaultTimeModel;
import edu.luc.etl.cs313.android.simplestopwatch.model.time.TimeModel;

/**
 * An implementation of the model facade.
 *
 * @author laufer
 */
public class ConcreteStopwatchModelFacade implements StopwatchModelFacade {

    private StopwatchStateMachine stateMachine;


    private TimeModel timeModel;

    public ConcreteStopwatchModelFacade() {
        timeModel = new DefaultTimeModel();
        stateMachine = new DefaultStopwatchStateMachine(timeModel);
    }

    @Override
    public void onStart() {
        stateMachine.actionInit();
    }

    @Override
    public void setUIUpdateListener(final StopwatchUIUpdateListener listener) {
        stateMachine.setUIUpdateListener(listener);
    }

    @Override
    public void onClick() {
        stateMachine.onClick();
    }

    @Override
    public void onRequest(int time) {
        stateMachine.actionSetTime(time);
    }
}
