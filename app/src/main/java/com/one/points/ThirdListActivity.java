package com.one.points;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ThirdListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "ThirdListActivity";
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_list);
        listView = findViewById(R.id.mylist3);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

//        ArrayList<HashMap<String,String>> listItems = new ArrayList<HashMap<String, String>>();//建立map
//        for(int i =0;i<10;i++){
//            HashMap<String,String> map = new HashMap<String, String>();
//            map.put("ItemTitle","Rate:"+i);
//            map.put("ItemDetail","Detail:"+i);
//            listItems.add(map);
//        }

//        SimpleAdapter listItemAdapater = new SimpleAdapter(this,listItems,R.layout.list_item,
//                new String[]{"ItemTitle","ItemDetail"},
//                new int[]{R.id.itemTitle,R.id.itemDetail});
//        listView.setAdapter(listItemAdapater);
//        listView.setVisibility(View.VISIBLE);

        listView.setOnItemClickListener(this);
        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==7){
                    ArrayList<HashMap<String,String>> listItems = (ArrayList<HashMap<String, String>>) msg.obj;
                    Log.i(TAG, "handleMessage: "+listItems);
//                    List<String> list3 = (List<String>) msg.obj;
//                    ListAdapter adapter = new ArrayAdapter<String>(ThirdListActivity.this, android.R.layout.simple_list_item_1,list3);
//                    listView.setAdapter(adapter);
//                    SimpleAdapter listItemAdapater = new SimpleAdapter(ThirdListActivity.this,listItems,R.layout.list_item,
//                            new String[]{"money","rate"},
//                            new int[]{R.id.itemTitle,R.id.itemDetail});
                    MyAdapter listItemAdapater = new MyAdapter(ThirdListActivity.this,R.layout.list_item,listItems);
                    listView.setAdapter(listItemAdapater);

                    //显示隐藏
                    listView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
                super.handleMessage(msg);
            }
        };

        FirstListActivity fla = new FirstListActivity();
        fla.setHandler(handler);
        Thread t = new Thread(fla);
        t.start();

    }

    @Override//复写接口方法，事件处理
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //两种获得view数据的方法
//        Object itemAtPosition = listView.getItemAtPosition(position);
//        HashMap<String,String> map = (HashMap<String,String>) itemAtPosition;
//        String titleStr = map.get("money");
//        String detailStr = map.get("rate");
//        Log.i(TAG, "onItemClick: titleStr="+titleStr);
//        Log.i(TAG, "onItemClick: detailStr="+detailStr);
//
        TextView title = (TextView) view.findViewById(R.id.itemTitle);
        TextView detail = (TextView) view.findViewById(R.id.itemDetail);
        String title2 = String.valueOf(title.getText());
        String detail2 = String.valueOf(detail.getText());
        Log.i(TAG, "onItemClick: title2="+title2);
        Log.i(TAG, "onItemClick: detail2="+detail2);

        Intent config = new Intent(this,Rate2.class);
        config.putExtra("title",title2);
        config.putExtra("detail",detail2);

        startActivityForResult(config,1);
    }
}