package com.hao.show;

import android.os.Bundle;
import android.util.Log;
import com.google.gson.Gson;
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
    }

}
