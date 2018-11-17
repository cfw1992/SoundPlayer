package com.work.chenfangwei.sound.downloader;



import android.content.Context;
import android.util.Log;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import java.io.File;

public class FileDownLoaderImpl implements IFileDownLoader {


    public  String TAG="FileDownLoaderImpl";

    public FileDownLoaderImpl(Context context){
        FileDownloader.setup(context);
    }

    @Override
    public void download(String url, String path,final  FileListner fileListner) {
        FileDownloader.getImpl().create(url)
                .setPath(path).setForceReDownload(true)
                .setListener(new FileDownloadListener() {
                    //等待
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e(TAG,"pending=="+soFarBytes);
                    }
                    //下载进度回调
                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e(TAG,"soFarBayte=="+soFarBytes);
                    }
                    //完成下载
                    @Override
                    protected void completed(BaseDownloadTask task) {
                        returnFile(task,fileListner);
                    }
                    //暂停
                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e(TAG,"BaseDownloadTask=="+soFarBytes);
                    }
                    //下载出错
                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        fileListner.error(e);
                    }
                    //已存在相同下载
                    @Override
                    protected void warn(BaseDownloadTask task) {
                        task.cancel();
                    }
                }).start();
    }

    private void returnFile(BaseDownloadTask task, FileListner fileListner) {
        File file=new File(task.getPath());
        fileListner.success(file);
        fileListner.success(file.getAbsolutePath());
    }
}
