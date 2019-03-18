package com.hao.mivoice;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import com.hao.mivoice.bluetooth.BluetoothActivity;
import com.hao.mivoice.util.AcpCallBack;
import com.hao.mivoice.util.AcpUtil;


/**
 * @Version: V1.0
 * @Date 18-4-28 下午5:21.
 * @Author: hushentao Email :120406097@qq.com
 * @Description:
 */
public class LauncherActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new FrameLayout(this));
        open();
    }

    @Override
    public void onClick(View v) {
    }

    /**
     * 必须需要录音和存储权限
     */
    private void open() {
        AcpUtil.doAcp(this, new AcpCallBack() {
            @Override
            public void doAcp() {
//                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                Intent intent = new Intent(LauncherActivity.this, BluetoothActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void canelAcp() {

            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO);

    }


}
