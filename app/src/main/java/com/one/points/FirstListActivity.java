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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FirstListActivity extends ListActivity implements Runnable {
    private static final String TAG = "FirstListActivity";
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_first_list);

//        String[] list_data = {"one","two","three","four"};
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==7){
                    List<String> list2 = (List<String>) msg.obj;
                    Log.i(TAG, "handleMessage: "+list2.get(1));
                    ListAdapter adapter = new ArrayAdapter<String>(FirstListActivity.this, android.R.layout.simple_list_item_1,list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };

        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        Document doc = null;
        List<String> list1 = new ArrayList<String>();
        try {
            doc = Jsoup.connect("http://www.usd-cny.com/icbc.htm").get();
            Log.i(TAG, "run: title:" + doc.title());
            Element table = doc.getElementsByTag("table").first();//获取table对象
            Elements tds = table.getElementsByTag("td");//获取所有的td 注意element（s）对象是集合还是单个元素
            for (int i = 0; i < tds.size() - 1; i += 5) {
                list1.add(tds.get(i).text() + "汇率=" + tds.get(i + 1).text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(7,list1);
        handler.sendMessage(msg);

    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}

