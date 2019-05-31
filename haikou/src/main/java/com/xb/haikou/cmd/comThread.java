package com.xb.haikou.cmd;

import android.os.Handler;
import android.util.Log;
import com.szxb.jni.SerialCom;
import com.xb.haikou.base.App;

import static com.szxb.jni.SerialCom.SerialComRead;


/**
 * Created by lilei on 18-1-24.
 */

public class comThread extends Thread {
    static final String TAG = "comThread";
    final static int maxLen = 1024;

    final static byte[] recvBuf = new byte[maxLen];


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        SerialCom.SerialComClose();
    }

    public comThread(Handler nHandler) {

        SerialCom.SerialComOpen();
    }

    public comThread() {
        SerialCom.SerialComOpen();
    }

    public int snedData(byte[] writeBuf, int len) {
        return SerialCom.SerialComWrite(writeBuf, len);
    }


    @Override
    public void run() {
        int ret = 0;
        while (true) {
            try {

                Log.i("线程028b", "运行");
                ret = SerialComRead(recvBuf, 5000);
                if (0 > ret) {
                    Log.i(TAG, "SerialComRead err");

                } else if (0 == ret) {
                    Log.i(TAG, "SerialComRead timeOut");
                } else {

                    devCmd mycmd = new devCmd();
                    if (mycmd.checkRecvData(recvBuf, ret) == 0) {
                        mycmd.doCmd();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("comThread",
                        "run(comThread.java:118)" + e.toString());
            }
        }
    }


}
