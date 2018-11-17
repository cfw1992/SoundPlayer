package com.work.chenfangwei.sound.media;

import android.content.Context;
import android.media.AudioManager;

public class PlayConfig {

    private int number;
    private int rate;
    private int priority;
    private float leftVolumnRatio;
    private float rightVolumnRatio;
    private SoundListner weak;

    public static PlayConfig defaultConfig(Context context){
        AudioManager audioManager=(AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return defaultConfig(audioManager);
    }

    public static PlayConfig defaultConfig(AudioManager audioManager){
        float audioMaxVolumn = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volumnCurrent = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volumnRatio = volumnCurrent / audioMaxVolumn;
        PlayConfig playConfig=new PlayConfig().
                setLeftVolumnRatio(volumnRatio).
                setRightVolumnRatio(volumnRatio).
                setRate(1).
                setPriority(1)
                .setNumber(0);
        return playConfig;
    }


    public int getNumber() {
        return number;
    }
    public PlayConfig setNumber(int number) {
        this.number = number;
        return this;
    }
    public int getRate() {
        return rate;
    }
    public PlayConfig setRate(int rate) {
        this.rate = rate;
        return this;
    }
    public int getPriority() {
        return priority;
    }
    public PlayConfig setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public float getLeftVolumnRatio() {
        return leftVolumnRatio;
    }

    public PlayConfig setLeftVolumnRatio(float leftVolumnRatio) {
        this.leftVolumnRatio = leftVolumnRatio;
        return this;
    }
    public float getRightVolumnRatio() {
        return rightVolumnRatio;
    }

    public PlayConfig setRightVolumnRatio(float rightVolumnRatio) {
        this.rightVolumnRatio = rightVolumnRatio;
        return this;
    }

    public void setSoundListner(SoundListner soundListner){
        weak=soundListner;
    }

    public SoundListner getSoundLisner(){
        return weak;
    }
}
