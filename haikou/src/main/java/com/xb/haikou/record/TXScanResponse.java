package com.xb.haikou.record;

import java.util.List;

public class TXScanResponse {

    /**
     * result : 调用成功
     * rescode : 0000
     * list : [{"result":"上送成功","mch_trx_id":"F9K0L67QH920190518114017541","rescode":"0000"}]
     */

    private String result;
    private String rescode;
    private List<ListBean> list;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRescode() {
        return rescode;
    }

    public void setRescode(String rescode) {
        this.rescode = rescode;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * result : 上送成功
         * mch_trx_id : F9K0L67QH920190518114017541
         * rescode : 0000
         */

        private String result;
        private String mch_trx_id;
        private String rescode;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getMch_trx_id() {
            return mch_trx_id;
        }

        public void setMch_trx_id(String mch_trx_id) {
            this.mch_trx_id = mch_trx_id;
        }

        public String getRescode() {
            return rescode;
        }

        public void setRescode(String rescode) {
            this.rescode = rescode;
        }
    }
}
