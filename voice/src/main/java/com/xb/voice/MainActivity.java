package com.xb.voice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.wtsd.voice.VoiceWaveManager;
import com.wtsd.voice.control.IVoiceRecognition;
import com.xb.voice.util.MediaPlayerUtil;
import com.xb.voice.util.VoiceUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    private EditText inputText;
    private EditText inputVoiceText;
    private Button sendData;
    private Button recieveData;
    private TextView result;

    private Button autoSendData;
    private TextView sendCount;
    private TextView recieveCount;
    private Button setVoice;
//    private MediaPlayerUtil mMediaPlayerUtil;
    private static VoiceWaveManager mVoiceWaveManager;
//    private VoiceUtils audioManagerUtil;
    private int recieveC;

    private Button cancelSendData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        init();
//        audioManagerUtil = new VoiceUtils(this);
//        audioManagerUtil.balanceVoice(Integer.parseInt(inputVoiceText.getText().toString()));

    }

    @Override
    protected void onResume() {
        super.onResume();
        mVoiceWaveManager.voiceRecognitionStart();
    }

    private void initViews() {
        inputText = findViewById(R.id.inputText);
        inputVoiceText = findViewById(R.id.inputVoiceText);
        sendData = findViewById(R.id.sendData);
        sendData.setOnClickListener(this);
        recieveData = findViewById(R.id.recieveData);
        recieveData.setOnClickListener(this);
        result = findViewById(R.id.result);
        autoSendData = findViewById(R.id.autoSendData);
        autoSendData.setOnClickListener(this);
        sendCount = findViewById(R.id.sendCount);
        recieveCount = findViewById(R.id.recieveCount);
        setVoice = findViewById(R.id.setVoice);
        setVoice.setOnClickListener(this);
        cancelSendData = findViewById(R.id.cancelSendData);
        cancelSendData.setOnClickListener(this);
    }

    private void init() {
//        mMediaPlayerUtil = new MediaPlayerUtil(this);
        mVoiceWaveManager = new VoiceWaveManager();
        //初始化发音
        mVoiceWaveManager.initVoicePlayer(this, null);
        //初始化接收
//        mVoiceWaveManager.initVoiceRecognition(this, new IVoiceRecognition.RecognitionCallback() {
//            @Override
//            public void start() {
//                Log.e(TAG, "initVoiceRecognition --->start");
//                result.setText("");
//            }
//
//            @Override
//            public void result(String s) {
//                Log.e(TAG, "initVoiceRecognition --->result");
//                Log.e(TAG, "result：" + s);
//                //mMediaPlayerUtil.startAlarm();
//                result.setText(BinarytoString(String.valueOf(s)));
//                result.setText(String.valueOf(s));
//                recieveC++;
//                recieveCount.setText(String.valueOf(recieveC));
//            }
//        });
    }

    public static String BinarytoString(String binary) {
        String[] tempStr = binary.split(" ");
        char[] tempChar = new char[tempStr.length];
        for (int i = 0; i < tempStr.length; i++) {
            tempChar[i] = BinstrToChar(tempStr[i]);
        }
        return String.valueOf(tempChar);
    }

    //将二进制转换成字符
    public static char BinstrToChar(String binStr) {
        int[] temp = BinstrToIntArray(binStr);
        int sum = 0;
        for (int i = 0; i < temp.length; i++) {
            sum += temp[temp.length - 1 - i] << i;
        }
        return (char) sum;
    }

    //将二进制字符串转换成int数组
    public static int[] BinstrToIntArray(String binStr) {
        char[] temp = binStr.toCharArray();
        int[] result = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            result[i] = temp[i] - 48;
        }
        return result;
    }


    @Override
    protected void onPause() {
        super.onPause();
        mVoiceWaveManager.stopSendData();
        mVoiceWaveManager.voiceRecognitionStop();
    }

    private String getRandomData() {
        long l = System.currentTimeMillis();
        String s = String.valueOf(l);
        return String.valueOf(l).substring(s.length() - 10, s.length());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVoiceWaveManager.unInitVoicePlayer();
        mVoiceWaveManager.uniniVoiceRecognition();
    }

    String scan = "123124";

    public static void sendDate(String str) {
        Log.i("发送数据", "str=" + str);
        mVoiceWaveManager.sendData(str);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendData:
//                String text = getRandomData();
//                inputText.setText(text);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String string = "测试";
//                        String[] strings = new String[string.length()];
//                        for (int i = 0; i < string.length(); i++) {
//                            strings[i] = string.substring(i, i + 1);
//                        }
//                        for (int i = 0; i < strings.length; i++) {
//                            char[] strChar = strings[i].toCharArray();
//                            String text = "";
//                            for (int j = 0; j < strChar.length; j++) {
//                                Log.i("char", "i=" + strChar[j]);
//                                try {
//                                    Thread.sleep(1000);
//                                    Log.i("char", "二进制=" + Integer.toBinaryString(strChar[j]));
//                                    BigInteger bi = new BigInteger(Integer.toBinaryString(strChar[j]), 2);
//                                    Log.i("char", "十进制=" + bi);
//                                    mVoiceWaveManager.sendData(bi + "");
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    }
//                }).start();
//                Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.i("tag", "发送声音");
//                        mVoiceWaveManager.sendData(scan + "");
//                    }
//                }, 1, 5, TimeUnit.SECONDS);
                mVoiceWaveManager.sendData("12312313");
                //为了保证准确率 默认发送2次
                break;
            case R.id.recieveData:
                if ("开始接受数据".equals(recieveData.getText())) {
                    mVoiceWaveManager.voiceRecognitionStart();
                    recieveData.setText("停止接收数据");
                    result.setText("");
                } else {
                    recieveData.setText("开始接受数据");
                    mVoiceWaveManager.voiceRecognitionStop();
                }
                break;
            case R.id.autoSendData:
                String text1 = getRandomData();
                inputText.setText(text1);
                //发送5次
                mVoiceWaveManager.sendData(text1, 5);
                break;
            case R.id.cancelSendData:
                mVoiceWaveManager.stopSendData();
                break;
            case R.id.setVoice:
//                audioManagerUtil.balanceVoice(Integer.parseInt(inputVoiceText.getText().toString()));
                break;
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}