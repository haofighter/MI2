package com.hao.mi2.net;

import android.util.Log;
import com.google.gson.Gson;
import okhttp3.*;
import okio.ByteString;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OkHttpManager<T> extends NetParamHelp {
    private List<RequestFormBodyParam> requestFormBodyParams;
    private List<RequestMultipartBodyParam> requestMultipartBodyParamList;
    private NetBodyType netBodyType = NetBodyType.form;
    private NetType netType;
    private String url = ""; //请求方式
    private Callback netCallBack;
    private Request request;
    private Class clazz;

    //枚举 请求的类型 multipart分块提交 form 表单提交
    private enum NetBodyType {
        multipart, form
    }

    //枚举请求的方式
    public enum NetType {
        Post, Get
    }


    //构造方法初始化参数
    public OkHttpManager() {
        requestFormBodyParams = new ArrayList<>();
        requestMultipartBodyParamList = new ArrayList<>();
    }


    //添加表单提交的参数配置

    public OkHttpManager addFromParam(String name, String content) {
        netBodyType = NetBodyType.form;
        requestFormBodyParams.add(new RequestFormBodyParam(name, content));
        return this;
    }

    //添加分块提交的参数配置
    public OkHttpManager addMultipartParam(RequestMultipartBodyParam requestMultipartBodyParam) {
        netBodyType = NetBodyType.multipart;
        requestMultipartBodyParamList.add(requestMultipartBodyParam);
        return this;
    }


    static class OkHttpHelper {
        public static OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor())
                .build();
    }

    //获取单例的okhttpClient对象
    public static OkHttpClient getOkhttpClient() {
        return OkHttpHelper.okHttpClient;
    }


    //构建Post格式的提交requet
    Request Post() {
        RequestBody body = RequestBody.create(null, "");
        if (netBodyType == NetBodyType.multipart) {
            MultipartBody.Builder builder = new MultipartBody.Builder("AaB03x").setType(MultipartBody.FORM);
            body = builder.build();
            //为数据添加复杂的请求的参数
            for (int i = 0; i < requestMultipartBodyParamList.size(); i++) {
                RequestMultipartBodyParam r = requestMultipartBodyParamList.get(i);
                if (r.mediaContent instanceof byte[]) {
                    body = RequestBody.create(r.mediaType, (byte[]) r.mediaContent);
                } else if (r.mediaContent instanceof File) {
                    body = RequestBody.create(r.mediaType, (File) r.mediaContent);
                } else if (r.mediaContent instanceof String) {
                    body = RequestBody.create(r.mediaType, (String) r.mediaContent);
                } else if (r.mediaContent instanceof ByteString) {
                    body = RequestBody.create(r.mediaType, (ByteString) r.mediaContent);
                }
                if (r.headerName != null) {
                    builder.addPart(Headers.of(r.headerName, r.headerContent), body);
                }
            }
        } else if (netBodyType == NetBodyType.form) {
            //为数据添加复杂的请求的参数
            FormBody.Builder builder = new FormBody.Builder();
            for (int i = 0; i < requestFormBodyParams.size(); i++) {
                RequestFormBodyParam r = requestFormBodyParams.get(i);
                builder.add(r.paramName, r.paramContent);
            }
            body = builder.build();
        }


        if (url.equals("")) {
            new NullPointerException("请求地址为空，请调用SeUrl()");
        }
        return new Request.Builder().url(url).post(body).build();
    }

    //构建GET格式的提交requet
    Request GET() {
        return new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .header("User-Agent", "OkHttp Example")
                .build();
    }


    //设置请求的地址
    public OkHttpManager setUrl(String postUrl) {
        this.url = postUrl;
        return this;
    }

    @Override
    public OkHttpManager setRClass(Class clazz) {
        this.clazz = clazz;
        return this;
    }

    //设置请求返回监听
    public void setNetBack(Callback netCallBack) {
        this.netCallBack = netCallBack;
        buildNet();
    }

    //开始进行请求
    void buildNet() {
        if (netType == NetType.Post) {
            request = Post();
        } else if (netType == NetType.Post) {
            request = GET();
        } else {
            new NumberFormatException("未设置请求方式");
        }
        Call call = getOkhttpClient().newCall(request);
        if (netCallBack != null)//do 设置请求回调
            call.enqueue(netCallBack);
    }

    public OkHttpManager setNetType(NetType netType) {
        this.netType = netType;
        return this;
    }


    //接口回调
    public interface NetCallBack<T> {
        void suc(T t);

        void fai(Exception e);
    }
}
