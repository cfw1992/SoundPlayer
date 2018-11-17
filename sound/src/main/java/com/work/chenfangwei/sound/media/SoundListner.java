package com.work.chenfangwei.sound.media;

/**
 * Created by chenfangwei on 2018/11/8.
 */

public interface SoundListner {
  public static final int PLAYING=1;
  public static final int FAIL=-1;
  public void state(int state);
}
