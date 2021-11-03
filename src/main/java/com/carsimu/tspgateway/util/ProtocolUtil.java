package com.carsimu.tspgateway.util;

import com.carsimu.tspcommon.pojo.EngineData;
import com.carsimu.tspcommon.pojo.Message17691;
import com.carsimu.tspcommon.pojo.OBDData;
import com.carsimu.tspcommon.pojo.OtherData;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;

public class ProtocolUtil {

    public static Message17691 parse17691(String message) {
        Message17691 result = new Message17691();
        if (message.length() < 48) {
            result.getError().add("length error!");
            return result;
        }
        String checkCode = message.substring(message.length() - 2);
        if (!getCheckCode(message.substring(0, message.length() - 2)).equals(checkCode))
            result.getError().add("checkCode error !");
        result.setOrderUnit(message.substring(4, 6));
        result.setVinCode(message.substring(6, 40));
        result.setSoftVer(message.substring(40, 42));
        if (message.startsWith("FE", 42)) {
            result.getError().add("data encryption exception!");
        } else if (message.startsWith("FF", 42)) {
            result.getError().add("data encryption invalid!");
        }
        result.setEncryption(message.substring(42, 44));
        result.setDataLength(message.substring(44, 48));
        result.setCheckCode(checkCode);

        String dataUnit = message.substring(48, message.length() - 2);
        if (message.startsWith("01", 4)) {
            vehicleParse(dataUnit, result);
            result.setDataUnit(dataUnit);
        } else if (message.startsWith("02", 4)) {
            parseRealTimeInfo(dataUnit, result);
            result.setDataUnit(dataUnit);
        } else if (message.startsWith("03", 4)) {
            reParseRealTimeInfo(dataUnit, result);
            result.setDataUnit(dataUnit);
        } else if (message.startsWith("04", 4)) {
            parseLogout(dataUnit, result);
            result.setDataUnit(dataUnit);
        } else {
            timingOrReserve();
        }
        return result;
    }

    private static void vehicleParse(String message, Message17691 result) {
        setTime(message.substring(0, 12), result);
        result.setLoginSN(message.substring(12, 20));
        result.setSim(message.substring(20));
    }

    private static void parseRealTimeInfo(String message, Message17691 result) {
        setTime(message.substring(0, 12), result);
        message = message.substring(12);
        String informationTypeFlag = "";
        int next = 0;
        while (!message.equals("")) {
            informationTypeFlag = message.substring(0, 2);
            result.getInfoType().add(informationTypeFlag);
            result.getInfoSN().add(message.substring(2, 6));
            if (informationTypeFlag.equals("01")) {
                next = getOBDInfo(message.substring(6), result);
                result.getInfoBody().add(message.substring(6, next + 6));
                message = message.substring(next + 6);
            } else if (informationTypeFlag.equals("02")) {
                parseEngineData(message.substring(6), result);
                next = 74;
                result.getInfoBody().add(message.substring(6, next + 6));
                message = message.substring(next + 6);
            } else {
                result.getInfoBody().add(message.substring(6));
                result.getInfoBodyList().add(new OtherData(message.substring(6)));
                break;
            }
        }
    }

    private static void reParseRealTimeInfo(String message, Message17691 result) {
        parseRealTimeInfo(message, result);
    }

    private static void parseLogout(String message, Message17691 result) {
        result.setLogoutTime(message.substring(0, 12));
        result.setLogoutSN(message.substring(12));
    }

    private static void timingOrReserve() {
    }

    private static int getOBDInfo(String message, Message17691 result) {
        OBDData obdData = new OBDData();

        if (message.startsWith("FE", 0)) result.getError().add("OBDDiagnosticProtocol invalid!");
        obdData.setObdProtocol(message.substring(0, 2));
        if (message.startsWith("FE", 2)) result.getError().add("MILStatus invalid!");
        obdData.setMilStatus(message.substring(2, 4));

        char c = message.charAt(4);
        int i = charToInt(c);
        if (i == -1) {
            result.getError().add("parse error!");
            return message.length();
        }
        obdData.setsSecAirSys(i % 2 == 1);
        i = i / 2;
        obdData.setsEvaSys(i % 2 == 1);
        i = i / 2;
        obdData.setsHeatedCAT(i % 2 == 1);
        i = i / 2;
        obdData.setsCAT(i % 2 == 1);

        c = message.charAt(5);
        i = charToInt(c);
        if (i == -1) {
            result.getError().add("parse error!");
            return message.length();
        }
        obdData.setsEGRnVVT(i % 2 == 1);
        i = i / 2;
        obdData.setsEGSH(i % 2 == 1);
        i = i / 2;
        obdData.setsEGS(i % 2 == 1);
        i = i / 2;
        obdData.setsACSys(i % 2 == 1);

        c = message.charAt(6);
        i = charToInt(c);
        if (i == -1) {
            result.getError().add("parse error!");
            return message.length();
        }
        obdData.setsNOx(i % 2 == 1);
        i = i / 2;
        obdData.setsDPF(i % 2 == 1);
        i = i / 2;
        obdData.setsBoostPCS(i % 2 == 1);
        i = i / 2;
        obdData.setsColdStart(i % 2 == 1);

        c = message.charAt(7);
        i = charToInt(c);
        if (i == -1) {
            result.getError().add("parse error!");
            return message.length();
        }
        obdData.setsCC(i % 2 == 1);
        i = i / 2;
        obdData.setsFuelSys(i % 2 == 1);
        i = i / 2;
        obdData.setsMisfire(i % 2 == 1);
        i = i / 2;
        obdData.setsNMHC(i % 2 == 1);

        c = message.charAt(8);
        i = charToInt(c);
        if (i == -1) {
            result.getError().add("parse error!");
            return message.length();
        }
        obdData.setrSecAirSys(i % 2 == 1);
        i = i / 2;
        obdData.setrEvaSys(i % 2 == 1);
        i = i / 2;
        obdData.setrHeatedCAT(i % 2 == 1);
        i = i / 2;
        obdData.setrCAT(i % 2 == 1);

        c = message.charAt(9);
        i = charToInt(c);
        if (i == -1) {
            result.getError().add("parse error!");
            return message.length();
        }
        obdData.setrEGRnVVT(i % 2 == 1);
        i = i / 2;
        obdData.setrEGSH(i % 2 == 1);
        i = i / 2;
        obdData.setrEGS(i % 2 == 1);
        i = i / 2;
        obdData.setrACSys(i % 2 == 1);

        c = message.charAt(10);
        i = charToInt(c);
        if (i == -1) {
            result.getError().add("parse error!");
            return message.length();
        }
        obdData.setrNOx(i % 2 == 1);
        i = i / 2;
        obdData.setrDPF(i % 2 == 1);
        i = i / 2;
        obdData.setrBoostPCS(i % 2 == 1);
        i = i / 2;
        obdData.setrColdStart(i % 2 == 1);

        c = message.charAt(11);
        i = charToInt(c);
        if (i == -1) {
            result.getError().add("parse error!");
            return message.length();
        }
        obdData.setrCC(i % 2 == 1);
        i = i / 2;
        obdData.setrFuelSys(i % 2 == 1);
        i = i / 2;
        obdData.setrMisfire(i % 2 == 1);
        i = i / 2;
        obdData.setrNMHC(i % 2 == 1);

        obdData.setVinCode(message.substring(12, 46));
        obdData.setSoftID(message.substring(46, 82));
        obdData.setCVN(message.substring(82, 118));
        obdData.setIUPR(message.substring(118, 190));

        i = hexToInt(message.substring(190, 192));
        obdData.setTotalFault(message.substring(190, 192));
        if (i == 254 || i == 255) result.getError().add("TotalNumberOfFaultCodes invalid!");
        for (int j = 0; j < i; j++) obdData.getFaultCodesList().add(message.substring(192 + j * 8, 192 + j * 8 + 8));

        result.getInfoBodyList().add(obdData);
        return 192 + i * 8;
    }

    private static void parseEngineData(String Msg, Message17691 Message17691) {
        EngineData engineDataStream = new EngineData();

        if (Msg.startsWith("FFFF", 0)) Message17691.getError().add("vehicleSpeed invalid!");
        engineDataStream.setCarSpeed(Msg.substring(0, 4));
        if (Msg.startsWith("FF", 4)) Message17691.getError().add("atmosphericPressure invalid!");
        engineDataStream.setAtmPre(Msg.substring(4, 6));
        if (Msg.startsWith("FF", 6)) Message17691.getError().add("netTorque invalid!");
        engineDataStream.setNetTor(Msg.substring(6, 8));
        if (Msg.startsWith("FF", 8)) Message17691.getError().add("frictionTorque invalid!");
        engineDataStream.setFriTor(Msg.substring(8, 10));
        if (Msg.startsWith("FFFF", 10)) Message17691.getError().add("engineSpeed invalid!");
        engineDataStream.setEngineSpeed(Msg.substring(10, 14));
        if (Msg.startsWith("FFFF", 14)) Message17691.getError().add("engineFuelFlow invalid!");
        engineDataStream.setEngineFuelFlow(Msg.substring(14, 18));
        if (Msg.startsWith("FFFF", 18)) Message17691.getError().add("SCRUpstreamNOxSensorOutput invalid!");
        engineDataStream.setUpNOx(Msg.substring(18, 22));
        if (Msg.startsWith("FFFF", 22)) Message17691.getError().add("SCRDownstreamNOxSensorOutput invalid!");
        engineDataStream.setDownNOx(Msg.substring(22, 26));
        if (Msg.startsWith("FF", 26)) Message17691.getError().add("reagentResidue invalid!");
        engineDataStream.setReagent(Msg.substring(26, 28));
        if (Msg.startsWith("FFFF", 28)) Message17691.getError().add("airIntake invalid!");
        engineDataStream.setAirIn(Msg.substring(28, 32));
        if (Msg.startsWith("FFFF", 32)) Message17691.getError().add("SCRInletTemperature invalid!");
        engineDataStream.setInTemp(Msg.substring(32, 36));
        if (Msg.startsWith("FFFF", 36)) Message17691.getError().add("SCROutletTemperature invalid!");
        engineDataStream.setOutTemp(Msg.substring(36, 40));
        if (Msg.startsWith("FFFF", 40)) Message17691.getError().add("DPFPressureDifference invalid!");
        engineDataStream.setDpf(Msg.substring(40, 44));
        if (Msg.startsWith("FF", 44)) Message17691.getError().add("engineCoolantTemperature invalid!");
        engineDataStream.setCoolantTemp(Msg.substring(44, 46));
        if (Msg.startsWith("FF", 46)) Message17691.getError().add("tankLevel invalid!");
        engineDataStream.setOilLevel(Msg.substring(46, 48));
        engineDataStream.setPosition(Msg.substring(48, 50));
        int p = charToInt(Msg.charAt(48));
        p = p / 2;
        engineDataStream.setEwLongitude(p % 2 == 1);
        p = p / 2;
        engineDataStream.setNaLatitude(p % 2 == 1);
        p = p / 2;
        engineDataStream.setPosStatus(p % 2 == 1);
        if (Msg.startsWith("FFFFFFFF", 50)) Message17691.getError().add("longitude invalid!");
        engineDataStream.setLongitude(Msg.substring(50, 58));
        if (Msg.startsWith("FFFFFFFF", 58)) Message17691.getError().add("latitude invalid!");
        engineDataStream.setLatitude(Msg.substring(58, 66));
        if (Msg.startsWith("FFFFFFFF", 66)) Message17691.getError().add("accumulatedMileage invalid!");
        engineDataStream.setTotalMile(Msg.substring(66));

        Message17691.getInfoBodyList().add(engineDataStream);
    }

    private static void setTime(String message, Message17691 result) {
        if (message.length() != 12) {
            result.setDataTime(null);
        } else {
            Calendar calendar = Calendar.getInstance();
            int year = charToInt(message.charAt(1)) + charToInt(message.charAt(0)) * 16;
            int month = charToInt(message.charAt(3)) + charToInt(message.charAt(2)) * 16;
            int date = charToInt(message.charAt(5)) + charToInt(message.charAt(4)) * 16;
            int hour = charToInt(message.charAt(7)) + charToInt(message.charAt(6)) * 16;
            int minute = charToInt(message.charAt(9)) + charToInt(message.charAt(8)) * 16;
            int second = charToInt(message.charAt(11)) + charToInt(message.charAt(10)) * 16;
            calendar.set(year, month - 1, date, hour, minute, second);
            result.setDataTime(calendar.getTime());
        }
    }

    private static String getTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR) % 100;
        //year = year % 100;
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        StringBuilder time = new StringBuilder();
        StringBuilder ye = new StringBuilder(intToHex(year));
        while (ye.length() < 2) {
            ye.insert(0, '0');
        }
        ;
        StringBuilder mo = new StringBuilder(intToHex(month));
        while (mo.length() < 2) {
            mo.insert(0, '0');
        }
        ;
        StringBuilder da = new StringBuilder(intToHex(day));
        while (da.length() < 2) {
            da.insert(0, '0');
        }
        ;
        StringBuilder ho = new StringBuilder(intToHex(hour));
        while (ho.length() < 2) {
            ho.insert(0, '0');
        }
        ;
        StringBuilder mi = new StringBuilder(intToHex(minute));
        while (mi.length() < 2) {
            mi.insert(0, '0');
        }
        ;
        StringBuilder se = new StringBuilder(intToHex(second));
        while (se.length() < 2) {
            se.insert(0, '0');
        }
        ;
        time.append(ye).append(mo).append(da).append(ho).append(mi).append(se);
        return time.toString();
    }

    public static int charToInt(char c) {
        int i;
        if (c >= '0' && c <= '9') i = c - 48;
        else if (c >= 'A' && c <= 'F') i = c - 55;
        else if (c >= 'a' && c <= 'f') i = c - 87;
        else i = -1;
        return i;
    }

    public static String hexToBin(String hex) {
        StringBuilder bin = new StringBuilder();
        int i[] = new int[hex.length()];
        for (int j = 0; j < hex.length(); j++) {
            i[j] = charToInt(hex.charAt(j));
        }
        for (int j = 0; j < hex.length(); j++) {
            StringBuilder temp = new StringBuilder(intToBin(i[j]));
            while (temp.length() < 4) temp.insert(0, '0');
            bin.append(temp);
        }
        return bin.toString();
    }

    public static String intToBin(int i) {
        StringBuilder bin = new StringBuilder();
        while (i != 0) {
            bin.insert(0, i % 2);
            i = i / 2;
        }
        return bin.toString();
    }

    public static int binToInt(String bin) {
        int i = 0;
        int last = i;
        int j = 1;
        for (int k = bin.length() - 1; k >= 0; k--) {
            i = i + j * charToInt(bin.charAt(k));
            if (last > i) return -1;
            last = i;
            j = j * 2;
        }
        return i;
    }

    public static int hexToInt(String hex) {
        String bin = hexToBin(hex);
        return binToInt(bin);
    }

    public static String intToHex(int i) {
        if (i == 0) return "0";
        StringBuilder hex = new StringBuilder();
        int j;
        while (i != 0) {
            j = i % 16;
            if (j <= 9) hex.insert(0, (char) (j + '0'));
            else hex.insert(0, (char) (j - 10 + 'A'));
            i = i / 16;
        }
        return hex.toString();
    }

    public static String binToHex(String bin) {
        int i = binToInt(bin);
        return intToHex(i);
    }

    public static String build17691(Message17691 message17691,int obd_error) {        // 创建17691数据
        StringBuilder msg = new StringBuilder("2323");

        if (message17691.getOrderUnit() == null) return "error orderUnit !";
        if (message17691.getVinCode() == null) return "error vinCode !";
        if (message17691.getSoftVer() == null) return "error softVer !";
        if (message17691.getEncryption() == null) return "error encryption !";
        if (message17691.getDataLength() == null) return "error dataLength !";
        msg.append(message17691.getOrderUnit()).append(message17691.getVinCode()).append(message17691.getSoftVer());
        msg.append(message17691.getEncryption()).append(message17691.getDataLength());

        if (message17691.getOrderUnit().startsWith("01")) {
            if (message17691.getDataTime() == null) return "error dataTime !";
            if (message17691.getLoginSN() == null) return "error loginSN !";
            if (message17691.getSim() == null) return "error sim !";
            msg.append(getTime(message17691.getDataTime())).append(message17691.getLoginSN()).append(message17691.getSim());
        } else if (message17691.getOrderUnit().startsWith("02") || message17691.getOrderUnit().startsWith("03")) {
            if (message17691.getDataTime() == null) return "error dataTime !";
            msg.append(getTime(message17691.getDataTime()));
            if (message17691.getInfoType().size() != message17691.getInfoSN().size() || message17691.getInfoType().size() != message17691.getInfoBodyList().size())
                return "error infoBody !";
            for (int i = 0; i < message17691.getInfoType().size(); i++) {
                if (message17691.getInfoType().get(i).startsWith("01")) {
                    msg.append("01").append(message17691.getInfoSN().get(i));
                    String temp = buildOBDInfo((OBDData) message17691.getInfoBodyList().get(i),obd_error);
                    if (temp.startsWith("error")) return "error obdData !";
                    else msg.append(temp);
                } else if (message17691.getInfoType().get(i).startsWith("02")) {
                    msg.append("02").append(message17691.getInfoSN().get(i));
                    String temp = buildEngineData((EngineData) message17691.getInfoBodyList().get(i));
                    if (temp.startsWith("error")) return "error engineData !";
                    else msg.append(temp);
                } else if (message17691.getInfoType().get(i).startsWith("03")) {
                    msg.append("03").append(message17691.getInfoSN().get(i));
                    msg.append(((OtherData) message17691.getInfoBodyList().get(i)).getOther());
                }
            }


        } else if (message17691.getOrderUnit().startsWith("04")) {
            if (message17691.getLogoutTime() == null) return "error logoutTime !";
            if (message17691.getLogoutSN() == null) return "error logoutSN !";
            msg.append(message17691.getLogoutTime()).append(message17691.getLogoutSN());
        }


        // 验证数据长度
//        System.out.println(hexToInt(message17691.getDataLength()) * 2);
//        System.out.println(msg.toString().length() - 48);
        if (hexToInt(message17691.getDataLength()) * 2 != msg.toString().length() - 48)
            return "error dataLength check !";
        // 添加校验码
        msg.append(getCheckCode(msg.toString()));
        // 返回前先尝试解析
        Message17691 m = parse17691(msg.toString());
        if (m.getError().isEmpty()) return msg.toString();
        else return m.getError().get(0);
    }

    private static String buildOBDInfo(OBDData obdData,int obd_error) {
        StringBuilder obd = new StringBuilder();

        if (obdData.getObdProtocol() == null) return "error obdProtocol !";
        if (obdData.getMilStatus() == null) return "error milStatus !";
        if (obdData.getVinCode() == null) return "error vinCode !";
        if (obdData.getSoftID() == null) return "error softID !";
        if (obdData.getCVN() == null) return "error CVN !";
        if (obdData.getIUPR() == null) return "error IUPR !";

        obd.append(obdData.getObdProtocol()).append(obdData.getMilStatus());
        StringBuilder temp = new StringBuilder();
        if (obdData.issCAT()) temp.append('1');
        else temp.append('0');
        if (obdData.issHeatedCAT()) temp.append('1');
        else temp.append('0');
        if (obdData.issEvaSys()) temp.append('1');
        else temp.append('0');
        if (obdData.issSecAirSys()) temp.append('1');
        else temp.append('0');
        obd.append(binToHex(temp.toString()));
        temp.delete(0, temp.length());
        if (obdData.issACSys()) temp.append('1');
        else temp.append('0');
        if (obdData.issEGS()) temp.append('1');
        else temp.append('0');
        if (obdData.issEGSH()) temp.append('1');
        else temp.append('0');
        if (obdData.issEGRnVVT()) temp.append('1');
        else temp.append('0');
        obd.append(binToHex(temp.toString()));
        temp.delete(0, temp.length());
        if (obdData.issColdStart()) temp.append('1');
        else temp.append('0');
        if (obdData.issBoostPCS()) temp.append('1');
        else temp.append('0');
        if (obdData.issDPF()) temp.append('1');
        else temp.append('0');
        if (obdData.issNOx()) temp.append('1');
        else temp.append('0');
        obd.append(binToHex(temp.toString()));
        temp.delete(0, temp.length());
        if (obdData.issNMHC()) temp.append('1');
        else temp.append('0');
        if (obdData.issMisfire()) temp.append('1');
        else temp.append('0');
        if (obdData.issFuelSys()) temp.append('1');
        else temp.append('0');
        if (obdData.issCC()) temp.append('1');
        else temp.append('0');
        obd.append(binToHex(temp.toString()));

        temp.delete(0, temp.length());
        if (obdData.isrCAT()) temp.append('1');
        else temp.append('0');
        if (obdData.isrHeatedCAT()) temp.append('1');
        else temp.append('0');
        if (obdData.isrEvaSys()) temp.append('1');
        else temp.append('0');
        if (obdData.isrSecAirSys()) temp.append('1');
        else temp.append('0');
        obd.append(binToHex(temp.toString()));
        temp.delete(0, temp.length());
        if (obdData.isrACSys()) temp.append('1');
        else temp.append('0');
        if (obdData.isrEGS()) temp.append('1');
        else temp.append('0');
        if (obdData.isrEGSH()) temp.append('1');
        else temp.append('0');
        if (obdData.isrEGRnVVT()) temp.append('1');
        else temp.append('0');
        obd.append(binToHex(temp.toString()));
        temp.delete(0, temp.length());
        if (obdData.isrColdStart()) temp.append('1');
        else temp.append('0');
        if (obdData.isrBoostPCS()) temp.append('1');
        else temp.append('0');
        if (obdData.isrDPF()) temp.append('1');
        else temp.append('0');
        if (obdData.isrNOx()) temp.append('1');
        else temp.append('0');
        obd.append(binToHex(temp.toString()));
        temp.delete(0, temp.length());
        if (obdData.isrNMHC()) temp.append('1');
        else temp.append('0');
        if (obdData.isrMisfire()) temp.append('1');
        else temp.append('0');
        if (obdData.isrFuelSys()) temp.append('1');
        else temp.append('0');
        if (obdData.isrCC()) temp.append('1');
        else temp.append('0');
        obd.append(binToHex(temp.toString()));

        obd.append(obdData.getVinCode()).append(obdData.getSoftID()).append(obdData.getCVN()).append(obdData.getIUPR());
        int totalFault = hexToInt(obdData.getTotalFault());
        if (totalFault != obdData.getFaultCodesList().size()) return "error faultCode !";
        obd.append(obdData.getTotalFault());
        for (int i = 0; i < totalFault; i++) {
            obd.append(obdData.getFaultCodesList().get(i));
        }

        return obd.toString();
    }

    private static String buildEngineData(EngineData engineData) {
        StringBuilder data = new StringBuilder();

        if (engineData.getCarSpeed() == null) return "error carSpeed !";
        if (engineData.getAtmPre() == null) return "error atmPre !";
        if (engineData.getNetTor() == null) return "error netTor !";
        if (engineData.getFriTor() == null) return "error friTor !";
        if (engineData.getEngineSpeed() == null) return "error engineSpeed !";
        if (engineData.getEngineFuelFlow() == null) return "error engineFuelFlow !";
        if (engineData.getUpNOx() == null) return "error upNOx !";
        if (engineData.getDownNOx() == null) return "error downNOx !";
        if (engineData.getReagent() == null) return "error reagent !";
        if (engineData.getAirIn() == null) return "error ainIn !";
        if (engineData.getInTemp() == null) return "error inTemp !";
        if (engineData.getOutTemp() == null) return "error outTemp !";
        if (engineData.getDpf() == null) return "error dpf !";
        if (engineData.getCoolantTemp() == null) return "error coolantTemp !";
        if (engineData.getOilLevel() == null) return "error oilLevel !";
        if (engineData.getPosition() == null) return "error position !";
        if (engineData.getLongitude() == null) return "error longitude !";
        if (engineData.getLatitude() == null) return "error latitude !";
        if (engineData.getTotalMile() == null) return "error totalMile !";

        data.append(engineData.getCarSpeed()).append(engineData.getAtmPre()).append(engineData.getNetTor());
        data.append(engineData.getFriTor()).append(engineData.getEngineSpeed()).append(engineData.getEngineFuelFlow());
        data.append(engineData.getUpNOx()).append(engineData.getDownNOx()).append(engineData.getReagent());
        data.append(engineData.getAirIn()).append(engineData.getInTemp()).append(engineData.getOutTemp());
        data.append(engineData.getDpf()).append(engineData.getCoolantTemp()).append(engineData.getOilLevel());
        data.append(engineData.getPosition()).append(engineData.getLongitude()).append(engineData.getLatitude());
        data.append(engineData.getTotalMile());

        return data.toString();
    }

    private static String getCheckCode(String message) {
        String checkCode = "00";
        for (int i = 0; i < message.length(); i = i + 2) {
            checkCode = bccXor(checkCode, message.substring(i, i + 2));
        }
        return checkCode;
    }

    private static String bccXor(@NotNull String x, @NotNull String r) {
        StringBuilder result = new StringBuilder();
        StringBuilder bx = new StringBuilder(hexToBin(x));
        StringBuilder br = new StringBuilder(hexToBin(r));
        while (bx.length() < 8) bx.insert(0, '0');
        while (br.length() < 8) br.insert(0, '0');
        for (int i = 0; i < 8; i++) {
            if (bx.charAt(i) == br.charAt(i)) result.append('0');
            else result.append('1');
        }
        result = new StringBuilder(binToHex(result.toString()));
        while (result.length() < 2) result.insert(0, '0');
        return result.toString();
    }


}
