package com.work.chenfangwei.sound.media;

import android.content.res.Resources;

import com.work.chenfangwei.sound.entity.SoundResource;

/**
 * Created by chenfangwei on 2018/10/27.
 */
public interface IAudioPlayer {
   public void play(SoundResource resources, final PlayConfig playConfig);
   public void play(SoundResource resources);
   public void start();
   public void release();
   public void stop();
   public void resume();
   public void pause();
   public void onDestrory();
}
