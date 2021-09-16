package com.carsimu.tspcommon.pojo;

public class OtherData implements InformationBody {
    private String other;           //没遇到过

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public OtherData(String other) {
        this.other = other;
    }
}
