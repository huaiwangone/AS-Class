package com.one.points;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Rate2 extends AppCompatActivity {
    Intent intent;
    private static final String TAG = "Rate2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate2);
        TextView money_kind = findViewById(R.id.money_kind);
        intent = getIntent();
        String moneyStr = intent.getStringExtra("title");
        money_kind.setText(moneyStr);
    }

    public void click2(View btn){
        TextView RMB = findViewById(R.id.RMB);
        TextView money= findViewById(R.id.money);
        String detail = intent.getStringExtra("detail");
        Double rate = Double.parseDouble(detail);
        Double rmb = Double.parseDouble(String.valueOf(RMB.getText()));
        money.setText(String.valueOf(rmb/rate*100));
    }

}