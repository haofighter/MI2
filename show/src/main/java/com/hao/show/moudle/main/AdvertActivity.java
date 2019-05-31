package com.hao.show.moudle.main;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.hao.lib.Util.ToastUtils;
import com.hao.lib.Util.TypeFaceUtils;
import com.hao.lib.base.MI2Activity;
import com.hao.show.R;
import com.hao.show.base.App;

public class AdvertActivity extends MI2Activity {
    SurfaceView show_advert;
    ImageView change_voice;
    TextView go_to_main;
    boolean isNeedVoice = true;
    Uri uri = Uri.parse("android.resource://" + App.getInstance().getPackageName() + "/" + R.raw.overlay);
    Uri uri1 = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);

        initVidio();
    }

    MediaPlayer player;

    private void initVidio() {
        try {
            show_advert = findViewById(R.id.show_advert);
            change_voice = findViewById(R.id.change_voice);
            go_to_main = findViewById(R.id.go_to_main);
            go_to_main.setTypeface(TypeFaceUtils.getHKSVZT(this));
            go_to_main.setTextColor(App.getInstance().getMi2Theme().getTextColorResuoce());
            go_to_main.setText("跳过");
            go_to_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(MainActivity.class);
                    finish();
                }
            });

            player = new MediaPlayer();
            player.setDataSource(this, uri1);

            change_voice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setVoice(!isNeedVoice);
                }
            });

            show_advert.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    player.setDisplay(holder);
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {

                }
            });

            player.prepare();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.setLooping(true);
                    player.start();
                }
            });

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                }
            });

        } catch (Exception e) {
            ToastUtils.INSTANCE.showMessage("播发视频出错了");
        }
    }


    @Override
    protected void initDrawView(View view) {

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.release();
        }
    }

    public void setVoice(boolean isneedVoice) {
        isNeedVoice = isneedVoice;
        if (isneedVoice) {
            player.setVolume(1f, 1f);
            change_voice.setImageResource(R.mipmap.voice);
        } else {
            player.setVolume(0f, 0f);
            change_voice.setImageResource(R.mipmap.no_voice);
        }
    }

}
