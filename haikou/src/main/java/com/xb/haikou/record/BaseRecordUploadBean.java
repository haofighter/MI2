package com.xb.haikou.record;

import java.util.List;

public class BaseRecordUploadBean {
    String app_id;
    ScanList biz_data;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public ScanList getData() {
        return biz_data;
    }

    public void setData(ScanList data) {
        this.biz_data = data;
    }

   public static class ScanList {
        List<ScanRecordEntity> order_list;

       public ScanList(List<ScanRecordEntity> order_list) {
           this.order_list = order_list;
       }
   }
}
