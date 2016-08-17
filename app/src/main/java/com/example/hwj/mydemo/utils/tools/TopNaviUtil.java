package com.example.hwj.mydemo.utils.tools;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.example.hwj.mydemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Apple on 16/5/4 15:27.
 */
public class TopNaviUtil {
    private static Context context;
    private static TopNaviUtil INSTANCE;

    private static Map cityItemMap;
    private static String[] topLevelCityArr;
    private static String topLevelCityStr;

    private static Map topCategoryItemMap;
    private static String[] topLevelCategoryArr;
    private static String topLevelCategoryStr;


    public void reset(){
        context = null;
        INSTANCE = null;
        cityItemMap = null;
        topLevelCityArr = null;
        topLevelCityStr = null;
        topCategoryItemMap = null;
        topLevelCategoryArr = null;
        topLevelCategoryStr = null;
    }

    public TopNaviUtil(Context context) {
        this.context = context;
    }

    public synchronized static TopNaviUtil getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TopNaviUtil(context);
        }
        return INSTANCE;
    }

    public synchronized static Map getCityItemMapInstance(int fileName, Context context){
        if (cityItemMap == null || cityItemMap.size() == 0) {
            cityItemMap = getInstance(context).getAllCityItems(fileName);
        }
        return cityItemMap;
    }

    public synchronized static String[] getTopLevelCityInstance(int fileName, Context context){
        if (topLevelCityArr == null) {
            topLevelCityArr = getInstance(context).getTopCityArr(fileName);
        }
        return topLevelCityArr;
    }

    public synchronized static Map getCategoryItemMapInstance(int fileName, Context context){
        if (topCategoryItemMap == null || topCategoryItemMap.size() == 0) {
            topCategoryItemMap = getInstance(context).getAllCategoryItems(fileName);
        }
        return topCategoryItemMap;
    }

    public synchronized static String[] getTopLevelCategoryInstance(int fileName, Context context){
        if (topLevelCategoryArr == null) {
            topLevelCategoryArr = getInstance(context).getTopCategoryArr(fileName);
        }
        return topLevelCategoryArr;
    }

    public String readFile2String(String fileName) {
        FileInputStream fis = null;
        try {
            fis = context.openFileInput("e_"+fileName);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            return new String(buffer);
        } catch (Exception e) {

        } finally {
            // 不关闭，直接把对象设置为null,让服务进程回收（法2）。
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                }
        }
        return "";
    }

    public static String readRawFile(int id)
    {
        Resources resources=context.getResources();
        InputStream is=null;
        try{
            is=resources.openRawResource(id);
            byte buffer[]=new byte[is.available()];
            is.read(buffer);
            return  new String(buffer);
        }
        catch(IOException e)
        {
            return "" ;
        }
        finally
        {
            if(is!=null)
            {
                try{
                    is.close();
                }catch(IOException e)
                {
                    return "" ;
                }
            }
        }
    }

    public JSONObject readFile2Json(int fileName) {
        String fileContent = readRawFile(fileName);
        JSONObject fileJson = null;
        try {
            fileJson = new JSONObject(fileContent);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return fileJson;
    }

    public static JSONObject readString2Json(String content) {
        JSONObject fileJson = null;
        try {
            fileJson = new JSONObject(content);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return fileJson;
    }

    public Map getAllCityItems(int fileName) {
        cityItemMap = new HashMap();
        topLevelCityStr = "";
        JSONObject fileJson = readFile2Json(fileName);
        if (fileJson == null) {
            return null;
        }
        try {
            JSONObject data = fileJson.getJSONObject("data");
            JSONArray list = data.getJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                Map item = new HashMap();
                String cityid = list.getJSONObject(i).getString("cityid");
                item.put("cityid", cityid);
                String cityname = list.getJSONObject(i).getString("cityname");
                item.put("cityname", cityname);
                topLevelCityStr = topLevelCityStr + cityname + ",";
                String depth = list.getJSONObject(i).getString("depth");
                item.put("depth", depth);
                String lat = list.getJSONObject(i).getString("lat");
                item.put("lat", lat);
                String lon = list.getJSONObject(i).getString("lon");
                item.put("lon", lon);

                JSONArray subcity = list.getJSONObject(i).getJSONArray("subcity");
                Map secondCityItemMap = new HashMap();
                for (int j = 0; j <subcity.length(); j++) {
                    Map subItem = new HashMap();
                    String subcityid = subcity.getJSONObject(j).getString("cityid");
                    subItem.put("cityid", subcityid);
                    String subcityname = subcity.getJSONObject(j).getString("cityname");
                    subItem.put("cityname", subcityname);
                    String subdepth = subcity.getJSONObject(j).getString("depth");
                    subItem.put("depth", subdepth);
                    String sublat = subcity.getJSONObject(j).getString("lat");
                    subItem.put("lat", sublat);
                    String sublon = subcity.getJSONObject(j).getString("lon");
                    subItem.put("lon", sublon);
                    secondCityItemMap.put(subcityname, subItem);
                }
                item.put("subcity", secondCityItemMap);

                cityItemMap.put(cityname,item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cityItemMap;
    }

    public String[] getTopCityArr(int fileName){
        getAllCityItems(fileName);
        topLevelCityArr = topLevelCityStr.split(",");
        return topLevelCityArr;
    }

    public Map getAllCategoryItems(int fileName) {
        topCategoryItemMap = new HashMap();
        topLevelCategoryStr = "";
        JSONObject fileJson = readFile2Json(fileName);
        if (fileJson == null) {
            return null;
        }
        try {
            JSONObject data = fileJson.getJSONObject("data");
            JSONArray list = data.getJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                Map item = new HashMap();
                String cateid = list.getJSONObject(i).getString("cateid");
                item.put("cateid", cateid);
                String catename = list.getJSONObject(i).getString("catename");
                item.put("catename", catename);
                topLevelCategoryStr = topLevelCategoryStr + catename + ",";
                String depth = list.getJSONObject(i).getString("depth");
                item.put("depth", depth);

                JSONArray subcate = list.getJSONObject(i).getJSONArray("subcate");
                Map secondCategoryItemMap = new HashMap();
                for (int j = 0; j <subcate.length(); j++) {
                    Map subItem = new HashMap();
                    String subcateid = subcate.getJSONObject(j).getString("cateid");
                    subItem.put("cateid", subcateid);
                    String subcatename = subcate.getJSONObject(j).getString("catename");
                    subItem.put("catename", subcatename);
                    String subdepth = subcate.getJSONObject(j).getString("depth");
                    subItem.put("depth", subdepth);

                    Map thirdCategoryItemMap = new HashMap();
                    JSONArray thirdcate = subcate.getJSONObject(j).getJSONArray("subcate");
                    for (int n = 0; n < thirdcate.length(); n++) {
                        Map thirdItem = new HashMap();
                        String thirdcateid = thirdcate.getJSONObject(n).getString("cateid");
                        thirdItem.put("cateid", thirdcateid);
                        String thirdcatename = thirdcate.getJSONObject(n).getString("catename");
                        thirdItem.put("catename", thirdcatename);
                        String thirddepth = thirdcate.getJSONObject(n).getString("depth");
                        thirdItem.put("depth", thirddepth);
                        thirdCategoryItemMap.put(thirdcatename, thirdItem);
                    }

                    subItem.put("subcate", thirdCategoryItemMap);
                    secondCategoryItemMap.put(subcatename, subItem);

                }
                item.put("subcate", secondCategoryItemMap);

                topCategoryItemMap.put(catename,item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return topCategoryItemMap;
    }

    public String[] getTopCategoryArr(int fileName){
        getAllCategoryItems(fileName);
        topLevelCategoryArr = topLevelCategoryStr.split(",");
        return topLevelCategoryArr;
    }

}