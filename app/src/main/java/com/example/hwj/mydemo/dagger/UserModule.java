package com.example.hwj.mydemo.dagger;


import android.content.Intent;

import com.example.hwj.mydemo.dagger.bean.Login;
import com.example.hwj.mydemo.dagger.qualifiers.User;
import com.example.hwj.mydemo.main.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by weilu on 2016/1/27.
 */
@Module
public class UserModule {

    @Provides
    Intent provideIntent (MainActivity mainActivity) {
        return mainActivity.getIntent();
    }

    @Provides
    @User
    Login provideXiaoMingUser () {
        Login xiaomin = new Login();
        xiaomin.setPassword("******");
        xiaomin.setName("小明");
        return xiaomin;
    }

    @Provides
    Login provideXiaoGuanUser (Intent intent) {
        Login xiaoguan = new Login();
        xiaoguan.setPassword("######");
        xiaoguan.setName("小关");
        return xiaoguan;
    }

}
