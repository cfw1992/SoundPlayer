package com.work.chenfangwei.sound.downloader;

import java.io.File;

public  interface FileListner{
    public void success(File file);
    public void success(String path);
    public void error(Throwable throwable);
}
