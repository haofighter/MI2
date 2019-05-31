package com.xb.haikou.moudle.init;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.hao.lib.Util.FileUtils;
import com.hao.lib.Util.ThreadUtils;
import com.hao.lib.base.Rx.Rx;
import com.hao.lib.base.Rx.RxMessage;
import com.szxb.jni.SerialCom;
import com.xb.haikou.R;
import com.xb.haikou.base.App;
import com.xb.haikou.cmd.DoCmd;
import com.xb.haikou.cmd.comThread;
import com.xb.haikou.cmd.devCmd;
import com.xb.haikou.config.AppRunConfigEntity;
import com.xb.haikou.config.ConfigContext;
import com.xb.haikou.config.InitConfig;
import com.xb.haikou.db.manage.DBManager;
import com.xb.haikou.moudle.Main2Activity;
import com.xb.haikou.util.BusToast;

import java.util.concurrent.TimeUnit;

import static java.lang.System.arraycopy;

public class InitActiivty extends AppCompatActivity implements RxMessage {
    EditText update_info;
    AnimationDrawable drawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().addActivity(this);
        setContentView(R.layout.activity);
        initView();
        initRx();
        new InitConfig().initBin()//加载bin文件
                .getWxMacKey()//获取微信秘钥
                .getWXPublicKey()//获取微信公钥
                .getALPublicKey()//获取支付宝公钥
//                .getTranPublicKey()//交通部
//                .getCertverquery()
        ;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void resetPSAM() {
        devCmd psamDate = DoCmd.resetPSAM();//重置PSAM／
        if (psamDate != null) {
            byte[] psamInfo = new byte[psamDate.getnRecvLen()];
            arraycopy(psamDate.getDataBuf(), 0, psamInfo, 0, psamInfo.length);

            int i = 0;
            //选择卡槽
            byte[] Slot = new byte[1];
            arraycopy(psamDate.getDataBuf(), i, Slot, 0, Slot.length);
            i += Slot.length;
            String slot = FileUtils.bytesToHexString(Slot);

            //终端号
            byte[] PosID = new byte[6];
            arraycopy(psamDate.getDataBuf(), i, PosID, 0, PosID.length);
            i += PosID.length;
            String posID = FileUtils.bytesToHexString(PosID);
            AppRunConfigEntity appRunConfigEntity = DBManager.checkRunConfig();
            appRunConfigEntity.setPSAMID(posID);


            //PSAM卡号
            byte[] SerialNum = new byte[10];
            arraycopy(psamDate.getDataBuf(), i, SerialNum, 0, SerialNum.length);
            i += SerialNum.length;
            String serialNum = FileUtils.bytesToHexString(SerialNum);
            appRunConfigEntity.setPSAM(serialNum);

            //密钥索引
            byte[] Key_index = new byte[1];
            arraycopy(psamDate.getDataBuf(), i, Key_index, 0, Key_index.length);
            String key_index = FileUtils.bytesToHexString(Key_index);
            appRunConfigEntity.setPSAMSY(key_index);

            DBManager.updateConfig(appRunConfigEntity);
        } else {
            BusToast.showToast("PSAM卡重置数据获取失败", false);
        }
    }

    private void initView() {
        ImageView progress = findViewById(R.id.progress);
        drawable = (AnimationDrawable) progress.getBackground();
        drawable.start();
        ((TextView) findViewById(R.id.tip_info)).setText(String.format("温馨提示:\n\t\t\t\t%1$s", ConfigContext.tip()));
        update_info = findViewById(R.id.update_info);
        update_info.setText("正在进行初始化\n");
    }

    private void initRx() {
        Rx.getInstance().addRxMessage(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Rx.getInstance().remove(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (drawable != null) {
            drawable.stop();
        }
    }

    @Override
    public void rxDo(Object tag, Object o) {
        update_info.append(tag + "\n");
        if (o instanceof Integer && (int) o == 1) {
            checkConfig();
        }
    }


    private void checkConfig() {
        if (App.appPreload.sucesse()) {
            Log.i("流程", "跳转主界面");
            SerialCom.DevRest();//重启k21
            ThreadUtils.getInstance().createSch("delaed").schedule(new Runnable() {
                @Override
                public void run() {
                    comThread comThread = new comThread();
                    comThread.setPriority(Thread.MAX_PRIORITY);
                    ThreadUtils.getInstance().createSingle("port").execute(comThread);
                    resetPSAM();
                    App.getInstance().getNowActivitie().startActivity(new Intent(App.getInstance(), Main2Activity.class));
                    App.getInstance().getNowActivitie().finish();
                }
            }, 3, TimeUnit.SECONDS);
        }
    }
}
