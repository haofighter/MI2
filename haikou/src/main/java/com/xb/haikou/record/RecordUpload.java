package com.xb.haikou.record;

import android.os.Environment;
import android.util.Log;
import com.google.gson.Gson;
import com.hao.lib.Util.FileUtils;
import com.hao.lib.net.OkHttpManager;
import com.xb.haikou.base.AppRunParam;
import com.xb.haikou.db.manage.DBManager;
import com.xb.haikou.moudle.function.unionpay.entity.UnionPayEntity;
import com.xb.haikou.net.NetUrl;
import com.xb.haikou.util.DateUtil;
import okhttp3.Call;
import okhttp3.Callback;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RecordUpload {

    //上传腾讯的记录
    public static void upLoadTXScanRecord() {
        List<ScanRecordEntity> scanRecordList = DBManager.checkTXScanRecord(false);
        if (scanRecordList.size() == 0) {
            return;
        }
        new OkHttpManager().setNetType(OkHttpManager.NetType.Post)
                .addFromParam("app_id", AppRunParam.getInstance().getAppId())
                .addFromParam("biz_data", new Gson().toJson(new BaseRecordUploadBean.ScanList(scanRecordList)))
                .setUrl(NetUrl.UPLOAD_TXSACN)
                .setNetBack(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("请求", "失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) {
                        try {
                            String result = response.body().string();
                            Log.i("TX请求", "成功：" + result);
                            TXScanResponse txScanResponse = new Gson().fromJson(result, TXScanResponse.class);
                            if (txScanResponse.getRescode().equals("0000")) {
                                for (int i = 0; i < txScanResponse.getList().size(); i++) {
                                    DBManager.updateTXScanRecord(txScanResponse.getList().get(i).getMch_trx_id());
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //上传阿里的记录
    public static void upLoadALScanRecord() {
        List<ScanRecordEntity> scanRecordList = DBManager.checkALScanRecord(false);
        if (scanRecordList.size() == 0) {
            return;
        }
        new OkHttpManager().setNetType(OkHttpManager.NetType.Post)
//                .addJsonParam(new Gson().toJson(baseRecordUploadBean))
                .addFromParam("app_id", AppRunParam.getInstance().getAppId())
                .addFromParam("data", new Gson().toJson(new BaseRecordUploadBean.ScanList(scanRecordList)))
                .setUrl(NetUrl.UPLOAD_ALSACN)
                .setNetBack(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("请求", "失败");
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) {
                        try {
                            String result = response.body().string();
                            Log.i("AL请求", "成功：" + result);
                            AliScanResponse aliScanResponse = new Gson().fromJson(result, AliScanResponse.class);
                            if (aliScanResponse.getRescode().equals("0000")) {
                                for (int i = 0; i < aliScanResponse.getDatalist().size(); i++) {
                                    DBManager.updateALScanRecord(aliScanResponse.getDatalist().get(i));
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public static void upLoadCardRecord() {
        List<CardRecordEntity> cardRecordEntities = DBManager.checkCardRecord(false);
        if (cardRecordEntities.size() == 0) {
            return;
        }
        new OkHttpManager().setNetType(OkHttpManager.NetType.Post)
//                .addJsonParam(new Gson().toJson(baseRecordUploadBean))
                .addFromParam("data", new Gson().toJson(cardRecordEntities))
                .setUrl(NetUrl.UP_CARD_RECORD)
                .setNetBack(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("请求", "失败");
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) {
                        try {
                            String result = response.body().string();
                            Log.i("card请求", "成功" + result);
                            CardUpResponse cardUpResponse = new Gson().fromJson(result, CardUpResponse.class);
                            if (cardUpResponse.getRescode().equals("0000")) {
                                for (int i = 0; i < cardUpResponse.getDataList().size(); i++) {
                                    DBManager.updateCardRecord(cardUpResponse.getDataList().get(i));
                                    Log.i("请求", "修改的Mch_trx_id()=" + cardUpResponse.getDataList().get(i).toString());
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public static void upUnionRecord() {
        List<UnionPayEntity> unionPayEntities = DBManager.checkUnionRecord(false);
        if (unionPayEntities.size() == 0) {
            return;
        }
        new OkHttpManager().setNetType(OkHttpManager.NetType.Post)
//                .addJsonParam(new Gson().toJson(baseRecordUploadBean))
                .addFromParam("data", new Gson().toJson(unionPayEntities))
                .setUrl(NetUrl.UP_UNION_RECORD)
                .setNetBack(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("请求", "失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) {
                        try {
                            String result = response.body().string();
                            Log.i("union请求", "成功" + result);
                            UnionUpResponse cardUpResponse = new Gson().fromJson(result, UnionUpResponse.class);
                            if (cardUpResponse.getRescode().equals("0000")) {
                                for (int i = 0; i < cardUpResponse.getDatalist().size(); i++) {
                                    DBManager.updatekUnionRecordUpStatus(cardUpResponse.getDatalist().get(i).getUniqueFlag());
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public static void upJTBRecord() {
        List<JTBscanRecord> unionPayEntities = DBManager.checkJTBScan(false);
        if (unionPayEntities.size() == 0) {
            return;
        }
        new OkHttpManager().setNetType(OkHttpManager.NetType.Post)
                .addFromParam("app_id", DBManager.checkBuildConfig().getMch_id())
                .addFromParam("data", new Gson().toJson(unionPayEntities))
                .setUrl(NetUrl.UP_JTB_RECORD)
                .setNetBack(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("请求", "失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) {
                        try {
                            String result = response.body().string();
                            Log.i("union请求", "成功" + result);
                            JTBScanUpResponse cardUpResponse = new Gson().fromJson(result, JTBScanUpResponse.class);
                            if (cardUpResponse.getRescode().equals("0000")) {
                                for (int i = 0; i < cardUpResponse.getDatalist().size(); i++) {
                                    DBManager.updateJTBRecord(cardUpResponse.getDatalist().get(i).getTerseno());
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public static void clearDateBase() {
        try {
            if (DBManager.checkScanRecordNum() > 5000) {
                List<ScanRecordEntity> scanRecordEntities = DBManager.checkScanRecordList();
                String scanRecord = new Gson().toJson(scanRecordEntities);
                FileUtils.saveStrToFile(scanRecord, new File(Environment.getExternalStorageDirectory() + "/NewRecord/scan_record" + DateUtil.getCurrentDate2() + ".txt"));
                DBManager.deleteScanRecord(scanRecordEntities);
            }

            if (DBManager.checkUnionRecordNum(true) > 5000) {
                List<UnionPayEntity> unionPayEntities = DBManager.checkUnionRecordList();
                String unionRecord = new Gson().toJson(unionPayEntities);
                if (FileUtils.saveStrToFile(unionRecord, new File(Environment.getExternalStorageDirectory() + "/NewRecord/union_record" + DateUtil.getCurrentDate2() + ".txt"))) {
                    DBManager.deleteUnionRecord(unionPayEntities);
                }
            }

            if (DBManager.checkJTBScanNum(true) > 5000) {
                List<JTBscanRecord> jtBscanRecords = DBManager.checkJTBScanRecordList();
                String unionRecord = new Gson().toJson(jtBscanRecords);
                if (FileUtils.saveStrToFile(unionRecord, new File(Environment.getExternalStorageDirectory() + "/NewRecord/jtb_record" + DateUtil.getCurrentDate2() + ".txt"))) {
                    DBManager.deleteJTBRecord(jtBscanRecords);
                }
            }

            if (DBManager.checkCardRecordNum(true) > 5000) {
                List<CardRecordEntity> cardRecordEntities = DBManager.checkCardRecord();
                String unionRecord = new Gson().toJson(cardRecordEntities);
                if (FileUtils.saveStrToFile(unionRecord, new File(Environment.getExternalStorageDirectory() + "/NewRecord/card_record" + DateUtil.getCurrentDate2() + ".txt"))) {
                    DBManager.deleteCardRecord(cardRecordEntities);
                }
            }
        } catch (Exception e) {
            Log.i("错误", "clearDateBase(RecordUpload.java:207) 数据清除错误" + e.getMessage());
        }
    }
}
