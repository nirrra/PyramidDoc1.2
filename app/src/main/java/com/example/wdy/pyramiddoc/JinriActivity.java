package com.example.wdy.pyramiddoc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.TimeZone;

public class JinriActivity extends AppCompatActivity {
    private static final int msgKey1=1;
    private static final String ACTIVITY_TAG = "Demo";
    private TextView Name,Temp,Blood_p1,Blood_p2,Diet,Defecation,Slumber,OtherInfo,zonghexinxi;
    private static final int UPDATE_TEXT=1;
    private String zonghexinxit;
    public static String oldersID;
    public static String namet;
    public static int backflag;
    public static Info info=new Info();

    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    Name.setText(namet);
                    Temp.setText(info.getTemp());
                    Blood_p1.setText(info.getBlood_p1());
                    Blood_p2.setText(info.getBlood_p2());
                    Diet.setText(info.getDiet());
                    Defecation.setText(info.getDefecation());
                    Slumber.setText(info.getSlumber());
                    OtherInfo.setText(info.getOtherInfo());
                    zonghexinxi.setText(zonghexinxit);
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
        setContentView(R.layout.activity_jinri);
        Name=findViewById(R.id.name);
        Temp=findViewById(R.id.temp);
        Blood_p1=findViewById(R.id.blood_p1);
        Blood_p2=findViewById(R.id.blood_p2);
        Diet=findViewById(R.id.diet);
        Defecation=findViewById(R.id.defecation);
        Slumber=findViewById(R.id.slumber);
        OtherInfo=findViewById(R.id.otherinfo);
        zonghexinxi=findViewById(R.id.zonghexinxi);

        if (backflag==1)zonghexinxit=XinxiActivity.zonghexinxit;
        else zonghexinxit=Xinxi2Activity.zonghexinxit2;

        final Thread thread = new Thread(new Runnable() {
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
                    Message message=new Message();
                    message.what=UPDATE_TEXT;
                    handler.sendMessage(message);

                }

            }
        });
        thread.start();
    }

    public void gwxx_click(View view) {
        GuowangActivity.zonghexinxit=zonghexinxit;
        Intent intent=new Intent(JinriActivity.this,GuowangActivity.class);
        startActivity(intent);
    }

    public void back_click(View view) {
        Intent intent=new Intent(JinriActivity.this,XinxiActivity.class);
        Intent intent2=new Intent(JinriActivity.this,Xinxi2Activity.class);
        if (backflag==1) {
            startActivity(intent);
        }
        else startActivity(intent2);
    }
}

