package com.xb.alipay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.hint);
        textView.setTextSize(24);

        Button b = (Button) findViewById(R.id.play_demo);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IBusCloudSDKDemo demo = new IBusCloudSDKDemo();
                TextView t = (TextView) findViewById(R.id.hint);
                t.setText(demo.play());
            }
        });

    }
}
