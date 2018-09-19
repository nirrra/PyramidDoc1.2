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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AlloldActivity extends AppCompatActivity {
    private static final String ACTIVITY_TAG = "Demo";
    private static final int UPDATE_TEXT1=1;
    public static String DoctorName = LogActivity.accountword;
    public static User2 muser =new User2("Bed_num","Name","Sex","ID",1);
    public static Info info=new Info();
    private List<User2> lists1 = new ArrayList<>();

    private Handler handler1=new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case UPDATE_TEXT1:
                    UserAdapter2 adapter1 = new UserAdapter2(AlloldActivity.this,R.layout.user_item,lists1);
                    ListView listView1 = (ListView)findViewById(R.id.listview3);
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
        setContentView(R.layout.activity_allold);
        Log.e(ACTIVITY_TAG,"createStatement error2");

        final Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {

                if(MainActivity.conn!=null){
                    String sql="select * from basic_info";

                    try{
                        java.sql.Statement statement=MainActivity.conn.createStatement();
                        ResultSet rSet=statement.executeQuery(sql);

                        while(rSet.next()){
                            muser.setBed_num(rSet.getString("Bed_num"));
                            muser.setName(rSet.getString("Name"));
                            muser.setSex(rSet.getString("Sex"));
                            muser.setID(rSet.getString("ID"));
                            muser.setFlag_doc(rSet.getInt("Flag_doc"));
                            User2 users = new User2(muser.getBed_num(),muser.getName(),muser.getSex(),muser.getID(),muser.getFlag_doc());
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
        thread1.start();

        ListView listView1 = findViewById(R.id.listview3);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User2 muser=lists1.get(position);
                int flag=muser.getFlag_doc();
                Intent intent1=new Intent();
                Intent intent2=new Intent();
                intent1.setClass(AlloldActivity.this,XinxiActivity.class);
                intent2.setClass(AlloldActivity.this,Xinxi2Activity.class);
                if(flag==0){
                    XinxiActivity.oldersID=muser.getID();
                    YizhuActivity.oldersID=muser.getID();
                    JinriActivity.oldersID=muser.getID();
                    XinxiActivity.backflag=1;
                    startActivity(intent1);
                }
                else{
                    Xinxi2Activity.oldersID=muser.getID();
                    YizhuActivity.oldersID=muser.getID();
                    Yizhu2Activity.oldersID=muser.getID();
                    JinriActivity.oldersID=muser.getID();
                    Xinxi2Activity.backflag=1;
                    startActivity(intent2);
                }

            }
        });

    }

    public void back_click(View view) {
        Intent intent=new Intent(AlloldActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void tjlr_click(View view) {
        Intent intent=new Intent(AlloldActivity.this,OldinfoActivity.class);
        startActivity(intent);
    }

}
