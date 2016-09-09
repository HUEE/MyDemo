package com.example.hwj.mydemo.SelectList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hwj.mydemo.R;
import com.example.hwj.mydemo.SelectList.CallBack.SelectListCallBack;
import com.example.hwj.mydemo.utils.sortlistview.CharacterParser;
import com.example.hwj.mydemo.utils.sortlistview.PinyinComparator;
import com.example.hwj.mydemo.utils.sortlistview.SideBar;
import com.example.hwj.mydemo.utils.sortlistview.SortAdapter;
import com.example.hwj.mydemo.utils.sortlistview.SortModel;
import com.example.hwj.mydemo.utils.tools.DeviceTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by hwj on 16-8-9.
 */

public class SelectListView extends LinearLayout {
    private LinearLayout listView1;
    private LinearLayout listView2;
    private LinearLayout listView3;
    private LinearLayout topnavll;
    private ListView sortListView1;
    private SortAdapter adapter1;
    private ListView sortListView2;
    private SortAdapter adapter2;
    private ListView sortListView3;
    private SortAdapter adapter3;
    private Context context;
    //汉字转换成拼音的类
    private CharacterParser characterParser;
    private Map secCateMap, allCityMap, allCategoryMap;
    private boolean cityButtonPressed = false;
    private boolean categoryButtonPressed = false;
    private String topNavCity, topNavCate;
    private boolean isAllCityData = true;
    //一级类别和一级城市列表
    String[] topCityArr;
    String[] topCategoryArr;
    // 根据拼音来排列ListView里面的数据类
    private PinyinComparator pinyinComparator;
    private List<SortModel> SourceDateList;
    private LinearLayout.LayoutParams twoListViewLp, threeListViewLp;
    private String firstCity, firstCityName, secondCity, firstCate, firstCateName, secondCate, secondCateName, thirdCate, cacheVersion;
    private SideBar sidebar;
    private String dispCityFullPath;
    private String dispCateFullPath;
    private SelectListCallBack selectListCallBack;
    private int listLevel ;
    Map firstCityMap;
    private static int MIN_LEVEL = 0 ;
    private static int MAX_LEVEL = 3 ;

    public SelectListView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SelectListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    /**
     * 初始化列表控件
     */
    private void init() {
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        LayoutInflater.from(getContext()).inflate(R.layout.select_view, this);
        topnavll = (LinearLayout) findViewById(R.id.lineviewlist);
        topnavll.setVisibility(View.GONE);
        sidebar = (SideBar) findViewById(R.id.sidrbar1);
        sidebar.setOnTouchingLetterChangedListener(onTouchingLetterChangedListener);
        sortListView1 = (ListView) findViewById(R.id.country_lvcountry1);
        sortListView2 = (ListView) findViewById(R.id.country_lvcountry2);
        sortListView3 = (ListView) findViewById(R.id.country_lvcountry3);
        sortListView1.setOnItemClickListener(onItemClickListener);
        sortListView2.setOnItemClickListener(onItemClickListener);
        sortListView3.setOnItemClickListener(onItemClickListener);
    }

    SideBar.OnTouchingLetterChangedListener onTouchingLetterChangedListener = new SideBar.OnTouchingLetterChangedListener() {
        @Override
        public void onTouchingLetterChanged(String s) {
            //该字母首次出现的位置
            int position = adapter1.getPositionForSection(s.charAt(0));
            if (position != -1) {
                sortListView1.setSelection(position);
            }
        }
    };

    /**
     * 初始化列表数据
     */
    public void initListData(Map allCategoryMap, Map allCityMap, String[] topCityArr, String[] topCategoryArr, SelectListCallBack callBack) {
        this.allCategoryMap = allCategoryMap;
        this.allCityMap = allCityMap;
        this.topCityArr = topCityArr;
        this.topCategoryArr = topCategoryArr;
        this.selectListCallBack = callBack;
        int height = DeviceTools.getHeight(context) - DeviceTools.getStatusBarHeight(context);
        int width = DeviceTools.getWidth(context);
        twoListViewLp = new LinearLayout.LayoutParams(width / 2, height);
        threeListViewLp = new LinearLayout.LayoutParams(width / 3, height);
    }

    /**
     * 列表点击监听
     */
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.country_lvcountry1:
                    firstListClicked(position);
                    break;
                case R.id.country_lvcountry2:
                    secondListClicked(position);
                    break;
                case R.id.country_lvcountry3:
                    thirdListClicked(position);
                    break;
            }
        }
    };

    /**
     * 处理一级列表点击
     */
    private void firstListClicked(int position) {
        listView1.setLayoutParams(twoListViewLp);
        listView2.setLayoutParams(twoListViewLp);
        if (cityButtonPressed) {
            firstCityName = ((SortModel) adapter1.getItem(position)).getName();
            topNavCity = firstCityName;
            checkData(firstCityName,allCityMap,(Map) allCityMap.get(firstCityName));
            firstCityMap = (Map) allCityMap.get(firstCityName);
            firstCity = (String) firstCityMap.get("cityid");
            Map subCityMap = (Map) firstCityMap.get("subcity");
            String secondCityStr = "";
            for (Object key : subCityMap.keySet()) {
                secondCityStr = secondCityStr + key + ",";
            }
            if (secondCityStr.equals("")||listLevel==1) {
                listView2.setVisibility(View.GONE);
                // 只有一级城市，处理数据请求
                dispCityFullPath = firstCity;
                itemClicked();
            } else {
                String[] secondCityArr = secondCityStr.split(",");
                SourceDateList = filledData(secondCityArr, false, true);
                // 根据a-z进行排序源数据
                Collections.sort(SourceDateList, pinyinComparator);
                adapter2 = new SortAdapter(context, SourceDateList);
                sortListView2.setAdapter(adapter2);
                listView2.setLayoutParams(twoListViewLp);  //参数依次为width、height、scrollX、scrollY
                listView2.setVisibility(View.VISIBLE);
            }
        } else if (categoryButtonPressed) {
            firstCateName = ((SortModel) adapter1.getItem(position)).getName();
            topNavCate = firstCateName;
            checkData(firstCateName,allCategoryMap,(Map) allCategoryMap.get(firstCateName));
            Map subCate = (Map) allCategoryMap.get(firstCateName);
            firstCate = (String) subCate.get("cateid");
            secCateMap = (Map) subCate.get("subcate");
            String secondCateStr = "";
            for (Object key : secCateMap.keySet()) {
                secondCateStr = secondCateStr + key + ",";
            }
            if (secondCateStr.equals("")) {
                //只有一级类别，处理数据请求  此情况不存在
                listView2.setVisibility(View.GONE);
            } else {
                String[] secondCateArr = secondCateStr.split(",");
                SourceDateList = filledData(secondCateArr, false, false);
                // 根据a-z进行排序源数据
                Collections.sort(SourceDateList, pinyinComparator);
                adapter2 = new SortAdapter(context, SourceDateList);
                sortListView2.setAdapter(adapter2);
                listView2.setLayoutParams(twoListViewLp);  //参数依次为width、height、scrollX、scrollY
                listView2.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 处理二级列表点击
     */
    private void secondListClicked(int position) {
        listView1.setLayoutParams(twoListViewLp);
        listView2.setLayoutParams(twoListViewLp);
        if (cityButtonPressed) {
            String secCityName = ((SortModel) adapter2.getItem(position)).getName();
            if (secCityName.equals("全部")) {
                isAllCityData = true;
                topNavCity = firstCityName;
                secondCity = "";
                dispCityFullPath = firstCity;
                itemClicked();
            } else {
                isAllCityData = false;
                Map subCityMap = (Map) firstCityMap.get("subcity");
                Map subCity = (Map) subCityMap.get(secCityName);
                topNavCity = secCityName;
                secondCity = (String) subCity.get("cityid");
                dispCityFullPath = firstCity + "," + secondCity;
                itemClicked();
            }
        } else if (categoryButtonPressed) {
            secondCateName = ((SortModel) adapter2.getItem(position)).getName();
            topNavCate = secondCateName;
            Map thirdCateMap = (Map) ((Map) secCateMap.get(secondCateName)).get("subcate");
            secondCate = (String) ((Map) secCateMap.get(secondCateName)).get("cateid");
            String thirdCateStr = "";
            for (Object key : thirdCateMap.keySet()) {
                thirdCateStr = thirdCateStr + key + ",";
            }

            if (thirdCateStr.equals("")) {
                // 没有三级列别了，只有两级列别，处理数据请求
                thirdCate = "0";
                dispCateFullPath = firstCate + "," + secondCate;
                itemClicked();
            } else {
                if (firstCateName.contains("兼职")) {
                    // 没有三级列别了，只有两级列别，处理数据请求
                    thirdCate = "0";
                    dispCateFullPath = firstCate + "," + secondCate;
                    itemClicked();
                } else {
                    String[] thirdCateArr = thirdCateStr.split(",");
                    // 有三级列别，处理listview的显示
                    SourceDateList = filledData(thirdCateArr, false, false);
                    // 根据a-z进行排序源数据
                    Collections.sort(SourceDateList, pinyinComparator);
                    adapter3 = new SortAdapter(context, SourceDateList);
                    sortListView3.setAdapter(adapter3);
                    listView1.setLayoutParams(threeListViewLp);  //参数依次为width、height、scrollX、scrollY
                    listView2.setLayoutParams(threeListViewLp);  //参数依次为width、height、scrollX、scrollY
                    listView3.setLayoutParams(threeListViewLp);  //参数依次为width、height、scrollX、scrollY
                    listView3.setVisibility(View.VISIBLE);
                }
            }

        }
    }

    /**
     * 处理三级列表点击
     */
    private void thirdListClicked(int position) {
        // 说明有三级类别，处理网络请求
        String thirdCateName = ((SortModel) adapter3.getItem(position)).getName();
        topNavCate = thirdCateName;
        Map thirdCateMap = (Map) ((Map) secCateMap.get(secondCateName)).get("subcate");
        Map thirdItem = (Map) thirdCateMap.get(thirdCateName);
        thirdCate = (String) thirdItem.get("cateid");
        dispCateFullPath = firstCate + "," + secondCate+","+thirdCate;
        itemClicked();
    }

    /**
     * 检测列表数据源是否有效
     */
    private void checkData(String name,Map dataMap ,Map data){
        if (null==name||null==dataMap||null == data) {
            // TODO: 7/19/16 防止空指针
            listView2.setVisibility(View.GONE);
            topnavll.setVisibility(View.GONE);
            cityButtonPressed = false;
            categoryButtonPressed = false;
            Toast.makeText(context, "获取数据出错，请清除缓存后重试", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /**
     * 设置点击列表时回调
     */
    private void itemClicked() {
        cityButtonPressed = false;
        categoryButtonPressed = false;
        topnavll.setVisibility(View.GONE);
        selectListCallBack.selectListClick();
    }

    /**
     * 共享点击列表数据
     */
    public Map getData() {
        Map map = new HashMap();
        map.put("topNavCity", topNavCity);
        map.put("topNavCate", topNavCate);
        map.put("dispCityFullPath", dispCityFullPath);
        map.put("topNavCity", topNavCity);
        return map;
    }
    /**
     * 设置城市显示级别
     */
    public void setListLevel(int level){
        if(level>=MIN_LEVEL&&level<=MAX_LEVEL) {
            this.listLevel = level;
        }
    }

    /**
     * 显示初级城市和类别菜单
     */
    public void showList(boolean isCity) {
        if (isCity) {
            categoryButtonPressed = false ;
            sidebar.setVisibility(View.VISIBLE);
            SourceDateList = filledData(topCityArr, true, false);
        } else {
            cityButtonPressed = false ;
            sidebar.setVisibility(View.GONE);
            SourceDateList = filledData(topCategoryArr, false, false);
        }
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter1 = new SortAdapter(context, SourceDateList);
        sortListView1.setAdapter(adapter1);
        listView1 = (LinearLayout) this.findViewById(R.id.listView1);
        listView2 = (LinearLayout) this.findViewById(R.id.listView2);
        listView3 = (LinearLayout) this.findViewById(R.id.listView3);
        if (isCity) {
            if (!cityButtonPressed) {
                cityButtonPressed = true;
                topnavll.setVisibility(View.VISIBLE);
                listView1.setVisibility(View.VISIBLE);
                listView1.setLayoutParams(twoListViewLp);  //参数依次为width、height、scrollX、scrollY
                listView2.setVisibility(View.GONE);
                listView3.setVisibility(View.GONE);
            } else {
                cityButtonPressed = false;
                listView1.setVisibility(View.GONE);
                topnavll.setVisibility(View.GONE);
            }
        } else {
            if (!categoryButtonPressed) {
                categoryButtonPressed = true;
                topnavll.setVisibility(View.VISIBLE);
                listView1.setVisibility(View.VISIBLE);
                listView1.setLayoutParams(twoListViewLp);  //参数依次为width、height、scrollX、scrollY
                listView2.setVisibility(View.GONE);
                listView3.setVisibility(View.GONE);
            } else {
                categoryButtonPressed = false;
                listView1.setVisibility(View.GONE);
                topnavll.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(String[] date, boolean topCity, boolean containAllItem) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        if (topCity) {
            SortModel shenzhen = new SortModel();
            shenzhen.setName("深圳");
            shenzhen.setSortLetters("热门");
            mSortList.add(shenzhen);

            SortModel guangzhou = new SortModel();
            guangzhou.setName("广州");
            guangzhou.setSortLetters("热门");
            mSortList.add(guangzhou);

            SortModel shanghai = new SortModel();
            shanghai.setName("上海");
            shanghai.setSortLetters("热门");
            mSortList.add(shanghai);

            SortModel beijing = new SortModel();
            beijing.setName("北京");
            beijing.setSortLetters("热门");
            mSortList.add(beijing);
        }

        for (int i = 0; i < date.length; i++) {
            if ("北京".equals(date[i]) || "上海".equals(date[i]) || "广州".equals(date[i]) || "深圳".equals(date[i])) {
                continue;
            }
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);

            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            if (pinyin == null || pinyin.length() == 0) {
                continue;
            }
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }
            mSortList.add(sortModel);
        }
        if (containAllItem) {
            SortModel all = new SortModel();
            all.setName("全部");
            all.setSortLetters("全部");
            mSortList.add(all);
        }
        return mSortList;
    }

}
