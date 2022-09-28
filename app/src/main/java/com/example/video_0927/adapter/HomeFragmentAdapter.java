package com.example.video_0927.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * ViewPager的适配器
 * 需要数据
 */

//TODO 适配器在源文件里面找相同的写法 （有）
public class HomeFragmentAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment>mFragments;

    public HomeFragmentAdapter(@NonNull FragmentManager fm,List<Fragment>fragments) {
        super(fm);
        mFragments = fragments;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
