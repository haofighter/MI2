package com.hao.mivoice.jdy_type.PraseBluetoothRecord;


import com.hao.mivoice.jdy_type.JDY_type;

public class PraseBlueTRecord {
    static byte dev_VID = (byte) 0x88;

    public static JDY_type dv_type(byte[] p) {
        //Log.d( "out_3=","scan_byte_len:"+ p.length);
        if (p.length != 62) return null;
        //if( p.length!=0 )return null;
        String str;

			/*
			str = String.format( "%02x", p[5] );
			//Log.d( "scan_byte_bit_0=",""+ str);
			str = String.format( "%02x", p[6] );
			//Log.d( "scan_byte_bit_0=",""+ str);

			str = String.format( "%02x", p[11] );
			//Log.d( "scan_byte_bit_0=",""+ str);
			str = String.format( "%02x", p[12] );
			//Log.d( "scan_byte_bit_0=",""+ str);
			*/

        byte m1 = (byte) ((p[18 + 2] + 1) ^ 0x11);////透传模块密码位判断
        str = String.format("%02x", m1);
        //Log.d( "out_1","="+ str);

        byte m2 = (byte) ((p[17 + 2] + 1) ^ 0x22);//透传模块密码位判断
        str = String.format("%02x", m2);
        //Log.d( "out_2","="+ str);

			/*
			str = String.format( "%02x", p[44] );//0x10 ibeacon
			//Log.d( "iBeacon_0=",""+ str);
			str = String.format( "%02x", p[45] );//0x16
			//Log.d( "iBeacon_1=",""+ str);

			str = String.format( "%02x", p[52] );//major h
			//Log.d( "major_H=",""+ str);
			str = String.format( "%02x", p[53] );//major L
			//Log.d( "major_L=",""+ str);

			str = String.format( "%02x", p[54] );//minor h
			//Log.d( "minor_H=",""+ str);
			str = String.format( "%02x", p[55] );//minor L
			//Log.d( "minor_L=",""+ str);
			*/


        int ib1_major = 0;
        int ib1_minor = 0;
        if (p[52] == (byte) 0xff) {
            if (p[53] == (byte) 0xff) ib1_major = 1;
        }
        if (p[54] == (byte) 0xff) {
            if (p[55] == (byte) 0xff) ib1_minor = 1;
        }


        if (p[5] == (byte) 0xe0 && p[6] == (byte) 0xff && p[11] == m1 && p[12] == m2 && (dev_VID == p[19 - 6]))//JDY
        {
            byte[] WriteBytes = new byte[4];
            WriteBytes[0] = p[19 - 6];
            WriteBytes[1] = p[20 - 6];

            if (p[20 - 6] == (byte) 0xa0) return JDY_type.JDY;//透传
            else if (p[20 - 6] == (byte) 0xa5) return JDY_type.JDY_AMQ;//按摩器
            else if (p[20 - 6] == (byte) 0xb1) return JDY_type.JDY_LED1;// LED灯
            else if (p[20 - 6] == (byte) 0xb2) return JDY_type.JDY_LED2;// LED灯
            else if (p[20 - 6] == (byte) 0xc4) return JDY_type.JDY_KG;// 开关控制
            else if (p[20 - 6] == (byte) 0xc5) return JDY_type.JDY_KG1;// 开关控制

            //Log.d( "JDY_type.JDY=","1");
            return JDY_type.JDY;
        } else if (p[44] == (byte) 0x10 && p[45] == (byte) 0x16 && (ib1_major == 1 || ib1_minor == 1))//sensor
        {
//				 byte[] WriteBytes1 = new byte[2];
//				 WriteBytes1[0]=p[56];
//				 WriteBytes1[1]=p[57];
//				 Log.d( "JDY_type.JDY_19=","SS"+list_cell_0.bytesToHexString1( WriteBytes1 ) );

            //Log.d( "JDY_type.JDY_sensor=","2");
            return JDY_type.sensor_temp;
        } else if (p[3] == (byte) 0x1a && p[4] == (byte) 0xff//p[44]==(byte)0x10 && p[45]==(byte)0x16              //sensor
                ) {
//				 byte[] WriteBytes1 = new byte[2];
//				 WriteBytes1[0]=p[56];
//				 WriteBytes1[1]=p[57];
//				 Log.d( "JDY_type.JDY_19=","IB"+list_cell_0.bytesToHexString1( WriteBytes1 ) );
				/*
				if( p[57]==(byte)0xe0 ){return JDY_type.JDY_iBeacon;}//iBeacon模式
				else if( p[57]==(byte)0xe1 ){return JDY_type.sensor_temp;}////温度传感器
				else if( p[57]==(byte)0xe2 ){return JDY_type.sensor_humid;}////湿度传感器
				else if( p[57]==(byte)0xe3 ){return JDY_type.sensor_temp_humid;}////湿湿度传感器
				else if( p[57]==(byte)0xe4 ){return JDY_type.sensor_fanxiangji;}////芳香机香水用量显示仪
				else if( p[57]==(byte)0xe5 ){return JDY_type.sensor_zhilanshuibiao;}////智能水表传感器，抄表仪
				else if( p[57]==(byte)0xe6 ){return JDY_type.sensor_dianyabiao;}////电压传感器
				else if( p[57]==(byte)0xe7 ){return JDY_type.sensor_dianliu;}////电流传感器
				else if( p[57]==(byte)0xe8 ){return JDY_type.sensor_zhonglian;}////称重传感器
				else if( p[57]==(byte)0xe9 ){return JDY_type.sensor_pm2_5;}////PM2.5传感器
				*/
            return JDY_type.JDY_iBeacon;
        } else {
            //Log.d( "JDY_type.UNKW=","0");
            return JDY_type.UNKW;
        }

        //return JDY_type.JDY_iBeacon;
    }
}
