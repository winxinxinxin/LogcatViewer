package com.hjq.logcat;

import android.os.Build;

/**
 * 配置项
 */
public class LogcatSetting {
    private LogSaveListener mLogSaveListener;
    /** 通知栏入口 */
    private boolean mLogcatNotifyEntranceOpen = false;
    /** 悬浮窗入口 */
    private boolean mLogcatWindowEntranceOpen = false;
    private Build mBuild = new Build();

    public Build getBuild() {
        return mBuild;
    }

    public LogSaveListener getLogSaveListener() {
        return mLogSaveListener;
    }

    public boolean isLogcatNotifyEntranceOpen() {
        return mLogcatNotifyEntranceOpen;
    }

    public boolean isLogcatWindowEntranceOpen() {
        return mLogcatWindowEntranceOpen;
    }


    public class Build{

        public Build() {
        }

        public Build setLogSaveListener(LogSaveListener logSaveListener) {
            mLogSaveListener = logSaveListener;
            return mBuild;
        }

        public Build setLogcatNotifyEntranceOpen(boolean logcatNotifyEntranceOpen) {
            mLogcatNotifyEntranceOpen = logcatNotifyEntranceOpen;
            return mBuild;
        }

        public Build setLogcatWindowEntranceOpen(boolean logcatWindowEntranceOpen) {
            mLogcatWindowEntranceOpen = logcatWindowEntranceOpen;
            return mBuild;
        }
    }


}
