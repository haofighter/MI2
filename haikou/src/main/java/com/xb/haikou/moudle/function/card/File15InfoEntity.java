package com.xb.haikou.moudle.function.card;

import com.hao.lib.Util.FileUtils;
import com.hao.lib.Util.Type;

import static java.lang.System.arraycopy;

public class File15InfoEntity {

    String card_issuer;            //发卡机构标识 判断白名单依据之一（前四字节）
    String application_type;        // 应用类型标识
    String enable_identification;    //发卡机构应用版本（启用标识）
    String pan;                //卡内号
    String enabling_time;        //启用日期
    String valid_time;            //有效日期

    public int praseFile15(int i, byte[] date) {
        //发卡机构标识 判断白名单依据之一（前四字节）
        byte[] Card_issuer = new byte[8];
        arraycopy(date, i, Card_issuer, 0, Card_issuer.length);
        i += Card_issuer.length;
        card_issuer = (String) FileUtils.byte2Parm(Card_issuer, Type.HEX);

        // 应用类型标识
        byte[] Application_type = new byte[1];
        arraycopy(date, i, Application_type, 0, Application_type.length);
        i += Application_type.length;
        application_type = (String) FileUtils.byte2Parm(Application_type, Type.HEX);


        //发卡机构应用版本（启用标识）
        byte[] Enable_identification = new byte[1];
        arraycopy(date, i, Enable_identification, 0, Enable_identification.length);
        i += Enable_identification.length;
        enable_identification = (String) FileUtils.byte2Parm(Enable_identification, Type.HEX);


        //卡内号
        byte[] Pan = new byte[10];
        arraycopy(date, i, Pan, 0, Pan.length);
        i += Pan.length;
        pan = (String) FileUtils.byte2Parm(Pan, Type.HEX);

        //发卡机构标识 判断白名单依据之一（前四字节）
        byte[] Enabling_time = new byte[4];
        arraycopy(date, i, Enabling_time, 0, Enabling_time.length);
        i += Enabling_time.length;
        enabling_time = (String) FileUtils.byte2Parm(Enabling_time, Type.HEX);


        //有效日期
        byte[] Valid_time = new byte[4];
        arraycopy(date, i, Valid_time, 0, Valid_time.length);
        i += Valid_time.length;
        valid_time = (String) FileUtils.byte2Parm(Valid_time, Type.HEX);
        return i;
    }


    public void getFile15HexString() {

    }


    @Override
    public String toString() {
        return "\nFile15InfoEntity{" +
                ", card_issuer='" + card_issuer + '\'' +
                ", application_type='" + application_type + '\'' +
                ", enable_identification='" + enable_identification + '\'' +
                ", pan='" + pan + '\'' +
                ", enabling_time='" + enabling_time + '\'' +
                ", valid_time='" + valid_time + '\'' +
                '}';
    }
}
