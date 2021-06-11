package com.one.points;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ViewPagerDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_demo);

        ViewPager2 viewPager2 = findViewById(R.id.viewpage2);
        MyPageAdapter pageAdapter = new MyPageAdapter(this);
        viewPager2.setAdapter(pageAdapter);//页面装配 初始化ViewPage、Adapter对象，进行绑定

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout,viewPager2,((tab, position) -> tab.setText("object"+(position+1)))).attach();//添加TabLayout控件
    }
}