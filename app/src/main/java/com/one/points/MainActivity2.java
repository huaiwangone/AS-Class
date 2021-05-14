package com.one.points;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.security.auth.login.LoginException;

public class MainActivity2 extends AppCompatActivity implements Runnable{
    EditText rmb;
    TextView result;
    double dollarate=0.0;
    double eurorate=0.0;
    double wonrate=0.0;
    Handler handler;
    private static final String TAG = "MainActivity2";
    String timeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        rmb = findViewById(R.id.input);
        result = findViewById(R.id.result);

        //读取数据
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        dollarate = Double.parseDouble(sharedPreferences.getString("dollare_rate","0.1503"));
        eurorate = Double.parseDouble(sharedPreferences.getString("euro_rate","0.1266"));
        wonrate = Double.parseDouble(sharedPreferences.getString("won_rate","170.2708"));
        timeStr = sharedPreferences.getString("time","");//设置时间变量
        Log.i(TAG, "onCreate: timeStr="+timeStr);

        Date date = new Date();
        SimpleDateFormat DF = new SimpleDateFormat("yyyy-mm-dd");//获取本次访问时间
        if(timeStr.equals(DF.format(date))) {
            Log.i(TAG, "onCreate: 今日汇率已更新");
        }
            //启用子线程
        else{
            Log.i(TAG, "onCreate: 更新今日汇率");
            Thread t = new Thread(this);
            t.start();//自动调用run方法
        }
        //创建handler对象
        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==7){
                    String val = (String) msg.obj;
                    result.setText(val);
                }
                timeStr = DF.format(date);
                Log.i(TAG, "handleMessage: timeStr="+timeStr);
                SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("dollar_rate",String.valueOf(dollarate));
                editor.putString("euro_rate",String.valueOf(eurorate));
                editor.putString("won_rate",String.valueOf(wonrate));//将网页获取的汇率存入SP中
                editor.putString("time",timeStr);//存入当前时间
                editor.apply();
                super.handleMessage(msg);
            }
        };
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
        openConfig();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_set){
            openConfig();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openConfig() {
        Log.i(TAG, "click1: 跳转到修改汇率界面");
//        Intent open = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jd.com"));
//        startActivity(open);
        Intent config = new Intent(this, ChangeRate.class);
        config.putExtra("dollarate", dollarate);
        config.putExtra("eurorate", eurorate);
        config.putExtra("wonrate", wonrate);

        startActivityForResult(config, 1);
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

    @Override
    public void run() {
        //子线程通常进行耗时操作
        URL url = null;
//        try { 将网页源码转换为文本
//            url = new URL("http://www.usd-cny.com/icbc.htm");
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            InputStream in = null;
//            in = http.getInputStream();
//            String html = inputStream2String(in);
//            Log.i(TAG, "run:html="+html);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//       catch (IOException e) {
//            e.printStackTrace();
//        }
        SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
            Document doc = null;
            try {
                doc = Jsoup.connect("http://www.usd-cny.com/icbc.htm").get();
                Log.i(TAG, "title:" + doc.title());
                Element table = doc.getElementsByTag("table").first();//获取table对象
                Elements tds = table.getElementsByTag("td");//获取所有的td 注意element（s）对象是集合还是单个元素

                for (int i = 0; i <= tds.size() - 1; i++) {
                    if (tds.get(i).text().equals("美元")) {
                        String D1 = tds.get(i + 1).text();
                        dollarate = Double.parseDouble(D1) / 100;
                        Log.i(TAG, "run: D1=" + D1);
                        continue;
                    }
                    if (tds.get(i).text().equals("欧元")) {
                        String E1 = tds.get(i + 1).text();
                        eurorate = Double.parseDouble(E1) / 100;
                        Log.i(TAG, "run: E1=" + E1);
                        continue;
                    }
                    if (tds.get(i).text().equals("韩币")) {
                        String W1;
                        W1 = tds.get(i + 2).text();
                        wonrate = Double.parseDouble(W1) * 100;
                        Log.i(TAG, "run: W1=" + W1);
                        continue;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }



        //运行完之后需要返回消息到主线程
        Message msg = handler.obtainMessage(7,"Hello from run()");
        //或msg.what = 7;
        msg.obj = "Hello from run()";
        handler.sendMessage(msg);//要传回handler才能运行存入时间部分的内容
    }

    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream,"gb2312");
        while(true){
            int rsz = in.read(buffer,0,buffer.length);
            if(rsz<0)
                break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }
}
