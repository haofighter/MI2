package com.xb.voice.task;

import android.util.Log;
import com.szxb.jni.libszxb;
import com.xb.voice.MainActivity;

public class LoopScanTask implements Runnable {
    @Override
    public void run() {
        Log.i("扫码", "线程开启");
        byte[] recv = new byte[1024];
        int barcode = 0;
        try {
            barcode = libszxb.getBarcode(recv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("扫码", "barcode=" + barcode);
        if (barcode > 0) {
            String result = new String(recv, 0, barcode);
            MainActivity.sendDate(result);
        }


    }
}
