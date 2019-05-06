/**
 * Project Name:Q6
 * File Name:libtest.java
 * Package Name:com.szxb.jni
 * Date:Apr 13, 20177:37:45 PM
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.
 */

package com.szxb.jni;


import android.content.res.AssetManager;
import android.util.Log;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * ClassName:libtest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Apr 13, 2017 7:37:45 PM <br/>
 *
 * @author lilei
 * @see
 * @since JDK 1.6
 */
public class libszxb {

    static {
        try {
            System.loadLibrary("szxb");
        } catch (Throwable e) {
            Log.e("jni", "i can't find business so!");
            e.printStackTrace();
        }
    }

    static {
        try {
            System.loadLibrary("ymodem");
        } catch (Throwable e) {
            Log.e("jni", "i can't find ymodem so!");
            e.printStackTrace();
        }
    }


    static {
        try {
            System.loadLibrary("halcrypto");
        } catch (Throwable e) {
            Log.e("jni", "i can't find halcrypto so!");
            e.printStackTrace();
        }
    }


    static {
        try {
            System.loadLibrary("QR");
        } catch (Throwable e) {
            Log.e("jni", "i can't find QR so!");
            e.printStackTrace();
        }
    }


    static {
        try {
            System.loadLibrary("QR");
        } catch (Throwable e) {
            Log.e("jni", "i can't find QR so!");
            e.printStackTrace();
        }
    }

    public static final int SM4_BLOCK_SIZE = 16;


    //公交车接口

    public native static void deviceReset();

    public static native int deviceTime(byte[] buf, boolean flag);

    public static native int devicekey(byte[] recv);

    public static native int getEnvironment(byte[] recv);

    public static native int getBarcode(byte[] recv);

    public static native int setNixietube(byte[] nos);

    public static native int setbuzzer(byte status);

    public static native int setLed(byte[] leds);

    public static native String getVersion();

    public static native int getCardInfo(byte[] buf);

    public static native int doTrans(byte[] buf, int len);

    public static native int deviceSerialSetBaudrate(int devNo, int Baudrate);

    public static native int deviceSerialSend(int devNo, byte[] sendBuf, int sendLen);

    public static native int deviceSerialRecv(int devNo, byte[] recvBuf, int timeOut);

    public static native int serialSetBaudrate(int Baudrate);

    public static native int serialSend(byte[] sendBuf, int sendLen);

    public static native int serialRecv(byte[] recvBuf, int timeOut);

    //bQRbuf 验签数据
    //pubKey 压缩公钥 66
    //sig r||s 校验值 128
    public static native int QrVerify(byte[] bQRbuf, String pubKey, String sig);

    public static native int sm3Digest                    (byte[] dataBuf, int len, byte[] digBuf);

    public static native int sm4Crypt(boolean b, byte[] input, byte[] outBuf, byte[] k);


    public static int deviceSettime(int year, int month, int date, int hour,
                                    int min, int sec) {

        byte[] settime = new byte[8];

        settime[0] = (byte) ((year >> 8) & 0xff);
        settime[1] = (byte) (year & 0xff);
        settime[2] = (byte) (month & 0xff);
        settime[3] = (byte) (date & 0xff);
        settime[4] = (byte) (hour & 0xff);
        settime[5] = (byte) (min & 0xff);
        settime[6] = (byte) (sec & 0xff);

        return libszxb.deviceTime(settime, true);
    }

    public static Calendar deviceGettime() {
        int ret;
        byte[] gettime = new byte[8];

        ret = libszxb.deviceTime(gettime, false);
        if (0 == ret) {
            int year = ((gettime[0] << 8) & 0xff00) | (gettime[1] & 0xff);

            return new GregorianCalendar(year, gettime[2] - 1, gettime[3], gettime[4], gettime[5], gettime[6]);
        }
        return null;

    }


    //更新固件
    public static native int ymodemUpdate(AssetManager ass, String filename);

    public static native int ymodemUpdate_ftp(String filename);


    //加密
    public static native String RSA_public_decrypt(String strN, String sInput, int e);

    public static native String Hash1(String inputStr);

    public static native String Hash224(String inputStr);

    public static native byte TripleDES(byte[] pszData, byte[] pszResult, byte[] pszKey, byte nFlag);

    public static native byte SingleDES(byte[] pszSrc, byte[] pszDst, byte[] pszDesKey, byte nFlag);


    //非接接口

    public native static int RFIDModuleOpen();

    public native static int RFIDMoudleClose();

    public native static int RFID_setAnt(int value);

    public native static String MifareGetSNR(byte[] cardType);

    public native static String TypeA_RATS();

    public native static String[] RFID_APDU(String sendApdu);

    public native static int RFIDAuthenCard(byte nBlock, byte keyType, byte[] key);

    public static native int RFIDReadCard(byte nBlock, byte[] buf);

    public static native int RFIDWriteCard(byte nBlock, byte[] buf);

    public static native int RFIDInitValue(byte nBlock, int nMoney);

    public static native int RFIDInctValue(byte nBlock, int nMoney);

    public static native int RFIDDectValue(byte nBlock, int nMoney);

    public static native int RFIDReadValue(byte nBlock, int nMoney);

    public static native int RFIDRestor(byte nSrcBlock, byte nDesBlock);


    //psam卡接口
    public static native int OpenPsamMoudle();

    public static native int ClosePsamModule();

    public static native String psamCardReset(int baud, int slot);

    public static native String[] psamCardSendAPDUT0(int slot, String sendApdu);

    public static native String[] psamCardSendAPDUT0_EX(int slot, String sendApdu);


    static final int SUCCESS = 1;
    static final int MALFORMED_QRCODE = -1;
    static final int QRCODE_INFO_EXPIRED = -2;
    static final int QRCODE_KEY_EXPIRED = -3;
    static final int POS_PARAM_ERROR = -4;
    static final int QUOTA_EXCEEDED = -5;
    static final int NO_ENOUGH_MEMORY = -6;
    static final int SYSTEM_ERROR = -7;
    static final int CARDTYPE_UNSUPPORTED = -8;
    static final int NOT_INITIALIZED = -9;
    static final int ILLEGAL_PARAM = -10;
    static final int PROTO_UNSUPPORTED = -11;
    static final int QRCODE_DUPLICATED = -12;

    public class VERIFY_REQUEST_V2 {
        byte[] qrcode;
        int qrcode_len;
        String pos_param;
        int amount_cent;

        public VERIFY_REQUEST_V2(byte[] qrcode, int qrcode_len, String pos_param, int amount_cent) {
            this.qrcode = qrcode;
            this.qrcode_len = qrcode_len;
            this.pos_param = pos_param;
            this.amount_cent = amount_cent;
        }

        @Override
        public String toString() {
            return "VERIFY_REQUEST_V2{" +
                    "qrcode=" + Arrays.toString(qrcode) +
                    ", qrcode_len=" + qrcode_len +
                    ", pos_param='" + pos_param + '\'' +
                    ", amount_cent=" + amount_cent +
                    '}';
        }
    }

    public class VERIFY_RESPONSE_V2 {
       public int nRet;
        byte[] uid;
        byte[] record;
        byte[] card_no;
        byte[] card_data;
        byte[] card_type;

        public VERIFY_RESPONSE_V2(int nRet, byte[] uid, byte[] record, byte[] card_no, byte[] card_data, byte[] card_type) {
            this.nRet = nRet;
            this.uid = uid;
            this.record = record;
            this.card_no = card_no;
            this.card_data = card_data;
            this.card_type = card_type;
        }
    }

    public static native int init_pos_verify(String key_list, String card_type_list);
    public static native VERIFY_RESPONSE_V2 verify_qrcode_v2(VERIFY_REQUEST_V2 QR_Request);

}
