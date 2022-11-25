package com.hjq.logcat;

public interface LogSaveListener {
    /**
     * 日志保存路径
     *
     * @param logData 将要保存的日志数据，可自行处理
     * @return 如果要sdk保存 就传路径。传空的话 sdk不会进行保存
     */
    public String provideFilePath(String logData);

    /**
     * 保存完成回调
     *
     * @param isSucc
     * @param filePath
     */
    public void onFinishSave(boolean isSucc, String filePath);
}
