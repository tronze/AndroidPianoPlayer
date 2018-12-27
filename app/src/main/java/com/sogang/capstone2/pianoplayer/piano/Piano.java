package com.sogang.capstone2.pianoplayer.piano;

import android.graphics.Color;
import android.util.SparseArray;

public class Piano {

    private SparseArray<SparseArray<Key>> ocataveList;
    private SparseArray<Key> keyList;

    public Piano(int startOctave, int endOctave) {
        ocataveList = new SparseArray<>();
        for ( int i = startOctave; i <= endOctave ; i++ ) {
            keyList = new SparseArray<>();
            keyList.put(1, new Key(1, i, Color.WHITE, false));
            keyList.put(2, new Key(2, i, Color.WHITE, false));
            keyList.put(3, new Key(3, i, Color.WHITE, false));
            keyList.put(4, new Key(4, i, Color.WHITE, false));
            keyList.put(5, new Key(5, i, Color.WHITE, false));
            keyList.put(6, new Key(6, i, Color.WHITE, false));
            keyList.put(7, new Key(7, i, Color.WHITE, false));
            keyList.put(8, new Key(2, i, Color.BLACK, true));
            keyList.put(9, new Key(3, i, Color.BLACK, true));
            keyList.put(11, new Key(5, i, Color.BLACK, true));
            keyList.put(12, new Key(6, i, Color.BLACK, true));
            keyList.put(13, new Key(7, i, Color.BLACK, true));
            ocataveList.put(i, keyList);
        }
    }

    public Key getKeyAt(int octave, int note) {
        return ocataveList.get(octave).get(note);
    }

}
