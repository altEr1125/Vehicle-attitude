package com.carsimu.tspcommon.pojo;

import java.util.Date;

public class EngineData implements InformationBody {
    private Long carId; //对应车辆的ID
    private String carVin;    //对应车辆的vin码
    private String carSpeed;      //速度                  0000
    private String atmPre;    // 大气压                    C5
    private String netTor; //净扭矩                           7D
    private String friTor;  //实际扭矩                      83
    private String engineSpeed; //发动机转速                 0000
    private String engineFuelFlow;  // 发动机燃料流量          0000
    private String upNOx;  //SCR上游NOx传感器                0000
    private String downNOx; //SCR下游NOx传感器               0000
    private String reagent; //反应剂余量                     61
    private String airIn;   //进气量                         0000
    private String inTemp; //SCR入口温度                    FEFF
    private String outTemp; //SCR出口温度                   FEFF
    private String dpf; //  DFP压差                       FEFF
    private String coolantTemp; //冷却液温度                 3D
    private String oilLevel;//邮箱液位                      00
    private String position;//定位状态                      00
    private boolean posStatus;                //true:无效定位,false:有效定位      false
    private boolean naLatitude;                //true:南纬,false:北纬           false
    private boolean ewLongitude;                //true:西经,false:东经          false
    private String longitude;   //经度                    06AFD317    06EEF972-06F206B2 116324722-116524722
    private String latitude;    //纬度                    01EA55FE    025F6273-02626FB3 39805555-40005555
    private String totalMile;   //累计里程                  00000028
    private Date uploadTime;

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getCarSpeed() {
        return carSpeed;
    }

    public void setCarSpeed(String carSpeed) {
        this.carSpeed = carSpeed;
    }

    public String getAtmPre() {
        return atmPre;
    }

    public void setAtmPre(String atmPre) {
        this.atmPre = atmPre;
    }

    public String getNetTor() {
        return netTor;
    }

    public void setNetTor(String netTor) {
        this.netTor = netTor;
    }

    public String getFriTor() {
        return friTor;
    }

    public void setFriTor(String friTor) {
        this.friTor = friTor;
    }

    public String getEngineSpeed() {
        return engineSpeed;
    }

    public void setEngineSpeed(String engineSpeed) {
        this.engineSpeed = engineSpeed;
    }

    public String getEngineFuelFlow() {
        return engineFuelFlow;
    }

    public void setEngineFuelFlow(String engineFuelFlow) {
        this.engineFuelFlow = engineFuelFlow;
    }

    public String getUpNOx() {
        return upNOx;
    }

    public void setUpNOx(String upNOx) {
        this.upNOx = upNOx;
    }

    public String getDownNOx() {
        return downNOx;
    }

    public void setDownNOx(String downNOx) {
        this.downNOx = downNOx;
    }

    public String getReagent() {
        return reagent;
    }

    public void setReagent(String reagent) {
        this.reagent = reagent;
    }

    public String getAirIn() {
        return airIn;
    }

    public void setAirIn(String airIn) {
        this.airIn = airIn;
    }

    public String getInTemp() {
        return inTemp;
    }

    public void setInTemp(String inTemp) {
        this.inTemp = inTemp;
    }

    public String getOutTemp() {
        return outTemp;
    }

    public void setOutTemp(String outTemp) {
        this.outTemp = outTemp;
    }

    public String getDpf() {
        return dpf;
    }

    public void setDpf(String dpf) {
        this.dpf = dpf;
    }

    public String getCoolantTemp() {
        return coolantTemp;
    }

    public void setCoolantTemp(String coolantTemp) {
        this.coolantTemp = coolantTemp;
    }

    public String getOilLevel() {
        return oilLevel;
    }

    public void setOilLevel(String oilLevel) {
        this.oilLevel = oilLevel;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isPosStatus() {
        return posStatus;
    }

    public void setPosStatus(boolean posStatus) {
        this.posStatus = posStatus;
    }

    public boolean isNaLatitude() {
        return naLatitude;
    }

    public void setNaLatitude(boolean naLatitude) {
        this.naLatitude = naLatitude;
    }

    public boolean isEwLongitude() {
        return ewLongitude;
    }

    public void setEwLongitude(boolean ewLongitude) {
        this.ewLongitude = ewLongitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getTotalMile() {
        return totalMile;
    }

    public void setTotalMile(String totalMile) {
        this.totalMile = totalMile;
    }

    public String getCarVin() {
        return carVin;
    }

    public void setCarVin(String carVin) {
        this.carVin = carVin;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
}