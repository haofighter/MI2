package com.xb.haikou.net;

public class NetUrl {


    //    private final static String IP = "http://111.230.85.238";
    private static final String IP = "http://2t183d9338.iask.in:37092/hkbus";


    //腾讯秘钥
    public final static String TENCENT_MAC_KEY = IP + "/interaction/gettxmac";
    //交通部秘钥
    public final static String TRAN_MAC_KEY = IP + "/interaction/getjtmac";
    //交通证书
    public final static String TRAN_CERTVERQUERY = IP + "/interaction/certverquery";
    //腾讯公钥
    public final static String TENCENT_PUBLIC_KEY = IP + "/interaction/getTxpubkey";
    //上传刷卡记录
    public static final String UP_CARD_RECORD = IP + "/interaction/carduploadzb1";
    //上传刷卡记录
    public static final String UP_JTB_RECORD = IP + "/interaction/ownqrc";

    //上传刷卡记录
    public static final String UP_UNION_RECORD = IP + "/interaction/bankjourAll";
    //阿里公钥
    public static final String ALI_PUBLIC_KEY = IP + "/interaction/getAlimainkey";


    //下载线路
    public final static String DOWN_LINE = IP + "/interaction/getLineInfo";

    //校准时间
    public static final String REG_TIME_URL = "http://134.175.56.14/bipeqt/interaction/getStandardTime";

    //TX二维码上传地址
    public static final String UPLOAD_TXSACN = IP + "/interaction/posrecv1";
    public static final String UPLOAD_ALSACN = IP + "/interaction/aliqrc";

//    {
//        if (BuildConfig.isTest) {
//            IP = "http://2t183d9338.iask.in:37092/hkbus";
//            TENCENT_MAC_KEY = IP + "/interaction/getmackey";
//            TENCENT_PUBLIC_KEY = IP + "/interaction/getpubkey";
//        } else {
//            IP = "http://111.230.85.238/bipbus";
//            TENCENT_MAC_KEY = IP + "/interaction/gettxmac";
//            TENCENT_PUBLIC_KEY = IP + "/interaction/getpubkey";
//        }
//    }

    //    public static final String TENCENT_MAC_KEY = IP + "/hkbus/interaction/gettxmac";
    //

//    public static final String TENCENT_PUBLIC_KEY = IP + "/hkbus/interaction/getTxpubkey";
//    public static final String ALI_PUBLIC_KEY = "http://2t183d9338.iask.in:37092/hkbus/interaction/getAlipayPubKey";
//    //小兵济南支付
////    public static final String XBPAY_JINA = IP + "/bippay/interaction/payment";
//    public static final String XBPAY_JINA_WX = "http://2t183d9338.iask.in:37092/hkbus/interaction/wxposrecv";
//    public static final String XBPAY_JINA_AL = "http://2t183d9338.iask.in:37092/hkbus/interaction/zfbposrecv";
//
//    //IC卡上传
//    public static final String IC_CARD_RECORD = IP + "/bipbus/interaction/carduploadzb";
//
//    //银联卡记录
//    public static final String UNION_CARD_RECORD = IP + "/bipbus/interaction/bankjourAll";
//
//    //校准时间
//    public static final String REG_TIME_URL = "http://134.175.56.14/bipeqt/interaction/getStandardTime";


}
