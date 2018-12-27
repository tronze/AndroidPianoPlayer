package com.sogang.capstone2.pianoplayer.piano;

import android.graphics.Color;
import android.support.annotation.ColorInt;

public class Key {

    private int sound; // 1~7 도 ~ 시
    private int octave; // 옥타브
    private @ColorInt  int color;
    private Boolean die = false; // true: white key, false: black key
    private Boolean down = false; // true: 누른거 , false: 안누른거


    public Key(int sound, int octave, @ColorInt int color, Boolean die) {
        this.sound = sound;
        this.octave = octave;
        this.color = color;
        this.die = die;
    }

    public int getSound() {
        if (die)
            return sound + 6;
        else
            return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public int getOctave() {
        return octave;
    }

    public void setOctave(int octave) {
        this.octave = octave;
    }

    public Boolean getDie() {
        return die;
    }

    public void setDie(Boolean die) {
        this.die = die;
    }

    public Boolean getDown() {
        return down;
    }

    public void setDown(Boolean down) {
        this.down = down;
    }

    public void initColor() {
        if (die)
            color = Color.BLACK;
        else
            color = Color.WHITE;
    }

    @ColorInt
    public int getColor() {
        return color;
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
    }
}
