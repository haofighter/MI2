package com.xb.haikou.moudle.function.card;

import com.hao.lib.Util.FileUtils;
import com.hao.lib.Util.Type;

import static java.lang.System.arraycopy;

//-------------------------------18文件
public class File18InfoEntity {
    String transaction_number_18;    //交易序号
    String overdrawn_account;        //透支限额
    String transaction_amount;       //交易金额
    String transaction_type;        //交易类型标识
    String pose_id;                //终端机编号
    String transaction_time;               //交易时间


    public int praseFile18(int i, byte[] date) {
        //国际代码
        byte[] Transaction_number_18 = new byte[2];
        arraycopy(date, i, Transaction_number_18, 0, Transaction_number_18.length);
        i += Transaction_number_18.length;
        transaction_number_18 = (String) FileUtils.byte2Parm(Transaction_number_18, Type.HEX);

        //省级代码
        byte[] Overdrawn_account = new byte[3];
        arraycopy(date, i, Overdrawn_account, 0, Overdrawn_account.length);
        i += Overdrawn_account.length;
        overdrawn_account = (String) FileUtils.byte2Parm(Overdrawn_account, Type.HEX);


        //城市代码
        byte[] Transaction_amount = new byte[4];
        arraycopy(date, i, Transaction_amount, 0, Transaction_amount.length);
        i += Transaction_amount.length;
        transaction_amount = (String) FileUtils.byte2Parm(Transaction_amount, Type.HEX);

        //互通标识
        byte[] Transaction_type = new byte[1];
        arraycopy(date, i, Transaction_type, 0, Transaction_type.length);
        i += Transaction_type.length;
        transaction_type = (String) FileUtils.byte2Parm(Transaction_type, Type.HEX);


        //互通卡种类型
        byte[] Pose_id = new byte[6];
        arraycopy(date, i, Pose_id, 0, Pose_id.length);
        i += Pose_id.length;
        pose_id = (String) FileUtils.byte2Parm(Pose_id, Type.HEX);

        //互通卡种类型
        byte[] Transaction_time = new byte[7];
        arraycopy(date, i, Transaction_time, 0, Transaction_time.length);
        i += Transaction_time.length;
        transaction_time = (String) FileUtils.byte2Parm(Transaction_time, Type.HEX);

        return i;
    }

    public String getTransaction_number_18() {
        return transaction_number_18;
    }

    public void setTransaction_number_18(String transaction_number_18) {
        this.transaction_number_18 = transaction_number_18;
    }

    public String getOverdrawn_account() {
        return overdrawn_account;
    }

    public void setOverdrawn_account(String overdrawn_account) {
        this.overdrawn_account = overdrawn_account;
    }

    public String getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(String transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getPose_id() {
        return pose_id;
    }

    public void setPose_id(String pose_id) {
        this.pose_id = pose_id;
    }

    public String getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(String transaction_time) {
        this.transaction_time = transaction_time;
    }
}
