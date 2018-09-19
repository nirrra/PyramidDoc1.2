package com.example.wdy.pyramiddoc.chakanyuyue;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.wdy.pyramiddoc.LogActivity;
import com.example.wdy.pyramiddoc.MainActivity;
import com.example.wdy.pyramiddoc.R;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class chakanyuyue extends AppCompatActivity {
    private static final int UPDATE_TEXT1=1;
    private static final String ACTIVITY_TAG = "yuyuelist";
    public static Yuyueinfo yuyueinfo=new Yuyueinfo("OName","DName");
    private List<Yuyueinfo> lists1 = new ArrayList<>();
    private Handler handler1=new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case UPDATE_TEXT1:
                    YuyueAdapter adapter1 = new YuyueAdapter(chakanyuyue.this,R.layout.yuyue_item,lists1);
                    ListView listView1 = findViewById(R.id.listview01);
                    listView1.setAdapter(adapter1);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chakanyuyue);
        final Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                if(MainActivity.conn!=null){
                    String sql="select * from order_info where DoctorID='"+ LogActivity.accountword+"' and Flag!=2";
                    try{
                        java.sql.Statement statement=MainActivity.conn.createStatement();
                        ResultSet rSet1=statement.executeQuery(sql);

                        while(rSet1.next()){
                            yuyueinfo.setDoctorName(rSet1.getString("DName"));
                            yuyueinfo.setOldersName(rSet1.getString("OlderID"));
                            Yuyueinfo yuyueinfo2=new Yuyueinfo(yuyueinfo.getDoctorName(),yuyueinfo.getOldersName());
                            lists1.add(yuyueinfo2);
                        }
                    }catch (SQLException e){
                        Log.e(ACTIVITY_TAG,"读取失败");
                    }
                    Message message1=new Message();
                    message1.what=UPDATE_TEXT1;
                    handler1.sendMessage(message1);
                }
                else {
                    try{
                        Class.forName("com.mysql.jdbc.Driver");
                    }catch (ClassNotFoundException e) {
                        Log.e(ACTIVITY_TAG,"驱动失败");
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

                    } catch (SQLException e) {
                        Log.e(ACTIVITY_TAG,"连接失败");
                    }
                }
            }
        });
        thread1.start();
        ListView listView1 = findViewById(R.id.listview01);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Yuyueinfo yuyueinfo=lists1.get(position);
                Intent intent1=new Intent();
                intent1.setClass(chakanyuyue.this,quxiaoyuyue.class);
                startActivity(intent1);
                quxiaoyuyue.OldID=yuyueinfo.getOldersName();
                finish();
            }
        });
    }
}
