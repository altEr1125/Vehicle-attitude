package com.carsimu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.carsimu.tspgateway.util.ProtocolUtil.intToHex;

public class Sensor {

    Random random;
    int seed;
    int speedControl;           //0为正常，1为超速
    int longitudeControl;       //0为正常，1为向东越界，2为向西越界
    int latitudeControl;        //0为正常，1为向南越界，2为向北越界

    public Sensor(int seed) {
        this.seed = seed;
        random = new Random(seed);
        int i = random.nextInt(100);
        if (i > 90) speedControl = 1;
        else speedControl = 0;
        i = random.nextInt(100);
        if (i < 90) longitudeControl = 0;
        else if (i < 95) longitudeControl = 1;
        else longitudeControl = 2;
        i = random.nextInt(100);
        if (i < 90) latitudeControl = 0;
        else if (i < 95) latitudeControl = 1;
        else latitudeControl = 2;
    }

    public String obdProtocol() {
        return "01";
    }

    public String milStatus() {
        return "00";
    }

    public boolean sCAT() {
        return random.nextBoolean();
    }

    public boolean sHeatedCAT() {
        return random.nextBoolean();
    }

    public boolean sEvaSys() {
        return random.nextBoolean();
    }

    public boolean sSecAirSys() {
        return random.nextBoolean();
    }

    public boolean sACSys() {
        return random.nextBoolean();
    }

    public boolean sEGS() {
        return random.nextBoolean();
    }

    public boolean sEGSH() {
        return random.nextBoolean();
    }

    public boolean sEGRnVVT() {
        return random.nextBoolean();
    }

    public boolean sColdStart() {
        return random.nextBoolean();
    }

    public boolean sBoostPCS() {
        return random.nextBoolean();
    }

    public boolean sDPF() {
        return random.nextBoolean();
    }

    public boolean sNOx() {
        return random.nextBoolean();
    }

    public boolean sNMHC() {
        return random.nextBoolean();
    }

    public boolean sMisfire() {
        return random.nextBoolean();
    }

    public boolean sFuelSys() {
        return random.nextBoolean();
    }

    public boolean sCC() {
        return random.nextBoolean();
    }

    public boolean rCAT() {
        return random.nextBoolean();
    }

    public boolean rHeatedCAT() {
        return random.nextBoolean();
    }

    public boolean rEvaSys() {
        return random.nextBoolean();
    }

    public boolean rSecAirSys() {
        return random.nextBoolean();
    }

    public boolean rACSys() {
        return random.nextBoolean();
    }

    public boolean rEGS() {
        return random.nextBoolean();
    }

    public boolean rEGSH() {
        return random.nextBoolean();
    }

    public boolean rEGRnVVT() {
        return random.nextBoolean();
    }

    public boolean rColdStart() {
        return random.nextBoolean();
    }

    public boolean rBoostPCS() {
        return random.nextBoolean();
    }

    public boolean rDPF() {
        return random.nextBoolean();
    }

    public boolean rNOx() {
        return random.nextBoolean();
    }

    public boolean rNMHC() {
        return random.nextBoolean();
    }

    public boolean rMisfire() {
        return random.nextBoolean();
    }

    public boolean rFuelSys() {
        return random.nextBoolean();
    }

    public boolean rCC() {
        return random.nextBoolean();
    }

    public int totalFault() {
        return 7;
    }

    public List<String> faultCodeList() {
        List<String> list = new ArrayList<>();
        list.add("145600AF");
        list.add("E00200AF");
        list.add("E06200AF");
        list.add("E00900AF");
        list.add("204700AF");
        list.add("245500AF");
        list.add("E01200AF");
        return list;
    }

    public String carSpeed() {
        int speed;
        if (speedControl == 0) speed = 90 + random.nextInt(30);
        else speed = 120 + random.nextInt(30);
        StringBuilder carSpeed = new StringBuilder(intToHex(speed));
        while (carSpeed.length() < 4) carSpeed.insert(0, '0');
        return carSpeed.toString();
    }

    public String atmPre() {
        return "C5";
    }

    public String netTor() {
        return "7D";
    }

    public String friTor() {
        return "83";
    }

    public String engineSpeed() {
        int speed = 128 + random.nextInt(64);
        StringBuilder engineSpeed = new StringBuilder(intToHex(speed));
        while (engineSpeed.length() < 4) engineSpeed.insert(0, '0');
        return engineSpeed.toString();
    }

    public String engineFuelFlow() {
        int fuel = 128 + random.nextInt(64);
        StringBuilder engineFuelFlow = new StringBuilder(intToHex(fuel));
        while (engineFuelFlow.length() < 4) engineFuelFlow.insert(0, '0');
        return engineFuelFlow.toString();
    }

    public String upNOx() {
        int up = 128 + random.nextInt(64);
        StringBuilder upNOx = new StringBuilder(intToHex(up));
        while (upNOx.length() < 4) upNOx.insert(0, '0');
        return upNOx.toString();
    }

    public String downNOx() {
        int down = 128 + random.nextInt(64);
        StringBuilder downNOx = new StringBuilder(intToHex(down));
        while (downNOx.length() < 4) downNOx.insert(0, '0');
        return downNOx.toString();
    }

    public String reagent() {
        return "61";
    }

    public String airIn() {
        int air = 128 + random.nextInt(64);
        StringBuilder airIn = new StringBuilder(intToHex(air));
        while (airIn.length() < 4) airIn.insert(0, '0');
        return airIn.toString();
    }

    public String inTemp() {
        return "FEFF";
    }

    public String outTemp() {
        return "FEFF";
    }

    public String dpf() {
        return "FEFF";
    }

    public String coolantTemp() {
        return "3D";
    }

    public String oilLevel() {
        return "00";
    }

    public String position() {
        return "00";
    }

    public String longitude() {
        int l;
        if (longitudeControl == 0) l = 116281237 + random.nextInt(116497980 - 116281237);
        else if (longitudeControl == 1) l = 116281237 - random.nextInt(2000);
        else l = 116497980 + random.nextInt(2000);
        StringBuilder longitude = new StringBuilder(intToHex(l));
        while (longitude.length() < 8) longitude.insert(0, '0');
        return longitude.toString();
    }

    public String latitude() {
        int l;
        if (latitudeControl == 0) l = 39842732 + random.nextInt(39991041 - 39842732);
        else if (latitudeControl == 1) l = 39842732 - random.nextInt(2000);
        else l = 39991041 + random.nextInt(2000);
        StringBuilder latitude = new StringBuilder(intToHex(l));
        while (latitude.length() < 8) latitude.insert(0, '0');
        return latitude.toString();
    }

    public String totalMile() {
        return "00000028";
    }
}
