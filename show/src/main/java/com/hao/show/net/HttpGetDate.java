package com.hao.show.net;

import android.util.Log;
import com.google.gson.Gson;
import com.hao.lib.base.BackCall;
import com.hao.lib.net.OkHttpManager;
import com.hao.show.moudle.entity.MusicInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public class HttpGetDate {

    public void getMusicListForSearchConent(final BackCall<MusicInfo> musicInfoBackCall) {
        new OkHttpManager().setNetType(OkHttpManager.NetType.Post)
                .addFromParam("s", "孙子涵")
                .addFromParam("offset", "1")
                .addFromParam("limit", "1")
                .addFromParam("type", "1")
                .setUrl("http://music.163.com/api/search/pc")
                .setNetBack(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        musicInfoBackCall.call(new Gson().fromJson(response.body().string(), MusicInfo.class));
                    }
                });
    }

    public static void testNet(final BackCall backCall) {
        new OkHttpManager().setNetType(OkHttpManager.NetType.Post)
                .setUrl("http://39.96.190.173/bipeqt/interaction/getStandardTime")
                .setNetBack(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                      ResponseBody responseBody= response.body();
                        Log.i("请求返回",responseBody.string());
                    }
                });
    }
}
