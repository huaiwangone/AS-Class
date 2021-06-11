package com.one.points;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

public class MyPageAdapter extends FragmentStateAdapter {

    public MyPageAdapter(@NonNull @NotNull ViewPagerDemo fragment) {
        super(fragment);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
       if(position==0){
            return new FirstFragment();
       }else{
           return  new SecondFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
