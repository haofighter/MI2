package com.hao.mivoice.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

/**
 * @Version: V1.0
 * @Date 18-5-8 下午1:30.
 * @Author: hushentao Email :120406097@qq.com
 * @Description:
 */
public class MediaPlayerUtil {

    private MediaPlayer mMediaPlayer;
    private Context mContext;

    public MediaPlayerUtil(Context context) {
        mContext = context;
    }

    public void startAlarm() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(mContext, getSystemDefultRingtoneUri());
            mMediaPlayer.setLooping(false);
        }
        try {
            mMediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }

    //获取系统默认铃声的Uri
    private Uri getSystemDefultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(mContext,
                RingtoneManager.TYPE_NOTIFICATION);
    }
}
