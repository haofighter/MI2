package com.xb.haikou.config.line;

import java.util.List;

public class MoreTicketInfo {

    int start;
    String tag;
    int len;

    public MoreTicketInfo(int start, String tag, int len) {
        this.start = start;
        this.tag = tag;
        this.len = len;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public static class MoreTicetDetail {
        private int up;
        private int down;
        private String price;
        private String diraction; //0 上行  1 下行

        public int getUp() {
            return up;
        }

        public void setUp(int up) {
            this.up = up;
        }

        public int getDown() {
            return down;
        }

        public void setDown(int down) {
            this.down = down;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDiraction() {
            return diraction;
        }

        public void setDiraction(String diraction) {
            this.diraction = diraction;
        }
    }

    List<MoreTicetDetail> moreTicetDetails;

    public List<MoreTicetDetail> getMoreTicetDetails() {
        return moreTicetDetails;
    }

    public void setMoreTicetDetails(List<MoreTicetDetail> moreTicetDetails) {
        this.moreTicetDetails = moreTicetDetails;
    }
}
