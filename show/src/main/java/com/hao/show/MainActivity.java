package com.hao.show;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.hao.mi2.net.OkHttpManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new OkHttpManager<MusicInfo>().setNetType(OkHttpManager.NetType.Post)
                .addFromParam("s", "孙子涵")
                .addFromParam("offset", "1")
                .addFromParam("limit", "1")
                .addFromParam("type", "1")
                .setUrl("http://music.163.com/api/search/pc")
//                .setRClass(MusicInfo.class)
                .setNetBack(new OkHttpManager.NetCallBack<MusicInfo>() {
                    @Override
                    public void suc(MusicInfo o) {
                        ((TextView)findViewById(R.id.tv)).setText(o.getCode());
                    }

                    @Override
                    public void fai(Exception e) {

                    }
                });


    }
}
