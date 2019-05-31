package com.xb.haikou.moudle.function.card;

import com.hao.lib.Util.FileUtils;
import com.hao.lib.Util.Type;

import static java.lang.System.arraycopy;

//1E文件
public class File1EInfoEntity {
    String transaction_type_1e;            //交易类型09
    String terminal_number_1e;            //终端编号
    String industry_code;                    //行业代码
    String line_1e;                        //线路
    String sites_1e;                    //站点
    String operation_code_1e;            //运营代码
    String reserve_1e;                        //预留
    String transaction_amount_1e;        //交易金额
    String transaction_balance_1e;        //交易后余额
    String transaction_time_1e;            //交易日期时间
    String transaction_city_code_1e;    //受理方城市代码
    String institutional_identity_1e;    //受理方机构标识
    String reserve2_1e;                    //本规范预留

    public int praseFile1E(int i, byte[] date) {
        //交易类型
        byte[] Transaction_type_1e = new byte[1];
        arraycopy(date, i, Transaction_type_1e, 0, Transaction_type_1e.length);
        i += Transaction_type_1e.length;
        transaction_type_1e = (String) FileUtils.byte2Parm(Transaction_type_1e, Type.HEX);

        //终端编号
        byte[] Terminal_number_1e = new byte[8];
        arraycopy(date, i, Terminal_number_1e, 0, Terminal_number_1e.length);
        i += Terminal_number_1e.length;
        terminal_number_1e = (String) FileUtils.byte2Parm(Terminal_number_1e, Type.HEX);

        //行业代码
        byte[] Industry_code = new byte[1];
        arraycopy(date, i, Industry_code, 0, Industry_code.length);
        i += Industry_code.length;
        industry_code = (String) FileUtils.byte2Parm(Industry_code, Type.HEX);


        //线路
        byte[] Line_1e = new byte[2];
        arraycopy(date, i, Line_1e, 0, Line_1e.length);
        i += Line_1e.length;
        line_1e = (String) FileUtils.byte2Parm(Line_1e, Type.HEX);


        //站点
        byte[] Sites_1e = new byte[2];
        arraycopy(date, i, Sites_1e, 0, Sites_1e.length);
        i += Sites_1e.length;
        sites_1e = (String) FileUtils.byte2Parm(Sites_1e, Type.HEX);


        //运营代码
        byte[] Operation_code_1e = new byte[2];
        arraycopy(date, i, Operation_code_1e, 0, Operation_code_1e.length);
        i += Operation_code_1e.length;
        operation_code_1e = (String) FileUtils.byte2Parm(Operation_code_1e, Type.HEX);


        //预留
        byte[] Reserve_1e = new byte[1];
        arraycopy(date, i, Reserve_1e, 0, Reserve_1e.length);
        i += Reserve_1e.length;
        reserve_1e = (String) FileUtils.byte2Parm(Reserve_1e, Type.HEX);


        //交易金额
        byte[] Transaction_amount_1e = new byte[4];
        arraycopy(date, i, Transaction_amount_1e, 0, Transaction_amount_1e.length);
        i += Transaction_amount_1e.length;
        transaction_amount_1e = (String) FileUtils.byte2Parm(Transaction_amount_1e, Type.HEX);

        //交易后余额
        byte[] Transaction_balance_1e = new byte[4];
        arraycopy(date, i, Transaction_balance_1e, 0, Transaction_balance_1e.length);
        i += Transaction_balance_1e.length;
        transaction_balance_1e = (String) FileUtils.byte2Parm(Transaction_balance_1e, Type.HEX);

        //交易日期时间
        byte[] Transaction_time_1e = new byte[7];
        arraycopy(date, i, Transaction_time_1e, 0, Transaction_time_1e.length);
        i += Transaction_time_1e.length;
        transaction_time_1e = (String) FileUtils.byte2Parm(Transaction_time_1e, Type.HEX);


        //受理方城市代码
        byte[] Transaction_city_code_1e = new byte[2];
        arraycopy(date, i, Transaction_city_code_1e, 0, Transaction_city_code_1e.length);
        i += Transaction_city_code_1e.length;
        transaction_city_code_1e = (String) FileUtils.byte2Parm(Transaction_city_code_1e, Type.HEX);


        //受理方机构标识
        byte[] Institutional_identity_1e = new byte[8];
        arraycopy(date, i, Institutional_identity_1e, 0, Institutional_identity_1e.length);
        i += Institutional_identity_1e.length;
        institutional_identity_1e = (String) FileUtils.byte2Parm(Institutional_identity_1e, Type.HEX);


        //本规范预留
        byte[] Reserve2_1e = new byte[6];
        arraycopy(date, i, Reserve2_1e, 0, Reserve2_1e.length);
        i += Reserve2_1e.length;
        reserve2_1e = (String) FileUtils.byte2Parm(Reserve2_1e, Type.HEX);


        return i;
    }

    public String getHexString() {
        return transaction_type_1e + terminal_number_1e + industry_code + line_1e + sites_1e +
                operation_code_1e + reserve_1e + transaction_amount_1e + transaction_balance_1e + transaction_time_1e +
                transaction_city_code_1e + institutional_identity_1e + reserve2_1e;
    }

    @Override
    public String toString() {
        return "File1EInfoEntity{" +
                "transaction_type_1e='" + transaction_type_1e + '\'' +
                ", terminal_number_1e='" + terminal_number_1e + '\'' +
                ", industry_code='" + industry_code + '\'' +
                ", line_1e='" + line_1e + '\'' +
                ", sites_1e='" + sites_1e + '\'' +
                ", operation_code_1e='" + operation_code_1e + '\'' +
                ", reserve_1e='" + reserve_1e + '\'' +
                ", transaction_amount_1e='" + transaction_amount_1e + '\'' +
                ", transaction_balance_1e='" + transaction_balance_1e + '\'' +
                ", transaction_time_1e='" + transaction_time_1e + '\'' +
                ", transaction_city_code_1e='" + transaction_city_code_1e + '\'' +
                ", institutional_identity_1e='" + institutional_identity_1e + '\'' +
                ", reserve2_1e='" + reserve2_1e + '\'' +
                '}';
    }
}
