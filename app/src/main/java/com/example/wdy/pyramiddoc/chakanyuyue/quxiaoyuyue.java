package com.example.wdy.pyramiddoc.chakanyuyue;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.wdy.pyramiddoc.LogActivity;
import com.example.wdy.pyramiddoc.MainActivity;
import com.example.wdy.pyramiddoc.R;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class quxiaoyuyue extends AppCompatActivity {
    private static final String ACTIVITY_TAG = "quxiaoyuyue";
    private static final int UPDATE_TEXT=1;
    public static String OldID,DocName,Bed_num,OldName,condition=null;
    int Flag;
    private TextView name,bed,doctor,cond,zonghe;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    name.setText(OldName);
                    bed.setText(Bed_num);
                    doctor.setText(DocName);
                    cond.setText(condition);
                    zonghe.setText(OldID+" "+OldName);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quxiaoyuyue);
        name=findViewById(R.id.xingming10);
        bed=findViewById(R.id.chuanghao10);
        doctor=findViewById(R.id.yuyueyisheng);
        cond=findViewById(R.id.yuyuezhuangtai);
        zonghe=findViewById(R.id.zonghexinxi);
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


                if (MainActivity.conn != null) {
                    String sql = "SELECT * FROM basic_info where ID ='" + OldID + "'";
                    try {
                        java.sql.Statement statement = MainActivity.conn.createStatement();
                        ResultSet rSet = statement.executeQuery(sql);
                        while (rSet.next()) {
                            OldName = rSet.getString("Name");
                            Bed_num = rSet.getString("Bed_num");

                        }
                    } catch (SQLException e) {
                        Log.e(ACTIVITY_TAG, "读取失败");
                    }
                    try {
                        String sql1 = "SELECT * FROM order_info where OlderID ='" + OldID + "' and DoctorID ='" + LogActivity.accountword + "' and Flag !=2" ;
                        java.sql.Statement statement1 = MainActivity.conn.createStatement();
                        ResultSet resultSet = statement1.executeQuery(sql1);
                        while (resultSet.next()) {
                            DocName = resultSet.getString("Date");
                            condition = resultSet.getString("causes");

                        }
                    } catch (SQLException e) {
                        Log.e(ACTIVITY_TAG, "读取失败");
                    }

                    Message message = new Message();
                    message.what = UPDATE_TEXT;
                    handler.sendMessage(message);
                } else {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                    } catch (ClassNotFoundException e) {
                        Log.e(ACTIVITY_TAG, "驱动失败");
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
                    } catch (SQLException e) {
                        Log.e(ACTIVITY_TAG, "连接失败");
                    }
                }


            }
        });
        thread.start();

    }

    public void quxiaoyuyue01(View view) {
        final Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                if (MainActivity.conn != null) {
                    String sql = "update order_info set Flag = 1 where OlderID ='" + OldID + "' and DoctorID ='" + LogActivity.accountword + "'";
                    try {
                        java.sql.Statement statement2 = MainActivity.conn.createStatement();
                        statement2.executeUpdate(sql);
                    } catch (SQLException e) {
                        Log.e(ACTIVITY_TAG, "读取失败");
                    }
                } else {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                    } catch (ClassNotFoundException e) {
                        Log.e(ACTIVITY_TAG, "驱动失败");
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
                    } catch (SQLException e) {
                        Log.e(ACTIVITY_TAG, "连接失败");
                    }
                }


            }
        });
        thread1.start();

    }

    public void quxiaoyuyue02(View view) {
        final Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                if (MainActivity.conn != null) {
                    String sql = "update order_info set Flag = 2 where OlderID ='" + OldID + "' and DoctorID ='" + LogActivity.accountword + "'";
                    try {
                        java.sql.Statement statement3 = MainActivity.conn.createStatement();
                        statement3.executeUpdate(sql);
                        Intent intent=new Intent(quxiaoyuyue.this,chakanyuyue.class);
                        startActivity(intent);
                    } catch (SQLException e) {
                        Log.e(ACTIVITY_TAG, "读取失败");
                    }
                } else {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                    } catch (ClassNotFoundException e) {
                        Log.e(ACTIVITY_TAG, "驱动失败");
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
                    } catch (SQLException e) {
                        Log.e(ACTIVITY_TAG, "连接失败");
                    }
                }


            }
        });
        thread2.start();

    }

    public void returnTo(View view) {
        Intent intent=new Intent(quxiaoyuyue.this,chakanyuyue.class);
        startActivity(intent);
    }
}
