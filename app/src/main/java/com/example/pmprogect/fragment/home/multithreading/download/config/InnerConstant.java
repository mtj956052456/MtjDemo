package com.example.pmprogect.fragment.home.multithreading.download.config;


/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/18 21:23
 * @since 1.0.0
 */
public class InnerConstant {

    //Db 数据库中用到的字段
    public static class Db {
        public static final String id = "id";
        public static final String downloadUrl = "downloadUrl";
        public static final String filePath = "filePath";
        public static final String size = "size";
        public static final String downloadLocation = "downloadLocation";
        public static final String downloadStatus = "downloadStatus";

        public static final String NAME_TABALE = "download_info";
        public static final String NAME_DB = "download.Db";
    }

    //请求参数中用到的信息
    public static class Request{
        public static final int loading = 10; //下载状态
        public static final int pause = 11; //暂停状态
    }

    public static class Inner{
        public static final String SERVICE_INTENT_EXTRA = "service_intent_extra";
    }

    /**
     * 下载过程会通过发送广播, 广播通过intent携带文件数据的 信息。
     * intent 的key值就是该字段
     * eg : FileInfo fileInfo = (FileInfo) intent.getSerializableExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD);
     */
    public static final String EXTRA_INTENT_DOWNLOAD = "yaoxiaowen_download_extra";
}
