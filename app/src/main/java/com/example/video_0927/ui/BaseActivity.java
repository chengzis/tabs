package com.example.video_0927.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayoutId());
        initView();
        initListener();
        initData();

    }

    protected abstract int getContentLayoutId();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();
}
