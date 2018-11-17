package com.work.chenfangwei.sound.lifeMannger;

/**
 * Created by chenfangwei on 2018/11/6.
 */

public interface LifeListner {
    public void onCreate();
    public void onStart();
    public void onResume();
    public void onPause();
    public void onStop();
    public void onDestroy();
}
