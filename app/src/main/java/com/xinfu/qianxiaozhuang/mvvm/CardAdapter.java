package com.xinfu.qianxiaozhuang.mvvm;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.xinfu.qianxiaozhuang.R;
import com.xinfu.qianxiaozhuang.mvvm.widgets.CardAdapterHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jameson on 8/30/16.
 */
class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<String> mList = new ArrayList<>();
    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();

    public CardAdapter(List<String> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_item, parent, false);
        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        Glide.with(holder.itemView.getContext()).load(mList.get(position)).into(holder.mImageView);
//        holder.mImageView.setImageResource(mList.get(position));
//        holder.mImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //ToastUtils.show(holder.mImageView.getContext(), "" + position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

    }

}
