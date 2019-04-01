package com.hao.lib.base.Rx;

import android.util.Log;
import com.hao.lib.base.MI2App;


import java.util.ArrayList;
import java.util.List;

public class Rx {

    private Rx() {
    }

    public static Rx getInstance() {
        return RxHelp.rx;
    }

    private static class RxHelp {
        static final Rx rx = new Rx();
    }

    List<RxMessage> rxMessageList = new ArrayList<>();

    public void sendMessage(final String tag, final Object o) {
        List<RxMessage> errorRx = new ArrayList<>();
        for (final RxMessage rxMessage : rxMessageList) {
            try {
                if (MI2App.getInstance().getNowActivitie() != null) {
                    MI2App.getInstance().getNowActivitie().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("发送消息", (String) o);
                            rxMessage.rxDo(tag, o);
                        }
                    });
                } else {
                    rxMessage.rxDo(tag, o);
                }
            } catch (Exception e) {
                errorRx.add(rxMessage);
            }
        }

        //用于清理出错了的消息
        for (RxMessage errMessage : errorRx) {
            rxMessageList.remove(errMessage);
        }
    }

    public void addRxMessage(RxMessage rxMessage) {
        rxMessageList.add(rxMessage);
    }

    public void remove(RxMessage rxMessage) {
        rxMessageList.add(rxMessage);
    }

    public void removeAll() {
        rxMessageList.clear();
    }

}
