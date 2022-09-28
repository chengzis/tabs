package com.example.video_0927.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.video_0927.R;
import com.example.video_0927.adapter.HomeFragmentAdapter;
import com.example.video_0927.fragment.HomeFragment;
import com.example.video_0927.fragment.MyFragment;
import com.example.video_0927.fragment.NewsFragment;
import com.example.video_0927.util.ToastUtil;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 上下Tab,中间Fragment
 * todo:ViewPager   CommonTabLayout   总结知识点(适配器，监听，选中)
 */
public class HomeActivity extends BaseActivity {

    private ViewPager viewPager;
    private CommonTabLayout tabLayout;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
    }

    @Override
    protected void initListener() {

        //TODO 在源文件里面找相同的写法 （有）
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                ToastUtil.show(HomeActivity.this, "点击了第" + position + "个");
                if (viewPager.getCurrentItem() != position) {
                    viewPager.setCurrentItem(position, false);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        //TODO 在源文件里面找相同的写法 （有）
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (tabLayout.getCurrentTab() != position) {
                    tabLayout.setCurrentTab(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {

        //TODO 在源文件里面找相同的写法 （有）
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new NewsFragment());
        fragments.add(new MyFragment());

        //viewPager的适配器
        HomeFragmentAdapter adapter = new HomeFragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);

        //TODO 在源文件里面找相同的写法 （有）
        ArrayList<CustomTabEntity> tabs = new ArrayList<>();
        tabs.add(new HomeTabEntity("首页", R.mipmap.home_selected, R.mipmap.home_unselect));
        tabs.add(new HomeTabEntity("资讯", R.mipmap.collect_selected, R.mipmap.collect_unselect));
        tabs.add(new HomeTabEntity("我的", R.mipmap.my_selected, R.mipmap.my_unselect));
        tabLayout.setTabData(tabs);


    }

    //TODO 在源文件里面找相同的写法 （有）
    private static class HomeTabEntity implements CustomTabEntity {
        private final String title;
        private final int selectedIcon;
        private final int unselectedIcon;

        public HomeTabEntity(String title, int selectedIcon, int unselectedIcon) {
            this.title = title;
            this.selectedIcon = selectedIcon;
            this.unselectedIcon = unselectedIcon;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return selectedIcon;
        }

        @Override
        public int getTabUnselectedIcon() {
            return unselectedIcon;
        }
    }
}