package com.hao.mi2.base;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class MI2Activity extends AppCompatActivity {


    public void stratActivityNoEl(Class<? extends Activity> a) {
        startActivity(new Intent(this, a));
    }


}
