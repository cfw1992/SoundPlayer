package com.work.chenfangwei.sound.media;

import java.util.ArrayList;
import java.util.List;

public class CacheIdFactory {
    private static List<CacheId>cacheArray=new ArrayList<>();

    public static CacheId createCache(int id,PlayConfig playConfig){
        CacheId cacheId=new CacheId(id,playConfig);
        cacheArray.add(cacheId);
        return cacheId;
    }

    public static CacheId find(int id){
        for(CacheId cacheId:cacheArray){
            if(cacheId.getId()==id){
                return cacheId;
            }
        }
        return null;
    }

    public static void  remove(int id){
        for(CacheId cacheId:cacheArray){
            if(cacheId.getId()==id){
                cacheArray.remove(cacheId);
            }
        }
    }


    public static void  remove(CacheId id){
        cacheArray.remove(id);
    }

    public static void updatePrepare(int id,boolean isPrepare){
        for(CacheId cacheId:cacheArray){
            if(cacheId.getId()==id){
               cacheId.setPrepare(isPrepare);
            }
        }
    }

    public static void clear(){
        cacheArray.clear();
    }
}
