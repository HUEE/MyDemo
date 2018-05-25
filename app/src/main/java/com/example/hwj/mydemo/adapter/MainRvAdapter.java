package com.example.hwj.mydemo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hwj.mydemo.R;

import java.util.List;

/**
 * Created by hwj on 2018/5/25.
 */

public class MainRvAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public MainRvAdapter(List<String> data) {
        super(R.layout.main_rv_item, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, String s) {
        holder.setText(R.id.item_text, s);
    }
}
