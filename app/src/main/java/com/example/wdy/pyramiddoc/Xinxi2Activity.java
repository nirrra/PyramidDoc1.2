package com.example.wdy.pyramiddoc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Xinxi2Activity extends AppCompatActivity {
    private static final String ACTIVITY_TAG = "Demo";
    private TextView Name,Sex,Bornyear,Bed_num,Doctor,Nurse,Tel1,Tel2,Risk_level,Risk_factor,zonghexinxi,abnormal;
    private String Blood_p1,Blood_p2,Temp,Diet,Defecation,Slumber;
    private String OtherInfo;
    private String abnormalt;
    private static final int UPDATE_TEXT=1;
    private static final int UPDATE_TEXT2=1;
    public static String oldersID;
    public static String zonghexinxit2;
    public static Info info=new Info();
    public static int backflag;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    Name.setText(info.getName());
                    Sex.setText(info.getSex());
                    Bed_num.setText(info.getBed_num());
                    Bornyear.setText(info.getBornyear());
                    Doctor.setText(info.getDoctor());
                    Nurse.setText(info.getNurse());
                    Tel1.setText(info.getTel1());
                    Tel2.setText(info.getTel2());
                    Risk_level.setText(info.getRisk_level());
                    Risk_factor.setText(info.getRisk_factor());

                    zonghexinxit2=info.getBed_num()+"床 "+info.getName()+" "+info.getSex()+"     ";
                    zonghexinxi.setText(zonghexinxit2);

                    JinriActivity.namet=info.getName();
                    break;
                default:
                    break;
            }
        }
    };

    private Handler handler2=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT2:
                    abnormalt="";

                    Blood_p1=info.getBlood_p1();
                    Blood_p2=info.getBlood_p2();
                    Temp=info.getTemp();
                    Diet=info.getDiet();
                    Defecation=info.getDefecation();
                    Slumber=info.getSlumber();
                    OtherInfo=info.getOtherInfo();

                    if (Blood_p1!=null&&Blood_p1!=""&&Float.parseFloat(Blood_p1)>90)abnormalt+="收缩压"+Blood_p1+"!\n";
                    if (Blood_p2!=null&&Blood_p2!=""&&Float.parseFloat(Blood_p2)>120)abnormalt+="舒张压"+Blood_p2+"!\n";
                    if (Temp!=null&&Temp!=""&&Float.parseFloat(Temp)>37.5)abnormalt+="体温"+Temp+"!\n";
                    if (OtherInfo!=null&&OtherInfo!="")abnormalt+="其他信息："+OtherInfo;

                    if (abnormalt!=null&&abnormalt!="")abnormal.setText(abnormalt);
                    else abnormal.setText("无异常状况");

                    info.setTemp("");
                    info.setBlood_p1("");
                    info.setBlood_p2("");
                    info.setDiet("");
                    info.setDefecation("");
                    info.setSlumber("");
                    info.setOtherInfo("");

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinxi2);
        Name=findViewById(R.id.xingming);
        Sex=findViewById(R.id.xingbie);
        Bornyear=findViewById(R.id.nianling);
        Bed_num=findViewById(R.id.chuanghao);
        Doctor=findViewById(R.id.zhuzhiyisheng);
        Nurse=findViewById(R.id.fuzehugong);
        Tel1=findViewById(R.id.lianxi1);
        Tel2=findViewById(R.id.lianxi2);
        Risk_level=findViewById(R.id.fengxiandengji);
        Risk_factor=findViewById(R.id.fengxianyinsu);
        zonghexinxi=findViewById(R.id.zonghexinxi);
        abnormal=findViewById(R.id.abnormal);

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                if(MainActivity.conn!=null){
                    String sql="SELECT * FROM basic_info where ID ='" + oldersID+"'";
                    try{
                        java.sql.Statement statement=MainActivity.conn.createStatement();
                        ResultSet rSet=statement.executeQuery(sql);
                        while(rSet.next()){
                            info.setID(rSet.getString("ID"));
                            info.setName(rSet.getString("Name"));
                            info.setSex(rSet.getString("Sex"));
                            info.setBornyear(rSet.getString("Born_year"));
                            info.setRisk_level(rSet.getString("Risk_rating"));
                            info.setRisk_factor(rSet.getString("Risk_factor"));
                            info.setDoctor(rSet.getString("Doctor"));
                            info.setNurse(rSet.getString("Carer"));
                            info.setTel1(rSet.getString("Tel1"));
                            info.setTel2(rSet.getString("Tel2"));
                            info.setBed_num(rSet.getString("Bed_num"));
                            rSet.close();
                        }
                    }catch (SQLException e){
                        Log.e(ACTIVITY_TAG,"createStatement error1");
                    }
                    Message message=new Message();
                    message.what=UPDATE_TEXT;
                    handler.sendMessage(message);

                }

            }
        });

        final Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {

                if(MainActivity.conn!=null){
                    String sql="SELECT * FROM import_info where ID ='" + oldersID+"'";
                    try{
                        java.sql.Statement statement=MainActivity.conn.createStatement();
                        ResultSet rSet=statement.executeQuery(sql);
                        while(rSet.next()){
                            info.setTemp(rSet.getString("Temp"));
                            info.setBlood_p1(rSet.getString("Blood_p1"));
                            info.setBlood_p2(rSet.getString("Blood_p2"));
                            info.setDiet(rSet.getString("Diet"));
                            info.setDefecation(rSet.getString("Defecation"));
                            info.setSlumber(rSet.getString("Slumber"));
                            info.setOtherInfo(rSet.getString("Other_info"));
                            rSet.close();
                        }
                    }catch (SQLException e){
                        Log.e(ACTIVITY_TAG,"createStatement error1");
                    }
                    Message message2=new Message();
                    message2.what=UPDATE_TEXT2;
                    handler2.sendMessage(message2);

                }

            }
        });
        thread.start();
        thread2.start();
    }

    public void xq_click(View view) {
        JinriActivity.backflag=0;
        Intent intent=new Intent(Xinxi2Activity.this,JinriActivity.class);
        startActivity(intent);
    }

    public void xgjbxx2_click(View view) {
        OldInfoXiugaiActivity.oldbackflag=2;
        OldInfoXiugaiActivity.oldersID=oldersID;
        Intent intent=new Intent(Xinxi2Activity.this,OldInfoXiugaiActivity.class);
        startActivity(intent);
    }

    public void back_click(View view) {
        Intent intent1=new Intent(Xinxi2Activity.this,MainActivity.class);
        Intent intent2=new Intent(Xinxi2Activity.this,AlloldActivity.class);
        if(backflag==0)startActivity(intent1);
        else startActivity(intent2);
    }

    public void chakanyizhu_click(View view) {
        Intent intent=new Intent(Xinxi2Activity.this,Yizhu2Activity.class);
        startActivity(intent);
    }

    public void woyizhixiao_click(View view) {
        Intent intent=new Intent(Xinxi2Activity.this,MainActivity.class);
        startActivity(intent);
    }
}

