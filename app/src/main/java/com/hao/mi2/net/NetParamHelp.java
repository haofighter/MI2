package com.hao.mi2.net;

import okhttp3.Request;

abstract class NetParamHelp {
    abstract NetParamHelp setUrl(String string);

    abstract OkHttpManager setRClass(Class clazz);

    abstract Request Post();

    abstract Request GET();

    abstract void buildNet();


}
