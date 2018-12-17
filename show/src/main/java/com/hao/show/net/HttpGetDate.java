package com.hao.show.net;

import com.google.gson.Gson;
import com.hao.mi2.base.BackCall;
import com.hao.mi2.net.OkHttpManager;
import com.hao.show.moudle.entity.MusicInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
}
