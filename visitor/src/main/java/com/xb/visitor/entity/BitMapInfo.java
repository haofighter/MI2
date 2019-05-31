package com.xb.visitor.entity;

import android.graphics.Bitmap;
import com.xb.visitor.Mqtt.MqttInfo;

public class BitMapInfo {
    Bitmap bitmap;
    MqttInfo mqttInfo;

    public BitMapInfo(Bitmap bitmap, MqttInfo mqttInfo) {
        this.bitmap = bitmap;
        this.mqttInfo = mqttInfo;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public MqttInfo getMqttInfo() {
        return mqttInfo;
    }

    public void setMqttInfo(MqttInfo mqttInfo) {
        this.mqttInfo = mqttInfo;
    }
}
