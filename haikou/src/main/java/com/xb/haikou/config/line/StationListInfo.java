package com.xb.haikou.config.line;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class StationListInfo {
    @Id(autoincrement = true)
    Long id;
    int start;
    @Unique
    String tag;
    int len;
    int stationNumUp;
    String StationsListUp;
    int stationNumDn;
    int CardTypeNum;


    public StationListInfo(int start, String tag, int len) {
        this.start = start;
        this.tag = tag;
        this.len = len;
    }

    @Generated(hash = 1289865865)
    public StationListInfo(Long id, int start, String tag, int len, int stationNumUp,
            String StationsListUp, int stationNumDn, int CardTypeNum) {
        this.id = id;
        this.start = start;
        this.tag = tag;
        this.len = len;
        this.stationNumUp = stationNumUp;
        this.StationsListUp = StationsListUp;
        this.stationNumDn = stationNumDn;
        this.CardTypeNum = CardTypeNum;
    }

    @Generated(hash = 49377661)
    public StationListInfo() {
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

    public int getCardTypeNum() {
        return CardTypeNum;
    }

    public void setCardTypeNum(int cardTypeNum) {
        CardTypeNum = cardTypeNum;
    }

    public int getStationNumUp() {
        return stationNumUp;
    }

    public void setStationNumUp(int stationNumUp) {
        this.stationNumUp = stationNumUp;
    }

    public String getStationsListUp() {
        return StationsListUp;
    }

    public List<StationInfoEntity> getStationsListUpArray() {
        return new Gson().fromJson(StationsListUp, new TypeToken<List<StationInfoEntity>>() {
        }.getType());
    }

    public void setStationsListUp(String stationsListUp) {
        StationsListUp = stationsListUp;
    }

    public void setStationsListUpArray(List<StationInfoEntity> stationsListUp) {
        StationsListUp = new Gson().toJson(stationsListUp);
    }

    public int getStationNumDn() {
        return stationNumDn;
    }

    public void setStationNumDn(int stationNumDn) {
        this.stationNumDn = stationNumDn;
    }

    @Override
    public String toString() {
        return "StationListInfo{" +
                "start=" + start +
                ", tag='" + tag + '\'' +
                ", len=" + len +
                ", stationNumUp=" + stationNumUp +
                ", StationsListUp=" + StationsListUp +
                ", stationNumDn=" + stationNumDn +
                ", CardTypeNum=" + CardTypeNum +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
