package com.example.wdy.pyramiddoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.wdy.pyramiddoc.chakanyuyue.chakanyuyue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainActivity extends AppCompatActivity {
    private static final String ACTIVITY_TAG = "Demo";
    private static final int UPDATE_TEXT1=1;
    private static final int UPDATE_TEXT2=1;
    public static String DoctorName = LogActivity.accountword;
    public static Connection conn=null;
    public static User muser =new User("Bed_num","Name","Sex","ID");
    public static Info info=new Info();
    private List<User> lists1 = new ArrayList<>();
    private List<User> lists2 = new ArrayList<>();


    private Handler handler1=new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case UPDATE_TEXT1:
                    UserAdapter adapter1 = new UserAdapter(MainActivity.this,R.layout.user_item,lists1);
                    ListView listView1 = (ListView)findViewById(R.id.listview1);
                    listView1.setAdapter(adapter1);
                    break;
                default:
                    break;
            }
        }
    };

    private Handler handler2=new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case UPDATE_TEXT2:
                    UserAdapter adapter2 = new UserAdapter(MainActivity.this,R.layout.user_item,lists2);
                    ListView listView2 = (ListView)findViewById(R.id.listview2);
                    listView2.setAdapter(adapter2);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Log.v(ACTIVITY_TAG,"驱动成功");
        }catch (ClassNotFoundException e){
            Log.e(ACTIVITY_TAG,"驱动失败");
            return;
        }

        final Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String ip = "tangjibin.mysql.rds.aliyuncs.com";
                int port = 3306;
                String dbName = "ex1";
                String url = "jdbc:mysql://" + ip + ":" + port + "/" + dbName;
                String user = "tangjibin";
                String password = "Jiaohua98";

                try {
                    conn = DriverManager.getConnection(url, user, password);
                    Log.i(ACTIVITY_TAG,"连接成功");
                } catch (SQLException e) {
                    Log.e(ACTIVITY_TAG,e.toString());
                    Log.e(ACTIVITY_TAG,"连接失败");
                }

                if(conn!=null){
                    String sql="select * from basic_info where Flag_doc=0 and Flag=1 and id=any(select id from import_info where flag_abnormal =1)";
                    String sql2="update import_info set Flag_abnormal = 1 where Temp>37.5 or Blood_p1>140 or Blood_p2>90 or Other_info != null";
                    try{
                        java.sql.Statement statement=MainActivity.conn.createStatement();
                        statement.executeUpdate(sql2);
                    }catch (SQLException e){
                        Log.e(ACTIVITY_TAG,"createStatement error"+e);
                    }
                    try{
                        java.sql.Statement statement=conn.createStatement();
                        ResultSet rSet=statement.executeQuery(sql);
                        while(rSet.next()){
                            muser.setBed_num(rSet.getString("Bed_num"));
                            muser.setName(rSet.getString("Name"));
                            muser.setSex(rSet.getString("Sex"));
                            muser.setID(rSet.getString("ID"));
                            User users = new User(muser.getBed_num(),muser.getName(),muser.getSex(),muser.getID());
                            lists1.add(users);
                            Log.i(ACTIVITY_TAG,rSet.getString("Name"));
                        }
                    }catch (SQLException e){
                        Log.e(ACTIVITY_TAG,"createStatement error2");
                    }
                    Message message1=new Message();
                    message1.what=UPDATE_TEXT1;
                    handler1.sendMessage(message1);
                }
            }
        });

        final Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String ip = "tangjibin.mysql.rds.aliyuncs.com";
                int port = 3306;
                String dbName = "ex1";
                String url = "jdbc:mysql://" + ip + ":" + port + "/" + dbName;
                String user = "tangjibin";
                String password = "Jiaohua98";

                try {
                    conn = DriverManager.getConnection(url, user, password);
                    Log.i(ACTIVITY_TAG,"连接成功2");
                } catch (SQLException e) {
                    Log.e(ACTIVITY_TAG,e.toString());
                    Log.e(ACTIVITY_TAG,"连接失败2");
                }

                try {
                    conn = DriverManager.getConnection(url, user, password);
                    Log.i(ACTIVITY_TAG,"连接成功");
                } catch (SQLException e) {
                    Log.e(ACTIVITY_TAG,e.toString());
                    Log.e(ACTIVITY_TAG,"连接失败");
                }

                if(conn!=null){
                    String sql="select * from basic_info where Flag_doc=1 and Flag=1 and id=any(select id from import_info where flag_abnormal =1)";
                    String sql2="update import_info set Flag_abnormal = 1 where Temp>37.5 or Blood_p1>140 or Blood_p2>90 or Other_info != null";

                    try{
                        java.sql.Statement statement=MainActivity.conn.createStatement();
                        statement.executeUpdate(sql2);

                    }catch (SQLException e){
                        Log.e(ACTIVITY_TAG,"createStatement error"+e);
                    }

                    try{
                        java.sql.Statement statement=conn.createStatement();
                        ResultSet rSet=statement.executeQuery(sql);

                        while(rSet.next()){
                            muser.setBed_num(rSet.getString("Bed_num"));
                            muser.setName(rSet.getString("Name"));
                            muser.setSex(rSet.getString("Sex"));
                            muser.setID(rSet.getString("ID"));
                            User users = new User(muser.getBed_num(),muser.getName(),muser.getSex(),muser.getID());
                            lists2.add(users);
                            Log.i(ACTIVITY_TAG,rSet.getString("Name"));
                        }
                    }catch (SQLException e){
                        Log.e(ACTIVITY_TAG,"createStatement error2");
                    }
                    Message message1=new Message();
                    message1.what=UPDATE_TEXT2;
                    handler2.sendMessage(message1);

                }
            }
        });
        thread1.start();
        thread2.start();

        ListView listView1 = findViewById(R.id.listview1);
        ListView listView2 = findViewById(R.id.listview2);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User muser=lists1.get(position);
                Intent intent1=new Intent();
                XinxiActivity.backflag=0;
                intent1.setClass(MainActivity.this,XinxiActivity.class);
                startActivity(intent1);
                XinxiActivity.oldersID=muser.getID();
                YizhuActivity.oldersID=muser.getID();
                JinriActivity.oldersID=muser.getID();
            }
        });
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User muser=lists2.get(position);
                Intent intent2=new Intent();
                Xinxi2Activity.backflag=0;
                intent2.setClass(MainActivity.this,Xinxi2Activity.class);
                startActivity(intent2);
                Xinxi2Activity.oldersID=muser.getID();
                YizhuActivity.oldersID=muser.getID();
                Yizhu2Activity.oldersID=muser.getID();
                JinriActivity.oldersID=muser.getID();
            }
        });

    }

    public void ksjrhd_click(View view) {
        /**取消弹窗**/
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setTitle("提示信息");
        localBuilder.setIcon(R.mipmap.ic_launcher);
        localBuilder.setMessage("确定开始？");
        localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {

                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if(MainActivity.conn!=null){
                            String sql="update basic_info set Flag_doc = 0, Flag=0 where Doc_ID='"+DoctorName+"'";

                            try{
                                java.sql.Statement statement=MainActivity.conn.createStatement();
                                statement.executeUpdate(sql);

                            }catch (SQLException e){
                                Log.e(ACTIVITY_TAG,"createStatement error"+e);
                            }

                        }

                    }
                });
                thread.start();
            }
        });
        localBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
                /*Intent intent = new Intent(YizhuActivity.this,YizhuActivity.class);
                startActivity(intent);*/
            }
        });

        localBuilder.setCancelable(false).create();

        localBuilder.show();
    }
    public void xssylr_click(View view) {
        Intent intent=new Intent(MainActivity.this,AlloldActivity.class);
        startActivity(intent);
    }

    public void yyys_click(View view) {
        Intent intent=new Intent(MainActivity.this,chakanyuyue.class);
        startActivity(intent);
    }

}
