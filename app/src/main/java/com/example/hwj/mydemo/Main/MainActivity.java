package com.example.hwj.mydemo.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.hwj.mydemo.Base.BaseActivity;
import com.example.hwj.mydemo.R;
import com.example.hwj.mydemo.RecyclerView.CollapsingToolbarLayoutActivity;
import com.example.hwj.mydemo.SelectList.TextSelectActivity;
import com.example.hwj.mydemo.utils.tools.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bt_selectlist)
    Button bt_selectlist;
    @BindView(R.id.bt_recyclerView1)
    Button bt_recyclerView1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.bt_selectlist, R.id.bt_recyclerView1})
    void btClick(View v) {
        switch (v.getId()) {
            case R.id.bt_selectlist:
                ToastUtils.showToast(this, "Main_btSelect");
                startActivity(new Intent(MainActivity.this, TextSelectActivity.class));
                break;
            case R.id.bt_recyclerView1:
                ToastUtils.showToast(this, "RecyclerView1");
                startActivity(new Intent(MainActivity.this, CollapsingToolbarLayoutActivity.class));
                break;
        }
    }
}
