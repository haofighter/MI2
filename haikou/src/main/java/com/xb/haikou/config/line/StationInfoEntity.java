package com.xb.haikou.config.line;

import com.hao.lib.Util.FileUtils;
import com.hao.lib.Util.Type;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import static java.lang.System.arraycopy;

@Entity
public class StationInfoEntity {
    @Id(autoincrement = true)
    private Long id;
    private String stationID;
    private String stationName;
    private String lat;//纬度
    private String lng;//经度
    private int stationRadius;//站点半径
    private String Reserved;
    private String diraction;//行驶方向 0 上行 1下行

    @Generated(hash = 1085007785)
    public StationInfoEntity(Long id, String stationID, String stationName, String lat, String lng,
                             int stationRadius, String Reserved, String diraction) {
        this.id = id;
        this.stationID = stationID;
        this.stationName = stationName;
        this.lat = lat;
        this.lng = lng;
        this.stationRadius = stationRadius;
        this.Reserved = Reserved;
        this.diraction = diraction;
    }

    @Generated(hash = 814053835)
    public StationInfoEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public int getStationRadius() {
        return stationRadius;
    }

    public void setStationRadius(int stationRadius) {
        this.stationRadius = stationRadius;
    }

    public String getReserved() {
        return Reserved;
    }

    public void setReserved(String reserved) {
        Reserved = reserved;
    }

    public void setLoaction(byte[] stationLocation) {
        int i = 0;
        byte[] LngRight = new byte[1];
        arraycopy(stationLocation, i, LngRight, 0, LngRight.length);
        i += LngRight.length;
        int lngRight = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(LngRight, Type.HEX));

        byte[] LngLeft = new byte[3];
        arraycopy(stationLocation, i, LngLeft, 0, LngLeft.length);
        i += LngLeft.length;
        int lngLeft = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(LngLeft, Type.HEX));

        lng = lngRight + "." + lngLeft;

        byte[] LatRight = new byte[1];
        arraycopy(stationLocation, i, LatRight, 0, LatRight.length);
        i += LatRight.length;
        int latRight = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(LatRight, Type.HEX));

        byte[] LatLeft = new byte[3];
        arraycopy(stationLocation, i, LatLeft, 0, LatLeft.length);
        i += LatLeft.length;
        int latLeft = FileUtils.hexStringToInt((String) FileUtils.byte2Parm(LatLeft, Type.HEX));

        lat = latRight + "." + latLeft;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDiraction() {
        return this.diraction;
    }

    public void setDiraction(String diraction) {
        this.diraction = diraction;
    }

    @Override
    public String toString() {
        return "\nStationInfoEntity{" +
                "id=" + id +
                ", stationID='" + stationID + '\'' +
                ", stationName='" + stationName + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", stationRadius=" + stationRadius +
                ", Reserved='" + Reserved + '\'' +
                ", diraction='" + diraction + '\'' +
                '}';
    }
}
