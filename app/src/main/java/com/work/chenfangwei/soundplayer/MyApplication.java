package com.work.chenfangwei.soundplayer;

import android.app.Application;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by chenfangwei on 2018/11/17.
 */

public class MyApplication extends Application{
    @Override
    public
  void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
