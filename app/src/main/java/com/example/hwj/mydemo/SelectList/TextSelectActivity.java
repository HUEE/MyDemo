package com.example.hwj.mydemo.SelectList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hwj.mydemo.Base.BaseActivity;
import com.example.hwj.mydemo.CallBack.SelectListCallBack;
import com.example.hwj.mydemo.R;
import com.example.hwj.mydemo.utils.tools.TopNaviUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hwj on 16-8-10.
 */

public class TextSelectActivity extends BaseActivity implements SelectListCallBack {
    @BindView(R.id.select_view)
    SelectListView selectListView;
    @BindView(R.id.content)
    TextView tv_content ;
    private Map allCategoryMap, allCityMap;
    String[] topCityArr, topCategoryArr;
    @BindView(R.id.bt1)
    Button bt1 ;
    @BindView(R.id.bt2)
    Button bt2 ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_selectlist);
        ButterKnife.bind(this);
        initSelectList();

    }

    private void initSelectList() {
        allCategoryMap = TopNaviUtil.getCategoryItemMapInstance(R.raw.e_100002_category, this);
        allCityMap = TopNaviUtil.getCityItemMapInstance(R.raw.e_100001_city, this);
        topCategoryArr = TopNaviUtil.getTopLevelCategoryInstance(R.raw.e_100002_category, this);
        topCityArr = TopNaviUtil.getTopLevelCityInstance(R.raw.e_100001_city, this);
        //selectListView.setListLevel(1);
        selectListView.initListData(allCategoryMap, allCityMap, topCityArr, topCategoryArr,this);
    }

    @OnClick({R.id.bt1, R.id.bt2})
    void fillList(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                Toast.makeText(this, "--->1", Toast.LENGTH_SHORT).show();
                selectListView.showList(true);
                break;
            case R.id.bt2:
                Toast.makeText(this, "--->2", Toast.LENGTH_SHORT).show();
                selectListView.showList(false);
                break;

        }
    }

    @Override
    public void selectListClick() {
        Map map = selectListView.getData();
        bt1.setText((String)map.get("topNavCity"));
        bt2.setText((String) map.get("topNavCate"));
    }
}
