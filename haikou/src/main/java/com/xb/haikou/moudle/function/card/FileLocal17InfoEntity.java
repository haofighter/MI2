package com.xb.haikou.moudle.function.card;

import com.hao.lib.Util.FileUtils;
import com.hao.lib.Util.Type;

import static java.lang.System.arraycopy;

//-----------------------------宝岛卡的--17文件
public class FileLocal17InfoEntity {
    String card_issuer;            //发卡方唯一标识，作为部级密钥一级分散因子,发卡时填写:BAA3C4CFBAA3C4CF
    String card_type;                // 应用类型（卡类型）
    String application_version_number;    //应用版本号应用序列号（卡号）
    String pan;                //应用序列号（卡号）
    String enabling_time;        //启用日期
    String valid_time;            //有效日期
    String reserve1;            //发卡自定义FCI数据
    String enable_identification;    //启用标识  01 未启用  02 启用
    String card_deposit;        //卡片押金	BCD 码表示， 例如： 00001234 表示：12.34 元， 默认为： 00000000
    String employee_number;    //记名卡员工号
    String co_branded_card;        //联名卡合作方标识
    String reserve2;



    public int praseLocalFile17(int i, byte[] date) {
        //发卡方唯一标识，作为部级密钥一级分散因子,发卡时填写:BAA3C4CFBAA3C4CF
        byte[] Card_issuer = new byte[8];
        arraycopy(date, i, Card_issuer, 0, Card_issuer.length);
        i += Card_issuer.length;
        card_issuer = (String) FileUtils.byte2Parm(Card_issuer, Type.HEX);

// 应用类型（卡类型）
        byte[] Card_type = new byte[1];
        arraycopy(date, i, Card_type, 0, Card_type.length);
        i += Card_type.length;
        card_type = (String) FileUtils.byte2Parm(Card_type, Type.HEX);

        //应用版本号应用序列号（卡号）
        byte[] Application_version_number = new byte[1];
        arraycopy(date, i, Application_version_number, 0, Application_version_number.length);
        i += Application_version_number.length;
        application_version_number = (String) FileUtils.byte2Parm(Application_version_number, Type.HEX);

        //应用序列号（卡号）
        byte[] Pan = new byte[10];
        arraycopy(date, i, Pan, 0, Pan.length);
        i += Pan.length;
        pan = (String) FileUtils.byte2Parm(Pan, Type.HEX);

        //启用日期
        byte[] Enabling_time = new byte[4];
        arraycopy(date, i, Enabling_time, 0, Enabling_time.length);
        i += Enabling_time.length;
        enabling_time = (String) FileUtils.byte2Parm(Enabling_time, Type.HEX);

        //启用日期
        byte[] Valid_time = new byte[4];
        arraycopy(date, i, Valid_time, 0, Valid_time.length);
        i += Valid_time.length;
        valid_time = (String) FileUtils.byte2Parm(Valid_time, Type.HEX);

//启用日期
        byte[] Reserve1 = new byte[2];
        arraycopy(date, i, Reserve1, 0, Reserve1.length);
        i += Reserve1.length;
        reserve1 = (String) FileUtils.byte2Parm(Reserve1, Type.HEX);


        //启用标识  01 未启用  02 启用
        byte[] Enable_identification = new byte[1];
        arraycopy(date, i, Enable_identification, 0, Enable_identification.length);
        i += Enable_identification.length;
        enable_identification = (String) FileUtils.byte2Parm(Enable_identification, Type.HEX);


        //卡片押金	BCD 码表示， 例如： 00001234 表示：12.34 元， 默认为： 00000000
        byte[] Card_deposit = new byte[4];
        arraycopy(date, i, Card_deposit, 0, Card_deposit.length);
        i += Card_deposit.length;
        card_deposit = (String) FileUtils.byte2Parm(Card_deposit, Type.HEX);

        //记名卡员工号
        byte[] Employee_number = new byte[10];
        arraycopy(date, i, Employee_number, 0, Employee_number.length);
        i += Employee_number.length;
        employee_number = (String) FileUtils.byte2Parm(Employee_number, Type.HEX);


        byte[] Co_branded_card = new byte[2];
        arraycopy(date, i, Co_branded_card, 0, Co_branded_card.length);
        i += Co_branded_card.length;
        co_branded_card = (String) FileUtils.byte2Parm(Co_branded_card, Type.HEX);


        byte[] Reserve2 = new byte[3];
        arraycopy(date, i, Reserve2, 0, Reserve2.length);
        i += Reserve2.length;
        reserve2 = (String) FileUtils.byte2Parm(Reserve2, Type.HEX);

        return i;
    }

}
