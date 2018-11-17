package com.work.chenfangwei.sound.entity;

import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.util.Log;

import com.work.chenfangwei.sound.util.SoundLog;

import java.io.IOException;

/**
 * Created by chenfangwei on 2018/11/16.
 */

public class AssetFile {
    private String TAG="AssetFile";
    private AssetFileDescriptor assetFileDescriptor;
    private String path;

    public AssetFile(Resources resources,String assetsPath){
        try {
            assetFileDescriptor= resources.getAssets().openFd(assetsPath);
            this.path=assetsPath;
            SoundLog.e("AssetFile资源打开成功,path=="+assetsPath,this);

        }catch (Exception e){
            e.printStackTrace();
            SoundLog.e("AssetFile资源打开失败"+"错误信息=="+e.toString(),this);
        }
    }
    public AssetFileDescriptor getAssetFile() {
        return assetFileDescriptor;
    }
    public String getPath() {
        return path;
    }

    public void close(){
        try {
            assetFileDescriptor.close();
            assetFileDescriptor=null;
            SoundLog.e("AssetFile资源关闭成功,path=="+path,this);
        } catch (IOException e) {
            e.printStackTrace();
            SoundLog.e("AssetFile资源关闭失败,path=="+path+"错误信息=="+e.toString(),this);
        }
    }
}
