package com.one.points;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SecondListActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_list);

        ListView listView = (ListView) findViewById(R.id.mylist);//获取ListView对象

        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==7){
                   List<String> list2 = (List<String>) msg.obj;
                   ListAdapter adapter = new ArrayAdapter<String>(SecondListActivity.this, android.R.layout.simple_list_item_1,list2);
                   listView.setAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };

        FirstListActivity fla = new FirstListActivity();//调用其他类的线程来完成
        fla.setHandler(handler);//同步handler
        Thread t = new Thread(fla);
        t.start();

    }
}