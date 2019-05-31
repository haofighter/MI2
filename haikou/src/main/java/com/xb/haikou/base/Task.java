package com.xb.haikou.base;

import com.hao.lib.Util.ThreadUtils;
import com.xb.haikou.record.RecordUpload;

import java.util.concurrent.TimeUnit;

public class Task {

    public static void runTask() {

        ThreadUtils.getInstance().
                createSch("upload").

                scheduleWithFixedDelay(new Runnable() {
                    @Override
                    public void run() {
                        RecordUpload.upLoadALScanRecord();
                        RecordUpload.upLoadCardRecord();
                        RecordUpload.upLoadTXScanRecord();
                        RecordUpload.upUnionRecord();
                        RecordUpload.upJTBRecord();
                    }
                }, 10, 30, TimeUnit.SECONDS);

    }


}
