package com.example.video_0927.fragment;


import androidx.viewpager.widget.ViewPager;

import com.example.video_0927.R;
import com.example.video_0927.adapter.CategoryAdapter;
import com.example.video_0927.api.Api;
import com.example.video_0927.api.ApiRequest;
import com.example.video_0927.entity.entity.CategoryEntity;
import com.example.video_0927.entity.entity.PageEntity;
import com.example.video_0927.entity.response.CategoryResponse;
import com.example.video_0927.entity.response.PageResponse;
import com.example.video_0927.util.GsonUtil;
import com.example.video_0927.util.ToastUtil;
import com.flyco.tablayout.SlidingTabLayout;

import java.io.IOException;
import java.util.List;

public class HomeFragment extends BaseFragment {

    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    protected void initView() {
        //TODO 在源文件里面找相同的写法 （有）
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        getTabList();
    }

    private void getTabList() {
    //连接获取Tab标题的Api
        ApiRequest.newGet(Api.VIDEO_CATEGORY_LIST).enqueue(new ApiRequest.CallBack() {
            @Override
            public void onFailure(IOException e) {

            }

            @Override
            public void onResponse(String string) {
                //解析json,得到数据
                CategoryResponse categoryResponse = GsonUtil.fromJson(string, CategoryResponse.class);

                if (categoryResponse.isSuccess()){
                    PageEntity<CategoryEntity> page = categoryResponse.page;

                    //得到tab标题集合数据
                    List<CategoryEntity> list = page.list;


                    CategoryAdapter adapter = new CategoryAdapter(getChildFragmentManager(),list);

                    //TODO 在源文件里面找相同的写法 （有）
                    viewPager.setAdapter(adapter);
                    tabLayout.setViewPager(viewPager);


                }else {
                    ToastUtil.show(HomeFragment.this, categoryResponse.msg);
                }


            }
        });
    }
}