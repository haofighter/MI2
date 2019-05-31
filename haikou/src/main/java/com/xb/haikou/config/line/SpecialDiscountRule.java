package com.xb.haikou.config.line;

public class SpecialDiscountRule {
    String startTime;
    String endTime;
    int discount;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "\nDiscountRule{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", discount='" + discount + '\'' +
                '}';
    }
}
