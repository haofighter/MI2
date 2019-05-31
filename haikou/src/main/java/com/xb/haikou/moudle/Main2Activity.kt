package com.xb.haikou.moudle

import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import com.hao.lib.Util.DataUtils
import com.hao.lib.Util.FileUtils
import com.hao.lib.Util.ThreadUtils
import com.szxb.jni.SerialCom
import com.xb.haikou.R
import com.xb.haikou.base.App
import com.xb.haikou.base.AppRunParam
import com.xb.haikou.base.BaseActivity
import com.xb.haikou.cmd.DoCmd
import com.xb.haikou.config.ConfigContext
import com.xb.haikou.config.InitConfig
import com.xb.haikou.db.manage.DBManager
import com.xb.haikou.moudle.function.card.CardInfoEntity
import com.xb.haikou.moudle.function.card.PraseCard
import com.xb.haikou.moudle.maintool.HistoryAdapter
import com.xb.haikou.moudle.maintool.MainToolAdapter
import com.xb.haikou.util.BusToast
import com.xb.haikou.util.DateUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.param_layout.*
import java.util.concurrent.TimeUnit

class Main2Activity : BaseActivity() {
    var operate = 0;//未操作的时间间隔
    var viewTag = 0;//1 菜单  2 历史记录  3 参数  0 主页无任何附加页面

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main.setBackgroundResource(R.mipmap.background)
        initView()
        updateView()
        now_time.setOnClickListener {
            SerialCom.DevRest()//重启k21
        }
    }

    private fun updateView() {
        ThreadUtils.getInstance().createSch("mian").scheduleWithFixedDelay(Runnable {
            runOnUiThread {
                try {
                    operate++
                    refreshView()
                    if (operate >= 6) {//6s钟未操作就重置界面
                        refreshMoudle()
                    }
                } catch (e: Exception) {
                    Log.i("界面更新", "失败：" + e.message);
                }
            }
        }, 0, 1, TimeUnit.SECONDS);

    }

    //更新UI
    fun refreshView() {
        now_time.text = DateUtil.getCurrentDate();
        version_tx.text = "[" + App.getInstance().getPakageVersion() + "]"
        if (!TextUtils.isEmpty(DBManager.checkRunConfig().driverNo)) {
            is_sign.visibility = View.VISIBLE;
            no_sign.visibility = View.GONE;
            line.text = DBManager.checkRunConfig().lineName
            price.text = DataUtils.fen2Yuan(DBManager.checkSinglePrice().priceBasic)
            pos_num.text = AppRunParam.getInstance().posSn
        } else {
            is_sign.visibility = View.GONE;
            no_sign.visibility = View.VISIBLE;
        }
    }

    fun refreshMoudle() {
        viewTag = 0
        if (tools_list.visibility == View.VISIBLE) {
            setViewAnimal(tools_list, false)
        }

        if (history_list.visibility == View.VISIBLE) {
            setViewAnimal(history_list, false)
        }

        if (param_layout.visibility == View.VISIBLE) {
            setViewAnimal(param_layout, false)
        }
    }

    private fun initView() {
        main.setBackgroundResource(R.mipmap.background)
        var tools: MutableList<String> = arrayListOf()
        tools.add("机器参数")
        tools.add("刷卡记录");
        tools.add("TX二维码记录");
        tools.add("银联记录");
        tools.add("支付宝记录");
        tools.add("交通部记录");
        tools.add("更新初始化的参数");
        tools.add("导出历史数据");
        tools_list.layoutManager = LinearLayoutManager(this)
        tools_list.adapter = MainToolAdapter(this, tools)

        history_list.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        history_list.layoutManager = LinearLayoutManager(this)
        history_list.adapter = HistoryAdapter(this)
    }

    fun keyUp() {
        if (tools_list.visibility == View.VISIBLE) {
            (tools_list.adapter as MainToolAdapter).setSelectRefuse();
        }
    }


    fun keyDown() {
        if (tools_list.visibility == View.VISIBLE) {
            (tools_list.adapter as MainToolAdapter).setSelectAdd()
        }
    }

    override fun rxDo(tag: Any, o: Any) {
        super.rxDo(tag, o)
        if (tag is ByteArray) {
            //消费获取记录
            PraseCard.payResponse(DoCmd.getPayRecord(tag), o as CardInfoEntity)
        } else if (tag is String) {
            when (o) {
                "setRight" -> PraseCard.checkMac(DoCmd.checkMac(o as ByteArray))

                ConfigContext.KEY_BUTTON_BOTTOM_LEFT -> {
                    operate = 0
                    keyDown()
                }
                ConfigContext.KEY_BUTTON_BOTTOM_RIGHT
                -> {
                    operate = 0
                    keyCancal()
                }
                ConfigContext.KEY_BUTTON_TOP_LEFT
                -> {
                    operate = 0
                    keyUp()
                }
                ConfigContext.KEY_BUTTON_TOP_RIGHT
                -> {
                    operate = 0
                    keyComfir()
                }
            }
        }
    }

    private fun keyComfir() {
        if (viewTag == 0) {
            viewTag = 1
            setViewAnimal(tools_list, true)
        } else if (viewTag == 1) {
            setViewAnimal(tools_list, false)
            when ((tools_list.adapter as MainToolAdapter).check) {
                0 -> {//机器参数
                    initParam()
                    viewTag = 3
                    setViewAnimal(param_layout, true)
                }
                1 -> {
                    //刷卡记录
                    setViewAnimal(history_list, true)
                    viewTag = 2
                    (history_list.adapter as HistoryAdapter).setType(0)
                }
                2 -> {
                    //TX二维码记录
                    setViewAnimal(history_list, true)
                    viewTag = 2
                    (history_list.adapter as HistoryAdapter).setType(1)
                }
                3 -> {
                    //银联记录
                    setViewAnimal(history_list, true)
                    viewTag = 2
                    (history_list.adapter as HistoryAdapter).setType(2)
                }
                4 -> {
                    //AL二维码记录
                    setViewAnimal(history_list, true)
                    viewTag = 2
                    (history_list.adapter as HistoryAdapter).setType(3)
                }
                5 -> {
                    //交通部二维码记录
                    setViewAnimal(history_list, true)
                    viewTag = 2
                    (history_list.adapter as HistoryAdapter).setType(4)
                }
                6 -> {
                    //更新初始化的参数
                    InitConfig().initBin()//加载bin文件
                        .wxMacKey//获取微信秘钥
                        .wxPublicKey//获取微信公钥
                        .alPublicKey//获取支付宝公钥
//                        .tranPublicKey//交通部
                }
                7 -> {//导出历史数据
                    FileUtils.copyDir(
                        Environment.getExternalStorageDirectory().toString() + "/NewRecord",
                        "/storage/sdcard1/newrecord"
                    )
                    BusToast.showToast("导出成功", true)
                    FileUtils.delFolder(Environment.getExternalStorageDirectory().toString() + "/NewRecord")
                }
            }
        }
    }

    private fun keyCancal() { refreshMoudle() }

    fun initParam() {
        driver_no.text = AppRunParam.getInstance().driverNo;
        bus_no.text = AppRunParam.getInstance().busNo
        line_name.text = AppRunParam.getInstance().lineName
        line_id.text = AppRunParam.getInstance().lineId
        bin_ver.text = DBManager.checkRunConfig().binVer
        app_ver.text = App.getInstance().pakageVersion
        txscan.text = DBManager.checkTXScanRecordNum(false).toString() + "/" + DBManager.checkTXScanRecordNum(true);
        alscan.text = DBManager.checkALScanRecordNum(false).toString() + "/" + DBManager.checkALScanRecordNum(true);
        jtbscan.text = DBManager.checkJTBScanNum(false).toString() + "/" + DBManager.checkJTBScanNum(true)
        union_date.text = DBManager.checkUnionRecordNum(false).toString() + "/" + DBManager.checkUnionRecordNum(true)
        card_date.text = DBManager.checkCardRecordNum(false).toString() + "/" + DBManager.checkCardRecordNum(true)
        param_date.setText("")
        param_date.append("TXMAC  " + if (App.appPreload.isTencentMacKeySuc) "成功" else "失败")
        param_date.append("\nTXPUC  " + if (App.appPreload.isTencentPublicKeySuc) "成功" else "失败")
        param_date.append("\nALPUC  " + if (App.appPreload.isALPublicKey) "成功" else "失败")
        param_date.append("\nJTBPUC " + if (App.appPreload.isJTBParam) "成功" else "失败")
    }
}

