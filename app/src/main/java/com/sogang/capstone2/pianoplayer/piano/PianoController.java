package com.sogang.capstone2.pianoplayer.piano;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

public class PianoController {

    private static final String AUTHOR = "TRONZE";

    private Piano piano;
    private int startOctave;
    private int endOctave;

    public PianoController(Context context) {
        startOctave = 5;
        endOctave = 6;
        piano = new Piano(startOctave, endOctave);
    }

    public Piano getPiano() {
        return piano;
    }

    public int getStartOctave() {
        return startOctave;
    }

    public int getEndOctave() {
        return endOctave;
    }

    private void pringLog(String str) {
        Log.d(AUTHOR, str);
    }

}
