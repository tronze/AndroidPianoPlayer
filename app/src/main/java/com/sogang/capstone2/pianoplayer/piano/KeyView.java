package com.sogang.capstone2.pianoplayer.piano;

import android.graphics.RectF;

public class KeyView {
    private Key key;
    private RectF rect;

    public KeyView(Key key, RectF rect) {
        this.key = key;
        this.rect = rect;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public RectF getRect() {
        return rect;
    }

    public void setRect(RectF rect) {
        this.rect = rect;
    }
}
