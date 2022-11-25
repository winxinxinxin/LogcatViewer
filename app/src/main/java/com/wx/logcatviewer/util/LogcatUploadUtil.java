package com.wx.logcatviewer.util;

import android.content.Context;
import android.util.Log;

import com.hjq.logcat.LogSaveListener;
import com.hjq.logcat.LogcatControl;
import com.hjq.logcat.LogcatSetting;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Desc: logcat日志 上传日志服务器
 * <p>
 * Author: wx
 * Date: 2022-11-08
 * Updater:
 * Update Time:
 * Update Comments:
 */
public class LogcatUploadUtil {
    private String TAG = "LogcatUploadUtil";
    private static LogcatUploadUtil mLogcatUploadUtil;
    private String logSaveFileDir;
    //    private LogUnityServerUtil mLogUnityServerUtil = new LogUnityServerUtil();
//    private FTPUtil mFTPUtil = new FTPUtil();

    public static LogcatUploadUtil getInstance() {
        if (mLogcatUploadUtil == null) {
            mLogcatUploadUtil = new LogcatUploadUtil();
        }
        return mLogcatUploadUtil;
    }

    public void initLogUpload(Context context) {
        logSaveFileDir = context.getExternalFilesDir("Log").getAbsolutePath();

        LogcatSetting logcatSetting = LogcatControl.getLogcatSetting();
        logcatSetting.getBuild().setLogSaveListener(mLogSaveListener);
//                .setLogcatNotifyEntranceOpen(false)
//                .setLogcatWindowEntranceOpen(true);

        LogcatControl.setLogcatSetting(logcatSetting);
    }

    /**
     * 日志保存监听
     */
    private LogSaveListener mLogSaveListener = new LogSaveListener() {
        @Override
        public String provideFilePath(String logData) {
            String fileName = "android_log_" + new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date()) + ".log";
            String filePath = logSaveFileDir + "/" + fileName;
            return filePath;
        }

        @Override
        public void onFinishSave(boolean isSucc, String filePath) {
            Log.d(TAG, "onFinishSave isSucc=" + isSucc);
            File file = new File(filePath);
            Log.d(TAG,
                    "save to: " + filePath + ";" + (file.exists() ? file.length() : "false"));
            //有日志保存触发 就触发上传
//            mLogUnityServerUtil.uploadToServer(logData, fileName, filePath);
//            mFTPUtil.uploadFileAsync(new File(filePath));

        }
    };
}

