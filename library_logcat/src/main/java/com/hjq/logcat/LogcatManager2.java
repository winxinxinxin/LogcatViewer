package com.hjq.logcat;


import android.util.Log;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/Logcat
 * time   : 2020/01/24
 * desc   : 日志管理类
 * doc    : https://developer.android.google.cn/studio/command-line/logcat
 */
@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
final class LogcatManager2 {

    /**
     * 日志捕捉监听对象
     */
    private static volatile Callback sCallback;
    /**
     * 日志捕捉标记
     */
    private static volatile boolean FLAG_WORK;
    /**
     * 备用存放集合
     */
    private static final List<LogcatInfo> LOG_BACKUP = new ArrayList<>();

    /**
     * 开始捕捉
     */
    static void start(Callback callback) {
        FLAG_WORK = true;
        new Thread(new LogRunnable()).start();
        sCallback = callback;
    }

    /**
     * 停止捕捉
     */
    static void destroy() {
        FLAG_WORK = false;
        // 把监听对象置空，不然会导致内存泄漏
        sCallback = null;
    }

    /**
     * 清空日志
     */
    static void clear() {
        try {
            new ProcessBuilder("logcat", "-c").start();
        } catch (IOException ignored) {
        }
    }

    /**
     * 创建 Logcat 日志缓冲区
     */
    private static BufferedReader createLogcatBufferedReader() throws IOException {

        Process process1 = Runtime.getRuntime().exec("logcat -b main -G 1M");
        Process process2 = Runtime.getRuntime().exec("logcat -b main -d");

        return new BufferedReader(new InputStreamReader(process2.getInputStream()));
    }

    /**
     * 关闭流
     */
    private static void closeStream(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class LogRunnable implements Runnable {

        @Override
        public void run() {
            BufferedReader reader = null;

            String line;
            if (reader == null) {
                try {
                    Log.d("logcat", "start createLogcatBufferedReader");
                    reader = createLogcatBufferedReader();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
            Log.d("logcat", "has createLogcatBufferedReader");
            while (true) {
                try {
                    line = reader.readLine();
                } catch (Exception e) {
                    Log.d("logcat", "readLine exception");
                    e.printStackTrace();
                    closeStream(reader);
                    break;
                }
                if (line == null) {
                    Log.d("logcat", "line == null");
                    // 正常情况讲，line 是不会为空的，因为没有新的日志前提下 reader.readLine() 会阻塞读取
                    // 但是在某些特殊机型（vivo iQOO 9 Pro Android 12）上面会出现，在没有新的日志前提下，会返回 null
                    // 并且等待一会儿再读取还不行，无论循环等待多次，因为原先的流里面已经没有东西了，要读取新的日志必须创建新的流
                    closeStream(reader);
                    reader = null;
                    break;
                } else {
                    if (LogcatInfo.IGNORED_LOG.contains(line)) {
                        continue;
                    }
                    LogcatInfo info = LogcatInfo.create(line);
                    if (info == null) {
                        continue;
                    }
                    if (!FLAG_WORK) {
                        // 这里可能会出现下标异常
                        LOG_BACKUP.add(info);
                        continue;
                    }

                    final Callback callback = sCallback;
                    if (callback != null) {
                        callback.onReceiveLog(info);
                    }
                }
            }

        }
    }

    public interface Callback {

        /**
         * 收到日志
         */
        void onReceiveLog(LogcatInfo info);
    }
}