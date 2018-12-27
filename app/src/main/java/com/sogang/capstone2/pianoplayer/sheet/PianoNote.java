package com.sogang.capstone2.pianoplayer.sheet;

import com.sogang.capstone2.pianoplayer.piano.Key;

import java.util.ArrayList;
import java.util.HashMap;

public class PianoNote {

	private ArrayList<Key> keyList;
	int durationCount;
	HashMap<Key, Integer> fingerNumberMap;
	
    public PianoNote(ArrayList<Key> keyList, HashMap<Key, Integer> fingerNumberMap) {
        this.keyList = keyList;
        this.fingerNumberMap = fingerNumberMap;
    }
	
    public ArrayList<Key> getKeyList() {
        return keyList;
    }
	
    public void setKeyList(ArrayList<Key> keyList) {
        this.keyList = keyList;
    }
	
    public int getDurationCount() {
        return durationCount;
    }
	
    public void setDurationCount(int durationCount) {
        this.durationCount = durationCount;
    }

    public HashMap<Key, Integer> getFingerNumberMap() {
        return fingerNumberMap;
    }
	
    public Integer getFingerNumber(Key key) {
        return fingerNumberMap.get(key);
    }

    public void setFingerNumber(Key key, int fingerNumber) {
        fingerNumberMap.put(key, fingerNumber);
    }
}
