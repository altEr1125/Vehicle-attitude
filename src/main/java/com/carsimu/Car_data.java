package com.carsimu;

import com.carsimu.tspcommon.pojo.EngineData;
import com.carsimu.tspcommon.pojo.Message17691;
import com.carsimu.tspgateway.util.ProtocolUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import static com.carsimu.tspgateway.util.ProtocolUtil.intToHex;

public class Car_data {

    String priKey;
    Sensor sensor;
    String vinCode;
    String softVer;
    String encryption;
    String sim;
    Calendar calendar;
    int logSN;
    int seed;

    public Car_data(int seed) {
        sensor = new Sensor(seed);
        this.seed = seed;
        Random random = new Random(seed);
        StringBuilder vinCodeBuilder = new StringBuilder("");
        for (int i = 0; i < 17; i++) {
            int a = random.nextInt(90) % (90 - 65 + 1) + 65;
            vinCodeBuilder.append(intToHex(a));
        }
        vinCode = vinCodeBuilder.toString();
        softVer = "01";
        encryption = "01";
        StringBuilder simBuilder = new StringBuilder("");
        for (int i = 0; i < 36; i++) {
            simBuilder.append(intToHex(random.nextInt(16)));
        }
        sim = simBuilder.toString();
        calendar = Calendar.getInstance();
        logSN = 0;
    }

    public String getVinCode() {
        return vinCode;
    }

    public void activate() {

    }

    public void start() {

    }

/*    public String sendMessage(int orderUnit) {
        Message17691 message17691 = new Message17691();
        StringBuilder temp = new StringBuilder(intToHex(orderUnit));
        while (temp.length() < 2) temp.insert(0, '0');
        message17691.setOrderUnit(temp.toString());
        basicInfo(message17691);
        if (orderUnit == 1) {         //登入
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String time1 = format.format(Calendar.getInstance().getTime());
            String time2 = format.format(calendar.getTime());
            if (time1.equals(time2)) logSN++;
            else {
                calendar = Calendar.getInstance();
                logSN = 1;
            }
            message17691.setDataTime(calendar.getTime());
            temp = new StringBuilder(intToHex(logSN));
            while (temp.length() < 8) temp.insert(0, '0');
            message17691.setLoginSN(temp.toString());
            message17691.setSim(sim);
            message17691.setDataLength("001C");
        } else if (orderUnit == 2 || orderUnit == 3) {      //实时信息上报
            message17691.setDataTime(calendar.getTime());
            //////////
            //暂时用现成数据
            Message17691 m = ProtocolUtil.parse17691("2323024C47444348413147384D41313237373333010100AD15080E082F230100010100ABDEA9D64C47444348413147384D4131323737333359435933303136352D363020202020200000EC40A1BF000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000007145600AFE00200AFE06200AFE00900AF204700AF245500AFE01200AF0200010000C57D830000000000000000610000FEFFFEFFFEFF3D000006AFD31701EA55FE000000289F");
            message17691.setInfoType(m.getInfoType());
            message17691.setInfoSN(m.getInfoSN());
            message17691.setInfoBodyList(m.getInfoBodyList());
            ((EngineData) message17691.getInfoBodyList().get(1)).setCarSpeed(sensor.carSpeed());
            ((EngineData) message17691.getInfoBodyList().get(1)).setLongitude(sensor.longitude());
            ((EngineData) message17691.getInfoBodyList().get(1)).setLatitude(sensor.latitude());
            //////////
            message17691.setDataLength("00AD");
        } else if (orderUnit == 4) {                //登出
            message17691.setLogoutTime(getTime());
            temp = new StringBuilder(intToHex(logSN));
            while (temp.length() < 8) temp.insert(0, '0');
            message17691.setLogoutSN(temp.toString());
            message17691.setDataLength("000A");
        } else {
            message17691.setDataLength("0000");
        }
        return ProtocolUtil.build17691(message17691,0);
    }*/

    public String sendMessage(int orderUnit,String vinCode) {
        Message17691 message17691 = new Message17691();
        StringBuilder temp = new StringBuilder(intToHex(orderUnit));
        while (temp.length() < 2) temp.insert(0, '0');
        message17691.setOrderUnit(temp.toString());
        basicInfo(message17691,vinCode);
        if (orderUnit == 1) {         //登入
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String time1 = format.format(Calendar.getInstance().getTime());
            String time2 = format.format(calendar.getTime());
            if (time1.equals(time2)) logSN++;
            else {
                calendar = Calendar.getInstance();
                logSN = 1;
            }
            message17691.setDataTime(calendar.getTime());
            temp = new StringBuilder(intToHex(logSN));
            while (temp.length() < 8) temp.insert(0, '0');
            message17691.setLoginSN(temp.toString());
            message17691.setSim(sim);
            message17691.setDataLength("001C");
        } else if (orderUnit == 2 || orderUnit == 3) {      //实时信息上报
            message17691.setDataTime(calendar.getTime());
            //暂时用现成数据
            Message17691 m = ProtocolUtil.parse17691("2323024C47444348413147384D41313237373333010100AD15080E082F230100010100ABDEA9D64C47444348413147384D4131323737333359435933303136352D363020202020200000EC40A1BF000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000007145600AFE00200AFE06200AFE00900AF204700AF245500AFE01200AF0200010000C57D830000000000000000610000FEFFFEFFFEFF3D000006AFD31701EA55FE000000289F");
            message17691.setInfoType(m.getInfoType());
            message17691.setInfoSN(m.getInfoSN());
            message17691.setInfoBodyList(m.getInfoBodyList());
            ((EngineData) message17691.getInfoBodyList().get(1)).setCarSpeed(sensor.carSpeed());
            ((EngineData) message17691.getInfoBodyList().get(1)).setLongitude(sensor.longitude());
            ((EngineData) message17691.getInfoBodyList().get(1)).setLatitude(sensor.latitude());
            message17691.setDataLength("00AD");
        } else if (orderUnit == 4) {                //登出
            message17691.setLogoutTime(getTime());
            temp = new StringBuilder(intToHex(logSN));
            while (temp.length() < 8) temp.insert(0, '0');
            message17691.setLogoutSN(temp.toString());
            message17691.setDataLength("000A");
        } else {
            message17691.setDataLength("0000");
        }
        return ProtocolUtil.build17691(message17691,0);
    }

    public String sendMessage_error(int orderUnit,String vinCode,int car_speed,int addr,int obd_error) {
        Message17691 message17691 = new Message17691();
        StringBuilder temp = new StringBuilder(intToHex(orderUnit));
        while (temp.length() < 2) temp.insert(0, '0');
        message17691.setOrderUnit(temp.toString());
        basicInfo(message17691,vinCode);
        if (orderUnit == 1) {         //登入
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String time1 = format.format(Calendar.getInstance().getTime());
            String time2 = format.format(calendar.getTime());
            if (time1.equals(time2)) logSN++;
            else {
                calendar = Calendar.getInstance();
                logSN = 1;
            }
            message17691.setDataTime(calendar.getTime());
            temp = new StringBuilder(intToHex(logSN));
            while (temp.length() < 8) temp.insert(0, '0');
            message17691.setLoginSN(temp.toString());
            message17691.setSim(sim);
            message17691.setDataLength("001C");
        } else if (orderUnit == 2 || orderUnit == 3) {      //实时信息上报
            message17691.setDataTime(calendar.getTime());
            //暂时用现成数据
            Message17691 m = ProtocolUtil.parse17691("2323024C47444348413147384D41313237373333010100AD15080E082F230100010100ABDEA9D64C47444348413147384D4131323737333359435933303136352D363020202020200000EC40A1BF000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000007145600AFE00200AFE06200AFE00900AF204700AF245500AFE01200AF0200010000C57D830000000000000000610000FEFFFEFFFEFF3D000006AFD31701EA55FE000000289F");
            message17691.setInfoType(m.getInfoType());
            message17691.setInfoSN(m.getInfoSN());
            message17691.setInfoBodyList(m.getInfoBodyList());
            if(car_speed==1)
                ((EngineData) message17691.getInfoBodyList().get(1)).setCarSpeed(sensor.carSpeed_error());
            else
                ((EngineData) message17691.getInfoBodyList().get(1)).setCarSpeed(sensor.carSpeed());
            if(addr==1) {
                ((EngineData) message17691.getInfoBodyList().get(1)).setLongitude(sensor.longitude());
                ((EngineData) message17691.getInfoBodyList().get(1)).setLatitude(sensor.latitude());
            }
            else {
                ((EngineData) message17691.getInfoBodyList().get(1)).setLongitude(sensor.longitude());
                ((EngineData) message17691.getInfoBodyList().get(1)).setLatitude(sensor.latitude());
            }
//            if(obd==1)
//
//            else
//
            message17691.setDataLength("00AD");
        } else if (orderUnit == 4) {                //登出
            message17691.setLogoutTime(getTime());
            temp = new StringBuilder(intToHex(logSN));
            while (temp.length() < 8) temp.insert(0, '0');
            message17691.setLogoutSN(temp.toString());
            message17691.setDataLength("000A");
        } else {
            message17691.setDataLength("0000");
        }
        return ProtocolUtil.build17691(message17691,obd_error);
    }

    public void stop() {

    }
    void basicInfo(Message17691 message17691) {
        message17691.setVinCode(vinCode);
        message17691.setSoftVer(softVer);
        message17691.setEncryption(encryption);
    }

    void basicInfo(Message17691 message17691,String vinCode1) {
        message17691.setVinCode(vinCode1);
        message17691.setSoftVer(softVer);
        message17691.setEncryption(encryption);
    }

    String getTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        year = year % 100;
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
}

