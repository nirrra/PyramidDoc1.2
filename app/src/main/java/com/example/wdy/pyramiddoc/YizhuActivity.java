package com.example.wdy.pyramiddoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class YizhuActivity extends AppCompatActivity {
    private static final String ACTIVITY_TAG = "Demo";
    public static String oldersID;
    public static String zonghexinxit3;
    public static int backflag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yizhu);

        TextView zonghexinxi=(TextView)findViewById(R.id.zonghexinxi);
        zonghexinxi.setText(zonghexinxit3);
    }
    public void add_click(View view) {        /**加号显隐**/
        TextView tv1 = (TextView) findViewById(R.id.drug_name);
        TextView tv2 = (TextView) findViewById(R.id.drug_dosage);
        EditText et1 = (EditText) findViewById(R.id.ypmc);
        EditText et2 = (EditText) findViewById(R.id.ypyl);
        ImageButton ib = (ImageButton) findViewById(R.id.delete);
        tv1.setVisibility(view.VISIBLE);
        tv2.setVisibility(view.VISIBLE);
        et1.setVisibility(view.VISIBLE);
        et2.setVisibility(view.VISIBLE);
        ib.setVisibility(view.VISIBLE);
    }

    public void delete_click(View view) {        /**加号显隐**/
        TextView tv1 = (TextView) findViewById(R.id.drug_name);
        TextView tv2 = (TextView) findViewById(R.id.drug_dosage);
        EditText et1 = (EditText) findViewById(R.id.ypmc);
        EditText et2 = (EditText) findViewById(R.id.ypyl);
        ImageButton ib = (ImageButton) findViewById(R.id.delete);
        tv1.setVisibility(view.INVISIBLE);
        tv2.setVisibility(view.INVISIBLE);
        et1.setVisibility(view.INVISIBLE);
        et2.setVisibility(view.INVISIBLE);
        ib.setVisibility(view.INVISIBLE);
    }

    public void back_click(View view) {
        Intent intent1=new Intent(YizhuActivity.this,XinxiActivity.class);
        Intent intent2=new Intent(YizhuActivity.this,Yizhu2Activity.class);
        if(backflag==1) {
            startActivity(intent1);
        }
        else{
            startActivity(intent2);
        }
    }

    public void ensure_click(View view){
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                EditText bingqing=findViewById(R.id.bingqing);
                EditText yizhu=findViewById(R.id.yizhu);
                EditText drug_name=findViewById(R.id.ypmc);
                EditText drug_dosage=findViewById(R.id.ypyl);

                String bingqingt=bingqing.getText().toString();
                String yizhut=yizhu.getText().toString();
                String drug_namet=drug_name.getText().toString();
                String drug_dosaget=drug_dosage.getText().toString();

                if(MainActivity.conn!=null){
                    String sql="update import_info set Past_conditon = '"+bingqingt+"' ," +
                            "Past_advice='"+yizhut+"',Past_prescription= '"+drug_namet+"'," +
                            "Past_dosage='"+drug_dosaget+"' where ID = '"+oldersID+"'";
                    String sql1="update basic_info set Flag_doc = 1 where ID ='"+oldersID+"'";

                    try{
                        java.sql.Statement statement=MainActivity.conn.createStatement();
                        statement.executeUpdate(sql);
                        statement.executeUpdate(sql1);

                    }catch (SQLException e){
                        Log.e(ACTIVITY_TAG,"createStatement error"+e);
                    }

                }

            }
        });
        thread.start();
        Intent intent=new Intent(YizhuActivity.this,MainActivity.class);
        startActivity(intent);
    }


    public void cancel_click(View view) {                   /**取消弹窗**/
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setTitle("提示信息");
        localBuilder.setIcon(R.mipmap.ic_launcher);
        localBuilder.setMessage("确定要退出？");
        localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
                Intent intent = new Intent(YizhuActivity.this,MainActivity.class);
                startActivity(intent);
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
}
