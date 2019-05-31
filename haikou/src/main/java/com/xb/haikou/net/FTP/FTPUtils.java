package com.xb.haikou.net.FTP;

import android.os.Environment;
import com.xb.haikou.base.App;
import com.xb.haikou.config.param.BuildConfigParam;
import com.xb.haikou.db.manage.DBManager;

public class FTPUtils {
    /**
     * @param fileName 文件名
     * @param ftpPath  ftp路径
     * @param tag      tag
     * @return 下载
     */
    public static boolean download(String fileName, String ftpPath, String tag) {
        BuildConfigParam buildConfigParam = DBManager.checkBuildConfig();
        return new FTP()
                .builder(buildConfigParam.getIp())
                .setPort(buildConfigParam.getPort())
                .setLogin(buildConfigParam.getUser(), buildConfigParam.getPsw())
                .setFileName(fileName)
                .setPath(Environment.getExternalStorageDirectory() + "/")
                .setFTPPath(ftpPath)
                .setTag(tag)
                .download();
    }


    /**
     * @param contentName 包含关键字的
     * @param ftpPath  ftp路径
     * @param tag      tag
     * @return 下载
     */
    public static int downloadContentName(String contentName, String ftpPath, String tag) {
        BuildConfigParam buildConfigParam = DBManager.checkBuildConfig();
        return new FTP()
                .builder(buildConfigParam.getIp())
                .setPort(buildConfigParam.getPort())
                .setLogin(buildConfigParam.getUser(), buildConfigParam.getPsw())
                .setPath(Environment.getExternalStorageDirectory() + "/")
                .setFTPPath(ftpPath)
                .setTag(tag)
                .downContentFile(contentName);
    }
}
