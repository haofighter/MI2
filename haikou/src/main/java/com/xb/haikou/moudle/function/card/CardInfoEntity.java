package com.xb.haikou.moudle.function.card;

import android.util.Log;
import com.hao.lib.Util.FileUtils;
import com.hao.lib.Util.ThreadUtils;
import com.hao.lib.Util.Type;
import com.xb.haikou.config.InitConfig;
import com.xb.haikou.db.manage.DBManager;
import com.xb.haikou.moudle.function.unionpay.UnionCard;
import com.xb.haikou.util.BusToast;

import static java.lang.System.arraycopy;

public class CardInfoEntity {

    //------------------------------------互联互通卡
    String status;    //用于存储错误码 00 正常的 01
    String sw;        //每次操作后的sw值，用于配合错误码上报错误
    String new_time;   //在寻卡阶段为k21时间，在消费阶段为消费时间

    String atqa;    //用于判断uid长度和
    String uid;        //唯一识别符
    String sak;        //选择确认
    String ats;    //RATS返回值
    String selete_aid; //0x01 表示老宝岛卡; 0x02 互联互通卡; 0x00 表示选卡失败//总结82字节

    /******互联互通卡*******/
    File15InfoEntity file15InfoEntity;
    File17InfoEntity file17InfoEntity;
    File1AInfoEntity file1AInfoEntity;
    File1EInfoEntity file1EInfoEntity;
    File19InfoEntity file19InfoEntity;
    File18InfoEntity file18InfoEntity;

    /******老宝岛卡*******/
    FileLocal17InfoEntity fileLocal17InfoEntity;
    FileLocal19InfoEntity fileLocal19InfoEntity;

    /*********管理卡************/
    String driver_id;//司机/售票员ID
    String company_id;//运营公司代码
    String line_number;//线路编号
    String line_company_id;//运营公司代码


    String balance;  //余额
    String card_id;            //应用主账号后8字节(组MAC1用)//取自15文件pan号


    public void putDate(byte[] bytes) throws Exception {
        int i = 0;

        //用于存储错误码
        byte[] Status = new byte[1];
        arraycopy(bytes, i, Status, 0, Status.length);
        i += Status.length;
        status = (String) FileUtils.byte2Parm(Status, Type.HEX);

        if (!status.equals("00")) {
            sendCardErr(status);
            return;
        }

        //每次操作后的sw值，用于配合错误码上报错误
        byte[] Sw = new byte[2];
        arraycopy(bytes, i, Sw, 0, Sw.length);
        i += Sw.length;
        sw = (String) FileUtils.byte2Parm(Sw, Type.HEX);


        //在寻卡阶段为k21时间，在消费阶段为消费时间
        byte[] New_time = new byte[7];
        arraycopy(bytes, i, New_time, 0, New_time.length);
        i += New_time.length;
        new_time = (String) FileUtils.byte2Parm(New_time, Type.HEX);
        //TODO 可用于校准时间


        //用于判断uid长度和
        byte[] Atqa = new byte[2];
        arraycopy(bytes, i, Atqa, 0, Atqa.length);
        i += Atqa.length;
        atqa = (String) FileUtils.byte2Parm(Atqa, Type.HEX);


        //唯一识别符
        byte[] Uid = new byte[4];
        arraycopy(bytes, i, Uid, 0, Uid.length);
        i += Uid.length;
        uid = (String) FileUtils.byte2Parm(Uid, Type.HEX);


        //选择确认
        byte[] Sak = new byte[1];
        arraycopy(bytes, i, Sak, 0, Sak.length);
        i += Sak.length;
        sak = (String) FileUtils.byte2Parm(Sak, Type.HEX);

        //RATS返回值
        byte[] Ats = new byte[64];
        arraycopy(bytes, i, Ats, 0, Ats.length);
        i += Ats.length;
        ats = (String) FileUtils.byte2Parm(Ats, Type.HEX);

        //0x01 表示老宝岛卡; 0x02 互联互通卡; 0x00 表示选卡失败//总结82字节
        byte[] Selete_aid = new byte[1];
        arraycopy(bytes, i, Selete_aid, 0, Selete_aid.length);
        i += Selete_aid.length;
        selete_aid = (String) FileUtils.byte2Parm(Selete_aid, Type.HEX);


        //判断卡类型进行解析
        Log.i("位置", "ℹ=" + i);
        byte[] date = new byte[1024];
        arraycopy(bytes, i, date, 0, bytes.length - i);
        if (selete_aid.equals("02")) {//互联互通卡
            praseTranCard(date);
        } else if (selete_aid.equals("01")) {//老宝岛卡
            praseLocalCard(date);
        } else if (selete_aid.equals("03")) {
            //TODO 选卡失败
        } else if (selete_aid.equals("00")) {//银联卡
            praseUnionCard(date);
        }
    }

    //银联卡解析
    private void praseUnionCard(final byte[] date) {

        ThreadUtils.getInstance().createSingle("union").execute(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                byte[] Ppse_length = new byte[2];
                arraycopy(date, 0, Ppse_length, 0, Ppse_length.length);
                int ppse_length = FileUtils.hexStringToInt(FileUtils.bytesToHexString(Ppse_length));
                i += Ppse_length.length;

                byte[] Ppse = new byte[ppse_length];
                arraycopy(date, i, Ppse, 0, Ppse.length);
                String ppse = FileUtils.bytesToHexString(Ppse);
                String ppseStr = new String(Ppse);
                i += Ppse_length.length;

                UnionCard.getInstance().run(ppse);
            }
        });
    }


    //刷卡出错
    private void sendCardErr(String status) throws Exception {
        //TODO 刷卡失败
//        SoundPoolUtil.play(VoiceConfig.IC_RE);
        BusToast.showToast("请重刷【" + status + "】", false);
    }


    //老宝岛卡的解析
    public void praseLocalCard(byte[] date) throws Exception {
        try {
            int i = 0;

            //应用主账号后8字节(组MAC1用)//取自15文件pan号
            byte[] Card_id = new byte[8];
            arraycopy(date, i, Card_id, 0, Card_id.length);
            i += Card_id.length;
            card_id = (String) FileUtils.byte2Parm(Card_id, Type.HEX);

            fileLocal17InfoEntity = new FileLocal17InfoEntity();
            i = fileLocal17InfoEntity.praseLocalFile17(i, date);

            file18InfoEntity = new File18InfoEntity();
            i = file18InfoEntity.praseFile18(i, date);

            fileLocal19InfoEntity = new FileLocal19InfoEntity();
            i = fileLocal19InfoEntity.praseFile19(i, date);

            //余额
            byte[] Balance = new byte[4];
            arraycopy(date, i, Balance, 0, Balance.length);
            i += Balance.length;
            balance = (String) FileUtils.byte2Parm(Balance, Type.HEX);

            if (fileLocal17InfoEntity.card_type.toUpperCase().equals("F1") || fileLocal17InfoEntity.card_type.toUpperCase().equals("FF")) {
                //司机/售票员ID
                byte[] Driver_id = new byte[16];
                arraycopy(date, i, Driver_id, 0, Driver_id.length);
                i += Driver_id.length;
                driver_id = (String) FileUtils.byte2Parm(Driver_id, Type.HEX);

                //运营公司代码
                byte[] Company_id = new byte[8];
                arraycopy(date, i, Company_id, 0, Company_id.length);
                i += Company_id.length;
                company_id = (String) FileUtils.byte2Parm(Company_id, Type.HEX);

                //线路编号
                byte[] Line_number = new byte[3];
                arraycopy(date, i, Line_number, 0, Line_number.length);
                i += Line_number.length;
                line_number = (String) FileUtils.byte2Parm(Line_number, Type.BCDB);


                byte[] Line_company_id = new byte[8];
                arraycopy(date, i, Line_company_id, 0, Line_company_id.length);
                i += Line_company_id.length;
                line_company_id = (String) FileUtils.byte2Parm(Line_company_id, Type.HEX);

                if (!line_number.equals("000000")) {
                    InitConfig.setLine("xl000000" + line_number);
                    //TODO  设置线路
//                    byte[] config = FileUtils.readAssetsFileTobyte("xl00000000030220190522_5_new.para", App.getInstance().getApplicationContext());
//                    PraseLine.praseLineFile(config);//解析线路
                }
                if (!driver_id.equals(FileUtils.byte2Parm(new byte[16], Type.HEX))) {
                    DBManager.setDriver(fileLocal17InfoEntity.pan);
                }
                //TODO 设置卡相关设置
            }
        } catch (Exception e) {
            Log.i("解析卡错误", e.getMessage());
        }
    }


    //解析互联互通卡中的各种文件
    public void praseTranCard(byte[] date) throws Exception {
        int i = 0;

        //应用主账号后8字节(组MAC1用)//取自15文件pan号
        byte[] Card_id = new byte[8];
        arraycopy(date, i, Card_id, 0, Card_id.length);
        i += Card_id.length;
        card_id = (String) FileUtils.byte2Parm(Card_id, Type.HEX);

        //解析15文件
        file15InfoEntity = new File15InfoEntity();
        i = file15InfoEntity.praseFile15(i, date);

        file17InfoEntity = new File17InfoEntity();
        i = file17InfoEntity.praseFile17(i, date);

        //余额
        byte[] Balance = new byte[4];
        arraycopy(date, i, Balance, 0, Balance.length);
        i += Balance.length;
        balance = (String) FileUtils.byte2Parm(Balance, Type.HEX);

        file1AInfoEntity = new File1AInfoEntity();
        i = file1AInfoEntity.praseFile1A(i, date);

        file1EInfoEntity = new File1EInfoEntity();
        i = file1EInfoEntity.praseFile1E(i, date);

        file19InfoEntity = new File19InfoEntity();
        i = file19InfoEntity.praseFile19(i, date);

        file18InfoEntity = new File18InfoEntity();
        i = file18InfoEntity.praseFile18(i, date);
    }

}
