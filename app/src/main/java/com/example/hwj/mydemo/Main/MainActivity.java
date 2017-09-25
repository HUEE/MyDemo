package com.example.hwj.mydemo.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hwj.mydemo.Base.BaseActivity;
import com.example.hwj.mydemo.NetWork.MovieActivity;
import com.example.hwj.mydemo.PopWindow.PopWindow;
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
    @BindView(R.id.bt_retrofit)
    Button bt_retrofit;
    @BindView(R.id.bt_pop)
    Button bt_pop;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayout () {
        return R.layout.activity_main;
    }

    @Override
    public void init () {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
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

    @OnClick({R.id.bt_selectlist, R.id.bt_recyclerView1, R.id.bt_retrofit, R.id.bt_pop})
    void btClick (View v) {
        switch (v.getId()) {
            case R.id.bt_selectlist:
//                textView.setText("vvdff");
                ToastUtils.showToast(this, "Main_btSelect---->hahahaha");
                startActivity(new Intent(mContext, TextSelectActivity.class));
                break;
            case R.id.bt_recyclerView1:
                ToastUtils.showToast(this, "RecyclerView1-->hahahahah");
                startActivity(new Intent(mContext, CollapsingToolbarLayoutActivity.class));
                break;
            case R.id.bt_retrofit:
                ToastUtils.showToast(mContext, "Retrofit--->hahahaha");
                startActivity(new Intent(mContext, MovieActivity.class));
                break;
            case R.id.bt_pop:
                final PopWindow.Builder builder = new PopWindow.Builder(this)
                        .setContentView(R.layout.pop)
                        .setBackgroundDimEnable(true)
                        .setFocusAndOutsideEnable(false)
                        .showAsDropDown(bt_pop);
                builder.getCurView().findViewById(R.id.pop_bt).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View view) {
                        Toast.makeText(mContext, "sucess", Toast.LENGTH_LONG).show();
                        builder.dismiss();
                    }
                });
                break;

        }
    }
}
