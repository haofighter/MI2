package com.xb.haikou.moudle.function.card;

import com.hao.lib.Util.FileUtils;
import com.hao.lib.Util.Type;

import static java.lang.System.arraycopy;

public class File1AInfoEntity {
    //--------------------------------------------------------------------------------------------------1A文件
    String record_id_1a;                //记录id
    String record_length_1a;                //记录长度
    String valid_identification_1a;        //应用有效标识
    String interflow_transactions_1a;        //互联互通交易标识
    String apply_lock_1a;                    //应用锁定标识
    String trade_serial_number_1a;        //交易流水号
    String transaction_status_1a;            //交易状态
    String boarding_city_code_1a;        //上车城市代码
    String boarding_mark_1a;            //上车机构标识
    String boarding_operator_code_1a;    //上车运营商代码
    String boarding_line_number_1a;        //上车线路号
    String boarding_the_site_1a;            //上车站点
    String boarding_vehicle_number_1a;    //上车车辆号
    String boarding_terminal_number_1a;
    String boarding_time_1a;            //上车时间
    String boarding_max_amount_1a;        //标注金额
    String direction_identity_1a;            //方向标识
    String alight_city_code_1a;            //下车城市代码
    String alight_mark_1a;                //下车机构标识
    String alight_operator_code_1a;        //下车运营商代码
    String alight_line_number_1a;        //下车线路号
    String alight_the_site_1a;                //下车站点
    String alight_vehicle_number_1a;    //下车车辆号
    String alight_terminal_number_1a;    //下车终端编号
    String alight_time_1a;                //下车时间
    String transaction_amount_1a;        //交易金额
    String driving_mileage_1a;            //乘车里程
    String reserved_1a;                //预留空间

    public int praseFile1A(int i, byte[] date) {
        //记录id
        byte[] Record_id_1a = new byte[2];
        arraycopy(date, i, Record_id_1a, 0, Record_id_1a.length);
        i += Record_id_1a.length;
        record_id_1a = (String) FileUtils.byte2Parm(Record_id_1a, Type.HEX);

        //记录长度
        byte[] Record_length_1a = new byte[1];
        arraycopy(date, i, Record_length_1a, 0, Record_length_1a.length);
        i += Record_length_1a.length;
        record_length_1a = (String) FileUtils.byte2Parm(Record_length_1a, Type.HEX);


        //应用有效标识
        byte[] Valid_identification_1a = new byte[1];
        arraycopy(date, i, Valid_identification_1a, 0, Valid_identification_1a.length);
        i += Valid_identification_1a.length;
        valid_identification_1a = (String) FileUtils.byte2Parm(Valid_identification_1a, Type.HEX);


        //互联互通交易标识
        byte[] Interflow_transactions_1a = new byte[1];
        arraycopy(date, i, Interflow_transactions_1a, 0, Interflow_transactions_1a.length);
        i += Interflow_transactions_1a.length;
        interflow_transactions_1a = (String) FileUtils.byte2Parm(Interflow_transactions_1a, Type.HEX);

        //应用锁定标识
        byte[] Apply_lock_1a = new byte[1];
        arraycopy(date, i, Apply_lock_1a, 0, Apply_lock_1a.length);
        i += Apply_lock_1a.length;
        apply_lock_1a = (String) FileUtils.byte2Parm(Apply_lock_1a, Type.HEX);

        //交易流水号
        byte[] Trade_serial_number_1a = new byte[8];
        arraycopy(date, i, Trade_serial_number_1a, 0, Trade_serial_number_1a.length);
        i += Trade_serial_number_1a.length;
        trade_serial_number_1a = (String) FileUtils.byte2Parm(Trade_serial_number_1a, Type.HEX);


        //交易状态
        byte[] Transaction_status_1a = new byte[1];
        arraycopy(date, i, Transaction_status_1a, 0, Transaction_status_1a.length);
        i += Transaction_status_1a.length;
        transaction_status_1a = (String) FileUtils.byte2Parm(Transaction_status_1a, Type.HEX);

        //上车城市代码
        byte[] Boarding_city_code_1a = new byte[2];
        arraycopy(date, i, Boarding_city_code_1a, 0, Boarding_city_code_1a.length);
        i += Boarding_city_code_1a.length;
        boarding_city_code_1a = (String) FileUtils.byte2Parm(Boarding_city_code_1a, Type.HEX);


        //上车机构标识
        byte[] Boarding_mark_1a = new byte[8];
        arraycopy(date, i, Boarding_mark_1a, 0, Boarding_mark_1a.length);
        i += Boarding_mark_1a.length;
        boarding_mark_1a = (String) FileUtils.byte2Parm(Boarding_mark_1a, Type.HEX);


        //上车运营商代码
        byte[] Boarding_operator_code_1a = new byte[2];
        arraycopy(date, i, Boarding_operator_code_1a, 0, Boarding_operator_code_1a.length);
        i += Boarding_operator_code_1a.length;
        boarding_operator_code_1a = (String) FileUtils.byte2Parm(Boarding_operator_code_1a, Type.HEX);


        //上车线路号
        byte[] Boarding_line_number_1a = new byte[2];
        arraycopy(date, i, Boarding_line_number_1a, 0, Boarding_line_number_1a.length);
        i += Boarding_line_number_1a.length;
        boarding_line_number_1a = (String) FileUtils.byte2Parm(Boarding_line_number_1a, Type.HEX);


        //上车站点
        byte[] Boarding_the_site_1a = new byte[1];
        arraycopy(date, i, Boarding_the_site_1a, 0, Boarding_the_site_1a.length);
        i += Boarding_the_site_1a.length;
        boarding_the_site_1a = (String) FileUtils.byte2Parm(Boarding_the_site_1a, Type.HEX);


        //上车车辆号
        byte[] Boarding_vehicle_number_1a = new byte[8];
        arraycopy(date, i, Boarding_vehicle_number_1a, 0, Boarding_vehicle_number_1a.length);
        i += Boarding_vehicle_number_1a.length;
        boarding_vehicle_number_1a = (String) FileUtils.byte2Parm(Boarding_mark_1a, Type.HEX);


        byte[] Boarding_terminal_number_1a = new byte[8];
        arraycopy(date, i, Boarding_terminal_number_1a, 0, Boarding_terminal_number_1a.length);
        i += Boarding_terminal_number_1a.length;
        boarding_terminal_number_1a = (String) FileUtils.byte2Parm(Boarding_mark_1a, Type.HEX);

        //上车时间
        byte[] Boarding_time_1a = new byte[7];
        arraycopy(date, i, Boarding_time_1a, 0, Boarding_time_1a.length);
        i += Boarding_time_1a.length;
        boarding_time_1a = (String) FileUtils.byte2Parm(Boarding_time_1a, Type.HEX);

        //标注金额
        byte[] Boarding_max_amount_1a = new byte[4];
        arraycopy(date, i, Boarding_max_amount_1a, 0, Boarding_max_amount_1a.length);
        i += Boarding_max_amount_1a.length;
        boarding_max_amount_1a = (String) FileUtils.byte2Parm(Boarding_max_amount_1a, Type.HEX);


        //方向标识
        byte[] Direction_identity_1a = new byte[1];
        arraycopy(date, i, Direction_identity_1a, 0, Direction_identity_1a.length);
        i += Direction_identity_1a.length;
        direction_identity_1a = (String) FileUtils.byte2Parm(Direction_identity_1a, Type.HEX);


        //下车城市代码
        byte[] Alight_city_code_1a = new byte[2];
        arraycopy(date, i, Alight_city_code_1a, 0, Alight_city_code_1a.length);
        i += Alight_city_code_1a.length;
        alight_city_code_1a = (String) FileUtils.byte2Parm(Alight_city_code_1a, Type.HEX);


        //下车机构标识
        byte[] Alight_mark_1a = new byte[8];
        arraycopy(date, i, Alight_mark_1a, 0, Alight_mark_1a.length);
        i += Alight_mark_1a.length;
        alight_mark_1a = (String) FileUtils.byte2Parm(Alight_mark_1a, Type.HEX);


        //下车运营商代码
        byte[] Alight_operator_code_1a = new byte[2];
        arraycopy(date, i, Alight_operator_code_1a, 0, Alight_operator_code_1a.length);
        i += Alight_operator_code_1a.length;
        alight_operator_code_1a = (String) FileUtils.byte2Parm(Alight_operator_code_1a, Type.HEX);


        ////下车线路号
        byte[] Alight_line_number_1a = new byte[2];
        arraycopy(date, i, Alight_line_number_1a, 0, Alight_line_number_1a.length);
        i += Alight_line_number_1a.length;
        alight_line_number_1a = (String) FileUtils.byte2Parm(Alight_line_number_1a, Type.HEX);


        //下车站点
        byte[] Alight_the_site_1a = new byte[1];
        arraycopy(date, i, Alight_the_site_1a, 0, Alight_the_site_1a.length);
        i += Alight_the_site_1a.length;
        alight_the_site_1a = (String) FileUtils.byte2Parm(Alight_the_site_1a, Type.HEX);


        //下车车辆号
        byte[] Alight_vehicle_number_1a = new byte[8];
        arraycopy(date, i, Alight_vehicle_number_1a, 0, Alight_vehicle_number_1a.length);
        i += Alight_vehicle_number_1a.length;
        alight_vehicle_number_1a = (String) FileUtils.byte2Parm(Alight_vehicle_number_1a, Type.HEX);


        //下车终端编号
        byte[] Alight_terminal_number_1a = new byte[8];
        arraycopy(date, i, Alight_terminal_number_1a, 0, Alight_terminal_number_1a.length);
        i += Alight_terminal_number_1a.length;
        alight_terminal_number_1a = (String) FileUtils.byte2Parm(Alight_terminal_number_1a, Type.HEX);


        //下车时间
        byte[] Alight_time_1a = new byte[7];
        arraycopy(date, i, Alight_time_1a, 0, Alight_time_1a.length);
        i += Alight_time_1a.length;
        alight_time_1a = (String) FileUtils.byte2Parm(Alight_time_1a, Type.HEX);


        //交易金额
        byte[] Transaction_amount_1a = new byte[4];
        arraycopy(date, i, Transaction_amount_1a, 0, Transaction_amount_1a.length);
        i += Transaction_amount_1a.length;
        transaction_amount_1a = (String) FileUtils.byte2Parm(Transaction_amount_1a, Type.HEX);


        //乘车里程
        byte[] Driving_mileage_1a = new byte[2];
        arraycopy(date, i, Driving_mileage_1a, 0, Driving_mileage_1a.length);
        i += Driving_mileage_1a.length;
        driving_mileage_1a = (String) FileUtils.byte2Parm(Driving_mileage_1a, Type.HEX);


        //预留空间
        byte[] Reserved_1a = new byte[26];
        arraycopy(date, i, Reserved_1a, 0, Reserved_1a.length);
        i += Reserved_1a.length;
        reserved_1a = (String) FileUtils.byte2Parm(Reserved_1a, Type.HEX);

        return i;
    }

    @Override
    public String toString() {
        return "\nFile1AInfoEntity{" +
                "record_id_1a='" + record_id_1a + '\'' +
                ", record_length_1a='" + record_length_1a + '\'' +
                ", valid_identification_1a='" + valid_identification_1a + '\'' +
                ", interflow_transactions_1a='" + interflow_transactions_1a + '\'' +
                ", apply_lock_1a='" + apply_lock_1a + '\'' +
                ", trade_serial_number_1a='" + trade_serial_number_1a + '\'' +
                ", transaction_status_1a='" + transaction_status_1a + '\'' +
                ", boarding_city_code_1a='" + boarding_city_code_1a + '\'' +
                ", boarding_mark_1a='" + boarding_mark_1a + '\'' +
                ", boarding_operator_code_1a='" + boarding_operator_code_1a + '\'' +
                ", boarding_line_number_1a='" + boarding_line_number_1a + '\'' +
                ", boarding_the_site_1a='" + boarding_the_site_1a + '\'' +
                ", boarding_vehicle_number_1a='" + boarding_vehicle_number_1a + '\'' +
                ", boarding_terminal_number_1a='" + boarding_terminal_number_1a + '\'' +
                ", boarding_time_1a='" + boarding_time_1a + '\'' +
                ", boarding_max_amount_1a='" + boarding_max_amount_1a + '\'' +
                ", direction_identity_1a='" + direction_identity_1a + '\'' +
                ", alight_city_code_1a='" + alight_city_code_1a + '\'' +
                ", alight_mark_1a='" + alight_mark_1a + '\'' +
                ", alight_operator_code_1a='" + alight_operator_code_1a + '\'' +
                ", alight_line_number_1a='" + alight_line_number_1a + '\'' +
                ", alight_the_site_1a='" + alight_the_site_1a + '\'' +
                ", alight_vehicle_number_1a='" + alight_vehicle_number_1a + '\'' +
                ", alight_terminal_number_1a='" + alight_terminal_number_1a + '\'' +
                ", alight_time_1a='" + alight_time_1a + '\'' +
                ", transaction_amount_1a='" + transaction_amount_1a + '\'' +
                ", driving_mileage_1a='" + driving_mileage_1a + '\'' +
                ", reserved_1a='" + reserved_1a + '\'' +
                '}';
    }
}
