# LogcatView

open logcatActivity in app to show log 

在这个库的基础上修改来的https://github.com/getActivity/Logcat
日志页面打开时即时读取一次 日志，读取后不再循环读取。支持保存、上传日志自定义

#### 集成步骤

* 在 `build.gradle` 文件中加入

```groovy
    repositories {
    maven { url 'https://jitpack.io' }
}
```

* 配置完远程仓库后，在项目 app 模块下的 `build.gradle` 文件中加入远程依赖

```groovy
dependencies {
    debugImplementation 'com.github.winxinxinxin:LogcatViewer:Tag'
    implementation 'com.github.getActivity:XToast:8.5'
}
```

#### 入口配置

* 框架默认提供了两种入口

    * 通知栏入口

    * 悬浮窗入口

* 入口默认的规则：在有通知栏权限的情况下，会优先使用通知栏入口，否则则会显示悬浮窗入口

* 如何修改默认的规则？可在清单文件中加入以下配置即可

```xml

<manifest>

    <application>

        <!-- 悬浮窗入口 -->
        <meta-data android:name="LogcatWindowEntrance" android:value="false" />

        <!-- 通知栏入口 -->
        <meta-data android:name="LogcatNotifyEntrance" android:value="true" />

    </application>

</manifest>
```

#### 日志保存、上传配置
```java
LogcatSetting logcatSetting=LogcatControl.getLogcatSetting();
        logcatSetting.getBuild().setLogSaveListener(mLogSaveListener);
        LogcatControl.setLogcatSetting(logcatSetting);

/**
 * 日志保存监听
 */
private LogSaveListener mLogSaveListener=new LogSaveListener(){
@Override public String
        provideFilePath(String logData){
        String fileName="android_log_"+new SimpleDateFormat(" yyyyMMddHHmmss",Locale.getDefault()).format(new Date())+".log";
        String filePath=logSaveFileDir+"/"+fileName;
        return filePath;
        }

@Override
public void onFinishSave(boolean isSucc,String filePath){
        Log.d(TAG,"onFinishSave isSucc="+isSucc);
        File file=new File(filePath);
        Log.d(TAG,"save to: "+filePath+";"+(file.exists()?file.length():"false"));
        //保存成功后 触发上传
        // mFTPUtil.uploadFileAsync(new File(filePath));

        }
};
```

感谢：https://github.com/getActivity/Logcat