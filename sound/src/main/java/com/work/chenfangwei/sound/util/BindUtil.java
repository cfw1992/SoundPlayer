package com.work.chenfangwei.sound.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import com.work.chenfangwei.sound.lifeMannger.EmptyFragment;
import com.work.chenfangwei.sound.lifeMannger.EmptyV4Fragment;
import com.work.chenfangwei.sound.lifeMannger.LifeFragment;

/**
 * Created by chenfangwei on 2018/11/8.
 */

public class BindUtil {
  public static LifeFragment bindLifeFragment(Context context){
      if(context instanceof FragmentActivity){
          EmptyV4Fragment emptyV4Fragment=new EmptyV4Fragment();
          addAcitvity(context,emptyV4Fragment);
          return emptyV4Fragment;
      }else {
          EmptyFragment emptyFragment=new EmptyFragment();
          addAcitvity(context,emptyFragment);
          return emptyFragment;
      }
  }

  public static void addAcitvity(Context context,Fragment fragment){
      Activity activity= (Activity) context;
      FragmentTransaction fragmentTransaction=activity.getFragmentManager().beginTransaction();
      fragmentTransaction.add(android.R.id.content,fragment);
      fragmentTransaction.commit();
  }
    public static void addAcitvity(Context context,android.support.v4.app.Fragment fragment){
        FragmentActivity fragmentActivity= (FragmentActivity) context;
        android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(android.R.id.content,fragment);
        fragmentTransaction.commit();
   }
}
