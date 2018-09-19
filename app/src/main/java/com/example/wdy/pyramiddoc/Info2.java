package com.example.wdy.pyramiddoc;

public class Info2 {
    private String Temp;
    private String Blood_p1;
    private String Blood_p2;

    public Info2(String Blood_p1,String Blood_p2,String Temp){
        this.Blood_p1=Blood_p1;
        this.Blood_p2=Blood_p2;
        this.Temp=Temp;
    }

    public String getTemp(){
        return Temp;
    }

    public void setTemp(String Temp) {
        this.Temp = Temp;
    }

    public String getBlood_p1(){
        return Blood_p1;
    }

    public void setBlood_p1(String Blood_p1) {
        this.Blood_p1 = Blood_p1;
    }

    public String getBlood_p2() {
        return Blood_p2;
    }

    public void setBlood_p2(String Blood_p2) {
        this.Blood_p2 = Blood_p2;
    }
}
