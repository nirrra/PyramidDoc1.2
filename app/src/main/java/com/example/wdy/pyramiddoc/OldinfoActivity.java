package com.example.wdy.pyramiddoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import java.sql.DriverManager;
import java.sql.SQLException;

public class OldinfoActivity extends AppCompatActivity {
    private static final String ACTIVITY_TAG = "Demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oldinfo);
    }

    public void tjxx_click(View view) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                EditText Name = findViewById(R.id.newName);
                EditText ID = findViewById(R.id.newID);
                EditText Sex = findViewById(R.id.newSex);
                EditText Bed_num = findViewById(R.id.newBed_num);
                EditText Born_year = findViewById(R.id.newBorn_year);
                EditText Doctor = findViewById(R.id.newDoctor);
                EditText Carer = findViewById(R.id.newCarer);
                EditText Tel1 = findViewById(R.id.newTel1);
                EditText Tel2 = findViewById(R.id.newTel2);
                EditText Risk_level = findViewById(R.id.newRisk_level);
                EditText Risk_factor = findViewById(R.id.newRisk_factor);
                EditText Height = findViewById(R.id.newHeight);
                EditText Weight = findViewById(R.id.newWeight);

                String Namet = Name.getText().toString();
                String IDt = ID.getText().toString();
                String Sext = Sex.getText().toString();
                String Bed_numt = Bed_num.getText().toString();
                String Born_yeart = Born_year.getText().toString();
                String Doctort = Doctor.getText().toString();
                String Carert = Carer.getText().toString();
                String Tel1t = Tel1.getText().toString();
                String Tel2t = Tel2.getText().toString();
                String Risk_levelt = Risk_level.getText().toString();
                String Risk_factort = Risk_factor.getText().toString();
                String Heightt = Height.getText().toString();
                String Weightt = Weight.getText().toString();

                if (MainActivity.conn != null) {
                    String sql = "insert into basic_info (Name,ID,Born_year,Sex,Height,Weight,Tel1,Tel2,Risk_rating,Risk_factor,Doctor,Carer,Bed_num,Flag,Flag_doc) VALUES " +
                            "('" + Namet + "','" + IDt + "','" + Born_yeart + "','" + Sext + "','" + Heightt + "','" + Weightt + "','" + Tel1t + "','" + Tel2t + "','" + Risk_levelt + "','" + Risk_factort + "','" + Doctort + "','" + Carert + "','" + Bed_numt + "',0,0)";
                    try {
                        java.sql.Statement statement = MainActivity.conn.createStatement();
                        statement.executeUpdate(sql);

                    } catch (SQLException e) {
                        Log.e(ACTIVITY_TAG, "createStatement error" + e);
                    }
                } else {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Log.v(ACTIVITY_TAG, "驱动成功");
                    } catch (ClassNotFoundException e) {
                        Log.e(ACTIVITY_TAG, "驱动失败");
                        return;
                    }
                    String ip = "tangjibin.mysql.rds.aliyuncs.com";
                    int port = 3306;
                    String dbName = "ex1";
                    String url = "jdbc:mysql://" + ip + ":" + port + "/" + dbName;
                    String user = "tangjibin";
                    String password = "Jiaohua98";
                    try {
                        MainActivity.conn = DriverManager.getConnection(url, user, password);
                        run();
                        Log.e(ACTIVITY_TAG, "远程连接成功！");
                    } catch (SQLException e) {
                        Log.e(ACTIVITY_TAG, "远程连接失败!");
                    }
                }
            }


        });
        thread.start();
        Intent intent=new Intent(OldinfoActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void back_click(View view) {
        Intent intent=new Intent(OldinfoActivity.this,AlloldActivity.class);
        startActivity(intent);
    }
}
