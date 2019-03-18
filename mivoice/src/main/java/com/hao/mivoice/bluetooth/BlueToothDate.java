package com.hao.mivoice.bluetooth;

import android.bluetooth.BluetoothDevice;
import com.hao.mivoice.jdy_type.JDY_type;

public class BlueToothDate {
    BluetoothDevice device;
    int rssi;
    byte[] scanRecord;
    JDY_type m_tyep;

    public BlueToothDate(BluetoothDevice device, int rssi, byte[] scanRecord, JDY_type m_tyep) {
        this.device = device;
        this.rssi = rssi;
        this.scanRecord = scanRecord;
        this.m_tyep = m_tyep;
    }

    public int get_vid(BlueToothDate blueToothDate) {
        String vid = null;
        byte[] byte1000 = (byte[]) blueToothDate.scanRecord;
        byte[] result = new byte[4];
        result[0] = 0X00;
        result[1] = 0X00;
        result[2] = 0X00;
        JDY_type tp = blueToothDate.m_tyep;
        if (tp == JDY_type.JDY || tp == JDY_type.JDY_LED1 || tp == JDY_type.JDY_LED2 || tp == JDY_type.JDY_AMQ || tp == JDY_type.JDY_KG || tp == JDY_type.JDY_KG1 || tp == JDY_type.JDY_WMQ || tp == JDY_type.JDY_LOCK) {
            result[3] = byte1000[19 - 6];
        } else {
            result[3] = byte1000[56];
        }

        int ii100 = byteArrayToInt1(result);
        //vid=String.valueOf(ii100);
        return ii100;
    }

    public int byteArrayToInt1(byte[] bytes) {
        int value = 0;
        //由高位到低位
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;//往高位游
        }
        return value;
    }




}
