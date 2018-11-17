package com.work.chenfangwei.sound.lifeMannger;

import android.content.Context;
import com.work.chenfangwei.sound.media.IAudioPlayer;

/**
 * Created by chenfangwei on 2018/11/8.
 */

public class VoiceLifeMannger implements LifeListner {
    private IAudioPlayer voicePlayer;
    private Context mContext;
    private boolean havePlayer;

    public VoiceLifeMannger(Context context,IAudioPlayer voicePlayer){
        this.mContext=context;
        this.voicePlayer=voicePlayer;
        build(context,voicePlayer);
    }
    public VoiceLifeMannger(){

    }
    public void build(Context context,IAudioPlayer voicePlayer){
        this.mContext=context;
        this.voicePlayer=voicePlayer;
        if(voicePlayer==null){
            throw new NullPointerException("IAudioPlayer不能为null,请检查你的对象是否完整");
        }
    }
    @Override
    public void onCreate() {
    }
    @Override
    public void onStart() {
        voicePlayer.start();
    }
    @Override
    public void onResume() {
        voicePlayer.resume();
    }
    @Override
    public void onPause() {
        voicePlayer.pause();
    }
    @Override
    public void onStop() {
        voicePlayer.stop();
    }
    @Override
    public void onDestroy() {
        voicePlayer.onDestrory();
    }
}
