package com.xb.haikou.voice;

public class VoiceConfig {
    public static final int LOGIN = 1;//上班
    public static final int OUT_LOGIN = 2;//下班
    public static final int SCAN_SUCCESS = 3;//二维码成功
    public static final int IC_RE = 4;//错误
    public static final int IC_BASE = 5;//刷卡成功 当
    public static final int EC_FEE = 6;//错误
    public static final int EC_BALANCE = 7;//超出最大金额
    public static final int EC_RE_QR_CODE = 8;//二维码过期
    public static final int IC_ERROR = 9;//二维码过期
    public static final int IC_INVALID = 10;//卡过期
    public static final int ZHIFU_SUC = 11;//支付成功
    public static final int IC_PUSH_MONEY = 12;//无效卡
    public static final int IC_OVER_TIME = 13;//卡过期
    public static final int IC_NO_ENABLE_TIME = 14;//卡未启用
    public static final int IC_CHECK_TIME = 15;//请年检
    public static final int IC_STUDENT = 16;//学生卡
    public static final int IC_OLD = 17;//老年卡
    public static final int IC_SOUVENIR = 18;//纪念卡
    public static final int IC_CARE = 19;//关爱卡
    public static final int IC_JUNREN = 20;//关爱卡
    public static final int IC_REPEAT = 21;//重复刷卡
    public static final int IC_LLLEGAL = 22;//非法卡
    public static final int IC_GUASHI = 23;//挂失卡
}
