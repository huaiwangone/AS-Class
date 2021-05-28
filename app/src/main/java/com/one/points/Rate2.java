package com.one.points;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Rate2 extends AppCompatActivity {
    Intent intent;
    private static final String TAG = "Rate2";
    @Override//可以在xml文件中增添属性android:theme="@style/Theme.AppCompat.Dialog" 可以使得跳转页面变成对话框
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate2);
        TextView money_kind = findViewById(R.id.money_kind);
        TextView money = findViewById(R.id.money);
        intent = getIntent();

        String moneyStr = intent.getStringExtra("title");
        money_kind.setText(moneyStr);

        EditText RMB = findViewById(R.id.RMB);//实时计算 监听文本框输入数据
        RMB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String detail = intent.getStringExtra("detail");
                Double rate = Double.parseDouble(detail);
                Double rmb = Double.parseDouble(String.valueOf(RMB.getText()));
                money.setText(String.valueOf(rmb/rate*100));
            }
        });
    }

//    public void click2(View btn){
//        TextView RMB = findViewById(R.id.RMB);
//        TextView money= findViewById(R.id.money);
//        String detail = intent.getStringExtra("detail");
//        Double rate = Double.parseDouble(detail);
//        Double rmb = Double.parseDouble(String.valueOf(RMB.getText()));
//        money.setText(String.valueOf(rmb/rate*100));
//    }

}