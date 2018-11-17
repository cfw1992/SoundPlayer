package com.work.chenfangwei.sound.entity;

import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.work.chenfangwei.sound.media.CacheId;

import java.io.File;


/**
 * Created by chenfangwei on 2018/11/16.
 */

public class SoundResource {
    private String TAG="SoundResource";

    private String path;
    private AssetFile assetFile;
    private int state;

    private boolean isReload;

    public static final int STATE_FILE=1;
    public static final int STATE_ASSETS=2;
    public static final int STATE_NULL=3;

    public SoundResource(File file){
    if(file!=null){
        path=file.getAbsolutePath();
    }
        state=STATE_FILE;
   }
    public SoundResource(String path){
        this.path=path;
        state=STATE_FILE;
   }
    public SoundResource(Resources resources,String assetsPath){
        assetFile=new AssetFile(resources,assetsPath);
        state=STATE_ASSETS;
    }
    public int load(ILoader loader){
        if (!TextUtils.isEmpty(path)) {
            return loader.load(path);
        }
        if (assetFile!=null&&assetFile.getAssetFile()!=null) {
            return loader.load(assetFile.getAssetFile());
        }

        Log.e(TAG,"no resource found");
        return 0;
    }

    public SoundResource setPath(String path) {
        this.path = path;
        return this;
    }

    public int readState(){
        return state;
    }
    public String key(){
        if(state==STATE_FILE){
            return path;
        }else{
            return assetFile.getPath();
        }
    }

    public void clear(){
        if(state==STATE_FILE){
            path=null;
        }else{
             assetFile.close();
        }
    }

    public boolean isReload() {
        return isReload;
    }

    public SoundResource setReload(boolean reload) {
        isReload = reload;
        return this;
    }

    public interface ILoader{
        public int  load(String path);
        public int  load(AssetFileDescriptor fileDescriptor);
    }
}
