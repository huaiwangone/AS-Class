package com.one.points;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

//多列列表
public class GridViewActivity extends AppCompatActivity {
    ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        GridView gridView = findViewById(R.id.my_GridView);
        List<String> list1 = new ArrayList<String>();
        for(int i=1;i<=100;i++){
            list1.add("item"+i);
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list1);
        gridView.setAdapter(adapter);
        gridView.setEmptyView(findViewById(R.id.Nodata));
    }
}
