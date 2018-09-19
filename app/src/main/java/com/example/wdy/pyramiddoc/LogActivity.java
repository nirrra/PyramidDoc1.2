package com.example.wdy.pyramiddoc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Scanner;

public class LogActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberpass;
    public static String accountword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        EditText accounttext=findViewById(R.id.AccountText);
        EditText Passwordtext=findViewById(R.id.PasswordText);
        rememberpass=findViewById(R.id.rempas);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        boolean whetherremember=pref.getBoolean("rempas",false);
        if(whetherremember){
            String accountword=pref.getString("accountword","");
            String password=pref.getString("password","");
            accounttext.setText(accountword);
            Passwordtext.setText(password);
            rememberpass.setChecked(true);
        }
    }
    public void judgelogin(View view) {
        EditText accounttext=findViewById(R.id.AccountText);
        EditText Passwordtext=findViewById(R.id.PasswordText);
        editor=pref.edit();
        accountword=accounttext.getText().toString();
        String password=Passwordtext.getText().toString();
        String rightpassword = readdefinition(accountword);
        if (password.equals(rightpassword)) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            if(rememberpass.isChecked()){
                editor.putBoolean("rempas",true);
                editor.putString("accountword",accountword);
                editor.putString("password",password);
            }
            else {
                editor.clear();
            }
            editor.apply();
            Intent intent=new Intent(LogActivity.this,MainActivity.class);
            startActivity(intent);
            finish();

        }
        else {
            Toast.makeText(this, "工号或密码错误", Toast.LENGTH_SHORT).show();
        }

    }


    private String readdefinition(String accountword) {
        Scanner in=new Scanner(getResources().openRawResource(R.raw.namelist));
        while(in.hasNext()){
            String ling=in.nextLine();
            String[] pieces=ling.split("\t");
            if(pieces[0].equals(accountword))return pieces[1];
        }
        return null;
    }
}
