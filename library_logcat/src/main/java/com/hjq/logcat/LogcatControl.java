package com.hjq.logcat;

public class LogcatControl {
    private static LogcatSetting mLogcatSetting = new LogcatSetting();

    public static LogcatSetting getLogcatSetting() {
        if (mLogcatSetting == null) {
            mLogcatSetting = new LogcatSetting();
        }
        return mLogcatSetting;
    }

    public static void setLogcatSetting(LogcatSetting logcatSetting) {
        mLogcatSetting = logcatSetting;
    }
}
