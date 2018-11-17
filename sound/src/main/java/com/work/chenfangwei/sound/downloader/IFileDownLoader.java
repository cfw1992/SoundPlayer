package com.work.chenfangwei.sound.downloader;

/**
 * Created by chenfangwei on 2018/10/27.
 */

public interface IFileDownLoader {
  public void download(String url, String basePath, FileListner fileListner);

}
