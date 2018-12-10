package com.hao.show;

import android.os.Bundle;
import com.hao.mi2.base.MI2Activity;
import com.hao.mi2.net.OkHttpManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

public class MainActivity extends MI2Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
                        stratActivityNoEl(ShowPPTActivity.class);
                    }
                });


    }
}
