package com.hao.mivoice.util;

import android.app.Service;
import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

/**
 * @Version: V1.0
 * @Date 18-6-25 上午11:35.
 * @Author: hushentao Email :120406097@qq.com
 * @Description:
 */
public class VoiceUtils {
    private static final String TAG = VoiceUtils.class.getSimpleName();
    Context context;
    AudioManager mAudioManager;

    public VoiceUtils(Context context) {
        this.context = context;
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) context.getSystemService(Service.AUDIO_SERVICE);
        }
    }

    /***
     * 增大音量
     */
    public void addVoide() {
        dealCallVoice(true);
        dealSystemVoice(true);
        dealRingVoice(true);
        dealMediaVoice(true);
        dealMessageVoice(true);
    }


    /***
     * 减少声音
     */
    public void reduceVoide() {
        dealCallVoice(false);
        dealSystemVoice(false);
        dealRingVoice(false);
        dealMediaVoice(false);
        dealMessageVoice(false);
    }

    /**
     * @param voice 　　　１－－－15
     */
    public void balanceVoice(int voice) {
        if (voice < 0 || voice > 15) {
            throw new RuntimeException("voice must >1 and <15");
        }
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) context.getSystemService(Service.AUDIO_SERVICE);
        }
        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Log.i(TAG, "===处理前提示音音量====" + current + "  /" + max);
        //  int newA = (int) (a * max / 10.0f);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, voice, 0);
        int maxdeal = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentdeal = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Log.e(TAG, "===处理前后提示音音量====" + currentdeal + "  /" + maxdeal);


    }

    /**
     * 提示音设置
     *
     * @param b
     */
    private void dealMessageVoice(boolean b) {
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) context.getSystemService(Service.AUDIO_SERVICE);
        }
        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        int current = mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        Log.i(TAG, "===处理前提示音音量====" + current + "  /" + max);
        if (b) {
            if (current == max) {
                return;
            }
            mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, current + 1, 0);
        } else {
            if (current < 1) {
                return;
            }
            mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, current - 1, 0);
        }
        int maxdeal = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        int currentdeal = mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        Log.e(TAG, "===处理前后提示音音量====" + currentdeal + "  /" + maxdeal);
    }

    /***
     * 处理媒体音量
     * @param b
     */
    private void dealMediaVoice(boolean b) {
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) context.getSystemService(Service.AUDIO_SERVICE);
        }
        int mediamax = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int mediacurrent = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Log.i(TAG, "===处理前媒体音量====" + mediacurrent + "  /" + mediamax);
        if (b) {
            if (mediacurrent == mediamax) {
                return;
            }
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mediacurrent + 1, 0);
        } else {
            if (mediacurrent < 1) {
                return;
            }
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mediacurrent - 1, 0);
        }
    }

    /***
     * 铃声音量
     * @param b
     */
    private void dealRingVoice(boolean b) {
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) context.getSystemService(Service.AUDIO_SERVICE);
        }
        int ringmax = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
        int ringcurrent = mAudioManager.getStreamVolume(AudioManager.STREAM_RING);
        Log.i(TAG, "===处理前铃声音量====" + ringcurrent + "  /" + ringmax);
        if (b) {
            if (ringcurrent == ringmax) {
                return;
            }
            mAudioManager.setStreamVolume(AudioManager.STREAM_RING, ringcurrent + 1, 0);
        } else {
            if (ringcurrent < 1) {
                return;
            }
            mAudioManager.setStreamVolume(AudioManager.STREAM_RING, ringcurrent - 1, 0);
        }
    }

    /***
     * 处理系统音量
     * @param b
     */
    private void dealSystemVoice(boolean b) {
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) context.getSystemService(Service.AUDIO_SERVICE);
        }
        int sysmax = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        int syscurrent = mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        Log.i(TAG, "===设置前系统音量====" + syscurrent + "  /" + sysmax);
        if (b) {
            if (syscurrent == sysmax) {
                return;
            }
            mAudioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, syscurrent + 1, 0);
        } else {
            if (syscurrent < 1) {
                return;
            }
            mAudioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, syscurrent - 1, 0);
        }
    }

    /***
     *  处理通话音量
     * @param isAdd
     * true 是音量+
     * false 是音量-
     */
    private void dealCallVoice(boolean isAdd) {
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) context.getSystemService(Service.AUDIO_SERVICE);
        }
        int callmax = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
        int callcurrent = mAudioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
        Log.e(TAG, "=====设置前通话音量==" + callcurrent + "/" + callmax);
        if (isAdd) {
            if (callcurrent == callmax) {
                return;
            }
            mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, callcurrent + 1, 0);
        } else {
            if (callcurrent < 1) {
                return;
            }
            mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, callcurrent - 1, 0);
        }
    }


    /***
     * 静音
     */
    public void stopMediaVoice() {
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) context.getSystemService(Service.AUDIO_SERVICE);
        }
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);  //设置媒体音量为 0
    }
}
