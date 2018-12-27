package com.sogang.capstone2.pianoplayer.sheet;

import android.graphics.Color;
import android.support.annotation.ColorInt;

import com.sogang.capstone2.pianoplayer.piano.Key;

import java.util.ArrayList;

public class PianoSheetController{

	private PianoSheet sheet;

	private PianoBar currentBar;
	private PianoNote currentNote;

	@ColorInt
	private static final int nextColor = Color.parseColor("#73d1f7");

	public PianoSheetController(PianoSheet sheet) {
		this.sheet = sheet;
		currentBar = getNextBar();
		currentNote = getNextRightNote(null);
		setNextNote(currentNote);
	}

	private PianoBar getNextBar() {
	    if (sheet.getBarList() != null)
	        return sheet.getBarList().poll();
	    return null;
    }

    public PianoNote getNextRightNote(ArrayList<Key> currentKeyList) {

	    if (currentNote != null) {
	        if (currentKeyList != null && checkCorrentness(currentNote, currentKeyList)) {
                for (Key key : currentKeyList)
                    key.initColor();
                currentNote = currentBar.getRightQueue().poll();

                if (currentNote == null) {
                    currentBar = getNextBar();
                    if (currentBar != null)
                        currentNote = currentBar.getRightQueue().poll();
                }
            }
        } else {
            if (currentBar != null) {
                currentNote = currentBar.getRightQueue().poll();

                if (currentNote == null) {
                    currentBar = getNextBar();
                    if (currentBar != null)
                        currentNote = currentBar.getRightQueue().poll();
                }
            }
        }
        setNextNote(currentNote);
        return currentNote;
    }

    private void setNextNote(PianoNote note) {
	    if (note != null)
	        for (Key key : note.getKeyList())
	            key.setColor(nextColor);
    }

	public Boolean checkCorrentness(PianoNote note, ArrayList<Key> currentKeyList){
	    for (Key key : note.getKeyList()) {
	        if (!currentKeyList.contains(key)) {
	            return false;
            }
        }
	    return true;
	}

	public PianoNote getCurrentNote() {
	    return currentNote;
    }
}
