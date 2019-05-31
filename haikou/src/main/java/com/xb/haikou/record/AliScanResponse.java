package com.xb.haikou.record;

import java.util.List;

public class AliScanResponse {

    /**
     * result : 消费流水已登记成功
     * datalist : [{"terseno":"GM68vW72D320190518155354306","result":"上传失败,订单重复","transTime":"2019-05-18 15:53:54","rescode":"0001"}]
     * rescode : 0000
     */

    private String result;
    private String rescode;
    private List<DatalistBean> datalist;

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

    public List<DatalistBean> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<DatalistBean> datalist) {
        this.datalist = datalist;
    }

    public static class DatalistBean {
        /**
         * terseno : GM68vW72D320190518155354306
         * result : 上传失败,订单重复
         * transTime : 2019-05-18 15:53:54
         * rescode : 0001
         */

        private String terseno;
        private String result;
        private String transTime;
        private String rescode;

        public String getTerseno() {
            return terseno;
        }

        public void setTerseno(String terseno) {
            this.terseno = terseno;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getTransTime() {
            return transTime;
        }

        public void setTransTime(String transTime) {
            this.transTime = transTime;
        }

        public String getRescode() {
            return rescode;
        }

        public void setRescode(String rescode) {
            this.rescode = rescode;
        }
    }
}
