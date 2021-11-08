package com.example.godgong;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyAdapter extends FragmentStateAdapter {

    public int mCount;
    Fragment_1 F1=new Fragment_1();
    Fragment_2 F2=new Fragment_2();
    Fragment_3 F3=new Fragment_3();

    public MyAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index==0) return F1;
        else if(index==1) return F2;
        else  return  F3;
//        else return new Fragment_4();
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public int getRealPosition(int position) {
        position %= mCount;
        return position; }

}
