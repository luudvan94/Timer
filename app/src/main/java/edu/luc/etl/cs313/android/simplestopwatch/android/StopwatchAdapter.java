package edu.luc.etl.cs313.android.simplestopwatch.android;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import edu.luc.etl.cs313.android.simplestopwatch.R;
import edu.luc.etl.cs313.android.simplestopwatch.common.Constants;
import edu.luc.etl.cs313.android.simplestopwatch.common.StopwatchUIUpdateListener;
import edu.luc.etl.cs313.android.simplestopwatch.model.ConcreteStopwatchModelFacade;
import edu.luc.etl.cs313.android.simplestopwatch.model.StopwatchModelFacade;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import java.io.IOException;

/**
 * A thin adapter component for the stopwatch.
 *
 * @author laufer
 */
public class StopwatchAdapter extends Activity implements StopwatchUIUpdateListener {

    private static String TAG = "stopwatch-android-activity";

    /**
     * The state-based dynamic model.
     */
    private StopwatchModelFacade model;
    private MediaPlayer mp;
    public EditText edittext;
    public Button lapButton;
    public TextView ts;

    protected void setModel(final StopwatchModelFacade model) {
        this.model = model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inject dependency on view so this adapter receives UI events
        setContentView(R.layout.activity_main);
        // inject dependency on model into this so model receives UI events
        this.setModel(new ConcreteStopwatchModelFacade());
        // inject dependency on this into model to register for UI updates
        model.setUIUpdateListener(this);

        mp = MediaPlayer.create(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        edittext = (EditText) findViewById(R.id.editText);
        edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    handleEditText();
                }
                return handled;
            }
        });

        lapButton = (Button) findViewById(R.id.resetLap);
        ts = (TextView) findViewById(R.id.seconds);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        model.onStart();
    }

    // TODO remaining lifecycle methods

    /**
     * Updates the seconds and minutes in the UI.
     * @param time
     */
    public void updateTime(final int time) {
        // UI adapter responsibility to schedule incoming events on UI thread
        runOnUiThread(() -> {
            final TextView tvS = (TextView) findViewById(R.id.seconds);
            tvS.setText(Integer.toString(time / 10) + Integer.toString(time % 10));
        });
    }

    @Override
    public boolean playSound() {
        if (mp == null) {
            return false;
        }

        try {
            if (mp.isPlaying()) {
                mp.stop();
                mp.release();

                mp = MediaPlayer.create(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            }

            mp.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void stopSound() {
        try {
            if (mp.isPlaying()) {
                mp.stop();
                mp.release();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * delegate the editability of input text to internal states
     */
    @Override
    public void isEditable(boolean editable) {
        focusOnEditText(editable);
    }

    public void onClick(final View view) {
        if (!handleEditText()) { // if there no text in editText field
            model.onClick();
        }
    }

    // credit: https://stackoverflow.com/questions/8991522/how-can-i-set-the-focus-and-display-the-keyboard-on-my-edittext-programmatical
    private void focusOnEditText(boolean isFocus) {
        edittext.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (isFocus) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            imm.hideSoftInputFromWindow(edittext.getWindowToken(), 0);
            edittext.setInputType(InputType.TYPE_NULL);
        }
    }

    /**
     * Handle when click go on keyboard
     */
    private boolean handleEditText() {
        if (edittext.getInputType() == InputType.TYPE_NULL) {
            return false;
        }

        String text = edittext.getText().toString();

        if (text == "" || text == null) {
            return false;
        }

        try {
            int timeRequest = Integer.parseInt(text);

            if (timeRequest > 99) {
                model.onRequest(99);
            } else {
                model.onRequest(timeRequest);
            }
            edittext.setText("");
            return true;

        } catch (Exception e) {
            return false;
        }

    }


}
