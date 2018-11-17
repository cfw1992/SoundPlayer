package com.work.chenfangwei.sound.media;

import android.content.res.Resources;

import com.work.chenfangwei.sound.entity.SoundResource;

/**
 * Created by chenfangwei on 2018/10/27.
 */
public class CacheId {
    private boolean prepare;
    private Integer id;
    private PlayConfig playConfig;
    private SoundResource soundResource;


    public CacheId(Integer id,PlayConfig playConfig) {
        this.id = id;
        this.playConfig=playConfig;
    }

    public boolean isPrepare() {
        return prepare;
    }

    public void setPrepare(boolean prepare) {
        this.prepare = prepare;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PlayConfig getPlayConfig() {
        return playConfig;
    }
    public void setPlayConfig(PlayConfig playConfig) {
        this.playConfig = playConfig;
    }

    public SoundResource getSoundResource() {
        return soundResource;
    }

    public void setSoundResource(SoundResource soundResource) {
        this.soundResource = soundResource;
    }
}
