package com.xb.haikou.base;

import android.util.Log;
import com.xb.haikou.config.line.DiscountRule;
import com.xb.haikou.config.line.PayRuleInfo;
import com.xb.haikou.config.line.SingleTicktInfo;
import com.xb.haikou.config.line.SpecialDiscountRule;
import com.xb.haikou.db.manage.DBManager;
import com.xb.haikou.util.BusToast;
import com.xb.haikou.util.DateUtil;

import java.util.List;


//APP运行需要的参数
public class AppRunParam {

    private AppRunParam() {
    }

    String PSAMNO;

    //num  需要截取的位数 0 表示不截取
    public String getPSAM(int num) {
        PSAMNO = DBManager.checkRunConfig().getPSAM();
        if (num != 0) {
            if (PSAMNO.length() < num) {
                for (int i = 0; i < num - PSAMNO.length(); i++) {
                    PSAMNO = "0" + PSAMNO;
                }
            } else {
                PSAMNO = PSAMNO.substring(PSAMNO.length() - num, PSAMNO.length());
            }
        }
        return PSAMNO == null ? "" : PSAMNO;
    }

    private String station;

    public String getNowStation() {
        return "01";
    }

    //商户号
    private String macID;

    public String getMchID() {
        if (macID == null || macID.equals("")) {
            macID = DBManager.checkBuildConfig().getMch_id();
        }
        return macID == null ? "" : macID;
    }

    //车辆号
    private String carNo = "";

    public String getCarNo() {
        //TODO 车辆号
        return carNo;
    }

    //行驶方向
    private String direction = "";

    public String getDirection() {
        //TODO 行驶方向
        return direction;
    }


    //售票员ID
    public String getConductorid() {
        String conductorid = DBManager.checkRunConfig().getConductor();
        return conductorid == null ? "" : conductorid;
    }


    private static class AppRunParamHelp {
        public static AppRunParam appRunParam = new AppRunParam();
    }

    public static AppRunParam getInstance() {
        return AppRunParamHelp.appRunParam;
    }

    private String appId;//Appid

    public String getAppId() {
        if (appId == null || appId.equals("")) {
            appId = DBManager.checkBuildConfig().getMch_id();
        }
        return appId == null ? "000000" : appId;
    }

    private String cityCode;//城市代码

    public String getCityCode() {
        if (cityCode == null || cityCode.equals("")) {
            cityCode = DBManager.checkBuildConfig().getCity_code();
        }
        return cityCode == null ? "000000" : cityCode;
    }

    private String unitno;//公司编号

    public String getUnitno() {
        //TODO 公司编号获取逻辑
        return unitno == null ? "0000" : unitno;
    }

    private String posSn;

    public String getPosSn() {
        if (posSn == null) {
            posSn = DBManager.checkConfig().getPosSn();
        }
        return posSn == null ? "" : posSn;
    }

    private String lineId;

    public String getLineId() {
        if (lineId == null || lineId.equals("")) {
            lineId = DBManager.checkConfig().getLineNo();
        }
        return lineId == null ? "" : lineId;
    }


    private String lineName;

    public String getLineName() {
        if (lineName == null || lineName.equals("")) {
            lineName = DBManager.checkConfig().getLineName();
        }
        return lineName == null ? "" : lineName;
    }


    private String busNo;

    public String getBusNo() {
        if (busNo == null) {
            busNo = DBManager.checkConfig().getBusNo();
        }
        return busNo == null ? "" : busNo;
    }

    private int basePrice;

    public int getBasePrice() {
        if (basePrice == 0) {
            basePrice = DBManager.checkSinglePrice().getPriceBasic();
        }
        return basePrice;
    }

    public void setBasePrice(int price) {
        SingleTicktInfo singleTicktInfo = DBManager.checkSinglePrice();
        if (singleTicktInfo != null) {
            singleTicktInfo.setPriceBasic(price);
            DBManager.updateSinglePrice(singleTicktInfo);
        } else {
            BusToast.showToast("请设置线路", false);
        }
    }

    public int getOtherPayfee(String tag) {
        PayRuleInfo payRuleInfo = DBManager.checkPayRuleInfo(tag);
        int price = getBasePrice();
        if (payRuleInfo == null) {
            return price;
        }
        if (payRuleInfo.getSpecialDiscountRuleFlag().equals("00")) {//特殊规则周期 为00 即没有 使用基础规则
            List<DiscountRule> discountRules = payRuleInfo.getDiscountRule();
            for (int i = 0; i < discountRules.size(); i++) {
                //判断当前时间
                if (DateUtil.getNowHourMin() > DateUtil.hmStrToLong(discountRules.get(i).getStartTime()) && DateUtil.getNowHourMin() < DateUtil.hmStrToLong(discountRules.get(i).getEndTime())) {
                    if (payRuleInfo.getSpecialDiscountRuleType().equals("00")) {//按折扣优惠
                        price = discountRules.get(i).getDiscount() * AppRunParam.getInstance().basePrice / 100;
                    } else if (payRuleInfo.getSpecialDiscountRuleType().equals("01")) {//固定票价优惠
                        price = discountRules.get(i).getDiscount();
                    }
                }
            }
        } else {//使用普通规则
            if (payRuleInfo.getSpecialDiscountRuleAll().size() > 0) {
                List<SpecialDiscountRule> specialDiscountRules = payRuleInfo.getSpecialDiscountRuleAll();
                for (int i = 0; i < specialDiscountRules.size(); i++) {
                    //判断当前时间
                    if (DateUtil.getNowHourMin() > DateUtil.hmStrToLong(specialDiscountRules.get(i).getStartTime()) && DateUtil.getNowHourMin() < DateUtil.hmStrToLong(specialDiscountRules.get(i).getEndTime())) {
                        if (payRuleInfo.getSpecialDiscountRuleType().equals("00")) {
                            price = specialDiscountRules.get(i).getDiscount() * AppRunParam.getInstance().basePrice / 100;
                        } else if (payRuleInfo.getSpecialDiscountRuleType().equals("01")) {
                            price = 0;
                        } else if (payRuleInfo.getSpecialDiscountRuleType().equals("02")) {
                            price = specialDiscountRules.get(i).getDiscount();
                        }
                    }
                }
            }
        }
        if (price < 0 && price > 5000) {
            price = AppRunParam.getInstance().basePrice;
        }

        Log.i("刷卡当前票价", "方式=" + tag + "        price=" + price);
        return 1;
    }

    public int getCardPayFee(String cardType) {
        try {
            PayRuleInfo payRuleInfo = DBManager.checkPayRuleInfo("PK", cardType);
            int price = getBasePrice();
            if (payRuleInfo == null) {
                return price;
            }
            if (payRuleInfo.getSpecialDiscountRuleFlag().equals("00")) {//特殊规则周期 为00 即没有 使用基础规则
                List<DiscountRule> discountRules = payRuleInfo.getDiscountRule();
                for (int i = 0; i < discountRules.size(); i++) {
                    if (DateUtil.hmStrToLong(discountRules.get(i).getStartTime()) > DateUtil.hmStrToLong(discountRules.get(i).getEndTime())) {//跨天的时间判断 23：00-05：00 优惠
                        //判断当前时间
                        if (DateUtil.getNowHourMin() > DateUtil.hmStrToLong(discountRules.get(i).getStartTime()) && DateUtil.getNowHourMin() < DateUtil.hmStrToLong(discountRules.get(i).getEndTime())) {
                            if (payRuleInfo.getSpecialDiscountRuleType().equals("00")) {//按折扣优惠
                                price = discountRules.get(i).getDiscount() * AppRunParam.getInstance().basePrice / 100;
                            } else if (payRuleInfo.getSpecialDiscountRuleType().equals("01")) {//固定票价优惠
                                price = discountRules.get(i).getDiscount();
                            }
                        }
                    } else {
                        //判断当前时间
                        if (DateUtil.getNowHourMin() > DateUtil.hmStrToLong(discountRules.get(i).getStartTime()) || DateUtil.getNowHourMin() < DateUtil.hmStrToLong(discountRules.get(i).getEndTime())) {
                            if (payRuleInfo.getSpecialDiscountRuleType().equals("00")) {//按折扣优惠
                                price = discountRules.get(i).getDiscount() * AppRunParam.getInstance().basePrice / 100;
                            } else if (payRuleInfo.getSpecialDiscountRuleType().equals("01")) {//固定票价优惠
                                price = discountRules.get(i).getDiscount();
                            }
                        }
                    }
                }
            } else {//使用特殊规则
                if (payRuleInfo.getSpecialDiscountRuleAll().size() > 0) {
                    List<SpecialDiscountRule> specialDiscountRules = payRuleInfo.getSpecialDiscountRuleAll();
                    for (int i = 0; i < specialDiscountRules.size(); i++) {
                        //跨天的时间判断 23：00-05：00 优惠
                        if (DateUtil.hmStrToLong(specialDiscountRules.get(i).getStartTime()) > DateUtil.hmStrToLong(specialDiscountRules.get(i).getEndTime())) {
                            //判断当前时间
                            if (DateUtil.getNowHourMin() > DateUtil.hmStrToLong(specialDiscountRules.get(i).getStartTime()) && DateUtil.getNowHourMin() < DateUtil.hmStrToLong(specialDiscountRules.get(i).getEndTime())) {
                                if (payRuleInfo.getSpecialDiscountRuleType().equals("00")) {
                                    price = specialDiscountRules.get(i).getDiscount() * AppRunParam.getInstance().basePrice / 100;
                                } else if (payRuleInfo.getSpecialDiscountRuleType().equals("01")) {
                                    price = 0;
                                } else if (payRuleInfo.getSpecialDiscountRuleType().equals("02")) {
                                    price = specialDiscountRules.get(i).getDiscount();
                                }
                            }
                        } else {
                            if (DateUtil.getNowHourMin() > DateUtil.hmStrToLong(specialDiscountRules.get(i).getStartTime()) || DateUtil.getNowHourMin() < DateUtil.hmStrToLong(specialDiscountRules.get(i).getEndTime())) {
                                if (payRuleInfo.getSpecialDiscountRuleType().equals("00")) {
                                    price = specialDiscountRules.get(i).getDiscount() * AppRunParam.getInstance().basePrice / 100;
                                } else if (payRuleInfo.getSpecialDiscountRuleType().equals("01")) {
                                    price = 0;
                                } else if (payRuleInfo.getSpecialDiscountRuleType().equals("02")) {
                                    price = specialDiscountRules.get(i).getDiscount();
                                }
                            }
                        }
                    }
                }
            }
            if (price < 0 || price > 5000) {
                price = AppRunParam.getInstance().basePrice;
            }

            Log.i("刷卡当前票价", "price=" + price);
            return price;
        } catch (Exception e) {
            return AppRunParam.getInstance().getBasePrice();
        }
    }


    //司机编号
    public String getDriverNo() {
        String driverNo = DBManager.checkRunConfig().getDriverNo();
        return driverNo == null ? "" : driverNo;
    }


}
