package com.hao.mi2.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.hao.mi2.Util.StatusBarUtil;

public abstract class MI2Activity extends AppCompatActivity {
    protected String MI2TAG = "MI2Activity";

    public String getMI2TAG() {
        return MI2TAG;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MI2App.getInstance().removeActivity(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTranslucent(this);
        super.onCreate(savedInstanceState);
        MI2App.getInstance().addActivity(this);

    }

    public void stratActivityNoEl(Class<? extends Activity> a) {
        startActivity(new Intent(this, a));
    }


}
