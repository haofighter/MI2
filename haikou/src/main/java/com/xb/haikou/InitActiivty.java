package com.xb.haikou;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.hao.lib.base.MI2Activity;
import com.xb.haikou.base.App;
import com.xb.haikou.config.InitConfig;

public class InitActiivty extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().addActivity(this);
        setContentView(R.layout.activity);
        InitConfig.initBin();
    }

}
