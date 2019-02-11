package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchUIListener;

/**
 * A state in a state machine. This interface is part of the State pattern.
 *
 * @author laufer
 */
public interface StopwatchState {
    void onClick();
    void onTick();
    String getId();
}
