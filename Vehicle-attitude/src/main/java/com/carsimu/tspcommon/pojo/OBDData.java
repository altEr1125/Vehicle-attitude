package com.carsimu.tspcommon.pojo;

import java.util.ArrayList;
import java.util.List;

public class OBDData implements InformationBody {
    private Long id;
    private String vinCode;                             //车辆识别码     4C47444348413147384D41313237373333
    private String obdProtocol;                     //OBD诊断协议        01
    private String milStatus;                       //MIL状态           00
    private boolean sCAT;                           //催化转化器监控支持             true
    private boolean sHeatedCAT;                     //加热催化转化器支持             false
    private boolean sEvaSys;                        //蒸发系统监控支持              true
    private boolean sSecAirSys;                     //二次空气系统监控支持            false
    private boolean sACSys;                         //A/C系统制冷剂监控支持          true
    private boolean sEGS;                           //排气传感器监控支持             false
    private boolean sEGSH;                          //排气传感器加热器监控支持          true
    private boolean sEGRnVVT;                       //EGR系统和VVT监控支持             true
    private boolean sColdStart;                     //冷启动辅助系统监控支持           true
    private boolean sBoostPCS;                      //增压压力控制系统支持            true
    private boolean sDPF;                           //DPF监控支持                   false
    private boolean sNOx;                           //SCR或NOx吸附器支持              true
    private boolean sNMHC;                          //NMHC氧化催化器监控支持             true
    private boolean sMisfire;                       //失火监控支持                    true
    private boolean sFuelSys;                       //燃油系统监控支持                  true
    private boolean sCC;                            //综合成分监控支持（0=不支持，1=支持）  false
    private boolean rCAT;                           //催化转化器监控就绪                 true
    private boolean rHeatedCAT;                     //加热催化转化器就绪                 false
    private boolean rEvaSys;                        //蒸发系统监控就绪                  true
    private boolean rSecAirSys;                     //二次空气系统监控就绪                false
    private boolean rACSys;                         //A/C系统制冷剂监控就绪              true
    private boolean rEGS;                           //排气传感器监控就绪                 false
    private boolean rEGSH;                          //排气传感器加热器监控就绪             false
    private boolean rEGRnVVT;                       //EGR系统和VVT监控就绪               true
    private boolean rColdStart;                     //冷启动辅助系统监控就绪               true
    private boolean rBoostPCS;                      //增压压力控制系统就绪                true
    private boolean rDPF;                           //DPF监控就绪                       false
    private boolean rNOx;                           //SCR或NOx吸附器就绪                  true
    private boolean rNMHC;                          //NMHC氧化催化器监控就绪                 false
    private boolean rMisfire;                       //失火监控就绪                        true
    private boolean rFuelSys;                       //燃油系统监控就绪                      true
    private boolean rCC;                            //综合成分监控就绪（0=测试完成或不支持，1=测试未完成）      false
    private String softID;                          //软件标定识别号           59435933303136352D363020202020200000
    private String CVN;                             //标定验证码               EC40A1BF0000000000000000000000000000
    private String IUPR;                            //IUPR值                 000000000000000000000000000000000000000000000000000000000000000000000000
    private String totalFault;                 //故障码总数                       07
    private List<String> faultCodesList;         //故障码（String）List        145600AF,E00200AF,E06200AF,E00900AF,204700AF,245500AF,E01200AF

    public OBDData() {
        faultCodesList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public String getObdProtocol() {
        return obdProtocol;
    }

    public void setObdProtocol(String obdProtocol) {
        this.obdProtocol = obdProtocol;
    }

    public String getMilStatus() {
        return milStatus;
    }

    public void setMilStatus(String milStatus) {
        this.milStatus = milStatus;
    }

    public boolean issCAT() {
        return sCAT;
    }

    public void setsCAT(boolean sCAT) {
        this.sCAT = sCAT;
    }

    public boolean issHeatedCAT() {
        return sHeatedCAT;
    }

    public void setsHeatedCAT(boolean sHeatedCAT) {
        this.sHeatedCAT = sHeatedCAT;
    }

    public boolean issEvaSys() {
        return sEvaSys;
    }

    public void setsEvaSys(boolean sEvaSys) {
        this.sEvaSys = sEvaSys;
    }

    public boolean issSecAirSys() {
        return sSecAirSys;
    }

    public void setsSecAirSys(boolean sSecAirSys) {
        this.sSecAirSys = sSecAirSys;
    }

    public boolean issACSys() {
        return sACSys;
    }

    public void setsACSys(boolean sACSys) {
        this.sACSys = sACSys;
    }

    public boolean issEGS() {
        return sEGS;
    }

    public void setsEGS(boolean sEGS) {
        this.sEGS = sEGS;
    }

    public boolean issEGSH() {
        return sEGSH;
    }

    public void setsEGSH(boolean sEGSH) {
        this.sEGSH = sEGSH;
    }

    public boolean issEGRnVVT() {
        return sEGRnVVT;
    }

    public void setsEGRnVVT(boolean sEGRnVVT) {
        this.sEGRnVVT = sEGRnVVT;
    }

    public boolean issColdStart() {
        return sColdStart;
    }

    public void setsColdStart(boolean sColdStart) {
        this.sColdStart = sColdStart;
    }

    public boolean issBoostPCS() {
        return sBoostPCS;
    }

    public void setsBoostPCS(boolean sBoostPCS) {
        this.sBoostPCS = sBoostPCS;
    }

    public boolean issDPF() {
        return sDPF;
    }

    public void setsDPF(boolean sDPF) {
        this.sDPF = sDPF;
    }

    public boolean issNOx() {
        return sNOx;
    }

    public void setsNOx(boolean sNOx) {
        this.sNOx = sNOx;
    }

    public boolean issNMHC() {
        return sNMHC;
    }

    public void setsNMHC(boolean sNMHC) {
        this.sNMHC = sNMHC;
    }

    public boolean issMisfire() {
        return sMisfire;
    }

    public void setsMisfire(boolean sMisfire) {
        this.sMisfire = sMisfire;
    }

    public boolean issFuelSys() {
        return sFuelSys;
    }

    public void setsFuelSys(boolean sFuelSys) {
        this.sFuelSys = sFuelSys;
    }

    public boolean issCC() {
        return sCC;
    }

    public void setsCC(boolean sCC) {
        this.sCC = sCC;
    }

    public boolean isrCAT() {
        return rCAT;
    }

    public void setrCAT(boolean rCAT) {
        this.rCAT = rCAT;
    }

    public boolean isrHeatedCAT() {
        return rHeatedCAT;
    }

    public void setrHeatedCAT(boolean rHeatedCAT) {
        this.rHeatedCAT = rHeatedCAT;
    }

    public boolean isrEvaSys() {
        return rEvaSys;
    }

    public void setrEvaSys(boolean rEvaSys) {
        this.rEvaSys = rEvaSys;
    }

    public boolean isrSecAirSys() {
        return rSecAirSys;
    }

    public void setrSecAirSys(boolean rSecAirSys) {
        this.rSecAirSys = rSecAirSys;
    }

    public boolean isrACSys() {
        return rACSys;
    }

    public void setrACSys(boolean rACSys) {
        this.rACSys = rACSys;
    }

    public boolean isrEGS() {
        return rEGS;
    }

    public void setrEGS(boolean rEGS) {
        this.rEGS = rEGS;
    }

    public boolean isrEGSH() {
        return rEGSH;
    }

    public void setrEGSH(boolean rEGSH) {
        this.rEGSH = rEGSH;
    }

    public boolean isrEGRnVVT() {
        return rEGRnVVT;
    }

    public void setrEGRnVVT(boolean rEGRnVVT) {
        this.rEGRnVVT = rEGRnVVT;
    }

    public boolean isrColdStart() {
        return rColdStart;
    }

    public void setrColdStart(boolean rColdStart) {
        this.rColdStart = rColdStart;
    }

    public boolean isrBoostPCS() {
        return rBoostPCS;
    }

    public void setrBoostPCS(boolean rBoostPCS) {
        this.rBoostPCS = rBoostPCS;
    }

    public boolean isrDPF() {
        return rDPF;
    }

    public void setrDPF(boolean rDPF) {
        this.rDPF = rDPF;
    }

    public boolean isrNOx() {
        return rNOx;
    }

    public void setrNOx(boolean rNOx) {
        this.rNOx = rNOx;
    }

    public boolean isrNMHC() {
        return rNMHC;
    }

    public void setrNMHC(boolean rNMHC) {
        this.rNMHC = rNMHC;
    }

    public boolean isrMisfire() {
        return rMisfire;
    }

    public void setrMisfire(boolean rMisfire) {
        this.rMisfire = rMisfire;
    }

    public boolean isrFuelSys() {
        return rFuelSys;
    }

    public void setrFuelSys(boolean rFuelSys) {
        this.rFuelSys = rFuelSys;
    }

    public boolean isrCC() {
        return rCC;
    }

    public void setrCC(boolean rCC) {
        this.rCC = rCC;
    }

    public String getSoftID() {
        return softID;
    }

    public void setSoftID(String softID) {
        this.softID = softID;
    }

    public String getCVN() {
        return CVN;
    }

    public void setCVN(String CVN) {
        this.CVN = CVN;
    }

    public String getIUPR() {
        return IUPR;
    }

    public void setIUPR(String IUPR) {
        this.IUPR = IUPR;
    }

    public String getTotalFault() {
        return totalFault;
    }

    public void setTotalFault(String totalFault) {
        this.totalFault = totalFault;
    }

    public List<String> getFaultCodesList() {
        return faultCodesList;
    }

    public void setFaultCodesList(List<String> faultCodesList) {
        this.faultCodesList = faultCodesList;
    }
}
