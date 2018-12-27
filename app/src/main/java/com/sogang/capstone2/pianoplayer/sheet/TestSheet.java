package com.sogang.capstone2.pianoplayer.sheet;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;

import com.sogang.capstone2.pianoplayer.piano.Key;
import com.sogang.capstone2.pianoplayer.piano.PianoController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class TestSheet extends PianoSheet {
    public TestSheet(PianoController pianoController, Context context) {
        super(0, 0, null);

        JSONObject jsonSheet;
        ArrayList<Key> keyRightList;
        HashMap<Key, Integer> fingerNumberMap;
        PianoNote pianoNote;
        Queue<PianoNote> rightQueue;
        PianoBar pianoBar;
        Queue<PianoBar> barList = null;

        try {
            jsonSheet = new JSONObject(loadJSONFromAsset(context));

            JSONArray bars = jsonSheet.getJSONArray("Sheet");

            barList = new LinkedList<>();
            for (int i = 0;i < bars.length();i++) {

                JSONArray bar = bars.getJSONArray(i);
                rightQueue = new LinkedList<>();

                for (int j = 0;j < bar.length();j++) {
                    JSONArray note = bar.getJSONArray(j);
                    keyRightList = new ArrayList<>();
                    fingerNumberMap = new HashMap<>();
                    for (int k = 0;k < note.length();k++) {
                        JSONObject key = note.getJSONObject(k);
                        keyRightList.add(pianoController.getPiano().getKeyAt(key.getInt("Octave"), key.getInt("Sound")));
                        fingerNumberMap.put(pianoController.getPiano().getKeyAt(key.getInt("Octave"), key.getInt("Sound")), key.getInt("Finger"));
                    }
                    pianoNote = new PianoNote(keyRightList, fingerNumberMap);
                    rightQueue.offer(pianoNote);
                }
                pianoBar = new PianoBar(0, rightQueue, rightQueue);
                barList.offer(pianoBar);
            }

            super.setBarList(barList);
        } catch (Exception e) {

        }
    }

    public String loadJSONFromAsset(Context context) {
        String path = "KissTheRain.json";
        String json = null;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
