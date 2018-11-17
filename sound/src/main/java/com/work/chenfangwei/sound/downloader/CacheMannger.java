package com.work.chenfangwei.sound.downloader;

import android.content.Context;
import android.os.Environment;

import com.work.chenfangwei.sound.fliter.DefaultFilter;
import com.work.chenfangwei.sound.fliter.Filter;

import java.io.File;

/**
 * Created by chenfangwei on 2018/10/27.
 */

public class CacheMannger {
private  String basePath=Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator;
private IFileDownLoader fileDownLoader;
private Filter filter=new DefaultFilter();

    public CacheMannger(Context context){
    fileDownLoader=new FileDownLoaderImpl(context);
    }
    public CacheMannger(IFileDownLoader iFileDownLoader, Context context){
        if(iFileDownLoader!=null){
            fileDownLoader=iFileDownLoader;
        }else{
            fileDownLoader=new FileDownLoaderImpl(context);
        }
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public void loadFile(String url, FileListner fileListner){
     File file= getCacheFile(url);
     if(file!=null){
         fileListner.success(file);
         fileListner.success(file.getAbsolutePath());
     }else{
         downLoad(url,fileListner);
     }
   }

    private File getCacheFile(String url) {
        File file=null;
        if(url.contains("http")){
            file=new File(basePath+url);
        }else{
            file=new File(url);
        }
        if(file.exists()){
           return file;
         }
       return null;
    }

    private void downLoad(String url,FileListner fileListner) {
        fileDownLoader.download(url,basePath+filter.createFileName(url),fileListner);
    }

}
