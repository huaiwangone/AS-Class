package com.one.points;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.chrono.ChronoLocalDateTime;

public class MainActivity2 extends AppCompatActivity {
    EditText rmb;
    TextView result;
    private static final String TAG = "MainActivity2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rmb = findViewById(R.id.input);
        result = findViewById(R.id.result);
    }

    public  void click(View btn){
        double rate = 0.0;
        if(btn.getId()==R.id.DOLLAR){
            rate = 0.1503;
        }
        else if(btn.getId()==R.id.EURO){
            rate =0.1266;
        }
        else{
            rate = 170.2708;
        }

        //获取用户输入
        if(rmb.getText().toString()!=null&&rmb.getText().toString().length()>0) {
            double input = Double.parseDouble(rmb.getText().toString());
            double output = input * rate;
            result.setText(String.valueOf(output));
        }
        else{
            //提示用户输入
            Toast.makeText(this, "请输入RMB金额", Toast.LENGTH_SHORT).show();

        }
    }

    public void click1(View btn){
        Log.i(TAG, "click1: 跳转JD");
        Intent open = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jd.com"));
        startActivity(open);
    }
}
