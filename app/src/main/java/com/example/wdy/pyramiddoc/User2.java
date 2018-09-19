package com.example.wdy.pyramiddoc;

public class User2 {
    private String Bed_num;
    private String Name;
    private String Sex;
    private String ID;
    private int Flag_doc;

    public User2(String Bed_num,String Name,String Sex,String ID,int Flag_doc){
        this.Bed_num=Bed_num;
        this.Name=Name;
        this.Sex=Sex;
        this.ID=ID;
        this.Flag_doc=Flag_doc;
    }

    public String getBed_num() {
        return Bed_num;
    }

    public void setBed_num(String Bed_num) {
        this.Bed_num = Bed_num;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public String getID(){return ID;}

    public void setID(String ID){this.ID = ID;}

    public int getFlag_doc() {
        return Flag_doc;
    }

    public void setFlag_doc(int flag_doc) {
        Flag_doc = flag_doc;
    }
}
