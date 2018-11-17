package com.work.chenfangwei.sound.util;

import android.util.Log;

/**
 * Created by chenfangwei on 2018/11/17.
 */

public class SoundLog {
  private static boolean isDebug=true;
  public static void debug(boolean isDebug){
      SoundLog.isDebug=isDebug;
  }
  public static void e(String e,Object object){
      if(isDebug){
          Log.e(object.getClass().getSimpleName(),e);
      }
  }
    public static void w(String e,Object object){
        if(isDebug){
            Log.w(object.getClass().getSimpleName(),e);
        }
    }
    public static void d(String e,Object object){
        if(isDebug){
            Log.d(object.getClass().getSimpleName(),e);
        }
    }
    public static void v(String e,Object object){
        if(isDebug){
            Log.v(object.getClass().getSimpleName(),e);
        }
    }
}
