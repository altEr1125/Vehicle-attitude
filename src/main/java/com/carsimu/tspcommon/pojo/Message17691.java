package com.carsimu.tspcommon.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Message17691 {
    private List<String> error;
    private String orderUnit;                           //命令单元              02
    private String vinCode;                                 //车辆识别号         4C47444348413147384D41313237373333
    private String softVer;             //终端软件版本号                           01
    private String encryption;                //数据加密方式                      01
    private String dataLength;                      //数据单元长度                00AD
    private String dataUnit;                            //数据单元（String）      15080E082F190100010100ABDEA9D64C47444348413147384D4131323737333359435933303136352D363020202020200000EC40A1BF000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000007145600AFE00200AFE06200AFE00900AF204700AF245500AFE01200AF0200010000C57D830000000000000000610000FEFFFEFFFEFF3D0001FFFFFFFFFFFFFFFF0000002889
    private String checkCode;                           //校验码                   9F
    private Date dataTime;                   //数据采集时间                       Aug 14, 0021 8:47:25 AM(这个是Date类，原始是12位字符 15080E082F19 )
    private String loginSN;                   //登入流水号                       00013839
    private String sim;                           //SIM卡号                       383630343933313932313930343934333233
    private List<String> infoType;           //信息类型标志List                   01,02
    private List<String> infoSN;       //信息流水号List                              0001,0001
    private List<String> infoBody;               //信息体（String）List
    private List<InformationBody> infoBodyList;  //信息体（解析后）List（OBD信息：OBDInformation，数据流信息：EngineDataStream，其他：OtherInformation）
    private String logoutTime;                          //登出时间                  没遇到过，12位字符
    private String logoutSN;                  //登出流水号                           没遇到过，8位字符

    public Message17691() {
        error = new ArrayList<>();
        orderUnit = null;
        vinCode = null;
        softVer = null;
        encryption = null;
        dataLength = null;
        dataUnit = null;
        checkCode = null;
        dataTime = new Date();
        loginSN = null;
        sim = null;
        infoType = new ArrayList<>();
        infoSN = new ArrayList<>();
        infoBody = new ArrayList<>();
        infoBodyList = new ArrayList<>();
        logoutTime = null;
        logoutSN = null;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    public String getOrderUnit() {
        return orderUnit;
    }

    public void setOrderUnit(String orderUnit) {
        this.orderUnit = orderUnit;
    }

    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public String getSoftVer() {
        return softVer;
    }

    public void setSoftVer(String softVer) {
        this.softVer = softVer;
    }

    public String getEncryption() {
        return encryption;
    }

    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    public String getDataLength() {
        return dataLength;
    }

    public void setDataLength(String dataLength) {
        this.dataLength = dataLength;
    }

    public String getDataUnit() {
        return dataUnit;
    }

    public void setDataUnit(String dataUnit) {
        this.dataUnit = dataUnit;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public String getLoginSN() {
        return loginSN;
    }

    public void setLoginSN(String loginSN) {
        this.loginSN = loginSN;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public List<String> getInfoType() {
        return infoType;
    }

    public void setInfoType(List<String> infoType) {
        this.infoType = infoType;
    }

    public List<String> getInfoSN() {
        return infoSN;
    }

    public void setInfoSN(List<String> infoSN) {
        this.infoSN = infoSN;
    }

    public List<String> getInfoBody() {
        return infoBody;
    }

    public void setInfoBody(List<String> infoBody) {
        this.infoBody = infoBody;
    }

    public List<InformationBody> getInfoBodyList() {
        return infoBodyList;
    }

    public void setInfoBodyList(List<InformationBody> infoBodyList) {
        this.infoBodyList = infoBodyList;
    }

    public String getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(String logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getLogoutSN() {
        return logoutSN;
    }

    public void setLogoutSN(String logoutSN) {
        this.logoutSN = logoutSN;
    }
}
