/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.hwj.mydemo.main;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * 应用主入口！
 * Created by hwj on 2016/7/27.
 */
public class MyApplication extends TinkerApplication {


    public MyApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.example.hwj.mydemo.ApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }

}
