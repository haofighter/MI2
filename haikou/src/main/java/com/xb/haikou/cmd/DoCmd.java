package com.xb.haikou.cmd;


import android.util.Log;
import com.hao.lib.Util.FileUtils;
import com.hao.lib.Util.Type;
import com.hao.lib.base.Rx.Rx;
import com.xb.haikou.config.ConfigContext;
import com.xb.haikou.moudle.function.card.PraseCard;
import com.xb.haikou.moudle.function.scan.ScanManage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.szxb.jni.SerialCom.SerialComWrite;
import static java.lang.System.arraycopy;

public class DoCmd {
    private static final String TAG = DoCmd.class.getSimpleName();


    public static BlockingQueue<devCmd> queue = new LinkedBlockingQueue<>(3);

    public static void doHeart(devCmd myCmd) {
        Log.d(TAG, "doHeart");
        Log.d(TAG, "len = " + myCmd.getnRecvLen());
    }

    public static void doQRcode(devCmd myCmd) {
        byte[] qrcode = new byte[myCmd.getnRecvLen()];
        arraycopy(myCmd.getDataBuf(), 0, qrcode, 0, myCmd.getnRecvLen());
        //进行二维码流程
        ScanManage.getInstance().scanRe(qrcode);
    }

    public static String doGetVersion() {
        devCmd verCmd = new devCmd();
        verCmd.setCla((byte) 0x81);
        verCmd.setIns((byte) 0);
        verCmd.setDataBuf(null);
        verCmd.setnRecvLen(0);

        byte[] sendCmd = verCmd.packageData();

        SerialComWrite(sendCmd, sendCmd.length);

        try {
            devCmd bean = queue.poll(2, TimeUnit.SECONDS);
            if (bean != null && bean.getS() == 0) {
                Log.d(TAG, "doGetVersion");
                return new String(bean.getDataBuf());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "doGetVersion err");

        return null;
    }

    public static void doCard(devCmd myCmd) {
        try {
            byte[] cardDate = new byte[myCmd.getnRecvLen()];
            arraycopy(myCmd.getDataBuf(), 0, cardDate, 0, myCmd.getnRecvLen());
            new PraseCard().praseCardDate(cardDate);
            Log.d(TAG, "刷卡 = " + FileUtils.byte2Parm(cardDate, Type.HEX));
        } catch (Exception e) {
            Log.i("错误", "doCard:" + e.getMessage());
        }
    }

    //设置时间
    public static String setTime() {

        devCmd verCmd = new devCmd();
        verCmd.setCla((byte) 0x82);


        return null;
    }


    //消费获取交易记录
    public static devCmd getPayRecord(byte[] date) {
        devCmd verCmd = new devCmd();
        verCmd.setCla((byte) 0x86);
        verCmd.setIns((byte) 0x41);
        verCmd.setDataBuf(date);
        verCmd.setnRecvLen(date.length);
        byte[] sendCmd = verCmd.packageData();
        SerialComWrite(sendCmd, sendCmd.length);
        try {
            devCmd bean = queue.poll(2000, TimeUnit.MILLISECONDS);
            if (bean != null && bean.getS() == 0) {
                Log.d(TAG, "doGetVersion");
                return bean;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    //mac2卡校验
    public static devCmd checkMac(byte[] date) {
        devCmd verCmd = new devCmd();
        verCmd.setCla((byte) 0x86);
        verCmd.setIns((byte) 0x43);
        verCmd.setDataBuf(date);
        verCmd.setnRecvLen(date.length);
        byte[] sendCmd = verCmd.packageData();
        SerialComWrite(sendCmd, sendCmd.length);
        try {
            devCmd bean = queue.poll(2000, TimeUnit.MILLISECONDS);
            if (bean != null && bean.getS() == 0) {
                Log.d(TAG, "doGetVersion");
                return bean;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    //银联
    public static devCmd checkUnion(byte[] date) {

        devCmd verCmd = new devCmd();
        verCmd.setCla((byte) 0x86);
        verCmd.setIns((byte) 0x3f);
        verCmd.setDataBuf(date);
        verCmd.setnRecvLen(date.length);
        byte[] sendCmd = verCmd.packageData();
        SerialComWrite(sendCmd, sendCmd.length);
        try {
            devCmd bean = queue.poll(2000, TimeUnit.MILLISECONDS);
            if (bean != null && bean.getS() == 0) {
                Log.d(TAG, "doGetVersion");
                return bean;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return null;
    }


    //复位PSAM卡命令
    public static devCmd resetPSAM() {
        devCmd verCmd = new devCmd();
        verCmd.setCla((byte) 0x85);
        verCmd.setIns((byte) 0x3e);
        verCmd.setDataBuf(null);
        verCmd.setnRecvLen(0);
        byte[] sendCmd = verCmd.packageData();
        SerialComWrite(sendCmd, sendCmd.length);
        try {
            devCmd bean = queue.poll(2, TimeUnit.SECONDS);
            if (bean != null && bean.getS() == 0 && bean.getDataBuf() != null) {
                Log.d(TAG, "doGetVersion");
                return bean;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    //银联卡命令
    public static devCmd sendUnion(byte[] date) {
        devCmd verCmd = new devCmd();
        verCmd.setCla((byte) 0x86);
        verCmd.setIns((byte) 0x3F);
        verCmd.setDataBuf(date);
        verCmd.setnRecvLen(date.length);
        byte[] sendCmd = verCmd.packageData();
        SerialComWrite(sendCmd, sendCmd.length);
        try {
            devCmd bean = queue.poll(2, TimeUnit.SECONDS);
            if (bean != null && bean.getS() == 0 && bean.getDataBuf() != null) {
                Log.d(TAG, "doGetVersion");
                return bean;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    //PSAM卡复位返回  如果无数据则表示未识别到PSAM卡
    public static void doPSAM(devCmd devCmd) {
        try {
            if (devCmd != null) {
                byte[] PASMresult = new byte[devCmd.getnRecvLen()];
                arraycopy(devCmd.getDataBuf(), 0, PASMresult, 0, PASMresult.length);

                int i = 0;
                //选择卡槽 固定00
                byte[] slot = new byte[1];
                arraycopy(PASMresult, i, slot, 0, slot.length);
                i += slot.length;

                //终端编号
                byte[] PosID = new byte[6];
                arraycopy(PASMresult, i, PosID, 0, PosID.length);
                i += PosID.length;
                String posID = (String) FileUtils.byte2Parm(PosID, Type.HEX);

                //PSAM卡号
                byte[] SerialNum = new byte[10];
                arraycopy(PASMresult, i, SerialNum, 0, SerialNum.length);
                i += SerialNum.length;
                String serialNum = (String) FileUtils.byte2Parm(SerialNum, Type.HEX);

                ////密钥索引 有卡01
                byte[] Key_index = new byte[10];
                arraycopy(PASMresult, i, Key_index, 0, Key_index.length);
                i += Key_index.length;
                String key_index = (String) FileUtils.byte2Parm(Key_index, Type.HEX);

            }

        } catch (Exception e) {
            Log.i("错误", "PASM卡复位失败");
        }
    }


    static long keyTime = 0;

    public static void doKeyPress(devCmd devCmd) {
        try {
            Log.i("按键", "doKeyPress(DoCmd.java:237)" + FileUtils.bytesToHexString(devCmd.getDataBuf()));

            if (Math.abs(keyTime - System.currentTimeMillis()) < 300) {
                return;
            }
            String keycode = FileUtils.bytesToHexString(devCmd.getDataBuf());
            if (keycode.equals("01000000")) {
                keycode = ConfigContext.KEY_BUTTON_TOP_LEFT;
            } else if (keycode.equals("00010000")) {
                keycode = ConfigContext.KEY_BUTTON_TOP_RIGHT;
            } else if (keycode.equals("00000100")) {
                keycode = ConfigContext.KEY_BUTTON_BOTTOM_RIGHT;
            } else if (keycode.equals("00000001")) {
                keycode = ConfigContext.KEY_BUTTON_BOTTOM_LEFT;
            }
            Rx.getInstance().sendMessage("key", keycode);
            keyTime = System.currentTimeMillis();
        } catch (Exception e) {
            Log.i("错误", "doKeyPress(DoCmd.java:237)" + e.getMessage());
        }

    }
}
