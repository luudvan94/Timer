package edu.luc.etl.cs313.android.simplestopwatch.model.state;

/**
 * The restricted view states have of their surrounding state machine.
 * This is a client-specific interface in Peter Coad's terminology.
 *
 * @author laufer
 */
interface StopwatchSMStateView {

    // state
    String currentStateId();

    // time
    int currentTime();

    // time

    // transitions
    void toRunningState();
    void toStoppedState();
    void toCountingState();
    void toAlarmingState();

    // actions
    void actionInit();
    int actionInc();
    void actionResetTimer();
    int actionDec();
    void actionCancelTimer();
    void actionPlaysound();
    void actionStopPlaying();
    void actionResetRunningTime();
    void actionSetTime(int time);

    //event
    void onClick();
}
