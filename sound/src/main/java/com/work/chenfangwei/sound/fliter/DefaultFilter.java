package com.work.chenfangwei.sound.fliter;

/**
 * Created by chenfangwei on 2018/11/8.
 */

public class DefaultFilter implements Filter{
    @Override
    public String createFileName(String url) {
        return url;
    }
}
