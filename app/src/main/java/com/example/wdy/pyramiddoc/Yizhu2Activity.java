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

public class Yizhu2Activity extends AppCompatActivity {
    private static final String ACTIVITY_TAG = "Demo";
    private TextView bingqing,yizhu,ypyl,ypmc,zonghexinxi;
    private static final int UPDATE_TEXT=1;
    public static String oldersID;
    public static Info info=new Info();
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    bingqing.setText(info.getBingqing());
                    yizhu.setText(info.getYizhu());
                    ypyl.setText(info.getYpyl());
                    ypmc.setText(info.getYpmc());
                    zonghexinxi.setText(Xinxi2Activity.zonghexinxit2);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yizhu2);
        bingqing=findViewById(R.id.bingqing);
        yizhu=findViewById(R.id.yizhu);
        ypmc=findViewById(R.id.ypmc);
        ypyl=findViewById(R.id.ypyl);
        zonghexinxi=findViewById(R.id.zonghexinxi);

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                if(MainActivity.conn!=null){
                    String sql="SELECT * FROM import_info where ID ='" + oldersID+"'";
                    try{
                        java.sql.Statement statement=MainActivity.conn.createStatement();
                        ResultSet rSet=statement.executeQuery(sql);
                        while(rSet.next()){
                            info.setBingqing(rSet.getString("Past_conditon"));
                            info.setYizhu(rSet.getString("Past_advice"));
                            info.setYpmc(rSet.getString("Past_prescription"));
                            info.setYpyl(rSet.getString("Past_dosage"));
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

    public void back_click(View view) {
        Intent intent=new Intent(Yizhu2Activity.this,Xinxi2Activity.class);
        startActivity(intent);
    }


    public void xiugai_click(View view) {
        YizhuActivity.zonghexinxit3=Xinxi2Activity.zonghexinxit2;
        YizhuActivity.backflag=0;
        Intent intent=new Intent(Yizhu2Activity.this,YizhuActivity.class);
        startActivity(intent);
    }
}

