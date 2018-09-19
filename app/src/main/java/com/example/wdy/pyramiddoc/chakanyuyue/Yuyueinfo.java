package com.example.wdy.pyramiddoc.chakanyuyue;
public class Yuyueinfo {
    private String OldersName;
    private String DoctorName;
    public Yuyueinfo(String DName,String OName){
        this.OldersName=OName;
        this.DoctorName=DName;


    }

    public String getDoctorName() {
        return DoctorName;
    }

    public String getOldersName() {
        return OldersName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public void setOldersName(String oldersName) {
        OldersName = oldersName;
    }
}
