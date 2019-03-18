package com.hao.mivoice.bluetooth;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.hao.mivoice.MainActivity;
import com.hao.mivoice.R;
import com.hao.mivoice.jdy_type.JDY_type;

import java.util.ArrayList;
import java.util.List;

import static com.hao.mivoice.jdy_type.PraseBluetoothRecord.PraseBlueTRecord.dv_type;


public class BluetoothActivity extends AppCompatActivity {
    private BluetoothAdapter mBluetoothAdapter;
    BluetoothManager bluetoothManager;
    ListView li;
    Button button;
    List<BlueToothDate> blueToothDateList = new ArrayList<>();
    ShowBlueToothAdapter showBlueToothAdapter;

    BluetoothAdapter.LeScanCallback leScanCallback;

    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            leScanCallback = new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                    JDY_type m_tyep = dv_type(scanRecord);
                    if (m_tyep != JDY_type.UNKW && m_tyep != null) {
                        Log.i("设备", device.getAddress());
                        int num = 0;
                        for (int i = 0; i < blueToothDateList.size(); i++) {
                            if (blueToothDateList.get(i).device.getAddress().equals(device.getAddress())) {
                                blueToothDateList.set(i, new BlueToothDate(device, rssi, scanRecord, m_tyep));
                            } else {
                                num++;
                            }
                        }
                        if (num >= blueToothDateList.size()) {
                            blueToothDateList.add(new BlueToothDate(device, rssi, scanRecord, m_tyep));
                        }
                    }
                    showBlueToothAdapter.setDate(blueToothDateList);
                }
            };
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        initView();
        openBuleTooth();
    }

    private void initView() {
        li = findViewById(R.id.blue_tooth_list);
        button = findViewById(R.id.button);
        showBlueToothAdapter = new ShowBlueToothAdapter();
        li.setAdapter(showBlueToothAdapter);
        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                connectBlueTooth(position);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View v) {
                mBluetoothAdapter.startLeScan(leScanCallback);
            }
        });

        findViewById(R.id.input).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BluetoothActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void openBuleTooth() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            mBluetoothAdapter = bluetoothManager.getAdapter();


            if (mBluetoothAdapter == null) {
                Toast.makeText(this, "该设备不支持蓝牙", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            // 如果本地蓝牙没有开启，则开启
            if (!mBluetoothAdapter.isEnabled()) {
                // 我们通过startActivityForResult()方法发起的Intent将会在onActivityResult()回调方法中获取用户的选择，比如用户单击了Yes开启，
                // 那么将会收到RESULT_OK的结果，
                // 如果RESULT_CANCELED则代表用户不愿意开启蓝牙
                Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(mIntent, 1);
                // 用enable()方法来开启，无需询问用户(实惠无声息的开启蓝牙设备),这时就需要用到android.permission.BLUETOOTH_ADMIN权限。
                // mBluetoothAdapter.enable();
                // mBluetoothAdapter.disable();//关闭蓝牙
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onResume() {
        super.onResume();
        mBluetoothAdapter.startLeScan(leScanCallback);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBluetoothAdapter.stopLeScan(leScanCallback);
    }


    void connectBlueTooth(int position) {
        BlueToothDate blueToothDate = showBlueToothAdapter.getItem(position);
        if ((byte) blueToothDate.get_vid(blueToothDate) == (byte) 0x88) {
            switch (blueToothDate.m_tyep) {
                case JDY:////为标准透传模块{
                    BluetoothDevice device1 = blueToothDate.device;
                    if (device1 == null) return;
                    Intent intent1 = new Intent(BluetoothActivity.this, MainActivity.class);
                    intent1.putExtra("name", device1.getName());
                    intent1.putExtra("address", device1.getAddress());
                    startActivity(intent1);
                    finish();
                    break;
                case JDY_iBeacon:////为iBeacon设备
//                            BluetoothDevice device1 = mDevListAdapter.get_item_dev(position);
//                            if (device1 == null) return;
//                            Intent intent1 = new Intent(DeviceScanActivity.this, jdy_ibeacon_Activity.class);
//                            ;
//                            intent1.putExtra(jdy_ibeacon_Activity.EXTRAS_DEVICE_NAME, device1.getName());
//                            intent1.putExtra(jdy_ibeacon_Activity.EXTRAS_DEVICE_ADDRESS, device1.getAddress());
//
//                            intent1.putExtra(jdy_ibeacon_Activity.EXTRAS_DEVICE_UUID, mDevListAdapter.get_iBeacon_uuid(position));
//                            intent1.putExtra(jdy_ibeacon_Activity.EXTRAS_DEVICE_MAJOR, mDevListAdapter.get_ibeacon_major(position));
//                            intent1.putExtra(jdy_ibeacon_Activity.EXTRAS_DEVICE_MINOR, mDevListAdapter.get_ibeacon_minor(position));
//
//                            // if (mScanning)
//                        {
//                            mDevListAdapter.scan_jdy_ble(false);
//                            ;
//                            mScanning = false;
//                        }
//                        startActivity(intent1);
                    break;
                case sensor_temp://温度传感器

                    break;
                case JDY_KG://开关控制APP
//                            BluetoothDevice device1 = mDevListAdapter.get_item_dev(position);
//                            if (device1 == null) return;
//                            Intent intent1 = new Intent(DeviceScanActivity.this, jdy_switch_Activity.class);
//                            ;
//                            intent1.putExtra(jdy_switch_Activity.EXTRAS_DEVICE_NAME, device1.getName());
//                            intent1.putExtra(jdy_switch_Activity.EXTRAS_DEVICE_ADDRESS, device1.getAddress());
//                            // if (mScanning)
//                        {
//                            mDevListAdapter.scan_jdy_ble(false);
//                            ;
//                            mScanning = false;
//                        }
//                        startActivity(intent1);
                    break;
                case JDY_KG1://开关控制APP
//                            BluetoothDevice device1 = mDevListAdapter.get_item_dev(position);
//                            if (device1 == null) return;
//                            Intent intent1 = new Intent(DeviceScanActivity.this, shengjiangji.class);
//                            ;
//                            intent1.putExtra(jdy_switch_Activity.EXTRAS_DEVICE_NAME, device1.getName());
//                            intent1.putExtra(jdy_switch_Activity.EXTRAS_DEVICE_ADDRESS, device1.getAddress());
//                            // if (mScanning)
//                        {
//                            mDevListAdapter.scan_jdy_ble(false);
//                            ;
//                            mScanning = false;
//                        }
//                        startActivity(intent1);
                    break;
                case JDY_AMQ://massager 按摩器APP
//                            BluetoothDevice device1 = mDevListAdapter.get_item_dev(position);
//                            if (device1 == null) return;
//                            Intent intent1 = new Intent(DeviceScanActivity.this, AV_Stick.class);
//                            intent1.putExtra(AV_Stick.EXTRAS_DEVICE_NAME, device1.getName());
//                            intent1.putExtra(AV_Stick.EXTRAS_DEVICE_ADDRESS, device1.getAddress());
//                            // if (mScanning)
//                        {
//                            mDevListAdapter.scan_jdy_ble(false);
//                            ;
//                            mScanning = false;
//                        }
//                        startActivity(intent1);
                    break;
                case JDY_LED1:// LED灯 APP 测试版本
                        /*
							 BluetoothDevice device1 = mDevListAdapter.get_item_dev(position);
						        if (device1 == null) return;
						        Intent intent1 = new Intent(DeviceScanActivity.this,MainActivity.class);
						        intent1.putExtra( MainActivity.EXTRAS_DEVICE_NAME, device1.getName() );
						        intent1.putExtra( MainActivity.EXTRAS_DEVICE_ADDRESS, device1.getAddress() );
						       // if (mScanning)
						        {
						        	mDevListAdapter.scan_jdy_ble( false );;
						            mScanning = false;
						        }
						        startActivity(intent1);*/
                    break;
                case JDY_LED2:// LED灯 APP 正试版本
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


}
