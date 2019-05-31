package com.xb.haikou.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.xb.haikou.base.App;

public class AppUtil {
    /**
     * 随机字符串
     *
     * @param length
     * @return
     */
    public static String Random(int length) {
        char[] ss = new char[length];
        int i = 0;
        while (i < length) {
            int f = (int) (Math.random() * 5);
            if (f == 0)
                ss[i] = (char) ('A' + Math.random() * 26);
            else if (f == 1)
                ss[i] = (char) ('a' + Math.random() * 26);
            else
                ss[i] = (char) ('0' + Math.random() * 10);
            i++;
        }
        return String.valueOf(ss);
    }


    /**
     * 是否有网络
     *
     * @return boolean
     */
    public static boolean checkNetStatus() {
        ConnectivityManager cm = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Boolean isWifiConn = networkInfo.isConnected();
        NetworkInfo networkInfo_ = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        Boolean isMobileConn = networkInfo_.isConnected();
        return isWifiConn || isMobileConn;
    }


    public static int getTranNum() {
        int tranNum = (int) CommonSharedPreferences.get("tranNun", 0);
        if (tranNum >= 99999) {
            tranNum = 0;
        }
        tranNum++;
        CommonSharedPreferences.put("tranNun", tranNum);
        return tranNum;
    }
}
