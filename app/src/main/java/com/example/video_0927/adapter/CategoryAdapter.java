package com.example.video_0927.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.video_0927.entity.entity.CategoryEntity;
import com.example.video_0927.fragment.VideoFragment2;

import java.util.HashMap;
import java.util.List;

/**
 * viewPager的适配器
 * 需要数据
 *
 * todo：
 * 为什么tab的标题要在ViewPager里面设置？
 *
 */
public class CategoryAdapter extends FragmentStatePagerAdapter {

    private final List<CategoryEntity>mCategories;

    //由于是一个tab一个Fragment,就整个map集合（key,value）
    private final HashMap<CategoryEntity,Fragment>mFragments;

    public CategoryAdapter(@NonNull FragmentManager fm, List<CategoryEntity> list) {
        super(fm);
        this.mCategories = list;
        mFragments=new HashMap<>();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        CategoryEntity categoryEntity = mCategories.get(position);
        //一个CategoryEntity 对应一个Fragment
        Fragment fragment = mFragments.get(categoryEntity);

        if (fragment!=null){
            return fragment;
        }

        fragment = new VideoFragment2();

        /**
         * 通过Bundle向fragment传递参数
         *  fragment.setArguments(bundle);
         *   fragment = new VideoFragment2();
         */
        Bundle bundle =  new Bundle();
        //传递id,和fragment页码对应
        bundle.putInt("categoryId",categoryEntity.categoryId);
        fragment.setArguments(bundle);

        mFragments.put(categoryEntity,fragment);

        return fragment;
    }

    @Override
    public int getCount() {
        return mCategories.size();
    }


    /**
     * tab的标题
     * @param position  下标
     * @return   标题
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //获取分类
        CategoryEntity categoryEntity = mCategories.get(position);
        return categoryEntity.categoryName;

    }
}
