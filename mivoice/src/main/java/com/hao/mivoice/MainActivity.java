package com.hao.mivoice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.google.gson.Gson;
import com.hao.mivoice.bluetooth.Command;
import com.hao.mivoice.code.CodeCreate;
import com.hao.mivoice.util.MediaPlayerUtil;
import com.hao.mivoice.util.VoiceUtils;
import com.wtsd.voice.VoiceWaveManager;
import com.wtsd.voice.control.IVoiceRecognition;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    private EditText inputText;
    private EditText inputVoiceText;
    private EditText star_num;
    private EditText command_code;
    private EditText driver_code;
    private Button sendData;
    private Button recieveData;
    private Button create_code;
    private Button create_command_code;
    private TextView result;

    private Button autoSendData;
    private TextView sendCount;
    private TextView recieveCount;
    private Button setVoice;
    private MediaPlayerUtil mMediaPlayerUtil;
    private VoiceWaveManager mVoiceWaveManager;
    private boolean isStop;
    private VoiceUtils audioManagerUtil;
    private int recieveC;

    EditText edit_message;
    ImageView scan_code;
    ImageView scan_code2;
    TextView voice_show;
    String str = "";


    private Button cancelSendData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        init();
        audioManagerUtil = new VoiceUtils(this);
        audioManagerUtil.balanceVoice(Integer.parseInt(inputVoiceText.getText().toString()));

        String address = getIntent().getStringExtra("address");
        if (address != null && !address.equals("")) {
            edit_message.setText("bluetooth" + address);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVoiceWaveManager.voiceRecognitionStart();
    }

    private void initViews() {
        inputText = findViewById(R.id.inputText);
        create_code = findViewById(R.id.create_code);
        create_code.setOnClickListener(this);
        edit_message = findViewById(R.id.edit_message);
        scan_code = findViewById(R.id.scan_code);
        scan_code2 = findViewById(R.id.scan_code2);
        voice_show = findViewById(R.id.voice_show);
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
        cancelSendData = findViewById(R.id.cancelSendData);
        cancelSendData.setOnClickListener(this);


        star_num = findViewById(R.id.star_num);
        command_code = findViewById(R.id.command_code);
        driver_code = findViewById(R.id.driver_code);
        create_command_code = findViewById(R.id.create_command_code);
        create_command_code.setOnClickListener(this);

    }


    //初始化声音回传数据功能
    private void init() {
        mMediaPlayerUtil = new MediaPlayerUtil(this);
        mVoiceWaveManager = new VoiceWaveManager();
        //初始化发音
        mVoiceWaveManager.initVoicePlayer(this, null);
        //初始化接收
        mVoiceWaveManager.initVoiceRecognition(this, new IVoiceRecognition.RecognitionCallback() {
            @Override
            public void start() {
                Log.e(TAG, "initVoiceRecognition --->start");
                result.setText("");
            }

            @Override
            public void result(String s) {

                if (s.startsWith("9")) {
                    String codeStart = str.substring(0, 198);
                    String codeend = str.substring(198 + s.length(), str.length());

                    str = codeStart + s + codeend;
                    new CodeCreate().createQRcodeImage(str, scan_code);
                    voice_show.setText("声音识别：" + String.valueOf(s));
                    Log.i("刷吗返回二维码", s + "          scan_code=" + str);
                }
            }
        });
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
        isStop = true;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendData:
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
                audioManagerUtil.balanceVoice(Integer.parseInt(inputVoiceText.getText().toString()));
                break;

            case R.id.create_code:
                new CodeCreate().createQRcodeImage(edit_message.getText().toString(), scan_code);
                break;
            case R.id.create_command_code:
                Command command = new Command();
                command.setCommandCode(command_code.getText().toString());
                command.setDriverNum(driver_code.getText().toString());
                command.setStar(star_num.getText().toString());
                String com = new Gson().toJson(command);
                com = "scanCommand" + com;
                new CodeCreate().createQRcodeImage(com, scan_code);
                break;
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}