package com.one.points;

import androidx.annotation.Nullable;
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
    double dollarate=0.1503;
    double eurorate=0.1266;
    double wonrate=170.2708;
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
            rate = dollarate;
        }
        else if(btn.getId()==R.id.EURO){
            rate = eurorate;
        }
        else{
            rate = wonrate;
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
        Log.i(TAG, "click1: 跳转到修改汇率界面");
//        Intent open = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jd.com"));
//        startActivity(open);
        Intent config = new Intent(this,ChangeRate.class);
        config.putExtra("dollarate",dollarate);
        config.putExtra("eurorate",eurorate);
        config.putExtra("wonrate",wonrate);

        startActivityForResult(config,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1&&resultCode==2){
            dollarate = data.getDoubleExtra("dollar1",0.0);
            eurorate = data.getDoubleExtra("euro1",0.0);
            wonrate = data.getDoubleExtra("won1",0.0);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
