package com.example.video_0927.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    public BaseFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    public BaseFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initListener();
        initData();
    }
    /**
     * 查找view
     * @param id    view的id
     */
    protected <T extends View>T findViewById(int id){
        View rootView = getView();
        return rootView.findViewById(id);
    }
    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 初始化监听器
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

}
