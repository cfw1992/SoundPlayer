package com.work.chenfangwei.soundplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.work.chenfangwei.sound.entity.SoundResource;
import com.work.chenfangwei.sound.media.IAudioPlayer;
import com.work.chenfangwei.sound.media.PlayConfig;
import com.work.chenfangwei.sound.media.SmallSoundPlayer;
import com.work.chenfangwei.sound.media.SoundListner;


public class MainActivity extends AppCompatActivity {
    private IAudioPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        soundPlayer= new SmallSoundPlayer.SoundPlayerBuilder(this).setMaxMusicLoadNum(2).setMaxStream(10).build();
    }
    public void openSound(View v){
        soundPlayer.play(new SoundResource(getResources(),"Fight26.wav"));
        soundPlayer.play(new SoundResource(getResources(),"Fight29.wav"));
        soundPlayer.play(new SoundResource(getResources(),"Fight37.wav"));

       PlayConfig playConfig=PlayConfig.defaultConfig(this);
        playConfig.setSoundListner(new SoundListner() {
            @Override
            public void state(int state) {
                Log.e("===","state=="+state+"&thread=="+Thread.currentThread().getName());
            }
        });
        soundPlayer.play(new SoundResource("http://hanguo.yunbaozhibo.com/data/upload/20181024/5bcfe2772b1b7.mp3"),playConfig);
        soundPlayer.play(new SoundResource("http://hanguo.yunbaozhibo.com/data/upload/20181024/5bd01617637a4.mp3"),playConfig);
        soundPlayer.play(new SoundResource(getResources(),"Fight59.wav"));
        soundPlayer.play(new SoundResource(getResources(),"Fight59.wav"));
        //soundPlayer.play(new SoundResource(getResources(),"Fight1003.wav"));
    }

    public void openNew(View v){
        startActivity(new Intent(this,Main2Activity.class));
    }
}
