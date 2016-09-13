// (c)2016 Flipboard Inc, All Rights Reserved.

package com.example.hwj.mydemo.NetWork.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hwj.mydemo.NetWork.http.Bean.Subject;
import com.example.hwj.mydemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter<T> extends RecyclerView.Adapter {
    List<T> data;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new DebounceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DebounceViewHolder debounceViewHolder = (DebounceViewHolder) holder;
        Subject item_data = (Subject) data.get(position);
        //Glide.with(holder.itemView.getContext()).load(item_data.image_url).into(debounceViewHolder.imageView);
        debounceViewHolder.descriptionTv.setText(item_data.getTitle());
        debounceViewHolder.description.setText(item_data.getId());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setImages(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    static class DebounceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.descriptionTv)
        TextView descriptionTv;
        @BindView(R.id.imageIv)
        ImageView imageView;

        public DebounceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
