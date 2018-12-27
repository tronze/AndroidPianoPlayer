package com.sogang.capstone2.pianoplayer.sheet;

import java.util.Queue;

public class PianoBar {

	//** ADD - #1**//
	int fingerCount;
	
	Queue<PianoNote> rightQueue;
	Queue<PianoNote> leftQueue;
	
    public PianoBar(int fingerCount, Queue<PianoNote> rightQueue, Queue<PianoNote> leftQueue) {
        this.fingerCount = fingerCount;
        this.rightQueue = rightQueue;
        this.leftQueue = leftQueue;
    }

    public int getFingerCount() {
        return fingerCount;
    }

    public void setFingerCount(int fingerCount) {
        this.fingerCount = fingerCount;
    }

    public Queue<PianoNote> getRightQueue() {
        return rightQueue;
    }

    public void setRightQueue(Queue<PianoNote> rightQueue) {
        this.rightQueue = rightQueue;
    }

    public Queue<PianoNote> getLeftQueue() {
        return leftQueue;
    }

    public void setLeftQueue(Queue<PianoNote> leftQueue) {
        this.leftQueue = leftQueue;
    }

}
