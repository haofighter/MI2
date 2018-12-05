package com.hao.mi2.net;

import okhttp3.Request;

abstract class NetParamHelp {
    abstract NetParamHelp setUrl(String string);

    abstract void setNetBack(OkHttpManager.NetCallBack netCallBack);

    abstract Request Post();

    abstract Request GET();

    abstract void buildNet();


}
