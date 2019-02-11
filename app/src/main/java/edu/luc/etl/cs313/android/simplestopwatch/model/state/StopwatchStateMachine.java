package edu.luc.etl.cs313.android.simplestopwatch.model.state;

import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchUIUpdateListener;
import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchUIUpdateSource;
import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchUIListener;

/**
 * The state machine for the state-based dynamic model of the stopwatch.
 * This interface is part of the State pattern.
 *
 * @author laufer
 */
//public interface StopwatchStateMachine extends StopwatchUIListener, OnTickListener, StopwatchUIUpdateSource, StopwatchSMStateView { }
public interface StopwatchStateMachine extends StopwatchSMStateView, StopwatchUIUpdateSource { }