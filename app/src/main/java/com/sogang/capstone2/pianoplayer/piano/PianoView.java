package com.sogang.capstone2.pianoplayer.piano;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.sogang.capstone2.pianoplayer.AudioSoundPlayer;
import com.sogang.capstone2.pianoplayer.sheet.PianoNote;
import com.sogang.capstone2.pianoplayer.sheet.PianoSheetController;
import com.sogang.capstone2.pianoplayer.sheet.TestSheet;

import java.util.ArrayList;

public class PianoView extends View {

    public static final int NB = 14;

    private int keyWidth, height;
    private Paint black, yellow, white, keyPainter, textPainter;

    private ArrayList<KeyView> blacks;
    private ArrayList<KeyView> whites;
    private ArrayList<Key> currentKeys;

    private PianoController pianoController;
    private PianoSheetController pianoSheetController;
    private AudioSoundPlayer soundPlayer;

    public PianoView (Context context, AttributeSet attrs) {
        super(context, attrs);
        whites = new ArrayList<>();
        blacks = new ArrayList<>();
        currentKeys = new ArrayList<>();

        black = new Paint();
        black.setColor(Color.BLACK);
        white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);
        yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.FILL);
        keyPainter = new Paint();
        keyPainter.setStyle(Paint.Style.FILL);
        textPainter = new Paint();
        textPainter.setColor(Color.RED);
        textPainter.setTextAlign(Paint.Align.CENTER);

        pianoController = new PianoController(context);
        pianoSheetController = new PianoSheetController(new TestSheet(pianoController, context));
        soundPlayer = new AudioSoundPlayer(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        keyWidth = w / NB;
        height = h;

        Key key;
        for (int i = 0; i < NB; i++) {
            int left = i * keyWidth;
            int right = left + keyWidth;

            if (i == NB - 1) {
                right = w;
            }

            key = pianoController.getPiano().getKeyAt((i + 1) / 8 + pianoController.getStartOctave(), (i + 1 < 8 ? i + 1 : i - 6));

            RectF rect = new RectF(left, 0, right, h);
            whites.add(new KeyView(key, rect));

            if (i != 0  &&   i != 3  &&  i != 7  &&  i != 10) {
                rect = new RectF((float) (i - 1) * keyWidth + 0.5f * keyWidth + 0.25f * keyWidth, 0,
                        (float) i * keyWidth + 0.25f * keyWidth, 0.67f * height);
                blacks.add(new KeyView(pianoController.getPiano().getKeyAt((i + 1) / 8 + pianoController.getStartOctave(), (i + 1 < 8 ? i + 1 : i - 6) + 6), rect));
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (KeyView kv : whites) {
            keyPainter.setColor(kv.getKey().getColor());
            canvas.drawRect(kv.getRect(), (kv.getKey().getDown() ? yellow : keyPainter));
            textPainter.setTextSize(kv.getRect().width() / 4);
            if (pianoSheetController.getCurrentNote() != null && pianoSheetController.getCurrentNote().getFingerNumber(kv.getKey()) != null)
                canvas.drawText(String.valueOf(pianoSheetController.getCurrentNote().getFingerNumber(kv.getKey())), kv.getRect().width() / 2 + kv.getRect().left, kv.getRect().height() / 10 * 9 + kv.getRect().top, textPainter);
        }

        for (int i = 1; i < NB; i++) {
            canvas.drawLine(i * keyWidth, 0, i * keyWidth, height, black);
        }

        for (KeyView kv : blacks) {
            keyPainter.setColor(kv.getKey().getColor());
            canvas.drawRect(kv.getRect(), (kv.getKey().getDown() ? yellow : keyPainter));
            textPainter.setTextSize(kv.getRect().width() / 3);
            if (pianoSheetController.getCurrentNote() != null && pianoSheetController.getCurrentNote().getFingerNumber(kv.getKey()) != null)
                canvas.drawText(String.valueOf(pianoSheetController.getCurrentNote().getFingerNumber(kv.getKey())), kv.getRect().width() / 2 + kv.getRect().left, kv.getRect().height() / 10 * 9 + kv.getRect().top, textPainter);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        boolean isDownAction = action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE;
        int octave, note;

        for (int touchIndex = 0; touchIndex < event.getPointerCount(); touchIndex++) {
            float x = event.getX(touchIndex);
            float y = event.getY(touchIndex);

            Key k = keyForCoords(x,y);

            if (k != null) {
                k.setDown(isDownAction);
            }
        }

        ArrayList<KeyView> tmp = new ArrayList<>(whites);
        tmp.addAll(blacks);

        for (KeyView kv : tmp) {
            octave = kv.getKey().getOctave();
            note = kv.getKey().getSound();
            if (kv.getKey().getDown()) {
                currentKeys.add(kv.getKey());
                if (!soundPlayer.isNotePlaying(octave, note)) {
                    soundPlayer.playNote(octave, note);
                    invalidate();
                } else {
                    releaseKey(kv.getKey());
                }
            } else {
                soundPlayer.stopNote(octave, note);
                releaseKey(kv.getKey());
            }
        }

        return true;
    }

    private Key keyForCoords(float x, float y) {
        for (KeyView kv : blacks) {
            if (kv.getRect().contains(x,y)) {
                return kv.getKey();
            }
        }

        for (KeyView kv : whites) {
            if (kv.getRect().contains(x,y)) {
                return kv.getKey();
            }
        }

        return null;
    }

    private void releaseKey(final Key k) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                k.setDown(false);
                handler.sendEmptyMessage(0);
                pianoSheetController.getNextRightNote(currentKeys);
                currentKeys.clear();
            }
        }, 100);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            invalidate();
        }
    };

}
