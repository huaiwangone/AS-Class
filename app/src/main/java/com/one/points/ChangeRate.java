package com.one.points;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ChangeRate extends AppCompatActivity {
        private static final String TAG = "ChangeRate";
        double dollar0;
        double euro0;
        double won0;
        TextView dollar;
        TextView euro;
        TextView won;
        Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_rate);
        Intent intent = getIntent();
        dollar0 = intent.getDoubleExtra("dollarate",0.0);
        euro0 = intent.getDoubleExtra("eurorate",0.0);
        won0 = intent.getDoubleExtra("wonrate",0.0);

        dollar = (TextView) findViewById(R.id.editTextTextPersonName3);
        euro = (TextView) findViewById(R.id.editTextTextPersonName4);
        won = (TextView) findViewById(R.id.editTextTextPersonName5);

        dollar.setText(String.valueOf(dollar0));
        euro.setText(String.valueOf(euro0));
        won.setText(String.valueOf(won0));


    }

    public void onClick2(View btn){
        intent1 = new Intent(this,MainActivity2.class);
        double dollar1 = Double.parseDouble(dollar.getText().toString());
        double euro1 = Double.parseDouble(euro.getText().toString());
        double won1 = Double.parseDouble(won.getText().toString());
        intent1.putExtra("dollar1",dollar1);
        intent1.putExtra("euro1",euro1);
        intent1.putExtra("won1",won1);
        setResult(2,intent1);
        finish();
    }
}