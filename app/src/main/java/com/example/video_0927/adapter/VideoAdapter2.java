package com.example.video_0927.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.video_0927.R;
import com.example.video_0927.entity.entity.VideoEntity;
import com.example.video_0927.listener.OnItemChildClickListener;

import java.util.ArrayList;
import java.util.List;

import xyz.doikki.videocontroller.component.PrepareView;

/**
 * 视频fragment的适配器
 */
public class VideoAdapter2 extends RecyclerView.Adapter<VideoAdapter2.VideoViewHolder>{


    private final List<VideoEntity>mDataList = new ArrayList<>();

    //添加数据进来
    public void setDataList(List<VideoEntity>list){
        mDataList.clear();
        mDataList.addAll(list);
        notifyDataSetChanged();

    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivHeader;
        private final TextView tvTitle;
        private final TextView tvAuthor;

        //修改的布局 复制
        private final FrameLayout layoutPlayerContainer;
        //修改的布局 复制
        private final PrepareView ivCover;
        private final TextView tvCommentCount;
        private final TextView tvCollectCount;
        private final TextView tvDianZanCount;

        private final ImageView ivCollect;
        private final ImageView ivDianZan;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHeader = itemView.findViewById(R.id.iv_header);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            layoutPlayerContainer = itemView.findViewById(R.id.player_container);
            ivCover = itemView.findViewById(R.id.iv_cover);
            tvCommentCount = itemView.findViewById(R.id.tv_comment_count);
            tvCollectCount = itemView.findViewById(R.id.tv_collect_count);
            tvDianZanCount = itemView.findViewById(R.id.tv_dianzan_count);
            ivCollect = itemView.findViewById(R.id.iv_collect);
            ivDianZan = itemView.findViewById(R.id.iv_dianzan);
        }


        public PrepareView getPrepareView() {
            return ivCover;
        }

        public FrameLayout getPlayerContainer() {
            return layoutPlayerContainer;
        }
    }

    @NonNull
    @Override
    public VideoAdapter2.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video2, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter2.VideoViewHolder holder, int position) {
        VideoEntity entity = mDataList.get(position);

        Glide.with(holder.ivHeader)
                .load("https://t7.baidu.com/it/u=2605426091,1199286953&fm=193&f=GIF")
                .transform(new CircleCrop())
                .into(holder.ivHeader);

        holder.tvTitle.setText(entity.getVtitle());
        holder.tvAuthor.setText(entity.getAuthor());
        holder.tvCommentCount.setText(entity.getCommentNum() + "");
        holder.tvCollectCount.setText(entity.getCollectNum() + "");
        holder.tvDianZanCount.setText(entity.getLikeNum() + "");

        holder.ivCollect.setImageResource(R.mipmap.collect);
        holder.ivDianZan.setImageResource(R.mipmap.dianzan);

        holder.layoutPlayerContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemChildClickListener!=null){
                    mOnItemChildClickListener.onItemChildClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public VideoEntity getItem(int position) {
        return mDataList.get(position);
    }


    //设置监听  复制
    private OnItemChildClickListener mOnItemChildClickListener;

    //设置监听  复制
    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        this.mOnItemChildClickListener = onItemChildClickListener;
    }


}
