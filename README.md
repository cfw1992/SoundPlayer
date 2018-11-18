# SoundPlayer

   因为公司业务需要完成了一套短音效的处理框架.SoundPlayer是基于androidSoundPool进行一次上层的封装.封装的目的是简洁的调用,屏蔽google的Sndpool的bug,
避免二次书写代码引起的问题风险.
   我对于一个成熟框架的理解是得具有稳定的性能,简洁的调用,和高度的可配置性.所以我在考虑这套框架的设计,尽可能地往这些方面努力,如果有不完善的地方,希望读者能
积极的issue我一下,我会在最快的时间内进行相应的修复和完善.Soundplayer实现了动态的音频资源回收管理,以及自动化的生命周期管理,支持本地文件，网络路径,本地assets
播放.不说那么多了下面介绍下如何使用.
    
   ## gradle集成配置
         
             根gradle: maven{url'https://www.jitpack.io'}
             app gradle：implementation 'com.github.cfw1992:SoundPlayer:1.0.0'
     
    
   ## 1.相关权限,6.0需要申请相关权限
     <uses-permission android:name="android.permission.INTERNET" />
     uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
     uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     
     
  ## 2.初始化player对象
        
     soundPlayer=new SmallSoundPlayer.SoundPlayerBuilder(this).
                setMaxWaitMusic(100).        //设置最大的音乐播放队列,当超过这个限制后,直接丢弃
                setMaxMusicLoadNum(10)      //设置最大的音乐存储数
                .setLifeCycleModel(LifeMode.AUTO) // auto是自动化的生命周期管理,self是自己处理生命周期
                .setiFileDownLoader(new IFileDownLoader() {//自定义下载管理,需要实现自定义的IFileDownLoader接口
                    @Override
                    public void download(String s, String s1, FileListner fileListner) {
                        fileListner.success(s);
                    }
                }).setBasePath("")//设置网络文件缓存路径
        .build();
        
     ** 如果不想配置的话,直接build就可以了,Soundplayer有默认的配置 **  
 
   ## 3.播放音频
       
      
        soundPlayer.play(new SoundResource("http://test.com/sound.mp3"));//播放网路路径
        soundPlayer.play(new SoundResource("android路径/sound.map3"));   //播放本地路径
        soundPlayer.play(new SoundResource(new File("文件路径"))); //播放本地文件
        soundPlayer.play(new SoundResource(getResources(),"sound.mp3"));//播放assets文件音乐

        PlayConfig playConfig=PlayConfig.defaultConfig(this);     
        playConfig.setSoundListner(new SoundListner() {
            @Override
            public void state(int i) {
                if(i==PLAYING){
                    //写上自己的逻辑,回调在主线程,可以处理UI
                }
            }
        });
        soundPlayer.play(new SoundResource("http://test.com/sound.mp3"),playConfig);//需要音频和逻辑代码同步的时候可以调用
        
        PlayConfig配置音频播放相关的一些属性,也可自行配置

       
       
        
       
        
        
     
