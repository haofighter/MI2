package com.xb.haikou.config.line;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 单票制票价信息
 */

@Entity
public class SingleTicktInfo {

    @Id(autoincrement = true)
    Long id;
    int start;
    @Unique
    String tag;
    int len;
    Long updatetime;

    //线路基本票价，单位为分
    private int priceBasic;
    //支持连刷标志，1:支持连刷，其他:不支持连刷
    private String continuousSwipFlag;
    //步长金额，单位为分
    // 例:在支持连刷的情况下，基础票价为 1 元，
    // 步长金 额为2元时，乘客刷1次码票价1元，
    // 刷第2次码票 价增加2元，刷第3次码票价再增加2元，
    // 对3次刷码做合并连刷处理后，做一次 5 元交易上送。
    private int stepPrice;

    public SingleTicktInfo(int start, String tag, int len) {
        this.start = start;
        this.tag = tag;
        this.len = len;
    }

    @Generated(hash = 1989132820)
    public SingleTicktInfo(Long id, int start, String tag, int len, Long updatetime,
                           int priceBasic, String continuousSwipFlag, int stepPrice) {
        this.id = id;
        this.start = start;
        this.tag = tag;
        this.len = len;
        this.updatetime = updatetime;
        this.priceBasic = priceBasic;
        this.continuousSwipFlag = continuousSwipFlag;
        this.stepPrice = stepPrice;
    }

    @Generated(hash = 1799821104)
    public SingleTicktInfo() {
    }

    public Long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Long updatetime) {
        this.updatetime = updatetime;
    }

    public int getPriceBasic() {
        return priceBasic;
    }

    public void setPriceBasic(int priceBasic) {
        this.priceBasic = priceBasic;
    }

    public String getContinuousSwipFlag() {
        return continuousSwipFlag;
    }

    public void setContinuousSwipFlag(String continuousSwipFlag) {
        this.continuousSwipFlag = continuousSwipFlag;
    }

    public int getStepPrice() {
        return stepPrice;
    }

    public void setStepPrice(int stepPrice) {
        this.stepPrice = stepPrice;
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

    @Override
    public String toString() {
        return "SingleTicktInfo{" +
                "id=" + id +
                ", start=" + start +
                ", tag='" + tag + '\'' +
                ", len=" + len +
                ", priceBasic=" + priceBasic +
                ", continuousSwipFlag='" + continuousSwipFlag + '\'' +
                ", stepPrice=" + stepPrice +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
