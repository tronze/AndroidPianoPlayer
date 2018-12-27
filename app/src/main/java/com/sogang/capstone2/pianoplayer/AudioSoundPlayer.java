package com.sogang.capstone2.pianoplayer;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.util.Log;
import android.util.SparseArray;
import android.media.AudioTrack;

import java.io.InputStream;

public class AudioSoundPlayer {
    private SparseArray<SparseArray<PlayThread>> threadMap = null;
    private SparseArray<PlayThread> tempThreadMap;
    private Context context;
    private static final SparseArray<SparseArray<String>> SOUND_MAP = new SparseArray<>();
    private static SparseArray<String> NOTE_MAP;
    public static final int MAX_VOLUME = 100, CURRENT_VOLUME = 90;
    static {
        NOTE_MAP = new SparseArray<>();
        NOTE_MAP.put(1, "C3");
        NOTE_MAP.put(2, "D3");
        NOTE_MAP.put(3, "E3");
        NOTE_MAP.put(4, "F3");
        NOTE_MAP.put(5, "G3");
        NOTE_MAP.put(6, "A3");
        NOTE_MAP.put(7, "B3");
        NOTE_MAP.put(8, "Db3");
        NOTE_MAP.put(9, "Eb3");
        NOTE_MAP.put(11, "Gb3");
        NOTE_MAP.put(12, "Ab3");
        NOTE_MAP.put(13, "Bb3");
        SOUND_MAP.put(3, NOTE_MAP);

        NOTE_MAP = new SparseArray<>();
        NOTE_MAP.put(1, "C4");
        NOTE_MAP.put(2, "D4");
        NOTE_MAP.put(3, "E4");
        NOTE_MAP.put(4, "F4");
        NOTE_MAP.put(5, "G4");
        NOTE_MAP.put(6, "A4");
        NOTE_MAP.put(7, "B4");
        NOTE_MAP.put(8, "Db4");
        NOTE_MAP.put(9, "Eb4");
        NOTE_MAP.put(11, "Gb4");
        NOTE_MAP.put(12, "Ab4");
        NOTE_MAP.put(13, "Bb4");
        SOUND_MAP.put(4, NOTE_MAP);

        NOTE_MAP = new SparseArray<>();
        NOTE_MAP.put(1, "C5");
        NOTE_MAP.put(2, "D5");
        NOTE_MAP.put(3, "E5");
        NOTE_MAP.put(4, "F5");
        NOTE_MAP.put(5, "G5");
        NOTE_MAP.put(6, "A5");
        NOTE_MAP.put(7, "B5");
        NOTE_MAP.put(8, "Db5");
        NOTE_MAP.put(9, "Eb5");
        NOTE_MAP.put(11, "Gb5");
        NOTE_MAP.put(12, "Ab5");
        NOTE_MAP.put(13, "Bb5");
        SOUND_MAP.put(5, NOTE_MAP);

        NOTE_MAP = new SparseArray<>();
        NOTE_MAP.put(1, "C6");
        NOTE_MAP.put(2, "D6");
        NOTE_MAP.put(3, "E6");
        NOTE_MAP.put(4, "F6");
        NOTE_MAP.put(5, "G6");
        NOTE_MAP.put(6, "A6");
        NOTE_MAP.put(7, "B6");
        NOTE_MAP.put(8, "Db6");
        NOTE_MAP.put(9, "Eb6");
        NOTE_MAP.put(11, "Gb6");
        NOTE_MAP.put(12, "Ab6");
        NOTE_MAP.put(13, "Bb6");
        SOUND_MAP.put(6, NOTE_MAP);
    }
    public AudioSoundPlayer(Context context){
        this.context = context;
        threadMap = new SparseArray<>();
    }

    public void playNote(int octave, int note) {
        if (!isNotePlaying(octave, note)) {
            PlayThread thread = new PlayThread(octave, note);
            thread.start();
            tempThreadMap = new SparseArray<>();
            tempThreadMap.put(note, thread);
            threadMap.put(octave, tempThreadMap);
        }
    }

    public void stopNote(int octave, int note) {

        if (threadMap.get(octave) != null && threadMap.get(octave).get(note) != null) {
            threadMap.get(octave).remove(note);
            threadMap.remove(octave);
        }
    }

    public boolean isNotePlaying(int octave, int note) {
        return threadMap.get(octave) != null && threadMap.get(octave).get(note) != null;
    }

    private class PlayThread extends Thread{
        private int octave;
        private int note;
        AudioTrack audioTrack;

        public PlayThread(int octave, int note){
            this.octave = octave;
            this.note = note;
        }

        public void run(){
            try{
                String path = SOUND_MAP.get(octave).get(note) + ".wav";
                AssetManager assetManager = context.getAssets();
                AssetFileDescriptor ad = assetManager.openFd(path);
                long fileSize = ad.getLength();
                int bufferSize = 4096;
                byte[] buffer = new byte[bufferSize];

                audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                        AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);

                float logVolume = (float) (1 - (Math.log(MAX_VOLUME - CURRENT_VOLUME) / Math.log(MAX_VOLUME)));
                audioTrack.setStereoVolume(logVolume, logVolume);

                audioTrack.play();

                InputStream audioStream = null;
                int headerOffset = 0x2C;
                long bytesWritten = 0;
                int byteRead = 0;

                audioStream = assetManager.open(path);
                audioStream.read(buffer, 0, headerOffset);
                while(bytesWritten < fileSize - headerOffset){
                    byteRead = audioStream.read(buffer, 0, bufferSize);
                    bytesWritten += audioTrack.write(buffer, 0, byteRead);
                }

                audioTrack.stop();
                audioTrack.release();

            }catch (Exception e){

            }finally{
                if(audioTrack != null){
                    audioTrack.release();
                }
            }
        }
    }
}
