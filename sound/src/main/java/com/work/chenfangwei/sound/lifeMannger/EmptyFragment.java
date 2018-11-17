package com.work.chenfangwei.sound.lifeMannger;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by chenfangwei on 2018/11/6.
 */

public class EmptyFragment extends Fragment implements LifeFragment{
    private LifeListner lifeListner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(lifeListner!=null){
            lifeListner.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(lifeListner!=null){
            lifeListner.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(lifeListner!=null){
            lifeListner.onPause();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if(lifeListner!=null){
            lifeListner.onStop();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(lifeListner!=null){
            lifeListner.onDestroy();
        }
        lifeListner=null;
    }
    public void join(LifeListner lifeListner){
        this.lifeListner=lifeListner;
    }
}
