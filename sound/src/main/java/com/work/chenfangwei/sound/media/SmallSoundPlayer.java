package com.work.chenfangwei.sound.media;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;
import com.work.chenfangwei.sound.downloader.CacheMannger;
import com.work.chenfangwei.sound.downloader.FileDownLoaderImpl;
import com.work.chenfangwei.sound.downloader.FileListner;
import com.work.chenfangwei.sound.downloader.IFileDownLoader;
import com.work.chenfangwei.sound.entity.SoundResource;
import com.work.chenfangwei.sound.fliter.Filter;
import com.work.chenfangwei.sound.lifeMannger.LifeFragment;
import com.work.chenfangwei.sound.lifeMannger.LifeListner;
import com.work.chenfangwei.sound.lifeMannger.LifeMode;
import com.work.chenfangwei.sound.lifeMannger.VoiceLifeMannger;
import com.work.chenfangwei.sound.util.BindUtil;
import com.work.chenfangwei.sound.util.SoundLog;
import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;

/*
*  make by cfw
* */

public class SmallSoundPlayer implements IAudioPlayer,SoundResource.ILoader{
    public String TAG="SmallSoundPlayer";
    public static final int HANDLER_PLAY_STATE=1;

    private android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==HANDLER_PLAY_STATE){
                if(msg.obj!=null){
                    SoundListner soundListner= (SoundListner) msg.obj;
                    soundListner.state(msg.arg1);
                }
            }
        }
    };

    public static final int MAX_RESOURE_COUNT=256;
    public static final int MAX_PLAY_NUM=32;

    private   boolean loopPlay;
    private   Thread thread;
    private Context context;
    private int maxStream=5;
    private int streamType=AudioManager.STREAM_MUSIC;
    private int srcQuality=0;
    private IFileDownLoader iFileDownLoader;
    private int lifeCycleModel= LifeMode.AUTO;
    private int maxMusicLoadNum=5;
    private int maxWaitMusic=1000;
    private String basePath;
    private Filter filter;

    private SoundPool soundPool;
    private CacheMannger cacheMannger;
    private LinkedBlockingQueue<String>linkedBlockingQueue;
    private LruCache<String, CacheId> lruCache;
    private AudioManager am;

    public SmallSoundPlayer(Context context, Integer maxStream, Integer streamType, Integer srcQuality, IFileDownLoader iFileDownLoader, Integer lifeCycleModel, Integer maxMusicLoadNum, Integer maxWaitMusic, String basePath, Filter fliter) {
        this.context = context;
        this.maxStream = maxStream!=null?maxStream:this.maxStream;
        this.streamType = streamType!=null?streamType:this.streamType;
        this.srcQuality = srcQuality!=null?srcQuality:this.srcQuality;;
        this.iFileDownLoader = iFileDownLoader!=null?iFileDownLoader:new FileDownLoaderImpl(context);;
        this.lifeCycleModel = lifeCycleModel!=null?lifeCycleModel:this.lifeCycleModel;;
        this.maxMusicLoadNum = maxMusicLoadNum!=null?maxMusicLoadNum:this.maxMusicLoadNum;;
        this.maxWaitMusic = maxWaitMusic!=null?maxWaitMusic:this.maxWaitMusic;
        this.basePath = basePath;
        this.filter = fliter;
        init();
    }
    private void initSoundPool() {
        soundPool = new SoundPool(maxStream, AudioManager.STREAM_MUSIC, srcQuality);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                Log.e(TAG,"sampleId=="+sampleId);
                CacheIdFactory.updatePrepare(sampleId,true);
            }
        });
    }
    private void init(){
        initSoundPool();
        cacheMannger=new CacheMannger(iFileDownLoader,context);
        if(!TextUtils.isEmpty(basePath)){
            cacheMannger.setBasePath(basePath);
        }
        if(filter!=null){
            cacheMannger.setFilter(filter);
        }
        linkedBlockingQueue=new LinkedBlockingQueue(maxWaitMusic);
        lruCache = new LruCache<String, CacheId>(maxMusicLoadNum) {
            @Override
            protected int sizeOf(String key, CacheId value) {
                return 1;
            }
            @Override
            protected void entryRemoved(boolean evicted, String key, CacheId oldValue, CacheId newValue) {
                int id=oldValue.getId();
                if(true){
                    Log.e(TAG,"id=="+id+"卸载是否成功==true");
                    SoundResource resource=oldValue.getSoundResource();
                    if(resource!=null){
                        resource.clear();
                    }
                    CacheIdFactory.remove(oldValue);
                }else{
                    newValue=oldValue;
                    Log.e(TAG,"id=="+id+"卸载是否成功==false");
                }
                super.entryRemoved(evicted, key, oldValue, newValue);
            }
        };

        am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if(lifeCycleModel==LifeMode.AUTO){
            LifeFragment lifeFragment= BindUtil.bindLifeFragment(context);
            LifeListner voiceLifeMannger=new VoiceLifeMannger(context,this);
            lifeFragment.join(voiceLifeMannger);
        }
    }

    @Override
    public void play(SoundResource resource, final PlayConfig playConfig) {
        if(resource==null){
            SoundLog.e(" Resource Not Found...",this);
            return;
        }
        String path=resource.key();
        if(TextUtils.isEmpty(path)){
            SoundLog.e(" Path Not Found...",this);
            return;
        }

        if(resource.readState()==SoundResource.STATE_FILE){
            loadByCache(resource,playConfig);
        }else if(resource.readState()==SoundResource.STATE_ASSETS){
            loadByAssets(resource,playConfig);
        }
    }

    private void loadByCache(final SoundResource resource,final PlayConfig playConfig) {
        cacheMannger.loadFile(resource.key(), new FileListner() {
            @Override
            public void success(File file) {
            }
            @Override
            public void success(String path) {
                resource.setPath(path);
                loadPathResource(resource,playConfig);
            }
            @Override
            public void error(Throwable throwable) {
                SoundListner soundListner=playConfig.getSoundLisner();
                if(playConfig.getSoundLisner()!=null){
                    sendState(soundListner,SoundListner.FAIL);
                }
                SoundLog.e(" Throwable=="+throwable.getMessage(),this);
            }
        });
    }
    private void loadByAssets(SoundResource resource,PlayConfig playConfig) {
        loadPathResource(resource,playConfig);
    }

    private void loadPathResource(SoundResource resource,PlayConfig playConfig) {
        if(lruCache.get(resource.key())==null){
            int id=resource.load(this);
            if(id==0){
                SoundListner soundListner=playConfig.getSoundLisner();
                if(soundListner!=null){
                    sendState(soundListner,SoundListner.FAIL);
                }
                SoundLog.e("声音资源加载失败",this);
                return;
            }else if(id==MAX_RESOURE_COUNT/2){
                release();
                initSoundPool();
                id=resource.load(this);
            }
            CacheId cacheId= CacheIdFactory.createCache(id,playConfig);
            cacheId.setSoundResource(resource);
            lruCache.put(resource.key(),cacheId);
            Log.e(TAG,"id=="+id);
        }
        if(linkedBlockingQueue.size()<maxWaitMusic&&!resource.isReload()){
            linkedBlockingQueue.add(resource.key());
        }else{
            SoundLog.e("当前音乐动作已经超过同时最大缓存限制，或者您开启的是预加载模式",this);
        }
    }

    private void sendState(SoundListner soundListner, int state) {
        Message message=Message.obtain();
        message.what=HANDLER_PLAY_STATE;
        message.obj=soundListner;
        message.arg1=state;
        handler.sendMessage(message);
    }

    @Override
    public void play(SoundResource resource) {
        play(resource,PlayConfig.defaultConfig(am));
    }
    @Override
    public void release() {
        releaseSoundPool();
        relaseCache();
    }
    private void relaseCache() {
        lruCache.evictAll();
        CacheIdFactory.clear();
    }
    private void releaseSoundPool() {
        soundPool.setOnLoadCompleteListener(null);
        soundPool.release();
    }
    @Override
    public void stop() {
    }
    @Override
    public void resume() {
        soundPool.autoResume();
    }
    @Override
    public void pause() {
        soundPool.autoPause();
    }
    @Override
    public void onDestrory() {
        loopPlay=false;
        handler=null;
        release();

    }
    private void playSound(int sound,PlayConfig config) {
        soundPool.play(sound,
                config.getLeftVolumnRatio(),// 左声道音量
                config.getRightVolumnRatio(),// 右声道音量
                config.getPriority(), // 优先级
                config.getNumber(),// 循环播放次数
                config.getRate());// 回放速度，该值在0.5-2.0之间 1为正常速度
    }

    public void start(){
        synchronized (SmallSoundPlayer.class){
            loopPlay=true;
            if(thread!=null){
                return;
            }
            thread= new Thread(new Runnable() {
                @Override
                public void run() {
                    startWatePlay();
                }
            });
            thread.start();
        }
    }

    private void startWatePlay() {
        while (loopPlay){
            String key=linkedBlockingQueue.poll();
            if(key==null){
                continue;
            }
            CacheId cacheId=lruCache.get(key);
            if(cacheId==null||!cacheId.isPrepare()){
                linkedBlockingQueue.add(key);
                continue;
            }
            PlayConfig config=cacheId.getPlayConfig();
            if(config==null){
                continue;
            }
            SoundListner soundLisner=config.getSoundLisner();
            if(soundLisner!=null){
                sendState(soundLisner,SoundListner.PLAYING);
            }
            playSound(cacheId.getId(),config);
        }
            thread=null;
    }
    @Override
    public int load(String path) {
        return soundPool.load(path,0) ;
    }
    @Override
    public int load(AssetFileDescriptor fileDescriptor) {
        if(fileDescriptor==null){
            return 0;
        }
        return soundPool.load(fileDescriptor,0) ;
    }


    public static class SoundPlayerBuilder{
       private Integer maxStream;
       private Integer streamType;
       private Integer srcQuality;
       private IFileDownLoader iFileDownLoader;
       private Integer lifeCycleModel;
       private Integer maxMusicLoadNum;
       private Context context;
       private Integer maxWaitMusic;

       private String basePath;
       private Filter fliter;

       public SoundPlayerBuilder(Context context){
           this.context=context;
       }
        public SoundPlayerBuilder setMaxStream(int maxStream) {
           if(maxStream>MAX_PLAY_NUM){
               throw new IllegalStateException("同时播放量不得大于32");
           }
            this.maxStream = maxStream;
            return this;
        }
        public SoundPlayerBuilder setStreamType(int streamType) {
            this.streamType = streamType;
            return this;
        }
        public SoundPlayerBuilder setSrcQuality(int srcQuality) {
            this.srcQuality = srcQuality;
            return this;
        }
        public SoundPlayerBuilder setiFileDownLoader(IFileDownLoader iFileDownLoader) {
            this.iFileDownLoader = iFileDownLoader;
            return this;
        }
        public SoundPlayerBuilder setLifeCycleModel(int lifeCycleModel) {
            this.lifeCycleModel = lifeCycleModel;
            return this;
        }
        public SoundPlayerBuilder setMaxMusicLoadNum(int maxMusicLoadNum) {
            if(maxMusicLoadNum>MAX_RESOURE_COUNT){
                throw new IllegalStateException("当前最大的资源加载量不得大于voicePool的最大允许量256,请重新设置");
            }
            this.maxMusicLoadNum = maxMusicLoadNum;
            return this;
        }
        public SoundPlayerBuilder setMaxWaitMusic(int maxWaitMusic) {
            this.maxWaitMusic = maxWaitMusic;
            return this;
        }
        public SoundPlayerBuilder setFliter(Filter fliter) {
            this.fliter = fliter;
            return this;
        }
        public SoundPlayerBuilder setBasePath(String basePath) {
            this.basePath = basePath;
            return this;
        }
        public SmallSoundPlayer build(){
           return new SmallSoundPlayer(context,maxStream,streamType,srcQuality,iFileDownLoader,lifeCycleModel,maxMusicLoadNum,maxWaitMusic,basePath,fliter);
        }
    }
}
