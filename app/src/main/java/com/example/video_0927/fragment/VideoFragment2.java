package com.example.video_0927.fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.video_0927.R;
import com.example.video_0927.adapter.VideoAdapter2;
import com.example.video_0927.api.Api;
import com.example.video_0927.api.ApiRequest;
import com.example.video_0927.entity.entity.VideoEntity;
import com.example.video_0927.entity.response.PageResponse;
import com.example.video_0927.listener.OnItemChildClickListener;
import com.example.video_0927.util.GsonUtil;
import com.example.video_0927.util.Utils;
import com.google.common.reflect.TypeToken;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videocontroller.component.CompleteView;
import xyz.doikki.videocontroller.component.ErrorView;
import xyz.doikki.videocontroller.component.GestureView;
import xyz.doikki.videocontroller.component.TitleView;
import xyz.doikki.videocontroller.component.VodControlView;
import xyz.doikki.videoplayer.player.VideoView;
import xyz.doikki.videoplayer.player.VideoViewManager;

public class VideoFragment2 extends BaseFragment {

    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;

    private int categoryId;

    private VideoView mVideoView;

    private int mCurPos = -1;

    private int mLastPos = mCurPos;

    protected StandardVideoController mController;
    protected ErrorView mErrorView;
    protected CompleteView mCompleteView;
    protected TitleView mTitleView;
    private VideoAdapter2 mVideoAdapter;


    public VideoFragment2() {
        super(R.layout.fragment_video);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //接收categoryEntity的id参数
        Bundle arguments = getArguments();
        categoryId = arguments.getInt("categoryId");

    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        refreshLayout = findViewById(R.id.refreshLayout);
    }

    @Override
    protected void initListener() {
        //处理播放相关的  复制
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                FrameLayout playerContainer = view.findViewById(R.id.player_container);
                View child = playerContainer.getChildAt(0);
                if (child != null && child == mVideoView && !mVideoView.isFullScreen()) {
                    releaseVideoView();
                }
            }
        });
    }

    @Override
    protected void initData() {
        initVideoView();

        /**
         * VideoFragment2的适配器
         */
        mVideoAdapter = new VideoAdapter2();
        mVideoAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(int position) {
                startPlay(position);
            }
        });

        getVideoList();

        recyclerView.setAdapter(mVideoAdapter);

    }

    private void getVideoList(){
        int page = 0;
        final int curPage = page;

        HashMap<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("page", curPage);
        params.put("limit", 5);

        ApiRequest.newGet(Api.VIDEO_LIST_BY_CATEGORY,params).enqueue(new ApiRequest.CallBack() {
            @Override
            public void onFailure(IOException e) {

            }

            @Override
            public void onResponse(String string) {
              //解析json
                Type type=new TypeToken<PageResponse<VideoEntity>>(){}.getType();
                PageResponse<VideoEntity> response= GsonUtil.fromJson(string, type);
                mVideoAdapter.setDataList(response.page.list);

            }
        });


    }


    //创建videoview 复制
    protected void initVideoView() {
        mVideoView = new VideoView(getActivity());
        mVideoView.setOnStateChangeListener(new VideoView.SimpleOnStateChangeListener() {
            @Override
            public void onPlayStateChanged(int playState) {
                //监听VideoViewManager释放，重置状态
                if (playState == VideoView.STATE_IDLE) {
                    Utils.removeViewFormParent(mVideoView);
                    mLastPos = mCurPos;
                    mCurPos = -1;
                }
            }
        });
        mController = new StandardVideoController(getActivity());
        mErrorView = new ErrorView(getActivity());
        mController.addControlComponent(mErrorView);
        mCompleteView = new CompleteView(getActivity());
        mController.addControlComponent(mCompleteView);
        mTitleView = new TitleView(getActivity());
        mController.addControlComponent(mTitleView);
        mController.addControlComponent(new VodControlView(getActivity()));
        mController.addControlComponent(new GestureView(getActivity()));
        mController.setEnableOrientation(true);
        mVideoView.setVideoController(mController);
    }

    //播放 复制
    private void startPlay(int position) {
        if (mCurPos == position) return;
        if (mCurPos != -1) {
            //释放之前在播放的类容
            releaseVideoView();
        }
        VideoEntity entity = mVideoAdapter.getItem(position);
        mVideoView.setUrl(entity.getPlayurl());
        mTitleView.setTitle(entity.getVtitle());
        View itemView = recyclerView.getLayoutManager().findViewByPosition(position);
        if (itemView == null) return;

        VideoAdapter2.VideoViewHolder viewHolder = (VideoAdapter2.VideoViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        //把列表中预置的PrepareView添加到控制器中，注意isPrivate此处只能为true。
        mController.addControlComponent(viewHolder.getPrepareView(), true);
        Utils.removeViewFormParent(mVideoView);
        viewHolder.getPlayerContainer().addView(mVideoView, 0);
        //播放之前将VideoView添加到VideoViewManager以便在别的页面也能操作它
        VideoViewManager.instance().add(mVideoView, this.toString());
        mVideoView.start();
        mCurPos = position;
    }

    //停止播放 复制
    private void releaseVideoView() {
        mVideoView.release();
        if (mVideoView.isFullScreen()) {
            mVideoView.stopFullScreen();
        }
        if (getActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mCurPos = -1;
    }

    //生命周期onPause 复制
    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    //暂停播放 复制
    /**
     * 由于onPause必须调用super。故增加此方法，
     * 子类将会重写此方法，改变onPause的逻辑
     */
    protected void pause() {
        releaseVideoView();
    }

    //生命周期onResume 复制
    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    //恢复播放 复制
    /**
     * 由于onResume必须调用super。故增加此方法，
     * 子类将会重写此方法，改变onResume的逻辑
     */
    protected void resume() {
        if (mLastPos == -1)
            return;
        //恢复上次播放的位置
        startPlay(mLastPos);
    }




}
