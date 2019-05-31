package com.xb.haikou.moudle.function.card;

import com.hao.lib.Util.FileUtils;
import com.hao.lib.Util.Type;

import static java.lang.System.arraycopy;

public class File19InfoEntity {
    //----------------------------------------------------------------------------------------------------------------------19文件
    String record_id;                    //记录id
    String record_length;                //记录长度
    String record_enable;                //应用有效标识
    String interflow_transactions;        //互联互通交易标识;
    String apply_lock;                    //应用锁定标识
    String board_alight_status;        //入出站/上下车状态
    String boarding_terminal_number;//上车终端编号
    String boarding_time;            //上车时间
    String boarding_vehicle_number;    //上车车辆号
    String boarding_the_site;            //上车站点
    String direction_identity;            //乘坐方向
    String alight_terminal_number;    //下车终端编号
    String alight_time;                //下车时间
    String alight_vehicle_number;    //下车车辆号
    String alight_the_site;            //下车站点
    String boarding_line_number;    //上车线路号
    String boarding_max_amount;        //标注金额
    String reserved3;                //预留空间
    String last_trading_time;        //最后一次交易时间
    String trading_number;

    public int praseFile19(int i, byte[] date) {
        //记录id
        byte[] Record_id = new byte[2];
        arraycopy(date, i, Record_id, 0, Record_id.length);
        i += Record_id.length;
        record_id = (String) FileUtils.byte2Parm(Record_id, Type.HEX);

//记录长度
        byte[] Record_length = new byte[1];
        arraycopy(date, i, Record_length, 0, Record_length.length);
        i += Record_length.length;
        record_length = (String) FileUtils.byte2Parm(Record_length, Type.HEX);

        //应用有效标识
        byte[] Record_enable = new byte[1];
        arraycopy(date, i, Record_enable, 0, Record_enable.length);
        i += Record_enable.length;
        record_enable = (String) FileUtils.byte2Parm(Record_enable, Type.HEX);

        //互联互通交易标识
        byte[] Interflow_transactions = new byte[1];
        arraycopy(date, i, Interflow_transactions, 0, Interflow_transactions.length);
        i += Interflow_transactions.length;
        interflow_transactions = (String) FileUtils.byte2Parm(Interflow_transactions, Type.HEX);

        //应用锁定标识
        byte[] Apply_lock = new byte[1];
        arraycopy(date, i, Apply_lock, 0, Apply_lock.length);
        i += Apply_lock.length;
        apply_lock = (String) FileUtils.byte2Parm(Apply_lock, Type.HEX);

//入出站/上下车状态
        byte[] Board_alight_status = new byte[1];
        arraycopy(date, i, Board_alight_status, 0, Board_alight_status.length);
        i += Board_alight_status.length;
        board_alight_status = (String) FileUtils.byte2Parm(Board_alight_status, Type.HEX);

        //上车终端编号
        byte[] Boarding_terminal_number = new byte[6];
        arraycopy(date, i, Boarding_terminal_number, 0, Boarding_terminal_number.length);
        i += Boarding_terminal_number.length;
        boarding_terminal_number = (String) FileUtils.byte2Parm(Boarding_terminal_number, Type.HEX);

        //上车时间
        byte[] Boarding_time = new byte[7];
        arraycopy(date, i, Boarding_time, 0, Boarding_time.length);
        i += Boarding_time.length;
        boarding_time = (String) FileUtils.byte2Parm(Boarding_time, Type.HEX);

        //上车车辆号
        byte[] Boarding_vehicle_number = new byte[5];
        arraycopy(date, i, Boarding_vehicle_number, 0, Boarding_vehicle_number.length);
        i += Boarding_vehicle_number.length;
        boarding_vehicle_number = (String) FileUtils.byte2Parm(Boarding_vehicle_number, Type.HEX);

        //上车站点
        byte[] Boarding_the_site = new byte[1];
        arraycopy(date, i, Boarding_the_site, 0, Boarding_the_site.length);
        i += Boarding_the_site.length;
        boarding_the_site = (String) FileUtils.byte2Parm(Boarding_the_site, Type.HEX);

        //乘坐方向
        byte[] Direction_identity = new byte[1];
        arraycopy(date, i, Direction_identity, 0, Direction_identity.length);
        i += Direction_identity.length;
        direction_identity = (String) FileUtils.byte2Parm(Direction_identity, Type.HEX);


        //下车终端编号
        byte[] Alight_terminal_number = new byte[6];
        arraycopy(date, i, Alight_terminal_number, 0, Alight_terminal_number.length);
        i += Alight_terminal_number.length;
        alight_terminal_number = (String) FileUtils.byte2Parm(Alight_terminal_number, Type.HEX);


        //下车时间
        byte[] Alight_time = new byte[7];
        arraycopy(date, i, Alight_time, 0, Alight_time.length);
        i += Alight_time.length;
        alight_time = (String) FileUtils.byte2Parm(Alight_time, Type.HEX);


        //下车车辆号
        byte[] Alight_vehicle_number = new byte[5];
        arraycopy(date, i, Alight_vehicle_number, 0, Alight_vehicle_number.length);
        i += Alight_vehicle_number.length;
        alight_vehicle_number = (String) FileUtils.byte2Parm(Alight_vehicle_number, Type.HEX);

        //下车站点
        byte[] Alight_the_site = new byte[1];
        arraycopy(date, i, Alight_the_site, 0, Alight_the_site.length);
        i += Alight_the_site.length;
        alight_the_site = (String) FileUtils.byte2Parm(Alight_the_site, Type.HEX);

        //上车线路号
        byte[] Boarding_line_number = new byte[4];
        arraycopy(date, i, Boarding_line_number, 0, Boarding_line_number.length);
        i += Boarding_line_number.length;
        boarding_line_number = (String) FileUtils.byte2Parm(Boarding_line_number, Type.HEX);

        //标注金额
        byte[] Boarding_max_amount = new byte[4];
        arraycopy(date, i, Boarding_max_amount, 0, Boarding_max_amount.length);
        i += Boarding_max_amount.length;
        boarding_max_amount = (String) FileUtils.byte2Parm(Boarding_max_amount, Type.HEX);

        //预留空间
        byte[] Reserved3 = new byte[4];
        arraycopy(date, i, Reserved3, 0, Reserved3.length);
        i += Reserved3.length;
        reserved3 = (String) FileUtils.byte2Parm(Reserved3, Type.HEX);

        //最后一次交易时间
        byte[] Last_trading_time = new byte[7];
        arraycopy(date, i, Last_trading_time, 0, Last_trading_time.length);
        i += Last_trading_time.length;
        last_trading_time = (String) FileUtils.byte2Parm(Last_trading_time, Type.HEX);

        //当月交易次数
        byte[] Trading_number = new byte[2];
        arraycopy(date, i, Trading_number, 0, Trading_number.length);
        i += Trading_number.length;
        trading_number = (String) FileUtils.byte2Parm(Trading_number, Type.HEX);

        return i;
    }


    @Override
    public String toString() {
        return "\nFile19InfoEntity{" +
                "record_id='" + record_id + '\'' +
                ", record_length='" + record_length + '\'' +
                ", record_enable='" + record_enable + '\'' +
                ", interflow_transactions='" + interflow_transactions + '\'' +
                ", apply_lock='" + apply_lock + '\'' +
                ", board_alight_status='" + board_alight_status + '\'' +
                ", boarding_terminal_number='" + boarding_terminal_number + '\'' +
                ", boarding_time='" + boarding_time + '\'' +
                ", boarding_vehicle_number='" + boarding_vehicle_number + '\'' +
                ", boarding_the_site='" + boarding_the_site + '\'' +
                ", direction_identity='" + direction_identity + '\'' +
                ", alight_terminal_number='" + alight_terminal_number + '\'' +
                ", alight_time='" + alight_time + '\'' +
                ", alight_vehicle_number='" + alight_vehicle_number + '\'' +
                ", alight_the_site='" + alight_the_site + '\'' +
                ", boarding_line_number='" + boarding_line_number + '\'' +
                ", boarding_max_amount='" + boarding_max_amount + '\'' +
                ", reserved3='" + reserved3 + '\'' +
                ", last_trading_time='" + last_trading_time + '\'' +
                ", trading_number='" + trading_number + '\'' +
                '}';
    }

    public String getHexString() {
        return record_id + record_length + record_enable + interflow_transactions + apply_lock + board_alight_status + boarding_terminal_number +
                boarding_time + boarding_vehicle_number + boarding_the_site + direction_identity + alight_terminal_number + alight_time + alight_vehicle_number +
                alight_the_site + boarding_line_number + boarding_max_amount + reserved3 + last_trading_time + trading_number;
    }
}
