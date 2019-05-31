package com.xb.haikou.moudle.function.card;

import com.hao.lib.Util.FileUtils;
import com.hao.lib.Util.Type;

import static java.lang.System.arraycopy;

//-------------------------------17文件
public class File17InfoEntity {
    String international_code;    //国际代码
    String province_code;        //省级代码
    String city_code;            //城市代码
    String interflow_flag;        //互通标识
    String card_type;                //互通卡种类型


    public int praseFile17(int i, byte[] date) {
        //国际代码
        byte[] International_code = new byte[4];
        arraycopy(date, i, International_code, 0, International_code.length);
        i += International_code.length;
        international_code = (String) FileUtils.byte2Parm(International_code, Type.HEX);

        //省级代码
        byte[] Province_code = new byte[2];
        arraycopy(date, i, Province_code, 0, Province_code.length);
        i += Province_code.length;
        province_code = (String) FileUtils.byte2Parm(Province_code, Type.HEX);


        //城市代码
        byte[] City_code = new byte[2];
        arraycopy(date, i, City_code, 0, City_code.length);
        i += City_code.length;
        city_code = (String) FileUtils.byte2Parm(City_code, Type.HEX);

        //互通标识
        byte[] Interflow_flag = new byte[2];
        arraycopy(date, i, Interflow_flag, 0, Interflow_flag.length);
        i += Interflow_flag.length;
        interflow_flag = (String) FileUtils.byte2Parm(Interflow_flag, Type.HEX);


        //互通卡种类型
        byte[] Card_type = new byte[1];
        arraycopy(date, i, Card_type, 0, Card_type.length);
        i += Card_type.length;
        card_type = (String) FileUtils.byte2Parm(Card_type, Type.HEX);

        return i;
    }

    @Override
    public String toString() {
        return "\nFile17InfoEntity{" +
                "international_code='" + international_code + '\'' +
                ", province_code='" + province_code + '\'' +
                ", city_code='" + city_code + '\'' +
                ", interflow_flag='" + interflow_flag + '\'' +
                ", card_type='" + card_type + '\'' +
                '}';
    }
}
