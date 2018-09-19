package com.example.wdy.pyramiddoc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class FirstActivity extends Activity{
    private final int SPLASH_DISPLAY_LENGTH = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(FirstActivity.this,LogActivity.class);
                FirstActivity.this.startActivity(intent);
                FirstActivity.this.finish();
            }
        },SPLASH_DISPLAY_LENGTH);
    }
}